package command;

import gui.AbstractSelectCharactersGUI;

public final class TrainingNumbersCommand extends AbstractTrainingCommand{
	
	public TrainingNumbersCommand(final AbstractSelectCharactersGUI gui) {
		super(gui);
	}

	@Override
	public void process() {
		getGUIManager().setTrainingNumbersGUI(getSigns());
	}
}
