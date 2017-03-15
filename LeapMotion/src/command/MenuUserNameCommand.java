package command;

import gui.ITextChanger;
import processing.core.PApplet;

public class MenuUserNameCommand extends AbstractTextCommand{

	public MenuUserNameCommand(final PApplet page,final ITextChanger changer) {
		super(page, changer);
	}

	@Override
	public void process() {
		getChanger().changeTextDisplayed();
	}
}
