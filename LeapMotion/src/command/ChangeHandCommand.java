package command;

import processing.Page;
import processing.core.PApplet;
import recording.HandData.Handedness;

public class ChangeHandCommand extends AbstractCommand{

	public ChangeHandCommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		Page page=getPage();
		if(page.getHand().equals(Handedness.RIGHT))
			page.setHand(Handedness.LEFT);
		else
			page.setHand(Handedness.RIGHT);
	}
}
