package gui;

import java.awt.Font;
import java.util.Map;
import com.leapmotion.leap.Controller;
import classifier.SignClassifier;
import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GControlMode;
import g4p_controls.GLabel;
import g4p_controls.GSlider;
import g4p_controls.GTextField;
import processing.Page;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import recording.IHandData;
import recording.OneHandData;

public abstract class AbstractSignCharacterGUI extends AbstractGUI{

	private final double difficulty=getPage().getDifficulty();
	private final Controller leap=getPage().getLeap();
	private final String imageName=SignClassifier.language +"/%s.jpg";
	protected int currentLetterPosition=0;

	private PImage img;
	protected final GTextField signInstruction;
	private final GSlider similaritySlider;
	private final GSlider attemptSlider;
	private GLabel sliderAcceptance;
	private final GLabel sliderAcceptance2;
	private final GLabel sliderExplanation;
	private final GLabel similarityLabel;
	private final GLabel attemptsLabel;

	private final int initialNumberOfAttempts=500;
	protected int attempts=initialNumberOfAttempts;
	private boolean firstPass=false;
	private int renderCount;

	private SignClassifier classifier;
	private IHandData handData= new OneHandData(leap);
	private String currentChar;
	protected final String[] array;

	public AbstractSignCharacterGUI(final PApplet papplet,
			final SignClassifier signClassifier,final String[] array) {
		super(papplet);
		this.classifier=signClassifier;
		this.array=array;
		final Page page=getPage();
		page.turnOffLeapMouseControl();
		signInstruction = new GTextField(page, 217, 513, 535, 81, G4P.SCROLLBARS_NONE);
		signInstruction.setOpaque(false);
		signInstruction.setFont(new Font("Dialog", Font.PLAIN, 58));
		signInstruction.setTextEditEnabled(false);

		similaritySlider = new GSlider(page, 918, 135, 354, 72,(float) 10.0);
		similaritySlider.setRotation(PConstants.PI/2, GControlMode.CORNER);
		similaritySlider.setNumberFormat(G4P.INTEGER, 0);
		similaritySlider.setLimits(0,0,100);
		similaritySlider.setOpaque(false);
		//similaritySlider.setShowValue(true);

		attemptSlider = new GSlider(page, 85, 135, 354, 72, (float) 10.0);
		attemptSlider.setRotation(PConstants.PI/2, GControlMode.CORNER);
		attemptSlider.setLimits(0, 0, initialNumberOfAttempts);
		attemptSlider.setNumberFormat(G4P.INTEGER, 0);
		attemptSlider.setOpaque(false);

		setSliderAcceptance();
		sliderAcceptance.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		sliderAcceptance.setText("_______");
		sliderAcceptance.setTextBold();
		sliderAcceptance.setLocalColorScheme(GCScheme.RED_SCHEME);
		sliderAcceptance.setOpaque(false);
		sliderAcceptance2 = new GLabel(page, 704, 12, 32, 30);
		sliderAcceptance2.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		sliderAcceptance2.setText("___");
		sliderAcceptance2.setTextBold();
		sliderAcceptance2.setLocalColorScheme(GCScheme.RED_SCHEME);
		sliderAcceptance2.setOpaque(false);

		sliderExplanation = new GLabel(page, 738, 7, 215, 48);
		sliderExplanation.setText("indicates point when your sign is accepted");
		sliderExplanation.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		sliderExplanation.setOpaque(false);

		similarityLabel = new GLabel(page, 806, 80, 147, 65);
		similarityLabel.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		similarityLabel.setText("Similarity meter");
		similarityLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
		similarityLabel.setOpaque(false);

		attemptsLabel = new GLabel(page, 2, 35, 139, 125);
		attemptsLabel.setText("Attempts remaining");
		attemptsLabel.setLocalColorScheme(GCScheme.RED_SCHEME);
		attemptsLabel.setOpaque(false);
		attemptsLabel.setFont(new Font("Monospaced", Font.PLAIN, 22));
	}

	private void setSliderAcceptance(){
		int y=305;
		if(difficulty==Page.MEDIUM)
			y= 265;
		else if(difficulty==Page.HARD)
			y= 205;
		sliderAcceptance = new GLabel(getPage(),853, y, 59, 15);
	}

	private void setSimilaritySliderValue(final float value){
		similaritySlider.setValue(100-value);
	}

	private void decrementAttemptsRemaining(){
		attemptSlider.setValue(initialNumberOfAttempts-(--attempts));
	}

	protected final void setHandData(final IHandData handData){
		this.handData=handData;
	}

	protected final void setClassifier(final SignClassifier classifier){
		this.classifier=classifier;
	}

	protected void updateSignCharactersGUI(final String currentCharacter, final String imageName){
		final Page page = getPage();
		img=page.loadImage(imageName);
		page.image(img,136, 65, 657, 408);
		//To be implemented by subclass
	}

	private void signCharacters(){	
		isPauseRequired();
		if(getPage().isLeapSeesYouMessageDisplayed()){
			final Map<String, Float> data=handData.getHandPosition();
			if(data!=null){
				final double score = classifier.score(data,currentChar);
				setSimilaritySliderValue((float) (score*100));
				if(score>=difficulty || attempts==0)
					if(firstPass)
						signAccepted();
					else
						firstPass=true;
				else
					decrementAttemptsRemaining();
			}
			else
				setSimilaritySliderValue((float)0);
		}
		else
			setSimilaritySliderValue((float)0);
	}

	private void signAccepted() {
		attempts=501;
		decrementAttemptsRemaining();
		classifier.resetRollingAverage();
		displayNextCharacter();
		firstPass=false;
	}

	private void isPauseRequired() {
		if(attempts==500)
			try {
				Thread.sleep(1000);
				decrementAttemptsRemaining();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
	}

	protected void displayNextCharacter(){
		++currentLetterPosition;
		updateSignCharacterDisplay();
		//To be implemented by subclass
	}

	public final void updateSignCharacterDisplay(){
		if(!(currentLetterPosition<array.length)) return;
		currentChar= array[currentLetterPosition];
		updateSignCharactersGUI(currentChar, String.format(imageName, currentChar));
	}

	@Override
	public void render() {
		if(renderCount==3){
			getPage().setDefaultBackground();
			updateSignCharacterDisplay();
		}
		signCharacters();
		++renderCount;
	}

	@Override
	public void dispose(){
		objectDisposal(similaritySlider, signInstruction,sliderAcceptance, sliderAcceptance2, 
				sliderExplanation, attemptsLabel, similarityLabel, attemptSlider);
	}
}
