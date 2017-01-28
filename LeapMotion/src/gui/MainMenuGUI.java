package gui;

import java.awt.Font;

import button.Button;
import command.AlphabetGUICommand;
import command.ChangeHandCommand;
import command.NumberGUICommand;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import processing.Page;
import processing.core.PApplet;
import recording.AbstractHandData.Handedness;

public class MainMenuGUI extends AbstractGeneralGUI{
	private Button alphabetButton; 
	private Button numbersButton;
	private GLabel preferredHandText;
	private Button changeHandButton;
	private Handedness hand;

	public MainMenuGUI(PApplet page) {
		super(page);
	} 

	@Override
	protected void createGUI(){
		Page page=getPage();
		hand=page.getHand();
		alphabetButton = new Button(page, 245, 111, 449, 142, new AlphabetGUICommand(page));
		alphabetButton.setText("Alphabet");
		alphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		alphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 
		alphabetButton.addEventHandler(page, "handleButtonEvents");
		numbersButton = new Button(page, 245, 371, 449, 142, new NumberGUICommand(page));
		numbersButton.setText("Numbers");
		numbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		numbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		numbersButton.addEventHandler(page, "handleButtonEvents");
		preferredHandText = new GLabel(page,309, 572, 187, 34);
		preferredHandText.setText("Your preferred hand is " + hand.toString().toUpperCase());
		preferredHandText.setOpaque(false);
		changeHandButton = new Button(page,497, 568, 181, 42,new ChangeHandCommand(page));
		changeHandButton.setText("Change");
		changeHandButton.setTextBold();
		changeHandButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		changeHandButton.addEventHandler(page, "handleButtonEvents");
		page.turnOnLeapMouseControl();
	}

	@Override
	public void dispose() {
		objectDisposal(alphabetButton);
		objectDisposal(numbersButton);
		objectDisposal(preferredHandText);
		objectDisposal(changeHandButton);
	}

	private void handChangeCheck(){
		Handedness oldHand=getPage().getHand();
		if(!hand.equals(oldHand)){
			hand=oldHand;
			preferredHandText.setText("Your preferred hand is " + hand.toString().toUpperCase());
		}
	}

	@Override
	public void render(){
		super.render();
		handChangeCheck();
		checkIfMouseOverButton(alphabetButton);
		checkIfMouseOverButton(numbersButton);
		checkIfMouseOverButton(changeHandButton);
	}
}
