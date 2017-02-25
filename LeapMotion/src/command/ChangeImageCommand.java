package command;

import button.ImageButton;

public final class ChangeImageCommand extends AbstractCommand{

	private final ImageButton button;

	public ChangeImageCommand(final ImageButton button) {
		super(button.getPApplet());
		this.button=button;
	}

	@Override
	public void process() {
		button.ticked();
	}
}