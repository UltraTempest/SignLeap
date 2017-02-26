package gui;

import processing.Page;
import processing.core.PApplet;

public final class AlphabetTrainingGUI extends AbstractSignCharacterGUI{
	private final String[] array;
	
	public AlphabetTrainingGUI(final PApplet page, final String[] array) {
		super(page,((Page) page).getAlphabetClassifier(),array);
		this.array=array;
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();
		
		if(currentLetterPosition==array.length){
			getPage().stateSwitch(new MainMenuGUI(getPage()));
			return;
		}	 
	}


	@Override
	protected void updateSignCharactersGUI(final String currentLetter,final String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the letter: " + currentLetter.toUpperCase()+" " + currentLetter);
	}
}
