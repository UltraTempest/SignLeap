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
import g4p_controls.GAbstractControl;
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
	private int userScore=0;
	private final Timer timer = new Timer();
	private int currentTime=0;
	private SignClassifier classifier;
	private IHandData handData= new OneHandData(leap);
	private String previousChar;
	private final String imageName=SignClassifier.language +  "/" + getPage().getHand() +"/";
	private final String[] array;

	protected void createGUI(){
		Page page=getPage();
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
		//slider.setLimits((float)50.0, (float)0.0, (float)100.0);
		slider.setLimits((float)0, (float)0, (float)50);
		slider.setNumberFormat(G4P.DECIMAL, 2);
		slider.setOpaque(false);
		//slider.setShowValue(true);
		slider.setShowValue(false);
		page.turnOffLeapMouseControl();
		time();
	}

	private void setProgressBarValue(float value){
		slider.setValue(value);
	}

	@Override
	protected void objectDisposal(final GAbstractControl object){
		object.setVisible(false);
		object.dispose();
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
		objectDisposal(slider);
		objectDisposal(signInstruction);
		objectDisposal(scoreTimerText);
	}
}
