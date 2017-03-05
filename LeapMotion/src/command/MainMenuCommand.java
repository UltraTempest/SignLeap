package command;

import processing.core.PApplet;

public final class MainMenuCommand extends AbstractCommand{

	public MainMenuCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getPage().setTextSizeToDefault();
		getGUIManager().setMainMenuGUI();
	}
}