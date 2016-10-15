package processing;
import java.awt.Font;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GImageButton;
import g4p_controls.GLabel;
import g4p_controls.GTextField;
import processing.core.PApplet;
import processing.core.PFont;
import recording.SignClassifier;

public class Page extends PApplet{
	
	private final int stateWelcomeScreenDisplay=0;
	final private int stateShowInstructions= 1;
	private int stateOfProgram = stateWelcomeScreenDisplay;
	
	PFont f;                           // STEP 1 Declare PFont variable
	
	private final Controller controller = new Controller();
	private final SignClassifier signClass= new SignClassifier();
	
	private final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private int currentLetterPosition=0;
	
	@SuppressWarnings("unused")
	private GImageButton imgButton; 
	private GTextField textfield; 
	private GButton startButton;
	private GLabel welcomeLabel;
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

	    public void settings(){
	    	size(480, 320);
	    }
	    
	    public void setup(){    	
	    	  createWelcomeGUI();
	    	f = createFont("Arial",16,true); // STEP 2 Create Font
	    }

	public void draw(){
	  switch(stateOfProgram) {
	case stateWelcomeScreenDisplay:
	   break;
	case stateShowInstructions:
	  // displayLeapInfo();
		signAlphabet();
	   break;
	  }
	}
	 
	void signAlphabet(){	
		createSignAlphabetGUI();
		  Frame frame = controller.frame();
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  if(data!=null){
			  double score = signClass.score(data,alphabet[currentLetterPosition]);
			  println(score);
			  if(score>0.1){
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==26)
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
	
	private void createWelcomeGUI(){
		  G4P.messagesEnabled(true);
		  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		  G4P.setCursor(ARROW);
		  surface.setTitle("Sketch Window");
		  welcomeLabel = new GLabel(this, 161, 67, 80, 100);
		  welcomeLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		  welcomeLabel.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		  welcomeLabel.setText("Welcome");
		  welcomeLabel.setTextBold();
		  welcomeLabel.setOpaque(false);
		  startButton = new GButton(this, 129, 136, 150, 30);
		  startButton.setText("Click to Start");
		  startButton.setTextBold();
		  startButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		  startButton.addEventHandler(this, "handleButtonEvents");
		}
	
	private void createSignAlphabetGUI(){
		char currentLetter= alphabet[currentLetterPosition];
		  String imageName= "ASL/" + currentLetter + ".gif";	
		  imgButton = new GImageButton(this, 104, 57, 300, 200, new String[] { imageName, imageName, imageName } );
		  textfield = new GTextField(this, 176, 280, 160, 30, G4P.SCROLLBARS_NONE);
		  textfield.setText("Sign the letter: " + Character.toUpperCase(currentLetter) +" " + currentLetter);
		  textfield.setOpaque(true);
	}

	public void handleButtonEvents(GButton button, GEvent event) { 
		 println("button1 - GButton >> GEvent." + event + " @ " + millis());
		 welcomeLabel=null;
		 startButton=null;
			stateOfProgram=stateShowInstructions;
	}
	
	public void handleButtonEvents(GImageButton button, GEvent event) { /* Not called*/ }

	public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }
}