package command;

import processing.core.PApplet;

public final class GameOverCommand extends AbstractCommand{

	private final int score;
	private final boolean leaderboardFlag;

	public GameOverCommand(final PApplet page,final int score,final boolean leaderboardFlag) {
		super(page);
		this.score=score;
		this.leaderboardFlag=leaderboardFlag;
	}

	@Override
	public void process() {
		getGUIManager().setGameOverGUI(score,leaderboardFlag);
	}
}
