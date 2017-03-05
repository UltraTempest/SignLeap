package command;

import processing.core.PApplet;

public final class SignNumberCommand extends AbstractCommand{

	public SignNumberCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getGUIManager().setSignNumbersGUI();
	}
}
