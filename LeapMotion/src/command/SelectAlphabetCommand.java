package command;

import gui.SelectAlphabetGUI;
import processing.core.PApplet;

public final class SelectAlphabetCommand extends AbstractCommand{
	
	public SelectAlphabetCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new SelectAlphabetGUI(getPage()));
	}
}