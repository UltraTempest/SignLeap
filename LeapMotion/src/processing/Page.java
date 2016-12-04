package processing;
import java.util.HashMap;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;

import classifier.AbstractSignClassifier;
import classifier.BinaryLibSVMClassifier;
import classifier.LibSVMClassifier;
import classifier.NeuralClassifier;
import classifier.OneClassifier;
import classifier.OneHandSignClassifier;
import classifier.SVMClassifier;
import controller.LeapMouseListener;
import g4p_controls.GButton;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import g4p_controls.GValueControl;
import processing.GUI.GUIFactory;
import processing.GUI.IGUI;
import processing.GUI.LeaderboardGUI;
import processing.core.PApplet;
import processing.core.PImage;
import recording.HandData;
import recording.HandData.Handedness;

public class Page extends PApplet{
	
	private final Controller controller = new Controller();
	private Handedness hand;
	private HandData handInfo= new HandData();
	private final String leapWarning="Warning! Please keep your %s hand placed over the Leap Motion";
	
	private final LeapMouseListener leapListen= new LeapMouseListener();
	
	private AbstractSignClassifier currentClassifier;
	
	private final LibSVMClassifier multiclassLib=null;
	//private final LibSVMClassifier multiclassLib = new LibSVMClassifier("right", "num", 60);
	private NeuralClassifier neuro=null;
	//private final NeuralClassifier neuro = new NeuralClassifier("right", "num", 60);
	//private final SVMClassifier model = new SVMClassifier();
	private final SVMClassifier model = null;
	//private final BinaryLibSVMClassifier classif=null;
	private final BinaryLibSVMClassifier classif= new BinaryLibSVMClassifier("right", "num", 60);
	//private final OneClassifier oneClassif= new OneClassifier("right", "num", 60);
	private final OneClassifier oneClassif=null;
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
		 controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
	     background(230);
	     currentGUIDisplayed=guiFactory.createWelcomeGUI();
	}

	 public void draw(){
		 displayLeapImages();
		renderLeapWarning();
		currentGUIDisplayed.render();
	}
	
	 private void stateSwitch(IGUI gui){
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
	 
	 public SVMClassifier getSVM(){
			return this.model;
		}
	 
	 public LibSVMClassifier getMultiClassClassifier(){
		 return this.multiclassLib;
	 }
	
	 public BinaryLibSVMClassifier getBinaryClassifier(){
		 return this.classif;
	}
	 
	 public AbstractSignClassifier getClassifier(){
		 return this.currentClassifier;
	 }
	 
	 public NeuralClassifier getNeuroClassifier(){
		 return this.neuro;
	 }
	 
	 public OneClassifier getOneClassifier(){
		 return this.oneClassif;
	 }
	 
	 public void setClassifier(String classifierToSet){
		 currentClassifier=classifierMap.get(classifierToSet);
	 }
	 
	 public void switchToIntroductionGUI(){
		 stateSwitch(guiFactory.createIntroductionGUI());
	 }
	 
	 public void switchToMainMenuGUI(){
		 stateSwitch(guiFactory.createMainMenuGUI());
	 }
	 
	 public void switchToSignNumbersGUI(){
		 stateSwitch(guiFactory.createSignNumbersGUI());
	 }
	 
	 public void switchToSignAlphabetGUI(){
		 stateSwitch(guiFactory.createSignAlphabetGUI());
	 }
	 
	 public void switchToLeaderboardGUI(){
		 stateSwitch(guiFactory.createLeaderboardGUI());
	 }
	 
	 public void switchToGameOverGUI(int userScore){
		 stateSwitch(guiFactory.createGameOverGUI(userScore));
	 }
	 
	  private void renderLeapWarning(){
		  if(currentGUIDisplayed.isWarningRequired()){
		  if(!handInfo.checkIfCorrectHandPlacedOverLeap(controller, hand)){
		   fill(205, 48, 48);
		   text(String.format(leapWarning,hand),250, 50);
		  }
		  else
			  background(230);
		  }
	  }
	  
	 public void turnOnLeapMouseControl(){
		 controller.addListener(leapListen);
	 }
	 
	 public void turnOffLeapMouseControl(){
		 controller.removeListener(leapListen);
	 }
	 
	 public void homeButtonClicked(GButton button, GEvent event){
		 ((LeaderboardGUI) currentGUIDisplayed).homeButtonClicked(button, event);
		}
	 
	 public void handleButtonEvents(GButton button, GEvent event) { /* Not called */ }
	 
	 public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }
	 
	 public void handlePanelEvents(GPanel panel, GEvent event) { /* Not called */ }
	 
	 public void handleSliderEvents(GValueControl slider, GEvent event) { /* Not called */ }
	 
	 private void initializeClassifiers(){
		// classifierMap.put(Handedness.LEFT.toString()+"alpha", new OneHandSignClassifier(Handedness.LEFT.toString(), "alpha"));
			// classifierMap.put(Handedness.LEFT.toString()+"num", new OneHandSignClassifier(Handedness.LEFT.toString(), "num"));
			 classifierMap.put(Handedness.RIGHT.toString()+"num", new OneHandSignClassifier(Handedness.RIGHT.toString(), "num"));
			 classifierMap.put(Handedness.RIGHT.toString()+"alpha", new OneHandSignClassifier(Handedness.RIGHT.toString(), "alpha"));
	 }
	 
	 private void displayLeapImages(){
		 Frame frame = controller.frame();
		 if(frame.isValid()){
		   ImageList images = frame.images();
		   for(Image image : images)
		   {
			   PImage[] cameras = new PImage[2];
		     //Processing PImage class
		     PImage camera = cameras[image.id()];
		     camera = createImage(image.width(), image.height(), RGB);
		     camera.loadPixels();
		     
		     //Get byte array containing the image data from Image object
		     byte[] imageData = image.data();

		     //Copy image data into display object, in this case PImage defined in Processing
		     for(int i = 0; i < image.width() * image.height(); i++){
		       int r = (imageData[i] & 0xFF) << 16; //convert to unsigned and shift into place
		       int g = (imageData[i] & 0xFF) << 8;
		       int b = imageData[i] & 0xFF;
		       camera.pixels[i] =  r | g | b; 
		     }
		     
		     //Show the image
		     camera.updatePixels();
		     image(camera, 640 * image.id(), 0);  
		   }
		}
	 }
}