package gui;

import processing.Page;
import processing.core.PApplet;

public final class IntroductionSignCharacterGUI extends AbstractSignCharacterGUI{
	private final static String[] array=new String[]{"1","a"};

	public IntroductionSignCharacterGUI(final PApplet page) {
		super(page,((Page) page).getNumberClassifier(),array);
		signCharacterChange();
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();

		if(currentLetterPosition==array.length){
			getPage().getGUIManager().setIntroductionGUI(3);
			return;
		}

		if(array[currentLetterPosition].equals("a"))
			setClassifier(getPage().getAlphabetClassifier());

	}

	@Override
	protected void updateSignCharactersGUI(final String currentLetter,final String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the character: " + currentLetter);
	}
}
