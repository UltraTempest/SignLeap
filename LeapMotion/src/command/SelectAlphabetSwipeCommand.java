package command;

import gui.SelectAlphabetGUI;

public final class SelectAlphabetSwipeCommand extends AbstractCommand{
	
	private final SelectAlphabetGUI gui;
	private final boolean right;
	
	public SelectAlphabetSwipeCommand(final SelectAlphabetGUI gui, final boolean right) {
		super(gui.getPApplet());
		this.gui=gui;
		this.right=right;
	}

	@Override
	public void process() {
		gui.swipe(right);
	}
}