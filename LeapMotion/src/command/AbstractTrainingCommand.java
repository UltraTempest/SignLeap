package command;

import gui.AbstractSelectCharactersGUI;

public abstract class AbstractTrainingCommand extends AbstractCommand{
	
	private final AbstractSelectCharactersGUI gui;
	
	public AbstractTrainingCommand(final AbstractSelectCharactersGUI gui) {
		super(gui.getPApplet());
		this.gui=gui;
	}
	
	protected AbstractSelectCharactersGUI getGUI(){
		return gui;
	}
}
