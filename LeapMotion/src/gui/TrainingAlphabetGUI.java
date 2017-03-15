package gui;

import processing.Page;
import processing.core.PApplet;

public final class TrainingAlphabetGUI extends AbstractSignCharacterGUI{
	
	public TrainingAlphabetGUI(final PApplet page, final String... array) {
		super(page,((Page) page).getAlphabetClassifier(),array);
		updateSignCharacterDisplay();
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();
		
		if(currentLetterPosition==array.length){
			getPage().getGUIManager().setMainMenuGUI();
			return;
		}	 
	}

	@Override
	protected void updateSignCharactersGUI(final String currentLetter,final String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the letter: " + currentLetter.toUpperCase()+" " + currentLetter);
	}
}
