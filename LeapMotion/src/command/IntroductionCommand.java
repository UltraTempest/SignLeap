package command;

import gui.GUIFactory;
import processing.core.PApplet;

public final class IntroductionCommand extends AbstractCommand{

	public IntroductionCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createIntroductionGUI());
	}
}
