package command;

import gui.NumberTrainingGUI;
import processing.core.PApplet;

public final class TrainingNumbersCommand extends AbstractTrainingCommand{
	public TrainingNumbersCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new NumberTrainingGUI(getPage(),getSigns()));
	}
}
