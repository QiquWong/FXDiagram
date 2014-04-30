package de.fxdiagram.xtext.glue

import de.fxdiagram.core.XDiagram
import de.fxdiagram.core.XRoot
import de.fxdiagram.core.layout.LayoutType
import de.fxdiagram.core.services.ClassLoaderProvider
import de.fxdiagram.core.tools.actions.CenterAction
import de.fxdiagram.core.tools.actions.DeleteAction
import de.fxdiagram.core.tools.actions.ExportSvgAction
import de.fxdiagram.core.tools.actions.FullScreenAction
import de.fxdiagram.core.tools.actions.LayoutAction
import de.fxdiagram.core.tools.actions.LoadAction
import de.fxdiagram.core.tools.actions.NavigateNextAction
import de.fxdiagram.core.tools.actions.NavigatePreviousAction
import de.fxdiagram.core.tools.actions.RedoAction
import de.fxdiagram.core.tools.actions.SaveAction
import de.fxdiagram.core.tools.actions.SelectAllAction
import de.fxdiagram.core.tools.actions.UndoAction
import de.fxdiagram.core.tools.actions.ZoomToFitAction
import de.fxdiagram.lib.actions.UndoRedoPlayerAction
import de.fxdiagram.swtfx.SwtToFXGestureConverter
import de.fxdiagram.xtext.glue.mapping.AbstractMapping
import de.fxdiagram.xtext.glue.mapping.DiagramMapping
import de.fxdiagram.xtext.glue.mapping.InterpreterContext
import de.fxdiagram.xtext.glue.mapping.NodeMapping
import de.fxdiagram.xtext.glue.mapping.XDiagramConfigInterpreter
import java.util.Set
import javafx.embed.swt.FXCanvas
import javafx.scene.PerspectiveCamera
import javafx.scene.Scene
import org.eclipse.emf.ecore.EObject
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IPartListener2
import org.eclipse.ui.IWorkbenchPartReference
import org.eclipse.ui.part.ViewPart
import org.eclipse.xtext.ui.editor.XtextEditor
import de.fxdiagram.core.layout.Layouter
import static extension de.fxdiagram.core.extensions.DurationExtensions.*
import de.fxdiagram.core.command.SelectAndRevealCommand
import de.fxdiagram.core.XNode
import de.fxdiagram.core.XConnection
import de.fxdiagram.core.command.SequentialAnimationCommand

class FXDiagramView extends ViewPart {

	FXCanvas canvas
	
	XRoot root
	
	SwtToFXGestureConverter gestureConverter
	
	Set<XtextEditor> contributingEditors = newHashSet
	Set<XtextEditor> changedEditors = newHashSet
	
	IPartListener2 listener 
	
	val domainObjectProvider = new XtextDomainObjectProvider
	val configInterpreter = new XDiagramConfigInterpreter(domainObjectProvider)
	
	override createPartControl(Composite parent) {
		canvas = new FXCanvas(parent, SWT.NONE)
		gestureConverter = new SwtToFXGestureConverter(canvas)
		canvas.scene = createFxScene
	}
	
	protected def Scene createFxScene() {
		new Scene(
			root = new XRoot => [
			 	rootDiagram = new XDiagram()
			 	getDomainObjectProviders += #[
			 		new ClassLoaderProvider,
			 		domainObjectProvider
			 	]
				getDiagramActionRegistry += #[
					new CenterAction,
					new DeleteAction,
					new LayoutAction(LayoutType.DOT),
					new ExportSvgAction,
					new UndoAction,
					new RedoAction,
					new LoadAction,
					new SaveAction,
					new SelectAllAction,
					new ZoomToFitAction,
					new NavigatePreviousAction,
					new NavigateNextAction,
					new FullScreenAction,
					new UndoRedoPlayerAction ]
			] 
		) => [
			camera = new PerspectiveCamera
			root.activate
		]
	}
	
	override setFocus() {
		canvas.setFocus
	}
	
	def clear() {
		contributingEditors.clear
		changedEditors.clear
		root.diagram = new XDiagram
	}
	
	def <T extends EObject> void revealElement(T element, AbstractMapping<T> mapping, XtextEditor editor) {
		val interpreterContext = new InterpreterContext
		if(mapping instanceof DiagramMapping<?>) {
			editor.register
			if(changedEditors.remove(editor)) {
				root.diagram = configInterpreter.createDiagram(element, mapping as DiagramMapping<T>, interpreterContext)
			} 
		} else if(mapping instanceof NodeMapping<?>) {
			editor.register
			interpreterContext.diagram = root.diagram
			configInterpreter.createNode(element, mapping as NodeMapping<T>, interpreterContext)		
		}
//		val command = new SequentialAnimationCommand
//		command += interpreterContext.command
//		if(interpreterContext.needsLayout)
//			command += new Layouter().createLayoutCommand(LayoutType.DOT, root.diagram, 200.millis)
//		val descriptor = domainObjectProvider.createDescriptor(element, mapping)
//		command += new SelectAndRevealCommand(root, [
//			switch it {
//				XNode: domainObject == descriptor
//				XConnection: domainObject == descriptor
//				default: false
//			}
//		])
//		root.commandStack.execute(command)
		root.commandStack.execute(interpreterContext.command)
		if(interpreterContext.needsLayout)
			root.commandStack.execute(new Layouter().createLayoutCommand(LayoutType.DOT, root.diagram, 200.millis))
		val descriptor = domainObjectProvider.createDescriptor(element, mapping)
		root.commandStack.execute(new SelectAndRevealCommand(root, [
			switch it {
				XNode: domainObject == descriptor
				XConnection: domainObject == descriptor
				default: false
			}
		]))
	}
	
	def void register(XtextEditor editor) {
		if(contributingEditors.add(editor)) {
			changedEditors += editor
			editor.document.addModelListener [
				changedEditors += editor
			]
		}
		if(listener == null) {
			listener = new EditorListener(this)
			editor.site.page.addPartListener(listener)
		}
	}
	
	def deregister(IWorkbenchPartReference reference) {
		val part = reference.getPart(false)
		if(part != null) {
			changedEditors.remove(part)
			contributingEditors.remove(part)
		}
	}
	
	override dispose() {
		gestureConverter.dispose
		super.dispose()
	}
}

class EditorListener implements IPartListener2 {
	
	FXDiagramView view
	
	new(FXDiagramView view) {
		this.view = view
	}
	
	override partActivated(IWorkbenchPartReference partRef) {
	}
	
	override partBroughtToTop(IWorkbenchPartReference partRef) {
	}
	
	override partClosed(IWorkbenchPartReference partRef) {
		view.deregister(partRef)
	}
	
	override partDeactivated(IWorkbenchPartReference partRef) {
	}
	
	override partHidden(IWorkbenchPartReference partRef) {
	}
	
	override partInputChanged(IWorkbenchPartReference partRef) {
	}
	
	override partOpened(IWorkbenchPartReference partRef) {
	}
	
	override partVisible(IWorkbenchPartReference partRef) {
	}
	
}