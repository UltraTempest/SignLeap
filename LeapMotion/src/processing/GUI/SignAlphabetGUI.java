package processing.GUI;

import java.util.Map;

import com.leapmotion.leap.Frame;

import processing.core.PApplet;
import recording.HandData;
import recording.OneHandSignClassifier;

public class SignAlphabetGUI extends AbstractSignCharacterGUI{
	
	private final String alphabet="Alphabet";
	private final char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private char previousChar;
	private String imageName;
	
	public SignAlphabetGUI(PApplet page) {
		super(page);
		imageName=OneHandSignClassifier.language +  "/" + getPage().getHand() +"/" + alphabet + "/";
	}
	
	private void signAlphabet(){
		  Frame frame = leap.frame();
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getOneHandPosition(getPage().getLeap());
		  if(data!=null){
			  double classProbValue = getPage().getClassifier().score(data,previousChar);
			  if(classProbValue>0.4)
				  getPage().text("Close!",50,50);
			  else
				  getPage().text("",50,50);
			  PApplet.println(classProbValue);
			  if(classProbValue>0.9){
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
		String image=imageName+currentLetter + imageType;	
		if(signInstruction==null){
			createGUI(getPage().getHand()+"alpha");
		}
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
