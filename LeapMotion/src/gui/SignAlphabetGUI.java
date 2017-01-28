package gui;

import java.util.Map;

import com.leapmotion.leap.Frame;

import classifier.SignClassifier;
import processing.Page;
import processing.core.PApplet;
import recording.IHandData;
import recording.OneHandData;

public class SignAlphabetGUI extends AbstractSignCharacterGUI{
	private final char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final SignClassifier classifier;
	private final IHandData handData = new OneHandData();
	private char previousChar;
	private String imageName;

	public SignAlphabetGUI(PApplet page){
		super(page);
		classifier=((Page) page).getAlphabetClassifier();
		imageName=SignClassifier.language +  "/" + ((Page) page).getHand() +"/";
	}

	private void signAlphabet(){
		Frame frame = leap.frame();
		Page page=getPage();
		if(frame.hands().count()==1){
			Map<String, Float> data=handData.getHandPosition(page.getLeap());
			if(data!=null){
				double score = classifier.score(data,previousChar);
				setProgressBarValue((float) (score*100));
				if((score*2)>=difficulty){
					displayNextCharacter();
				}
			}
			else
				setProgressBarValue((float)0);
		}
		else
			setProgressBarValue((float)0);
	}

	private void displayNextCharacter(){
		incrementUserScore();
		this.currentLetterPosition++;
		if(this.currentLetterPosition==26)
			this.currentLetterPosition=0;	 
	}

	@Override
	public void render() {
		char currentLetter=alphabetArray[currentLetterPosition];
		String image=imageName+currentLetter + imageType;	
		updateSignCharactersGUI(currentLetter, image);
		if(currentLetter!=previousChar){
			previousChar=currentLetter;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		signAlphabet();
	}
}
