package gui;

import java.awt.Font;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import classifier.SignClassifier;
import command.GameOverCommand;
import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import g4p_controls.GSlider;
import g4p_controls.GTextField;
import processing.Page;
import processing.core.PApplet;
import processing.core.PImage;
import recording.IHandData;
import recording.OneHandData;

public abstract class AbstractSignCharacterGUI extends AbstractGUI{

	public AbstractSignCharacterGUI(final PApplet page,
			final SignClassifier signClassifier,final String[] array) {
		super(page);
		this.classifier=signClassifier;
		this.array=array;
	}

	private final double difficulty=getPage().getDifficulty();
	private final Controller leap=getPage().getLeap();
	private final String imageType=".jpg";
	protected int currentLetterPosition=0;
	private PImage img;
	protected GTextField signInstruction;
	private GTextField scoreTimerText;
	private GSlider slider;
	private GLabel sliderAcceptance;
	private GLabel sliderAcceptance2;
	private GLabel sliderExplanation;
	private int userScore=0;
	private final Timer timer = new Timer();
	private int currentTime=0;
	private SignClassifier classifier;
	private IHandData handData= new OneHandData(leap);
	private String previousChar;
	private final String imageName=SignClassifier.language +  
			"/" + getPage().getHand() +"/";
	private final String[] array;

	protected void createGUI(){
		final Page page=getPage();
		signInstruction = new GTextField(page, 217, 513, 492, 81, G4P.SCROLLBARS_NONE);
		signInstruction.setOpaque(false);
		signInstruction.setFont(new Font("Dialog", Font.PLAIN, 58));
		signInstruction.setTextEditEnabled(false);
		scoreTimerText = new GTextField(page, 259, 6, 331, 20);
		scoreTimerText.setText("Score:                       Time left:");
		scoreTimerText.setOpaque(false);
		scoreTimerText.setFont(new Font("Dialog", Font.PLAIN, 16));
		scoreTimerText.setTextEditEnabled(false);
		slider = new GSlider(page, 640, 4, 249, 46, (float) 10.0);
		slider.setNumberFormat(G4P.INTEGER, 0);
		slider.setLimits(0, 0,50);
		slider.setOpaque(false);
		//slider.setShowValue(true);
		slider.setShowValue(false);
		setSliderAcceptance();
		sliderAcceptance.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		sliderAcceptance.setText("|");
		sliderAcceptance.setTextBold();
		sliderAcceptance.setLocalColorScheme(GCScheme.RED_SCHEME);
		sliderAcceptance.setOpaque(false);
		sliderAcceptance2 = new GLabel(page, 641, 2, 32, 19);
		sliderAcceptance2.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		sliderAcceptance2.setText("|");
		sliderAcceptance2.setTextBold();
		sliderAcceptance2.setLocalColorScheme(GCScheme.RED_SCHEME);
		sliderAcceptance2.setOpaque(false);
		sliderExplanation = new GLabel(page, 662, 5, 263, 18);
		sliderExplanation.setText("indicates point when your sign is accepted");
		sliderExplanation.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		sliderExplanation.setOpaque(false);
		page.turnOffLeapMouseControl();
		time();
	}

	private void setSliderAcceptance(){
		int x=740;
		if(difficulty==Page.MEDIUM)
			x= 770;
		else if(difficulty==Page.HARD)
			x= 829;
		sliderAcceptance = new GLabel(getPage(), x, 28, 33, 18);
	}

	private void setProgressBarValue(float value){
		slider.setValue(value);
	}

	private void time(){
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 61;//defined for a 60 second countdown
			public void run() {
				i--;
				currentTime=i;
				if (i<= 0){
					timer.cancel();
					new GameOverCommand(getPage(), userScore).process();
				}
			}
		}, 0, 1000);
	}

	private void incrementUserScore(){
		this.userScore+=1000;
	}

	protected void setHandData(final IHandData handData){
		this.handData=handData;
	}

	protected void setClassifier(final SignClassifier classifier){
		this.classifier=classifier;
	}

	protected void updateSignCharactersGUI(final String currentCharacter, 
			String imageName){
		super.render();
		Page page = getPage();
		img=page.loadImage(imageName);
		page.image(img,136, 65, 657, 408);
		scoreTimerText.setText("Score:        " + userScore +
				"               Time left:" + currentTime);
	}

	private void signCharacters(){	
		Frame frame = leap.frame();
		if(frame.hands().count()>0 && !getPage().isWarningDisplayed()){
			Map<String, Float> data=handData.getHandPosition();
			if(data!=null){
				double score = classifier.score(data,previousChar);
				setProgressBarValue((float) (score*100));
				if(score>=difficulty){
					displayNextCharacter();
					classifier.resetRollingAverage();
				}
			}
			else
				setProgressBarValue((float)0);
		}
		else
			setProgressBarValue((float)0);
	}

	protected void displayNextCharacter(){
		incrementUserScore();
		this.currentLetterPosition++;
		//To be implemented by subclass
	}

	@Override
	public void render() {
		String currentCharacter= array[currentLetterPosition];
		String image=imageName+currentCharacter+imageType;
		updateSignCharactersGUI(currentCharacter, image);
		if(!currentCharacter.equals(previousChar)){
			previousChar=currentCharacter;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		signCharacters();
	}

	@Override
	public void dispose(){
		objectDisposal(slider, signInstruction, scoreTimerText,
				sliderAcceptance, sliderAcceptance2, sliderExplanation);
	}
}
