package processing;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import processing.core.PApplet;
import processing.core.PImage;
import recording.SignClassifier;

public class Page extends PApplet{
	
	final int stateWelcomeScreenDisplay=0;
	final int stateShowInstructions= 1;

	private int stateOfProgram = stateWelcomeScreenDisplay;
	private final Controller controller = new Controller();
	private final SignClassifier signClass= new SignClassifier();
	private String[] imageArray={"ASL/a.gif","ASL/b.gif"}; 
	int currentImage=0;
	private PImage img;
	
	 public static void main(String[] args) {
	        PApplet.main("processing.Page");
	    }

	    public void settings(){
	    	size(600, 600);
	    }

	    public void setup(){
	    	size(600, 600);
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
	   text("Welcome", 100, 50);
	   //createStartButton();
	}
	 
	void signAlphabet(){
		  //img = loadImage("ASL/a.gif");  // Load the image into the program
			img = loadImage(imageArray[currentImage]);
		  //image(img,0,0);
		  image(img, 0, 0, img.width/2, img.height/2);
		  //background(img);
		  Frame frame = controller.frame();
		  if(frame.hands().count()>0){
		  Map<String, Float> data=new HandData().getHandPosition(controller);
		  if(data!=null){
			  double score = signClass.score(data,imageArray[currentImage].charAt(4));
			  //text(Double.toString(score),100,50);
			  System.out.println(score);
			  
			  if(score>0.95 && score<1.05){
				  if(this.currentImage==1)
					  this.currentImage=0;
				  else
					  this.currentImage=1;
				  
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
		  //System.out.println();
	  }
	}
	
	public void mousePressed() {
		  //line(mouseX, 10, mouseX, 90);
		stateOfProgram=stateShowInstructions;
		}
}