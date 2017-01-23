package command;

import gui.GUIFactory;
import processing.Page;
import processing.core.PApplet;

public class MainMenuCommand extends AbstractCommand{

	public MainMenuCommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		Page page=getPage();
		page.setTextSizeToDefault();
		executeCommand(new GUIFactory(page).createMainMenuGUI());
	}
}