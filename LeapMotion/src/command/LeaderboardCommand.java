package command;

import gui.GameOverGUI;
import processing.core.PApplet;

public final class LeaderboardCommand extends AbstractCommand{
	private final GameOverGUI gui;

	public LeaderboardCommand(final PApplet page,final GameOverGUI gui) {
		super(page);
		this.gui=gui;
	}

	@Override
	public void process() {
		gui.submitButtonClicked();
	}
}