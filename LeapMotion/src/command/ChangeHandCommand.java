package command;

import gui.AbstractMenuGUI;
import processing.core.PApplet;

public final class ChangeHandCommand extends AbstractCommand{
	
	private final AbstractMenuGUI gui;
	
	public ChangeHandCommand(final PApplet page, final AbstractMenuGUI gui) {
		super(page);
		this.gui=gui;
	}

	@Override
	public void process() {
		gui.handChange(null, null);
	}
}
