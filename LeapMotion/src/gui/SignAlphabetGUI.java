package gui;

import processing.Page;
import processing.core.PApplet;

public class SignAlphabetGUI extends AbstractSignCharacterGUI{
	private final static char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	public SignAlphabetGUI(PApplet page) {
		super(page,((Page) page).getAlphabetClassifier(), alphabetArray);
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();
		if(this.currentLetterPosition==26)
			this.currentLetterPosition=0;	 
	}

	@Override
	protected void updateSignCharactersGUI(char currentLetter, String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the letter: " + 
				Character.toUpperCase(currentLetter) +" " + currentLetter);
	}
}
