package gui;

import button.Button;
import command.ChangeHandCommand;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import processing.Page;
import processing.core.PApplet;
import recording.AbstractHandData.Handedness;

public abstract class AbstractMenuGUI extends AbstractGeneralGUI{
	private final GLabel preferredHandText;
	private final Button changeHandButton;
	private Handedness hand;

	public AbstractMenuGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		hand=page.getHand();

		preferredHandText = new GLabel(page,309, 572, 187, 34);
		preferredHandText.setText("Your preferred hand is " + hand.toString().toUpperCase());
		preferredHandText.setOpaque(false);
		changeHandButton = new Button(page,480, 568, 181, 42,new ChangeHandCommand(page));
		changeHandButton.setText("Change");
		changeHandButton.setTextBold();
		changeHandButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
	} 

	@Override
	public void dispose() {
		objectDisposal(preferredHandText,changeHandButton);
	}

	private void handChangeCheck(){
		final Handedness oldHand=getPage().getHand();
		if(!hand.equals(oldHand)){
			hand=oldHand;
			preferredHandText.setText("Your preferred hand is " + hand.toString().toUpperCase());
		}
	}

	@Override
	public void render(){
		super.render();
		handChangeCheck();
		handleMouseOverButton(changeHandButton);
	}
}
