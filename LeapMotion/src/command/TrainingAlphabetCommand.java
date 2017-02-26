package command;

import gui.AlphabetTrainingGUI;
import processing.core.PApplet;

public final class TrainingAlphabetCommand extends AbstractTrainingCommand{
	public TrainingAlphabetCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new AlphabetTrainingGUI(getPage(), getSigns()));
	}
}
