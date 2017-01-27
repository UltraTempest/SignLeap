package gui;

import java.util.Map;

import com.leapmotion.leap.Frame;

import classifier.SignClassifier;
import processing.Page;
import processing.core.PApplet;

public class SignNumbersGUI extends AbstractSignCharacterGUI{
	private final char[] numbersArray = {'1','2','3','4','5','6','7','8','9'};
	private char previousChar;
	private String imageName;

	public SignNumbersGUI(PApplet page) {
		super(page);
		imageName=SignClassifier.language +  "/" + ((Page) page).getHand() +"/";
	}

	private void signNumbers(){	
		Frame frame = leap.frame();
		Page page=getPage();
		if(frame.hands().count()>0){
			Map<String, Float> data;
			if(currentLetterPosition<=5)
				data=handData.getOneHandPosition(page.getLeap());
			else
				data=handData.getTwoHandsPosition(page.getLeap());
			if(data!=null){
				double score = page.getNumberClassifier().score(data,previousChar);
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
		if(this.currentLetterPosition==10)
			this.currentLetterPosition=0;	 
	}

	@Override
	protected void updateSignCharactersGUI(char currentLetter, String imageName){
		super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the number: " + Character.toUpperCase(currentLetter));
	}

	@Override
	public void render() {
		char currentNumber= numbersArray[currentLetterPosition];
		String image=imageName+currentNumber+imageType;
		updateSignCharactersGUI(currentNumber, image);
		if(currentNumber!=previousChar){
			previousChar=currentNumber;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		signNumbers();
	}
}
