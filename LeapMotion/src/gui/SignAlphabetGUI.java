package gui;

import processing.Page;
import processing.core.PApplet;
import trainer.AlphabetTrainer;

public final class SignAlphabetGUI extends AbstractSignCharacterGUI{

	public SignAlphabetGUI(final PApplet page) {
		super(page,((Page) page).getAlphabetClassifier(), 
				AlphabetTrainer.alphabet);
	}

	@Override
	protected final void displayNextCharacter(){
		super.displayNextCharacter();
		if(this.currentLetterPosition==26)
			this.currentLetterPosition=0;	 
	}

	@Override
	protected final void updateSignCharactersGUI(final String currentLetter, 
			final String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the letter: " + 
				currentLetter.toUpperCase()+" " + currentLetter);
	}
}
