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
import gui.IGUI;
import gui.WelcomeGUI;
import processing.core.PApplet;
import processing.core.PImage;
import recording.AbstractHandData.Handedness;
import recording.IHandData;
import recording.OneHandData;

public class Page extends PApplet{

	private final static Controller controller = new Controller();
	private Handedness hand;
	private final IHandData handInfo= new OneHandData(controller);
	private final String leapWarning="Warning! Please keep your %s hand placed over the Leap Motion";
	private final LeapMouseListener leapListen= new LeapMouseListener();

	private SignClassifier alphaClassifier;
	private SignClassifier numClassifier;
	private SignClassifier num2Classifier;

	private final double easy=0.75;
	private final double medium=0.8;
	private final double hard=0.9;
	private final double difficulty=medium;

	private IGUI currentGUIDisplayed;

	private final float defaultTextSize=(float) 12.0;

	public static void main(String[] args) {
		PApplet.main("processing.Page");
	}

	public void settings(){
		size(960, 640);
	}

	public void setup(){ 
		changeAppTitle("Irish Sign Language Tool");
		changeAppIcon("hand.png");
		controller.enableGesture( Gesture.Type.TYPE_KEY_TAP);
		//controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		initializeClassifiers();
		currentGUIDisplayed=new WelcomeGUI(this);
	}

	public void draw(){
		// displayLeapImages();
		renderLeapWarning();
		currentGUIDisplayed.render();
	}

	private void changeAppIcon(final String img) {
		PImage icon = loadImage(img);
		surface.setIcon(icon);
	}

	private void changeAppTitle(final String title) {
		surface.setTitle(title);
	}

	public void stateSwitch(IGUI gui){
		currentGUIDisplayed.dispose();
		this.currentGUIDisplayed=gui;
	} 

	public Controller getLeap(){
		return Page.controller;
	}

	public void setTextSizeToDefault(){
		textSize(this.defaultTextSize);
	}

	public void setHand(Handedness hand){
		this.hand=hand;
		numClassifier=new SignClassifier(hand, "num");
		alphaClassifier=new SignClassifier(hand, "alpha");
	}

	public double getDifficulty(){
		return this.difficulty;
	}

	public Handedness getHand(){
		return this.hand;
	}

	public SignClassifier getNumberClassifier(){
		return this.numClassifier;
	}

	public SignClassifier getTwoHandNumberClassifier(){
		return this.num2Classifier;
	}

	public SignClassifier getAlphabetClassifier(){
		return this.alphaClassifier;
	}

	public void renderLeapWarning(){
		if(currentGUIDisplayed.isWarningRequired()){
			setDefaultBackground();
			if(!handInfo.checkIfCorrectHandPlacedOverLeap(hand)){
				fill(205, 48, 48);
				text(String.format(leapWarning,hand),250, 50);
			}
		}
	}

	public void turnOnLeapMouseControl(){
		controller.addListener(leapListen);
	}

	public void turnOffLeapMouseControl(){
		controller.removeListener(leapListen);
	}

	public void setDefaultBackground(){
		background(230);
	}

	public void handleButtonEvents(Button button, GEvent event) { 
		button.getCommand().process();
	}

	public void handleButtonEvents(GButton button, GEvent event) { 
		((Button) button).getCommand().process();
	}

	public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) { /* Not called */ }

	public void handlePanelEvents(GPanel panel, GEvent event) { /* Not called */ }

	public void handleSliderEvents(GValueControl slider, GEvent event) { /* Not called */ }

	private void initializeClassifiers(){
		new SignClassifier(Handedness.RIGHT, "alpha");
		new SignClassifier(Handedness.RIGHT, "num");
		num2Classifier=new SignClassifier(null, "num2");
		//		new SignClassifier(Handedness.LEFT, "alpha");
		//		new SignClassifier(Handedness.LEFT, "num");
	}

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