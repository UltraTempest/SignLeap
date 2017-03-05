package command;

import gui.ITextChanger;
import processing.core.PApplet;

public abstract class AbstractTextCommand extends AbstractCommand{
	
	private final ITextChanger changer;
	
	public AbstractTextCommand(final PApplet page,final ITextChanger changer) {
		super(page);
		this.changer=changer;
	}
	
	protected final ITextChanger getChanger(){
		return changer;
	}
}
