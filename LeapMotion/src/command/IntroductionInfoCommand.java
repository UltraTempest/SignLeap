package command;

import gui.ITextChanger;
import processing.core.PApplet;

public final class IntroductionInfoCommand extends AbstractTextCommand{

	public IntroductionInfoCommand(final PApplet page,final ITextChanger gui) {
		super(page,gui);
	}

	@Override
	public void process() {
		if(getChanger().isLastTextDisplayed())
			new MainMenuCommand(getPage()).process();
		else
			getChanger().changeTextDisplayed();	
	}
}
