package command;

import gui.GUIFactory;
import processing.core.PApplet;

public class IntroductionCommand extends AbstractCommand{

	public IntroductionCommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createIntroductionGUI());
	}
}
