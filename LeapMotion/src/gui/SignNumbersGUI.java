package gui;

import processing.Page;
import processing.core.PApplet;
import recording.TwoHandData;

public final class SignNumbersGUI extends AbstractSignCharacterGUI{
	private final static String[] numbersArray = {"1","2","3","4","5","6","7"
			,"8","9", "10"};

	public SignNumbersGUI(final PApplet page) {
		super(page,((Page) page).getNumberClassifier(),numbersArray);
	}

	@Override
	protected final void displayNextCharacter(){
		super.displayNextCharacter();

		if(numbersArray[this.currentLetterPosition].equals("6")){
			final Page page=getPage();
			setClassifier(page.getTwoHandNumberClassifier());
			setHandData(new TwoHandData(page.getLeap()));
		}

		if(this.currentLetterPosition==numbersArray.length)
			this.currentLetterPosition=0;	 
	}

	@Override
	protected final void updateSignCharactersGUI(final String currentLetter,
			final String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the number: " + currentLetter);
	}
}
