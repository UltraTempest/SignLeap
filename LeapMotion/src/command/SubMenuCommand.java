package command;

import gui.SubMenuGUI;
import processing.core.PApplet;

public final class SubMenuCommand extends AbstractCommand{
	
	private final ICommand command;
	
	public SubMenuCommand(final PApplet page, final ICommand command) {
		super(page);
		this.command=command;
	}

	@Override
	public void process() {
		executeCommand(new SubMenuGUI(getPage(),command));
	}
}