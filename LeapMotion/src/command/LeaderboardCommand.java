package command;

import gui.GUIFactory;
import processing.core.PApplet;

public final class LeaderboardCommand extends AbstractCommand{

	public LeaderboardCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createLeaderboardGUI());
	}
}