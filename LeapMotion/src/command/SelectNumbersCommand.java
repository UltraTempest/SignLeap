package command;

import gui.SelectNumbersGUI;
import processing.core.PApplet;

public final class SelectNumbersCommand extends AbstractCommand{

	public SelectNumbersCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new SelectNumbersGUI(getPage()));
	}
}