package command;

import gui.GUIFactory;
import gui.IGUI;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractCommand implements ICommand{
	private static PApplet page;
	private final GUIFactory guiFact;

	public AbstractCommand(final PApplet page){
		AbstractCommand.page=page;
		guiFact=new GUIFactory(page);
	}

	protected final Page getPage(){
		return (Page) page;
	}

	protected final GUIFactory getGUIFactory(){
		return guiFact;
	}

	protected final void executeCommand(final IGUI gui){
		getPage().stateSwitch(gui);
	}
}