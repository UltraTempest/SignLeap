package processing;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;

import button.Button;
import classifier.SignClassifier;
import controller.LeapMouseListener;
import g4p_controls.GButton;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import g4p_controls.GValueControl;
import processing.GUI.GUIFactory;
import processing.GUI.IGUI;
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

	private SignClassifier currentClassifier= new SignClassifier(Handedness.RIGHT.toString(), "num");

	private final GUIFactory guiFactory= new GUIFactory(this);
	private IGUI currentGUIDisplayed;

	private float defaultTextSize;

	public static void main(String[] args) {
		PApplet.main("processing.Page");
	}

	public void settings(){
		size(960, 640);
	}

	public void setup(){ 
		//initializeClassifiers();
		//controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		background(230);
		defaultTextSize=g.textSize;
		currentGUIDisplayed=guiFactory.createWelcomeGUI();
	}

	public void draw(){
		// displayLeapImages();
		renderLeapWarning();
		currentGUIDisplayed.render();
	}

	public void stateSwitch(IGUI gui){
		currentGUIDisplayed.dispose();
		this.currentGUIDisplayed=gui;
		background(230);
	} 

	public Controller getLeap(){
		return this.controller;
	}

	public void setTextSizeToDefault(){
		textSize(this.defaultTextSize);
	}

	public void setHand(Handedness hand){
		this.hand=hand;
	}

	public String getHand(){
		return this.hand.toString();
	}

	public void setClassifier(String classifierToSet){
		//currentClassifier=classifierMap.get(classifierToSet);
	}

	public SignClassifier getClassifier(){
		return this.currentClassifier;
	}

	public void switchToGameOverGUI(int userScore){
		stateSwitch(guiFactory.createGameOverGUI(userScore));
	}

	public void renderLeapWarning(){
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

	public void handleButtonEvents(Button button, GEvent event) { 
		button.getCommand().process();
	}

	public void handleButtonEvents(GButton button, GEvent event) { /* Not called */ }

	public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }

	public void handlePanelEvents(GPanel panel, GEvent event) { /* Not called */ }

	public void handleSliderEvents(GValueControl slider, GEvent event) { /* Not called */ }

	//	 private void initializeClassifiers(){
	//		// classifierMap.put(Handedness.LEFT.toString()+"alpha", new OneHandSignClassifier(Handedness.LEFT.toString(), "alpha"));
	//			// classifierMap.put(Handedness.LEFT.toString()+"num", new OneHandSignClassifier(Handedness.LEFT.toString(), "num"));
	//			 classifierMap.put(Handedness.RIGHT.toString()+"num", new OneHandSignClassifier(Handedness.RIGHT.toString(), "num"));
	//			 classifierMap.put(Handedness.RIGHT.toString()+"alpha", new OneHandSignClassifier(Handedness.RIGHT.toString(), "alpha"));
	//	 }

	@SuppressWarnings("unused")
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