package processing;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import button.Button;
import button.IButton;
import button.ImageButton;
import classifier.AlphabetClassifier;
import classifier.NumberClassifier;
import classifier.SignClassifier;
import controller.LeapMouseListener;
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

	public static void main(final String[] args) {
		PApplet.main("processing.Page");
	}

	@Override
	public void settings(){
		size(960, 640,JAVA2D);
	}

	@Override
	public void setup(){ 
		cursor(WAIT);
		surface.setTitle(appTitle);
		final PImage image=loadImage(appIcon);
		surface.setIcon(image);
		initializeClassifiers();
		cursor(image);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		currentGUIDisplayed=new WelcomeGUI(this);
	}

	@Override
	public void draw(){
		renderLeapWarning();
		currentGUIDisplayed.render();
		//println(frameRate);
	}

	public void stateSwitch(final IGUI gui){
		currentGUIDisplayed.dispose();
		currentGUIDisplayed=gui;
	} 

	public Controller getLeap(){
		return controller;
	}

	public void setTextSizeToDefault(){
		textSize(defaultTextSize);
	}

	public void setHand(final Handedness hand){
		this.hand=hand;
		numClassifier=new NumberClassifier(hand);
		alphaClassifier=new AlphabetClassifier(hand);
	}

	public double getDifficulty(){
		return difficulty;
	}

	public Handedness getHand(){
		return hand;
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

	private void renderLeapWarning(){
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

	public boolean isWarningDisplayed() {
		return isWarningDisplayed;
	}

	public void turnOnLeapMouse(){
		controller.addListener(leapListen);
	}

	public void turnOffLeapMouseControl(){
		controller.removeListener(leapListen);
	}

	public void setDefaultBackground(){
		background(230);
	}

	public void handleButtonEvents(final IButton button){ 
		button.getCommand().process();
	}

	public void handleButtonEvents(final Button button,final GEvent event){ 
		handleButtonEvents(((IButton)button));
	}

	public void handleButtonEvents(final ImageButton button,final GEvent event){ 
		handleButtonEvents(((IButton)button));
	}

	public void handlePanelEvents(final GPanel panel,final GEvent event){/* Not called */ }

	public void handleSliderEvents(final GValueControl slider,final GEvent event) { /* Not called */ }

	private void initializeClassifiers(){
		new AlphabetClassifier(Handedness.RIGHT);
		new NumberClassifier(Handedness.RIGHT);
		new NumberClassifier(Handedness.LEFT);
		num2Classifier=new SignClassifier(null, "num2");
		//		new SignClassifier(Handedness.LEFT, "alpha");
		//		new SignClassifier(Handedness.LEFT, "num");
	}
}