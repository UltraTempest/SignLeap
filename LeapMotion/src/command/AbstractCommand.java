package command;

import processing.Page;
import processing.GUI.IGUI;
import processing.core.PApplet;

public abstract class AbstractCommand implements ICommand{
	private static PApplet page;
	
	public AbstractCommand(PApplet page){
	AbstractCommand.page=page;
	}
	
	protected Page getPage(){
		return (Page) AbstractCommand.page;
	}
	
	protected void executeCommand(IGUI gui){
		getPage().stateSwitch(gui);
	}
}