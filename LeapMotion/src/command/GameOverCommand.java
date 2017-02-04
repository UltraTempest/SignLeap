package command;

import gui.GUIFactory;
import processing.core.PApplet;

public final class GameOverCommand extends AbstractCommand{
	
	private final int score;
	
	public GameOverCommand(final PApplet page,final int score) {
		super(page);
		this.score=score;
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createGameOverGUI(score));
	}
}
