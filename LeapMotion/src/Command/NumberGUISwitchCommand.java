package Command;

import processing.core.PApplet;

public class NumberGUISwitchCommand extends AbstractCommand{

	public NumberGUISwitchCommand(PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		getPage().switchToSignNumbersGUI();
	}
   
}
