package processing;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;

import button.Button;
import classifier.AlphabetClassifier;
import classifier.NumberClassifier;
import classifier.SignClassifier;
import controller.LeapMouseListener;
import g4p_controls.GButton;
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

public final class Page extends PApplet{

	private final String appTitle="Irish Sign Language Tool";
	private final String appIcon="hand.png";

	private final static Controller controller = new Controller();
	private Handedness hand;
	private final IHandData handInfo= new OneHandData(controller);
	private final String leapWarning="Warning! Please keep your %s hand placed over the Leap Motion";
	private final LeapMouseListener leapListen= new LeapMouseListener();
	private boolean isWarningDisplayed;


	private SignClassifier alphaClassifier;
	private SignClassifier numClassifier;
	private SignClassifier num2Classifier;

	public static final double EASY=0.25;
	public static final double MEDIUM=0.3;
	public static final double HARD=0.4;
	private final double difficulty=MEDIUM;

	private IGUI currentGUIDisplayed;

	private final float defaultTextSize=(float) 12.0;

	public final static void main(final String[] args) {
		PApplet.main("processing.Page");
	}

	public final void settings(){
		size(960, 640,JAVA2D);
	}

	public final void setup(){ 
		cursor(WAIT);
		surface.setTitle(appTitle);
		final PImage image=loadImage(appIcon);
		surface.setIcon(image);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		//controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		initializeClassifiers();
		cursor(image);
		currentGUIDisplayed=new WelcomeGUI(this);
	}

	public final void draw(){
		// displayLeapImages();
		renderLeapWarning();
		currentGUIDisplayed.render();
		//println(frameRate);
	}

	public final void stateSwitch(final IGUI gui){
		currentGUIDisplayed.dispose();
		this.currentGUIDisplayed=gui;
	} 

	public final Controller getLeap(){
		return controller;
	}

	public final void setTextSizeToDefault(){
		textSize(defaultTextSize);
	}

	public final void setHand(final Handedness hand){
		this.hand=hand;
		numClassifier=new NumberClassifier(hand);
		alphaClassifier=new AlphabetClassifier(hand);
	}

	public final double getDifficulty(){
		return this.difficulty;
	}

	public final Handedness getHand(){
		return this.hand;
	}

	public final SignClassifier getNumberClassifier(){
		return this.numClassifier;
	}

	public final SignClassifier getTwoHandNumberClassifier(){
		return this.num2Classifier;
	}

	public final SignClassifier getAlphabetClassifier(){
		return this.alphaClassifier;
	}

	private final void renderLeapWarning(){
		if(currentGUIDisplayed.isWarningRequired()){
			setDefaultBackground();
			if(!handInfo.isCorrectHandPlacedOverLeap(hand)){
				setTextSizeToDefault();
				fill(205, 48, 48);
				text(String.format(leapWarning,hand),250, 50);
				isWarningDisplayed=true;
			}
			else
				isWarningDisplayed=false;
		}
	}

	public final boolean isWarningDisplayed() {
		return isWarningDisplayed;
	}

	public final void turnOnLeapMouseControl(){
		controller.addListener(leapListen);
	}

	public final void turnOffLeapMouseControl(){
		controller.removeListener(leapListen);
	}

	public final void setDefaultBackground(){
		background(230);
	}

	public final void handleButtonEvents(final Button button,final GEvent event){ 
		button.getCommand().process();
	}

	public final void handleButtonEvents(final GButton button,final GEvent event){ 
		((Button) button).getCommand().process();
	}

	public final void handlePanelEvents(final GPanel panel,final GEvent event){
	/* Not called */ }

	public final void handleSliderEvents(final GValueControl slider,
			final GEvent event) { /* Not called */ }

	private final void initializeClassifiers(){
		new AlphabetClassifier(Handedness.RIGHT);
		new NumberClassifier(Handedness.RIGHT);
		num2Classifier=new SignClassifier(null, "num2");
		//		new SignClassifier(Handedness.LEFT, "alpha");
		//		new SignClassifier(Handedness.LEFT, "num");
	}

	@SuppressWarnings("unused")
	private final void displayLeapImages(){
		final Frame frame = controller.frame();
		if(frame.isValid()){
			final ImageList images = frame.images();
			for(Image image : images)
			{
				final PImage[] cameras = new PImage[2];
				//Processing PImage class
				PImage camera = cameras[image.id()];
				camera = createImage(image.width(), image.height(), RGB);
				camera.loadPixels();

				//Get byte array containing the image data from Image object
				final byte[] imageData = image.data();

				//Copy image data into display object, in this case PImage defined in Processing
				int r,g,b;
				for(int i = 0; i < image.width() * image.height(); i++){
					r = (imageData[i] & 0xFF) << 16; //convert to unsigned and shift into place
					g = (imageData[i] & 0xFF) << 8;
					b = imageData[i] & 0xFF;
					camera.pixels[i] =  r | g | b; 
				}

				//Show the image
				camera.updatePixels();
				image(camera, 640 * image.id(), 0);  
			}
		}
	}
}