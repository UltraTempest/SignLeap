package processing;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import leaderboard.HighScoreManager;
import processing.GUI.IGUI;
import processing.core.PApplet;
import recording.HandData;
import recording.HandData.Handedness;
import recording.SignClassifier;

public class Page extends PApplet{
	
	private int stateOfProgram = StateProperties.stateWelcomeScreenDisplay;
	
	private final Controller controller = new Controller();
	private Handedness hand;
	
	private SignClassifier signClass;
	private SignClassifier leftSignClass=new SignClassifier(Handedness.LEFT.toString(), "alpha");
	private SignClassifier rightSignClass=new SignClassifier(Handedness.RIGHT.toString(), "alpha");
	
	private final GUIFactory guiFactory= new GUIFactory(this);
	private IGUI currentGUIDisplayed;
	
	private int userScore=0;
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

	    public void settings(){
	    	size(960, 640);
	    }
	    
	    public void setup(){    
	    	background(230);
	    	currentGUIDisplayed=guiFactory.createWelcomeGUI();
	    	currentGUIDisplayed.render();
	    }

	public void draw(){
	  switch(stateOfProgram) {
	case StateProperties.stateWelcomeScreenDisplay:
		currentGUIDisplayed.render();
	   break;
	case StateProperties.stateMainMenu:
		currentGUIDisplayed.render();
	   break;
	case StateProperties.stateSignAlphabet:
		currentGUIDisplayed.render();
		checkIfTimerExpired();
		   break;
	case StateProperties.stateSignNumbers:
		currentGUIDisplayed.render();
		  checkIfTimerExpired();
		  break;
	case StateProperties.userSubmissionScreen:
		currentGUIDisplayed.render();
	case StateProperties.leaderboardScreen:
		break;
	  }
	}
	
	public void stateSwitch(int state, IGUI gui){
		currentGUIDisplayed.dispose();
		this.currentGUIDisplayed=gui;
		this.stateOfProgram=state;
	}
	 
	
	public Controller getLeap(){
		return this.controller;
	}
	
	public void setHand(Handedness hand){
		this.hand=hand;
		if(this.hand.equals(Handedness.RIGHT))
			  signClass=rightSignClass;
			  else
				  signClass=leftSignClass;
	}
	
	public String getHand(){
		return this.hand.toString();
	}
	
	public SignClassifier getClassifier(){
		return this.signClass;
	}
	
	public void incrementUserScore(){
		this.userScore+=1000;
	}
	
	public int getCurrentUserScore(){
		return this.userScore;
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
		stateSwitch(StateProperties.userSubmissionScreen, guiFactory.createGameOverGUI());
	}
}