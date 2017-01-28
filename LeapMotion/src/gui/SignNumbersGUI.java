package gui;

import java.util.Map;

import com.leapmotion.leap.Frame;

import classifier.SignClassifier;
import processing.Page;
import processing.core.PApplet;
import recording.IHandData;
import recording.OneHandData;
import recording.TwoHandData;

public class SignNumbersGUI extends AbstractSignCharacterGUI{
	private final char[] numbersArray = {'1','2','3','4','5','6','7','8','9'};
	private SignClassifier classifier;
	private IHandData handData;
	private char previousChar;
	private String imageName;

	public SignNumbersGUI(PApplet page) {
		super(page);
		classifier=((Page) page).getNumberClassifier();
		handData= new OneHandData();
		imageName=SignClassifier.language +  "/" + ((Page) page).getHand() +"/";
	}

	private void signNumbers(){	
		Frame frame = leap.frame();
		Page page=getPage();
		if(frame.hands().count()>0){
			Map<String, Float> data=handData.getHandPosition(page.getLeap());
			if(data!=null){
				double score = classifier.score(data,previousChar);
				setProgressBarValue((float) (score*100));
				if((score)>=difficulty){
					displayNextCharacter();
					classifier.resetRollingAverage();
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

		if(this.numbersArray[this.currentLetterPosition]=='6'){
			classifier=getPage().getNumberClassifier();
			handData= new TwoHandData();
		}

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
