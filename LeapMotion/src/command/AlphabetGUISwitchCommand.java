package command;

import processing.core.PApplet;

public class AlphabetGUISwitchCommand extends AbstractCommand{

	public AlphabetGUISwitchCommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getPage().switchToSignAlphabetGUI();
	}
   
}
