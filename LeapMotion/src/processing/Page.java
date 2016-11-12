package processing;
import java.util.HashMap;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;

import g4p_controls.GButton;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import processing.GUI.IGUI;
import processing.core.PApplet;
import recording.AbstractSignClassifier;
import recording.HandData.Handedness;
import recording.OneHandSignClassifier;

public class Page extends PApplet{
	
	private final Controller controller = new Controller();
	private Handedness hand;
	
	private AbstractSignClassifier currentClassifier;
	
	private final Map<String,AbstractSignClassifier> classifierMap= new HashMap<String, AbstractSignClassifier>();
	//private TwoHandSignClassifier twoSignClass= new TwoHandSignClassifier(Handedness.RIGHT.toString());
	
	private final GUIFactory guiFactory= new GUIFactory(this);
	private IGUI currentGUIDisplayed;
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

     public void settings(){
	    size(960, 640);
	}
	    
	 public void setup(){ 
		 initializeClassifiers();
	     background(230);
	     currentGUIDisplayed=guiFactory.createWelcomeGUI();
	     controller.enableGesture(Gesture.Type.TYPE_SWIPE);
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
	}
	
	 public String getHand(){
		return this.hand.toString();
	}
	
	 public AbstractSignClassifier getClassifier(){
		return this.currentClassifier;
	}
	 
	 public void setClassifier(String classifierToSet){
		 currentClassifier=classifierMap.get(classifierToSet);
	 }
	 
	 public void handleButtonEvents(GButton button, GEvent event) { /* Not called */ }
	 
	 public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }
	 
	 public void handlePanelEvents(GPanel panel, GEvent event) { /* Not called */ }
	 
	 private void initializeClassifiers(){
		// classifierMap.put(Handedness.LEFT.toString()+"alpha", new OneHandSignClassifier(Handedness.LEFT.toString(), "alpha"));
			// classifierMap.put(Handedness.LEFT.toString()+"num", new OneHandSignClassifier(Handedness.LEFT.toString(), "num"));
			 classifierMap.put(Handedness.RIGHT.toString()+"num", new OneHandSignClassifier(Handedness.RIGHT.toString(), "num"));
			 classifierMap.put(Handedness.RIGHT.toString()+"alpha", new OneHandSignClassifier(Handedness.RIGHT.toString(), "alpha"));
	 }

//	 @SuppressWarnings("unused")
//	 private void displayLeapInfo(){
//	  background(0);
//	  Frame frame = controller.frame();
//	  text( frame.hands().count() + " Hands", 50, 50 );
//	  text( frame.fingers().count() + " Fingers", 50, 100 );
//	  if(frame.hands().count()>0){
//		  Map<String, Float> data=new HandData().getOneHandPosition(controller);
//		  text( "Letter:" + currentClassifier.classify(data), 50, 150);
//	  }
//	}
}