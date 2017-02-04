package command;

import gui.IntroductionGUI;
import processing.core.PApplet;

public final class IntroductionInfoCommand extends AbstractCommand{

	private final IntroductionGUI gui;

	public IntroductionInfoCommand(final PApplet page,
			final IntroductionGUI gui) {
		super(page);
		this.gui=gui;
	}

	@Override
	public void process() {
		if(gui.isLastTextDisplayed())
			new MainMenuCommand(getPage()).process();
		else
			gui.changeTextDisplayed();	
	}
}
