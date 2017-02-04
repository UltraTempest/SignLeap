package command;

import gui.GUIFactory;
import processing.core.PApplet;

public final class AlphabetGUICommand extends AbstractCommand{

	public AlphabetGUICommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createSignAlphabetGUI());
	}
   
}
