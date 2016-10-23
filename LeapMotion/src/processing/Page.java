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
import g4p_controls.GTextArea;
import g4p_controls.GTextField;
import processing.core.PApplet;
import recording.HandData;
import recording.SignClassifier;

public class Page extends PApplet{
	
	private final int stateWelcomeScreenDisplay=0;
	final private int stateShowInstructions= 1;
	private int stateOfProgram = stateWelcomeScreenDisplay;
	
	private final Controller controller = new Controller();
	private final SignClassifier signClass= new SignClassifier();
	
	private final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private int currentLetterPosition=0;
	
	@SuppressWarnings("unused")
	private GImageButton imgButton; 
	private GTextField textfield; 
	private GLabel welcomeLabel;
	private GTextArea textarea1; 
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

	    public void settings(){
	    	size(480, 320);
	    }
	    
	    public void setup(){    	
	    	  createWelcomeGUI();
	    }

	public void draw(){
	  switch(stateOfProgram) {
	case stateWelcomeScreenDisplay:
		checkIfHandPlacedOverLeap();
	   break;
	case stateShowInstructions:
	  // displayLeapInfo();
		signAlphabet();
	   break;
	  }
	}
	 
	private void signAlphabet(){	
		createSignAlphabetGUI();
		  Frame frame = controller.frame();
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  if(data!=null){
			  double score = signClass.score(data,alphabet[currentLetterPosition]);
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
		  stateOfProgram=stateShowInstructions;
	  }
	}
	
	private void createWelcomeGUI(){
		  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		  surface.setTitle("Sketch Window");
		  welcomeLabel = new GLabel(this, 161, 67, 80, 100);
		  welcomeLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		  welcomeLabel.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		  welcomeLabel.setText("Welcome");
		  welcomeLabel.setTextBold();
		  welcomeLabel.setOpaque(false);
		  textarea1 = new GTextArea(this, 162, 135, 160, 80, G4P.SCROLLBARS_NONE);
		  textarea1.setText("Place your hand over the Leap Motion to begin!");
		  textarea1.setOpaque(true);
		  textarea1.addEventHandler(this, "textarea1_change1");
		}
	
	private void createSignAlphabetGUI(){
		  char currentLetter= alphabet[currentLetterPosition];
		  String imageName= "ISLpersonAlphabetImages/" + currentLetter + ".jpg";	
		  imgButton = new GImageButton(this, 104, 57, 300, 200, new String[] { imageName, imageName, imageName } );
		  textfield = new GTextField(this, 176, 280, 160, 30, G4P.SCROLLBARS_NONE);
		  textfield.setText("Sign the letter: " + Character.toUpperCase(currentLetter) +" " + currentLetter);
		  textfield.setOpaque(true);
	}

	public void handleButtonEvents(GButton button, GEvent event) { 
		 println("button1 - GButton >> GEvent." + event + " @ " + millis());
		 welcomeLabel=null;
		 textarea1=null;
			stateOfProgram=stateShowInstructions;
	}
	
	public void handleButtonEvents(GImageButton button, GEvent event) { /* Not called*/ 
		if(this.currentLetterPosition==26)
			this.currentLetterPosition=0;
		this.currentLetterPosition++;
	}

	public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }
	
	public void textarea1_change1(GTextArea textarea, GEvent event) { /* Not called */ }
}