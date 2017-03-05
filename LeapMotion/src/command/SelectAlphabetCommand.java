package command;

import processing.core.PApplet;

public final class SelectAlphabetCommand extends AbstractCommand{
	
	public SelectAlphabetCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getGUIManager().setSelectAlphabetGUI();
	}
}