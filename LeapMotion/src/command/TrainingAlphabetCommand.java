package command;

import gui.AbstractSelectCharactersGUI;
import gui.AlphabetTrainingGUI;

public final class TrainingAlphabetCommand extends AbstractTrainingCommand{
	
	public TrainingAlphabetCommand(final AbstractSelectCharactersGUI gui) {
		super(gui);
	}

	@Override
	public void process() {
		executeCommand(new AlphabetTrainingGUI(getPage(), getGUI().getSigns()));
	}
}
