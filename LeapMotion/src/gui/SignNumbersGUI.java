package gui;

import processing.Page;
import processing.core.PApplet;
import recording.TwoHandData;

public class SignNumbersGUI extends AbstractSignCharacterGUI{
	private final static char[] numbersArray = {'1','2','3','4','5','6','7','8','9'};

	public SignNumbersGUI(PApplet page) {
		super(page,((Page) page).getNumberClassifier(),numbersArray);
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();

		if(SignNumbersGUI.numbersArray[this.currentLetterPosition]=='6'){
			setClassifier(getPage().getNumberClassifier());
			setHandData(new TwoHandData(getPage().getLeap()));
		}

		if(this.currentLetterPosition==10)
			this.currentLetterPosition=0;	 
	}

	@Override
	protected void updateSignCharactersGUI(char currentLetter, String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the number: " + 
				Character.toUpperCase(currentLetter));
	}
}
