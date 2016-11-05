package processing.GUI;

import java.util.Map;

import com.leapmotion.leap.Frame;

import processing.core.PApplet;
import recording.HandData;
import recording.SignClassifier;

public class SignAlphabetGUI extends AbstractSignCharacterGUI{
	
	private final String alphabet="Alphabet";
	private final char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public SignAlphabetGUI(PApplet page) {
		super(page);
	}
	
	private void signAlphabet(){
		  Frame frame = getPage().getLeap().frame();
		  char currentLetter=alphabetArray[currentLetterPosition];
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getOneHandPosition(getPage().getLeap());
		  if(data!=null){
			  double classProbValue = getPage().getClassifier().score(data,currentLetter);
			  if(classProbValue>0.000000001)
				  getPage().text("Close!",50,50);
			  else
				  getPage().text("",50,50);
			  PApplet.println(classProbValue);
			  if(classProbValue>0.7){
				  incrementUserScore();;
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==26)
					  this.currentLetterPosition=0;	  
			  }
		  	}
		  }
		  checkIfTimerExpired();
	}

	@Override
	public void render() {
		char currentLetter=alphabetArray[currentLetterPosition];
		String imageName= SignClassifier.language +  "/" + getPage().getHand() +"/" + alphabet + "/" + currentLetter + imageType;	
		if(signInstruction==null){
			createGUI();
		}
		  updateSignCharactersGUI(currentLetter, imageName);
		  signAlphabet();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
