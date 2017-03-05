package command;

import gui.AbstractSelectCharactersGUI;

public final class TrainingAlphabetCommand extends AbstractTrainingCommand{
	
	public TrainingAlphabetCommand(final AbstractSelectCharactersGUI gui) {
		super(gui);
	}

	@Override
	public void process() {
		getGUIManager().setTrainingAlphabetGUI(getSigns());
	}
}
