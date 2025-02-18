package gui;

import processing.Page;
import processing.core.PApplet;

public final class IntroductionSignCharacterGUI extends AbstractSignCharacterGUI{
	private final static String[] array=new String[]{"1","a"};

	public IntroductionSignCharacterGUI(final PApplet page) {
		super(page,((Page) page).getNumberClassifier(),array);
		updateSignCharacterDisplay();
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();

		if(currentLetterPosition==array.length){
			getPage().getGUIManager().setIntroductionGUI(4);
			return;
		}

		if(array[currentLetterPosition].equals("a"))
			setClassifier(getPage().getAlphabetClassifier());

	}

	@Override
	protected void updateSignCharactersGUI(final String currentLetter,final String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		if(currentLetter.charAt(0)=='a')
		signInstruction.setText("Sign the letter: " + currentLetter);
		else
			signInstruction.setText("Sign the number: " + currentLetter);
	}
}
