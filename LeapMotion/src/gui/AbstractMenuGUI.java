package gui;

import java.awt.Font;

import button.Button;
import command.ChangeHandCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractMenuGUI extends AbstractGeneralGUI implements IGUIListener{
	private final Button changeHandButton;
	private final String changeHandText="Your preferred hand is %s. Change?";

	public AbstractMenuGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		changeHandButton = new Button(page,340, 516, 320, 113,new ChangeHandCommand(page));
		changeHandButton.setText(String.format(changeHandText, page.getHand()));
		changeHandButton.setTextBold();
		changeHandButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		changeHandButton.setFont(new Font("Monospaced", Font.PLAIN, 27));
		page.setGUIListener(this);
	} 

	@Override
	public void dispose() {
		objectDisposal(changeHandButton);
	}

	@Override
	public void actionPerformed(){
		handChange();
	}

	private void handChange(){
		changeHandButton.setText(String.format(changeHandText, getPage().getHand()));
		changeHandButton.setTextBold();
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(changeHandButton);
	}
}
