package processing;

import java.awt.Font;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import button.Button;
import button.IButton;
import button.ImageButton;
import classifier.AlphabetClassifier;
import classifier.NumberClassifier;
import classifier.SignClassifier;
import controller.LeapMouseListener;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GPanel;
import g4p_controls.GValueControl;
import gui.GUIManager;
import gui.IGUI;
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
	private GLabel warning;
	private final LeapMouseListener leapListen= new LeapMouseListener();

	private SignClassifier alphaClassifier;
	private SignClassifier numClassifier;
	private SignClassifier num2Classifier;

	public static final double EASY=0.25;
	public static final double MEDIUM=0.3;
	public static final double HARD=0.4;
	private final double difficulty=MEDIUM;

	private IGUI currentGUI;
	private GUIManager guiManage;
	
	private String username;

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
		warning =  new GLabel(this,220,40,475,27);
		warning.setTextBold();
		warning.setLocalColorScheme(GCScheme.RED_SCHEME);
		warning.setVisible(false);
		warning.setFont(new Font("Monospaced", Font.PLAIN, 12));
		warning.setOpaque(true);
		guiManage= new GUIManager(this);
		guiManage.setWelcomeGUI();
	}

	@Override
	public void draw(){
		currentGUI.render();
		//println(frameRate);
	}

	public void changeGUI(final IGUI gui){
		currentGUI=gui;
		warning.setVisible(false);
	} 
	
	public void currentGUIDisposal(){
		currentGUI.dispose();
		setDefaultBackground();
	}
	
	public IGUI getCurrentGUI(){
		return currentGUI;
	}
	
	public GUIManager getGUIManager(){
		return guiManage;
	}
	
	public void setUsername(final String username){
		this.username=username;
	}
	
	public String getUsername(){
		return username;
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
		warning.setText(String.format(leapWarning,hand));
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

	public void renderLeapWarning(){
		if(!handInfo.isCorrectHandPlacedOverLeap(hand))
			warning.setVisible(true);
		else
			warning.setVisible(false);
	}

	public boolean isWarningDisplayed() {
		return warning.isVisible();
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
		button.cancelTimerTask();
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