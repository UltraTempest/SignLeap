package command;

import processing.core.PApplet;

public final class GameSubMenuCommand extends AbstractCommand{

	private final ICommand command;

	public GameSubMenuCommand(final PApplet page, final ICommand command) {
		super(page);
		this.command=command;
	}

	@Override
	public void process() {
		getGUIManager().setGameSubMenuGUI(command);
	}
}