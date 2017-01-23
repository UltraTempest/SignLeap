package command;

import gui.GUIFactory;
import processing.core.PApplet;

public class AlphabetGUICommand extends AbstractCommand{

	public AlphabetGUICommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createSignAlphabetGUI());
	}
   
}
