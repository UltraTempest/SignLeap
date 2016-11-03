package processing.GUI;

import processing.core.PApplet;
import recording.SignClassifier;

public class SignNumbersGUI extends AbstractSignCharacterGUI{
	
	private final String numbers="Numbers";
	private final char[] numbersArray = {0,1,2,3,4,5,6,7,8,9,10};
	
	public SignNumbersGUI(PApplet page, String hand) {
		super(page, hand);
	}
	
	@Override
	public void render() {
		char currentNumber= numbersArray[currentLetterPosition];
		  String imageName= SignClassifier.language +  "/" + hand.toString() +"/" + numbers + "/" + currentNumber + imageType;
			if(signInstruction==null){
				createGUI();
			}
			  updateSignCharactersGUI(currentNumber, imageName);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
