package command;

import processing.core.PApplet;

public final class IntroductionCommand extends AbstractCommand{
	
	private final int index;
	
	public IntroductionCommand(final PApplet page, final int index) {
		super(page);
		this.index=index;
	}

	@Override
	public void process() {
		getGUIManager().setIntroductionGUI(index);
	}
}
