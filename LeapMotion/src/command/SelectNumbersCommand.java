package command;

import processing.core.PApplet;

public final class SelectNumbersCommand extends AbstractCommand{
	
	public SelectNumbersCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getGUIManager().setSelectNumberGUI();
	}
}