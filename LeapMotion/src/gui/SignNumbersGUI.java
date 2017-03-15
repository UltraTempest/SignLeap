package gui;

import processing.Page;
import processing.core.PApplet;
import recording.TwoHandData;

public final class SignNumbersGUI extends AbstractTimedSignCharacterGUI{
	private final static String[] numbersArray = {"1","2","3","4","5","6","7","8","9", "10"};

	public SignNumbersGUI(final PApplet page) {
		super(page,((Page) page).getNumberClassifier(),numbersArray);
		updateSignCharacterDisplay();
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();
		
		if(this.currentLetterPosition==numbersArray.length)
			currentLetterPosition=0;	
		
		if(numbersArray[this.currentLetterPosition].equals("6")){
			final Page page=getPage();
			setClassifier(page.getTwoHandNumberClassifier());
			setHandData(new TwoHandData(page.getLeap()));
		} 
	}

	@Override
	protected void updateSignCharactersGUI(final String currentLetter,final String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the number: " + currentLetter);
	}
}
