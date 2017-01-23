package command;

import gui.GUIFactory;
import processing.core.PApplet;

public class NumberGUICommand extends AbstractCommand{

	public NumberGUICommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createSignNumbersGUI());
	}
   
}
