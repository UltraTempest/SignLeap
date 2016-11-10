package processing;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import g4p_controls.GButton;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import processing.GUI.IGUI;
import processing.core.PApplet;
import recording.AbstractSignClassifier;
import recording.HandData;
import recording.HandData.Handedness;
import recording.OneHandSignClassifier;
import recording.TwoHandSignClassifier;

public class Page extends PApplet{
	
	private final Controller controller = new Controller();
	private Handedness hand;
	
	private OneHandSignClassifier oneSignClass;
	private TwoHandSignClassifier twoSignClass;
	//private TwoHandSignClassifier twoSignClass= new TwoHandSignClassifier(Handedness.RIGHT.toString());
	private OneHandSignClassifier leftSignClass=new OneHandSignClassifier(Handedness.LEFT.toString(), "alpha");
	private OneHandSignClassifier rightSignClass=new OneHandSignClassifier(Handedness.RIGHT.toString(), "num");
	
	private final GUIFactory guiFactory= new GUIFactory(this);
	private IGUI currentGUIDisplayed;
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

     public void settings(){
	    size(960, 640);
	}
	    
	 public void setup(){    
	     background(230);
	     currentGUIDisplayed=guiFactory.createWelcomeGUI();
	}

	 public void draw(){
		currentGUIDisplayed.render();
	}
	
	 public void stateSwitch(IGUI gui){
		currentGUIDisplayed.dispose();
		this.currentGUIDisplayed=gui;
	} 
	
	 public Controller getLeap(){
		return this.controller;
	}
	
	 public void setHand(Handedness hand){
		this.hand=hand;
		if(this.hand.equals(Handedness.RIGHT))
			  oneSignClass=rightSignClass;
			  else
				  oneSignClass=leftSignClass;
	}
	
	 public String getHand(){
		return this.hand.toString();
	}
	
	 public AbstractSignClassifier getClassifier(){
		return this.oneSignClass;
	}
	 
	 public AbstractSignClassifier getTwoHandClassifier(){
			return this.twoSignClass;
		}
	 
	 public void handleButtonEvents(GButton button, GEvent event) { /* Not called */ }
	 
	 public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }
	 
	 public void handlePanelEvents(GPanel panel, GEvent event) { /* Not called */ }

	 @SuppressWarnings("unused")
	 private void displayLeapInfo(){
	  background(0);
	  Frame frame = controller.frame();
	  text( frame.hands().count() + " Hands", 50, 50 );
	  text( frame.fingers().count() + " Fingers", 50, 100 );
	  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getOneHandPosition(controller);
		  text( "Letter:" + oneSignClass.classify(data), 50, 150);
	  }
	}
}