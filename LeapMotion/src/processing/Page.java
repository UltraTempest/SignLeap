package processing;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import recording.SignClassifier;

public class Page extends PApplet{
	
	final int stateWelcomeScreenDisplay=0;
	final int stateShowInstructions= 1;
	private int stateOfProgram = stateWelcomeScreenDisplay;
	
	private int buttonX, buttonY, buttonW, buttonH;
	
	PFont f;                           // STEP 1 Declare PFont variable
	
	private final Controller controller = new Controller();
	private final SignClassifier signClass= new SignClassifier();
	
	private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private int currentLetterPosition=0;
	private PImage img;
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

	    public void settings(){
	    	size(700, 700);
	    }
	    
	    public void setup(){
	    	f = createFont("Arial",16,true); // STEP 2 Create Font
	    }

	public void draw(){
	  switch(stateOfProgram) {
	case stateWelcomeScreenDisplay:
	   doStateWelcomeScreenDisplay();
	   break;
	case stateShowInstructions:
	  // displayLeapInfo();
		signAlphabet();
	   break;
	  }
	}

	void doStateWelcomeScreenDisplay(){
	   background(0);
 	  // Some basic parameters for a button
	    	 buttonW = 335;
	    	 buttonH = 100;
	    	 textSize(buttonH);
	    	 buttonX = (width-buttonW)/2;
	    	 buttonY = (height-buttonH)/2;
	    	// Show the button
	    	fill(255);
	    	rect(buttonX, buttonY, buttonW, buttonH);
	    	fill(0);
	    	text("START", buttonX+10, buttonY+buttonH-10);
	}
	 
	void signAlphabet(){	
		  background(0);
		  char currentLetter=alphabet[currentLetterPosition];
		  img = loadImage("ASL/"+currentLetter + ".gif");// Load the image into the program
		  image(img, 0, 0, img.width/2, img.height/2);
		  textFont(f,16);                  // STEP 3 Specify font to be used
		  fill(255);                         // STEP 4 Specify font color 
		  textAlign(CENTER);
		  text("Sign the letter: " + Character.toUpperCase(currentLetter) +" " + currentLetter,600,600);   // STEP 5 Display Text
		  //background(img);
		  Frame frame = controller.frame();
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  if(data!=null){
			  double score = signClass.score(data,currentLetter);
			  //text(Double.toString(score),100,50);
			  System.out.println(score);
			  if(score>0.95 && score<1.05){
				  this.currentLetterPosition++;
				  if(this.currentLetterPosition==26)
					  this.currentLetterPosition=0;	  
				  background(0);
			  }
		  	}
		  }
		}

	void displayLeapInfo(){
	  background(0);
	  Frame frame = controller.frame();
	  text( frame.hands().count() + " Hands", 50, 50 );
	  text( frame.fingers().count() + " Fingers", 50, 100 );
	  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  text( "Letter:" + signClass.classify(data), 50, 150);
	  }
	}
	
	public void mousePressed() {
		if (mouseX > buttonX && mouseX < buttonX+buttonW && mouseY > buttonY && mouseY < buttonY+buttonH)
		stateOfProgram=stateShowInstructions;
		}
}