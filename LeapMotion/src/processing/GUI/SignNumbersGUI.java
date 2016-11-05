package processing.GUI;

import java.util.Map;

import com.leapmotion.leap.Frame;

import processing.core.PApplet;
import recording.HandData;
import recording.SignClassifier;

public class SignNumbersGUI extends AbstractSignCharacterGUI{
	
	private final String numbers="Numbers";
	private final char[] numbersArray = {0,1,2,3,4,5,6,7,8,9,10};
	
	public SignNumbersGUI(PApplet page) {
		super(page);
	}
	
	private void signNumbers(){	
		  Frame frame = getPage().getLeap().frame();
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(getPage().getLeap());
		  if(data!=null){
			  double score = getPage().getClassifier().score(data,numbersArray[currentLetterPosition]);
			  if(score>0.9)
				  getPage().text("Close!",50,50);
			  else
				  getPage().text("",50,50);
			  getPage();
			PApplet.println(score);
			  if(score>0.7){
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==10)
					  this.currentLetterPosition=0;	  
			  }
		  	}
		  }
		}
	
	@Override
	public void render() {
		char currentNumber= numbersArray[currentLetterPosition];
		  String imageName= SignClassifier.language +  "/" + getPage().getHand() +"/" + numbers + "/" + currentNumber + imageType;
			if(signInstruction==null){
				createGUI();
			}
			  updateSignCharactersGUI(currentNumber, imageName);
			  signNumbers();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
