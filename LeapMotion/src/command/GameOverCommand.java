package command;

import processing.GUI.GUIFactory;
import processing.core.PApplet;

public class GameOverCommand extends AbstractCommand{
	
	private final int score;
	
	public GameOverCommand(PApplet page,final int score) {
		super(page);
		this.score=score;
	}

	@Override
	public void process() {
		executeCommand(new GUIFactory(getPage()).createGameOverGUI(score));
	}
}
