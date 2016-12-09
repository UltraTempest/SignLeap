package command;

import processing.GUI.GUIFactory;
import processing.core.PApplet;

public class LeaderboardCommand extends AbstractCommand{

	public LeaderboardCommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createLeaderboardGUI());
	}
}