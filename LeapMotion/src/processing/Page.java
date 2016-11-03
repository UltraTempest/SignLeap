package processing;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import leaderboard.HighScoreManager;
import processing.GUI.GUI;
import processing.core.PApplet;
import recording.HandData;
import recording.HandData.Handedness;
import recording.SignClassifier;

public class Page extends PApplet{
	
	public static final int stateWelcomeScreenDisplay=0;
	final public static int stateMainMenu= 1;
	public static final int stateSignAlphabet=2;
	public static final int stateSignNumbers=3;
	public static final int userSubmissionScreen=4;
	public static final int leaderboardScreen=5;
	private int stateOfProgram = stateWelcomeScreenDisplay;
	
	private final Controller controller = new Controller();
	private Handedness hand;
	
	private SignClassifier signClass;
	private SignClassifier leftSignClass=new SignClassifier(Handedness.LEFT.toString(), "alpha");
	private SignClassifier rightSignClass=new SignClassifier(Handedness.RIGHT.toString(), "alpha");
	
	
	private final char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final char[] numbersArray = {0,1,2,3,4,5,6,7,8,9,10};
	private int currentLetterPosition=0;
	
	private final GUIHandler guiHandle= new GUIHandler(this);
	private GUI currentGUIDisplayed;
	
	private int userScore=0;
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

	    public void settings(){
	    	size(960, 640);
	    }
	    
	    public void setup(){    
	    	background(230);
	    	currentGUIDisplayed=guiHandle.getWelcomeGUI();
	    	currentGUIDisplayed.render();
	    }

	public void draw(){
	  switch(stateOfProgram) {
	case stateWelcomeScreenDisplay:
		checkIfHandPlacedOverLeap();
	   break;
	case stateMainMenu:
	  // displayLeapInfo();
		signAlphabet();
		checkIfTimerExpired();
	   break;
	case stateSignAlphabet:
		signAlphabet();
		checkIfTimerExpired();
		   break;
	case stateSignNumbers:
		  signNumbers();
		  checkIfTimerExpired();
		  break;
	case userSubmissionScreen:
		currentGUIDisplayed.render();
	case leaderboardScreen:
		break;
	  }
	}
	
	public void stateSwitch(int state, GUI gui){
		currentGUIDisplayed.dispose();
		this.currentGUIDisplayed=gui;
		this.stateOfProgram=state;
	}
	 
	private void signAlphabet(){	
		//createSignAlphabetGUI();
		currentGUIDisplayed.render();
		  Frame frame = controller.frame();
		  char currentLetter=alphabetArray[currentLetterPosition];
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  if(data!=null){
			  double classProbValue = signClass.score(data,currentLetter);
			  if(classProbValue>0.000000001)
				  text("Close!",50,50);
			  else
				  text("",50,50);
			  println(classProbValue);
			  if(classProbValue>0.7){
				  userScore++;
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==26)
					  this.currentLetterPosition=0;	  
			  }
		  	}
		  }
	}
	
	private void signNumbers(){	
		//createSignNumberGUI();
		currentGUIDisplayed.render();
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
		  stateSwitch(stateMainMenu, guiHandle.getMainMenuGUI(hand.toString()));
	  }
	}
	
	public int time(){
		  int c;
		  int climit = 60; //defined for a 60 second countdown

		  c = climit*1000 - millis();
		 return (c/(1000));
	}
	
	private void checkIfTimerExpired(){
		if(time()!=0)
			return;
		new HighScoreManager().addScore("TestUser", userScore);
		stateSwitch(userSubmissionScreen, guiHandle.getGameOverGUI());
	}
}