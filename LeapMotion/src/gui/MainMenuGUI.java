package gui;

import java.awt.Font;

import button.Button;
import command.AlphabetGUICommand;
import command.ChangeHandCommand;
import command.NumberGUICommand;
import command.SelectNumbersCommand;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import processing.Page;
import processing.core.PApplet;
import recording.AbstractHandData.Handedness;

public final class MainMenuGUI extends AbstractGeneralGUI{
	private final Button alphabetButton; 
	private final Button numbersButton;
	private final Button practiceButton;
	private final GLabel preferredHandText;
	private final Button changeHandButton;
	private Handedness hand;

	public MainMenuGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		hand=page.getHand();
		alphabetButton = new Button(page,  150, 115, 612, 100, 
				new AlphabetGUICommand(page));
		alphabetButton.setText("Alphabet");
		alphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		alphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 
		alphabetButton.addEventHandler(page, "handleButtonEvents");

		numbersButton = new Button(page, 150, 435, 612, 100, 
				new NumberGUICommand(page));
		numbersButton.setText("Numbers");
		numbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		numbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		numbersButton.addEventHandler(page, "handleButtonEvents");

		practiceButton = new Button(page, 150, 276, 612, 100, 
				new SelectNumbersCommand(page));
		practiceButton.setText("Practice");
		practiceButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		practiceButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		practiceButton.addEventHandler(page, "handleButtonEvents");

		preferredHandText = new GLabel(page,309, 572, 187, 34);
		preferredHandText.setText("Your preferred hand is " + 
				hand.toString().toUpperCase());
		preferredHandText.setOpaque(false);
		changeHandButton = new Button(page,480, 568, 181, 42,
				new ChangeHandCommand(page));
		changeHandButton.setText("Change");
		changeHandButton.setTextBold();
		changeHandButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		changeHandButton.addEventHandler(page, "handleButtonEvents");
	} 

	@Override
	public void dispose() {
		objectDisposal(alphabetButton, numbersButton,practiceButton, 
				preferredHandText,changeHandButton);
	}

	private void handChangeCheck(){
		final Handedness oldHand=getPage().getHand();
		if(!hand.equals(oldHand)){
			hand=oldHand;
			preferredHandText.setText("Your preferred hand is " + 
					hand.toString().toUpperCase());
		}
	}

	@Override
	public void render(){
		handChangeCheck();
		handleMouseOverButton(alphabetButton,numbersButton,
				changeHandButton,practiceButton);
	}
}
