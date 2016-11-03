package processing.GUI;

import processing.core.PApplet;
import recording.SignClassifier;

public class SignAlphabetGUI extends AbstractSignCharacterGUI{
	
	private final String alphabet="Alphabet";
	private final char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public SignAlphabetGUI(PApplet page, String hand) {
		super(page, hand);
	}

	@Override
	public void render() {
		char currentLetter=alphabetArray[currentLetterPosition];
		String imageName= SignClassifier.language +  "/" + hand +"/" + alphabet + "/" + currentLetter + imageType;	
		if(signInstruction==null){
			createGUI();
		}
		  updateSignCharactersGUI(currentLetter, imageName);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
