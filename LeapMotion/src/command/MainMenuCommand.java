package command;

import gui.GUIFactory;
import processing.Page;
import processing.core.PApplet;

public final class MainMenuCommand extends AbstractCommand{

	public MainMenuCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		final Page page=getPage();
		page.setTextSizeToDefault();
		executeCommand(new GUIFactory(page).createMainMenuGUI());
	}
}