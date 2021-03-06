package de.fxdiagram.lib.simple

import de.fxdiagram.core.XConnection
import de.fxdiagram.core.XNode
import de.fxdiagram.core.command.AddRemoveCommand
import de.fxdiagram.lib.buttons.RapidButton
import de.fxdiagram.lib.buttons.RapidButtonAction
import de.fxdiagram.lib.buttons.RapidButtonBehavior
import de.fxdiagram.lib.chooser.CarusselChoice
import de.fxdiagram.lib.chooser.ConnectedNodeChooser
import de.fxdiagram.lib.chooser.CoverFlowChoice
import de.fxdiagram.lib.chooser.CubeChoice

import static de.fxdiagram.core.extensions.ButtonExtensions.*
import static javafx.geometry.Side.*

import static extension de.fxdiagram.core.extensions.CoreExtensions.*

/** 
 * Examplary rapid button behavior. Should be moved to examples.
 */
class AddRapidButtonBehavior<T extends XNode> extends RapidButtonBehavior<T> {

	(ConnectedNodeChooser)=>void choiceInitializer

	new(T host) {
		super(host)
	}
	
	override getBehaviorKey() {
		AddRapidButtonBehavior
	}
	
	def setChoiceInitializer((ConnectedNodeChooser)=>void choiceInitializer) {
		this.choiceInitializer = choiceInitializer
	}

	override doActivate() {
		val host = this.getHost as XNode
		val RapidButtonAction addAction = [ RapidButton button |
			val target = new SimpleNode("New Node")
			val source = button.getHost
			val connection = new XConnection(source, target)
			target.layoutX = source.layoutX
			target.layoutY = source.layoutY
			switch button.position {
				case TOP: target.layoutY = target.layoutY - 150  
				case BOTTOM: target.layoutY = target.layoutY + 150 
				case LEFT: target.layoutX = target.layoutX - 200
				case RIGHT: target.layoutX = target.layoutX +200
			}
			host.root.commandStack.execute(AddRemoveCommand.newAddCommand(host.diagram, target, connection))
		]
		val RapidButtonAction chooseAction = [ RapidButton button |
			val chooser = new ConnectedNodeChooser(host, button.position, new CarusselChoice)
			chooser.addChoices
			host.root.currentTool = chooser
		]
		val RapidButtonAction cubeChooseAction = [ RapidButton button |
			val chooser = new ConnectedNodeChooser(host, button.position, new CubeChoice)
			chooser.addChoices
			host.root.currentTool = chooser
		]
		val RapidButtonAction coverFlowChooseAction = [ RapidButton button |
			val chooser = new ConnectedNodeChooser(host, button.position, new CoverFlowChoice)
			chooser.addChoices
			host.root.currentTool = chooser
		]
		add(new RapidButton(host, TOP, getFilledTriangle(TOP, 'Add node'), cubeChooseAction))
		add(new RapidButton(host, BOTTOM, getFilledTriangle(BOTTOM, 'Add node'), coverFlowChooseAction))
		add(new RapidButton(host, LEFT, getFilledTriangle(LEFT, 'Add node'), chooseAction))
		add(new RapidButton(host, RIGHT, getFilledTriangle(RIGHT, 'Add node'), addAction))
		super.doActivate
	}

	protected def addChoices(ConnectedNodeChooser chooser) {
		choiceInitializer.apply(chooser)
	}
}
