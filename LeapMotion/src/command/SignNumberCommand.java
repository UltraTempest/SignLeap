package command;

import gui.GUIFactory;
import processing.core.PApplet;

public final class SignNumberCommand extends AbstractCommand{

	public SignNumberCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createSignNumbersGUI());
	}
   
}
