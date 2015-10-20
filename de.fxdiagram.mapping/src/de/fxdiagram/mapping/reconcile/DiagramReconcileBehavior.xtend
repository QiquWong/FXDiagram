package de.fxdiagram.mapping.reconcile

import de.fxdiagram.core.XDiagram
import de.fxdiagram.core.XNode
import de.fxdiagram.core.behavior.AbstractReconcileBehavior
import de.fxdiagram.core.tools.actions.ReconcileAction
import de.fxdiagram.mapping.DiagramMapping
import de.fxdiagram.mapping.IMappedElementDescriptor
import de.fxdiagram.mapping.execution.InterpreterContext
import de.fxdiagram.mapping.execution.XDiagramConfigInterpreter
import de.fxdiagram.mapping.shapes.BaseDiagram
import eu.hansolo.enzo.radialmenu.SymbolCanvas
import eu.hansolo.enzo.radialmenu.SymbolType
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.paint.Color

import static de.fxdiagram.core.behavior.DirtyState.*

import static extension de.fxdiagram.core.extensions.CoreExtensions.*
import static extension de.fxdiagram.core.extensions.TooltipExtensions.*

class DiagramReconcileBehavior<T> extends AbstractReconcileBehavior<XDiagram> {

	val interpreter = new XDiagramConfigInterpreter
	
	Node repairButton

	new(BaseDiagram<T> host) {
		super(host)
	}

	override getDirtyState() {
		if (host.domainObjectDescriptor instanceof IMappedElementDescriptor<?>) {
			val descriptor = host.domainObjectDescriptor as IMappedElementDescriptor<T>
			descriptor.withDomainObject [
				val dummyDiagram = new BaseDiagram(descriptor)
				val context = new InterpreterContext(dummyDiagram)
				interpreter.createDiagram(it, descriptor.mapping as DiagramMapping<T>, false, context)
				val descriptors = (host.nodes + host.connections).map[domainObjectDescriptor].toSet
				for (recreatedShape : dummyDiagram.nodes + dummyDiagram.connections) {
					if (!descriptors.remove(recreatedShape.domainObjectDescriptor))
						return DIRTY
				}
				if (descriptors.empty)
					return CLEAN
				else
					return DIRTY
			]
		}
	}

	override reconcile(UpdateAcceptor acceptor) {
		if (host.domainObjectDescriptor instanceof IMappedElementDescriptor<?>) {
			val descriptor = host.domainObjectDescriptor as IMappedElementDescriptor<T>
			descriptor.withDomainObject [
				// add new shapes first
				val currentContext = new InterpreterContext(host)
				interpreter.createDiagram(it, descriptor.mapping as DiagramMapping<T>, false, currentContext)
				for(addedShape: currentContext.addedShapes) 
					acceptor.add(addedShape)
				
				// create dummy diagram from scratch and remove old shapes from host
				val dummyDiagram = new BaseDiagram(descriptor)
				val context = new InterpreterContext(dummyDiagram)
				interpreter.createDiagram(it, descriptor.mapping as DiagramMapping<T>, false, context)
				val descriptors = (host.nodes + host.connections).toMap[domainObjectDescriptor]
				(dummyDiagram.nodes + dummyDiagram.connections).forEach[
					descriptors.remove(domainObjectDescriptor)
				]
				descriptors.values.forEach [
					acceptor.delete(it)
					if(it instanceof XNode) 
						(outgoingConnections + incomingConnections).forEach[acceptor.delete(it)]
				]
				null
			]
		}
	}

	override protected dirtyFeedback(boolean isDirty) {
		if(isDirty)
			host.root.headsUpDisplay.add(repairButton, Pos.TOP_RIGHT)
		else 
			host.root.headsUpDisplay.children -= repairButton
	}
	
	override protected doActivate() {
		repairButton = SymbolCanvas.getSymbol(SymbolType.TOOL, 32, Color.GRAY) => [
			onMouseClicked = [
				new ReconcileAction().perform(host.root)
				host.root.headsUpDisplay.children -= repairButton
			]
			tooltip = "Repair diagram"
		]
		showDirtyState(dirtyState)
	}
}