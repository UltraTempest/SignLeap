package command;

import gui.IntroductionGUI;
import processing.core.PApplet;

public class IntroductionInfoCommand extends AbstractCommand{

	private final IntroductionGUI gui;

	public IntroductionInfoCommand(PApplet page,IntroductionGUI gui) {
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
