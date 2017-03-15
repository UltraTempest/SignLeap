package command;

import processing.Page;
import processing.core.PApplet;
import recording.AbstractHandData.Handedness;

public final class ChangeHandCommand extends AbstractCommand{

	public ChangeHandCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		final Page page=getPage();
		if(page.getHand().equals(Handedness.RIGHT))
			page.setHand(Handedness.LEFT);
		else
			page.setHand(Handedness.RIGHT);
	}
}
