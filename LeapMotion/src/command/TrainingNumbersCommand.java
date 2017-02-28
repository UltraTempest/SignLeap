package command;

import gui.AbstractSelectCharactersGUI;
import gui.NumberTrainingGUI;

public final class TrainingNumbersCommand extends AbstractTrainingCommand{
	
	public TrainingNumbersCommand(final AbstractSelectCharactersGUI gui) {
		super(gui);
	}

	@Override
	public void process() {
		executeCommand(new NumberTrainingGUI(getPage(), getGUI().getSigns()));
	}
}
