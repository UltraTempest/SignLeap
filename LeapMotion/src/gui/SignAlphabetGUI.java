package gui;

import processing.Page;
import processing.core.PApplet;
import recording.AlphabetTrainer;

public final class SignAlphabetGUI extends AbstractSignCharacterGUI{

	public SignAlphabetGUI(PApplet page) {
		super(page,((Page) page).getAlphabetClassifier(), 
				AlphabetTrainer.alphabet);
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();
		if(this.currentLetterPosition==26)
			this.currentLetterPosition=0;	 
	}

	@Override
	protected void updateSignCharactersGUI(String currentLetter, String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the letter: " + 
				currentLetter.toUpperCase()+" " + currentLetter);
	}
}
