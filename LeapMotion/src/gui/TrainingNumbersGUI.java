package gui;

import processing.Page;
import processing.core.PApplet;
import recording.TwoHandData;

public final class TrainingNumbersGUI extends AbstractSignCharacterGUI{
	
	public TrainingNumbersGUI(final PApplet page, final String... array) {
		super(page,((Page) page).getNumberClassifier(),array);
		updateSignCharacterDisplay();
	}

	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();
		
		if(currentLetterPosition==array.length){
			getPage().getGUIManager().setMainMenuGUI();
			return;
		}
		
		if(array[currentLetterPosition].equals("6")){
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
