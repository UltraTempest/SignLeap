package command;

import button.ImageButton;
import processing.core.PApplet;

public final class ChangeImageCommand extends AbstractCommand{

	private final ImageButton button;

	public ChangeImageCommand(final PApplet page, final ImageButton button) {
		super(page);
		this.button=button;
	}

	@Override
	public void process() {
		button.ticked();
	}
}