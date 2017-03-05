package command;

import processing.core.PApplet;

public final class SignAlphabetCommand extends AbstractCommand{

	public SignAlphabetCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getGUIManager().setSignAlphabetGUI();
	}
   
}
