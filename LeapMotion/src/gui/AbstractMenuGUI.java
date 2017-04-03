package gui;

import java.awt.Font;

import button.Button;
import command.ChangeHandCommand;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import processing.Page;
import processing.core.PApplet;
import recording.AbstractHandData.Handedness;

public abstract class AbstractMenuGUI extends AbstractGeneralGUI{
	private final Button changeHandButton;
	private final String changeHandText="Your preferred hand is %s. Change?";

	public AbstractMenuGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		changeHandButton = new Button(page,340, 516, 320, 113,new ChangeHandCommand(page, this));
		changeHandButton.setText(String.format(changeHandText, page.getHand()));
		changeHandButton.setTextBold();
		changeHandButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		changeHandButton.setFont(new Font("Monospaced", Font.PLAIN, 27));
	} 

	@Override
	public void dispose() {
		objectDisposal(changeHandButton);
	}

	public void handChange(final Button utton,final GEvent event){
		final Page page=getPage();
		if(page.getHand().equals(Handedness.RIGHT)){
			changeHandButton.setText(String.format(changeHandText,Handedness.LEFT));
			changeHandButton.setTextBold();
			page.setHand(Handedness.LEFT);
		}
		else{
			changeHandButton.setText(String.format(changeHandText,Handedness.RIGHT));
			changeHandButton.setTextBold();
			page.setHand(Handedness.RIGHT);
		}

	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(changeHandButton);
	}
}
