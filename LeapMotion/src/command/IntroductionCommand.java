package command;

import processing.GUI.GUIFactory;
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
