package command;

import gui.GameOverGUI;
import leaderboard.HighScoreManager;
import processing.core.PApplet;

public final class LeaderboardCommand extends AbstractCommand{
	private final GameOverGUI gui;
	private final HighScoreManager scoreManager;

	public LeaderboardCommand(final PApplet page,final GameOverGUI gui) {
		super(page);
		this.gui=gui;
		scoreManager=null;
	}

	public LeaderboardCommand(final PApplet page, final HighScoreManager scoreManager) {
		super(page);
		gui=null;
		this.scoreManager=scoreManager;
	}

	@Override
	public void process() {
		if(gui!=null)
			gui.submitButtonClicked();
		else
			getGUIManager().setLeaderboardGUI(scoreManager);
	}
}