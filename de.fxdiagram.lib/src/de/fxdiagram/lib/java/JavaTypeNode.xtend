package de.fxdiagram.lib.java

import de.fxdiagram.core.XNode
import de.fxdiagram.core.XRapidButton
import de.fxdiagram.core.behavior.AbstractBehavior
import de.fxdiagram.core.tools.chooser.CarusselChooser
import de.fxdiagram.core.tools.chooser.CoverFlowChooser
import de.fxdiagram.lib.nodes.RectangleBorderPane
import java.lang.reflect.Type
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.control.Separator
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text

import static java.lang.reflect.Modifier.*

import static extension de.fxdiagram.core.Extensions.*

class JavaTypeNode extends XNode {

	Class<?> javaType
	
	Text name
	VBox attributeCompartment
	VBox operationCompartment
	
	new() {
		node = new RectangleBorderPane => [
			children += new VBox => [
				children += name = new Text => [
					textOrigin = VPos.TOP
					font = Font.font(getFont.family, FontWeight.BOLD, getFont.size*1.5)
					VBox.setMargin(it, new Insets(20, 20, 20, 15))
				]
				children += new Separator 
				children += attributeCompartment = new VBox => [
					VBox.setMargin(it, new Insets(10, 10, 10, 10))
				]
				children += new Separator
				children += operationCompartment = new VBox => [
					VBox.setMargin(it, new Insets(10, 10, 10, 10))
				]
				alignment = Pos.CENTER
				spacing = 5
			]
		]
	}

	def setJavaType(Class<?> javaType) {
		this.javaType = javaType
		name.text = javaType.simpleName
		javaType.declaredFields.filter[isPublic(it.modifiers) && (type.primitive || String.equals(type))].forEach [ 
			attribute |
			attributeCompartment.children += new Text => [
				text = attribute.name + ': ' + attribute.type.simpleName
				textOrigin = VPos.TOP
			]				
		]
		javaType.declaredConstructors.filter[isPublic(it.modifiers)].forEach [
			constructor |
			operationCompartment.children += new Text => [
				text = '''«javaType.simpleName»(«constructor.parameterTypes.map[simpleName].join(', ')»)''' 
			]
		]
		javaType.declaredMethods.filter[isPublic(it.modifiers)].forEach [
			method |
			operationCompartment.children += new Text => [
				text = '''«method.name»(«method.parameterTypes.map[simpleName].join(', ')»): «method.returnType.simpleName»''' 
			]
		]
	}
	
	def getJavaType() {
		javaType
	}
	
	override activate() {
		if(javaType != null) {
			super.activate()
			new JavaTypeRapidButtonBehavior(this).activate	
		}
	}
	
}

class JavaTypeRapidButtonBehavior extends AbstractBehavior {
	
	new(JavaTypeNode host) {
		super(host)
	}
	
	override protected doActivate() {
		val host = this.getHost as XNode
		val addSuperTypeAction = [
			XRapidButton button |
			val chooser = new CoverFlowChooser(host, button.getChooserPosition)
			val javaType = (host as JavaTypeNode).getJavaType
			val supertypes = <Class<?>>newArrayList
			if(javaType.superclass != null)
				supertypes += javaType.superclass
			supertypes += javaType.interfaces
			chooser += supertypes.map[
				superType | new JavaTypeNode => [ it.javaType = superType ]
			] 
			host.getRootDiagram.currentTool = chooser
		]
		val addReferencesAction = [
			XRapidButton button |
			val chooser = new CarusselChooser(host, button.getChooserPosition)
			val javaType = (host as JavaTypeNode).getJavaType
			val references = javaType.declaredFields.filter[isPublic(it.modifiers) && !Type.primitive && !String.equals(Type)]
			chooser += references.map[
				reference | new JavaTypeNode => [ it.javaType = reference.type ]
			] 
			host.getRootDiagram.currentTool = chooser
		]
		val buttons = #[
			new XRapidButton(host, 0.5, 0, 'icons/add_16.png', addSuperTypeAction),
			new XRapidButton(host, 0.5, 1, 'icons/add_16.png', addSuperTypeAction),
			new XRapidButton(host, 0, 0.5, 'icons/add_16.png', addReferencesAction),
			new XRapidButton(host, 1, 0.5, 'icons/add_16.png', addReferencesAction)
		]
		buttons.forEach[ host.getDiagram.addButton(it) ]
	}
	
	
}