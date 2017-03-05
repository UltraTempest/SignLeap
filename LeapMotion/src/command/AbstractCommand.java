package command;

import gui.GUIManager;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractCommand implements ICommand{
	private static PApplet page;

	public AbstractCommand(final PApplet page){
		AbstractCommand.page=page;
	}

	protected final Page getPage(){
		return (Page) page;
	}

	protected final GUIManager getGUIManager(){
		return getPage().getGUIManager();
	}
}