package processing.GUI;

import java.util.Map;

import com.leapmotion.leap.Frame;

import processing.core.PApplet;
import recording.HandData;
import recording.OneHandSignClassifier;

public class SignNumbersGUI extends AbstractSignCharacterGUI{
	
	private final String numbers="Numbers";
	private final char[] numbersArray = {'1','2','3','4','5','6','7','8','9'};
	private char previousChar;
	
	public SignNumbersGUI(PApplet page) {
		super(page);
	}
	
	private void signNumbers(){	
		  Frame frame = getPage().getLeap().frame();
		  if(frame.hands().count()>0){
			  Map<String, Float> data;
			  if(currentLetterPosition<=5)
		       data=new HandData().getOneHandPosition(getPage().getLeap());
			  else
				  data=new HandData().getTwoHandsPosition(getPage().getLeap());
		  if(data!=null){
			  double score;
			  //score = getPage().getClassifier().score(data,previousChar);
			  score = getPage().getSVM().getProbabilityForInstance(data, previousChar);
			  if(score>0.9)
				  getPage().text("Close!",50,50);
			  else
				  getPage().text("",50,50);
			PApplet.println(score);
			  if(score>0.7){
				  incrementUserScore();
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==10)
					  this.currentLetterPosition=0;	  
			  }
		  	}
		  }
		  checkIfTimerExpired();
		}
	
	@Override
	protected void updateSignCharactersGUI(char currentLetter, String imageName){
        super.updateSignCharactersGUI(currentLetter, imageName);
		signInstruction.setText("Sign the number: " + Character.toUpperCase(currentLetter));
	}
	
	@Override
	public void render() {
		char currentNumber= numbersArray[currentLetterPosition];
		String imageName= OneHandSignClassifier.language +  "/" + getPage().getHand() +"/" + numbers + "/" + currentNumber + imageType;
			if(signInstruction==null){
				createGUI(getPage().getHand()+"num");
			}
			  updateSignCharactersGUI(currentNumber, imageName);
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
