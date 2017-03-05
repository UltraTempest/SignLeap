package gui;

import java.awt.Font;
import java.util.Map;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

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
	public static final String imageType=".jpg";
	private final String imageName=SignClassifier.language +  "/" + getPage().getHand() +"/";
	protected int currentLetterPosition=0;
	private PImage img;
	protected final GTextField signInstruction;
	private final GSlider slider;
	private GLabel sliderAcceptance;
	private final GLabel sliderAcceptance2;
	private final GLabel sliderExplanation;
	protected int attempts=500;
	private final String attemptsText="Skip in %s more attempts";
	private final GLabel attemptsLabel;
	private SignClassifier classifier;
	private IHandData handData= new OneHandData(leap);
	private String previousChar;
	protected final String[] array;
	private int renderCount;

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

		slider = new GSlider(page,  931, 49, 568, 110,(float) 10.0);
		slider.setRotation(PConstants.PI/2, GControlMode.CORNER);
		slider.setNumberFormat(G4P.INTEGER, 0);
		slider.setLimits(0,0,50);
		slider.setOpaque(false);
		//slider.setShowValue(true);
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
		sliderExplanation = new GLabel(page, 735, 19, 215, 22);
		sliderExplanation.setText("indicates point when your sign is accepted");
		sliderExplanation.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		sliderExplanation.setOpaque(false);

		attemptsLabel = new GLabel(page, 10, 119, 109, 282);
		attemptsLabel.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		attemptsLabel.setText(String.format(attemptsText,attempts));
		attemptsLabel.setTextBold();
		attemptsLabel.setLocalColorScheme(GCScheme.RED_SCHEME);
		attemptsLabel.setOpaque(true);
		attemptsLabel.setFont(new Font("Dialog", Font.PLAIN, 30));
		//TODO add button to skip or add it after certain amount of attempts
	}

	private void setSliderAcceptance(){
		int y=325;
		if(difficulty==Page.MEDIUM)
			y= 260;
		else if(difficulty==Page.HARD)
			y= 155;
		sliderAcceptance = new GLabel(getPage(),846, y, 59, 15);
	}

	private void setProgressBarValue(final float value){
		slider.setValue(50-value);
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
	}

	private void signCharacters(){	
		final Frame frame = leap.frame();
		if(frame.hands().count()>0 && !getPage().isWarningDisplayed()){
			Map<String, Float> data=handData.getHandPosition();
			if(data!=null){
				final double score = classifier.score(data,previousChar);
				setProgressBarValue((float) (score*100));	
				if(score>=difficulty || attempts==0){
					displayNextCharacter();
					attempts=500;
					classifier.resetRollingAverage();
				}
				else
					attemptsLabel.setText(String.format(attemptsText,--attempts));
			}
			else
				setProgressBarValue((float)0);
		}
		else
			setProgressBarValue((float)0);
	}

	protected void displayNextCharacter(){
		++currentLetterPosition;
		signCharacterChange();
		//To be implemented by subclass
	}

	public final void signCharacterChange(){
		if(currentLetterPosition<array.length){
			final String currentCharacter= array[currentLetterPosition];
			final String image=imageName+currentCharacter+imageType;
			updateSignCharactersGUI(currentCharacter, image);
			if(!currentCharacter.equals(previousChar)){
				previousChar=currentCharacter;
				try {
					Thread.sleep(500);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void render() {
		super.render();
		if(++renderCount==3)
			signCharacterChange();
		signCharacters();
	}

	@Override
	public void dispose(){
		objectDisposal(slider, signInstruction,sliderAcceptance, sliderAcceptance2, 
				sliderExplanation, attemptsLabel);
	}
}
