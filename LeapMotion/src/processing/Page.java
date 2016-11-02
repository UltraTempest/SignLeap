package processing;
import java.awt.Font;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GTextField;
import g4p_controls.GTimer;
import processing.core.PApplet;
import processing.core.PImage;
import recording.HandData;
import recording.HandData.Handedness;
import recording.SignClassifier;

public class Page extends PApplet{
	
	private final int stateWelcomeScreenDisplay=0;
	final private int stateMainMenu= 1;
	private final int stateSignAlphabet=2;
	private final int stateSignNumbers=3;
	private int stateOfProgram = stateWelcomeScreenDisplay;
	
	private final Controller controller = new Controller();
	
	private Handedness hand;
	private SignClassifier signClass;
	private SignClassifier leftSignClass=new SignClassifier(Handedness.LEFT.toString(), "alpha");
	private SignClassifier rightSignClass=new SignClassifier(Handedness.RIGHT.toString(), "alpha");
	
	private final String alphabet="Alphabet";
	private final String numbers="Numbers";
	private final String imageType=".jpg";
	private final char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final char[] numbersArray = {0,1,2,3,4,5,6,7,8,9,10};
	private int currentLetterPosition=0;
	
	private PImage img;
	private GTextField instruction;
	private GLabel score;
	private GTimer scoreTimer; 
	private GLabel PreferredHandText; 
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

	    public void settings(){
	    	size(960, 640);
	    }
	    
	    public void setup(){    
	    	background(230);
	    	createWelcomeGUI();
	    }

	public void draw(){
	  switch(stateOfProgram) {
	case stateWelcomeScreenDisplay:
		checkIfHandPlacedOverLeap();
	   break;
	case stateMainMenu:
	  // displayLeapInfo();
		signAlphabet();
	   break;
	case stateSignAlphabet:
		signAlphabet();
		   break;
	  case stateSignNumbers:
		  signNumbers();
		  break;
	  }
	}
	 
	private void signAlphabet(){	
		createSignAlphabetGUI();
		  Frame frame = controller.frame();
		  char currentLetter=alphabetArray[currentLetterPosition];
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  if(data!=null){
			  double score = signClass.score(data,currentLetter);
			  if(score>0.9)
				  text("Close!",50,50);
			  else
				  text("",50,50);
			  println(score);
			  if(score>0.7){
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==26)
					  this.currentLetterPosition=0;	  
			  }
		  	}
		  }
	}
	
	private void signNumbers(){	
		createSignNumberGUI();
		  Frame frame = controller.frame();
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  if(data!=null){
			  double score = signClass.score(data,numbersArray[currentLetterPosition]);
			  if(score>0.9)
				  text("Close!",50,50);
			  else
				  text("",50,50);
			  println(score);
			  if(score>0.7){
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==10)
					  this.currentLetterPosition=0;	  
			  }
		  	}
		  }
		}

	@SuppressWarnings("unused")
	private void displayLeapInfo(){
	  background(0);
	  Frame frame = controller.frame();
	  text( frame.hands().count() + " Hands", 50, 50 );
	  text( frame.fingers().count() + " Fingers", 50, 100 );
	  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  text( "Letter:" + signClass.classify(data), 50, 150);
	  }
	}
	
	private void checkIfHandPlacedOverLeap(){
	  Frame frame = controller.frame();
	  if(frame.hands().count()>0){
		  hand=new HandData().GetHandedness(frame.hands().frontmost());
		  if(hand.equals(Handedness.RIGHT))
			  signClass=rightSignClass;
			  else
				  signClass=leftSignClass;
		  
		  background(230);
		  PreferredHandText.setVisible(false);
		  PreferredHandText.dispose();
		  stateOfProgram=stateMainMenu;
	  }
	}
	
	private void createWelcomeGUI(){
		  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		  surface.setTitle("Sketch Window");  
		  PreferredHandText = new GLabel(this, 3, 497, 950, 139);
		  PreferredHandText.setTextAlign(GAlign.CENTER, GAlign.BOTTOM);
		  PreferredHandText.setText("Place your preferred hand over the Leap Motion to begin!");
		  PreferredHandText.setFont(new Font("Dialog", Font.PLAIN, 58));
		  PreferredHandText.setOpaque(false);
		  String logoFile="ISL_logo.png";
		  img=loadImage(logoFile);
		  image(img,31, 7, 886, 482);
		}
	
	private void createSignAlphabetGUI(){
		char currentLetter=alphabetArray[currentLetterPosition];
		String imageName= SignClassifier.language +  "/" + hand.toString() +"/" + alphabet + "/" + currentLetter + imageType;	
		if(instruction==null){
			startGUITimerAndInitInstruction();
		}
		  updateSignCharactersGUI(currentLetter, imageName);
	}
	
	private void startGUITimerAndInitInstruction(){
		  instruction = new GTextField(this, 217, 513, 492, 81, G4P.SCROLLBARS_NONE);
		  instruction.setOpaque(false);
		  instruction.setFont(new Font("Dialog", Font.PLAIN, 58));
		  instruction.setVisible(true);
		  instruction.setTextEditEnabled(false);
		  score = new GLabel(this, 259, 28, 331, 20);
		  score.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		  score.setText("Score:                       Time left:");
		  score.setOpaque(false);
		  score.setFont(new Font("Dialog", Font.PLAIN, 16));
		  scoreTimer = new GTimer(this, this, "timeUp", 1000);
		  scoreTimer.start();
	}
	
	private void updateSignCharactersGUI(char currentLetter, String imageName){
		img=loadImage(imageName);
		image(img,136, 65, 657, 408);
		instruction.setText("Sign the letter: " + Character.toUpperCase(currentLetter) +" " + currentLetter);
	}
	
	private void createSignNumberGUI(){
		  char currentNumber= numbersArray[currentLetterPosition];
		  String imageName= SignClassifier.language +  "/" + hand.toString() +"/" + numbers + "/" + currentNumber + imageType;
			if(instruction==null){
				startGUITimerAndInitInstruction();
			}
			  updateSignCharactersGUI(currentNumber, imageName);
	}

	public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }
	public void timeUp(GTimer timer) { println("Time's up"); }
}