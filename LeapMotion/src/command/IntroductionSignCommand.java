package command;

import processing.core.PApplet;

public final class IntroductionSignCommand extends AbstractCommand{
	
	public IntroductionSignCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getGUIManager().setIntroductionSignCharacterGUI();
	}
}