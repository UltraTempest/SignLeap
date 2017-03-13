package gui;

import java.awt.Font;

import button.Button;
import command.ChangeHandCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;
import recording.AbstractHandData.Handedness;

public abstract class AbstractMenuGUI extends AbstractGeneralGUI{
	private final Button changeHandButton;
	private final String changeHandText="Your preferred hand is %s. Change?";
	private Handedness hand;

	public AbstractMenuGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		hand=page.getHand();
		changeHandButton = new Button(page,340, 516, 320, 113,new ChangeHandCommand(page));
		changeHandButton.setText(String.format(changeHandText, hand));
		changeHandButton.setTextBold();
		changeHandButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		changeHandButton.setFont(new Font("Monospaced", Font.PLAIN, 27));
	} 

	@Override
	public void dispose() {
		objectDisposal(changeHandButton);
	}

	private void handChangeCheck(){
		final Handedness oldHand=getPage().getHand();
		if(!hand.equals(oldHand)){
			hand=oldHand;
			changeHandButton.setText(String.format(changeHandText, hand));
			changeHandButton.setTextBold();
		}
	}

	@Override
	public void render(){
		super.render();
		handChangeCheck();
		handleMouseOverButton(changeHandButton);
	}
}
