package command;

import gui.GUIFactory;
import processing.core.PApplet;

public final class SignAlphabetCommand extends AbstractCommand{

	public SignAlphabetCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createSignAlphabetGUI());
	}
   
}
