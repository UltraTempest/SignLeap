package gui;

import java.util.Map;

import com.leapmotion.leap.Frame;

import classifier.SignClassifier;
import processing.Page;
import processing.core.PApplet;

public class SignAlphabetGUI extends AbstractSignCharacterGUI{
	private final char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private char previousChar;
	private String imageName;

	public SignAlphabetGUI(PApplet page) {
		super(page);
		imageName=SignClassifier.language +  "/" + ((Page) page).getHand() +"/";
	}

	private void signAlphabet(){
		Frame frame = leap.frame();
		Page page=getPage();
		if(frame.hands().count()>0){
			Map<String, Float> data=handData.getOneHandPosition(page.getLeap());
			if(data!=null){
				double score = page.getAlphabetClassifier().score(data,previousChar);
				setProgressBarValue((float) (score*100));
				PApplet.println(score);
				if(score<0.5)
					setProgressBarValue((float) (score*2*100));
				else{
					displayNextCharacter();
					return;
				}
				PApplet.println(score);
				if((score*2)>=difficulty){
					displayNextCharacter();
				}
			}
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
