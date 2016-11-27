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
	private String imageName;
	
	public SignNumbersGUI(PApplet page) {
		super(page);
		imageName=OneHandSignClassifier.language +  "/" + getPage().getHand() +"/" + numbers + "/";
	}
	
	private void signNumbers(){	
		  Frame frame = leap.frame();
		  if(frame.hands().count()>0){
			  Map<String, Float> data;
			  if(currentLetterPosition<=5)
		       data=new HandData().getOneHandPosition(getPage().getLeap());
			  else
				  data=new HandData().getTwoHandsPosition(getPage().getLeap());
		  if(data!=null){
			  //double score = getPage().getNeuroClassifier().score(data,previousChar);
			  double score = getPage().getClassifier().score(data,previousChar);
			  //score = getPage().getSVM().getProbabilityForInstance(data, previousChar);
			  setProgressBarValue((float) (score*100));
			PApplet.println(score);
			  if(score>0.95){
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
		String image=imageName+currentNumber+imageType;
			if(signInstruction==null){
				createGUI(getPage().getHand()+"num");
			}
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
