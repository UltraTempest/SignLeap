package processing.GUI;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import com.leapmotion.leap.Controller;

import g4p_controls.G4P;
import g4p_controls.GAbstractControl;
import g4p_controls.GSlider;
import g4p_controls.GTextField;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class AbstractSignCharacterGUI extends AbstractGUI{
	
	public AbstractSignCharacterGUI(PApplet page) {
		super(page);
	}
    
	protected Controller leap;
	protected final String imageType=".jpg";
	protected int currentLetterPosition=0;
	private PImage img;
	protected GTextField signInstruction;
	private GTextField scoreTimerText;
	private GSlider slider;
	private int userScore=0;
	private final Timer timer = new Timer();
	private int currentTime=0;
	
	protected void createGUI(String classifierToSet){
		  getPage().setClassifier(classifierToSet);
		  leap=getPage().getLeap();
		  signInstruction = new GTextField(getPage(), 217, 513, 492, 81, G4P.SCROLLBARS_NONE);
		  signInstruction.setOpaque(false);
		  signInstruction.setFont(new Font("Dialog", Font.PLAIN, 58));
		  signInstruction.setTextEditEnabled(false);
		  scoreTimerText = new GTextField(getPage(), 259, 28, 331, 20);
		  scoreTimerText.setText("Score:                       Time left:");
		  scoreTimerText.setOpaque(false);
		  scoreTimerText.setFont(new Font("Dialog", Font.PLAIN, 16));
		  scoreTimerText.setTextEditEnabled(false);
		  slider = new GSlider(getPage(), 637, 14, 249, 46, (float) 10.0);
		  slider.setLimits((float)50.0, (float)0.0, (float)100.0);
		  slider.setNumberFormat(G4P.DECIMAL, 2);
		  slider.setOpaque(false);
		  slider.setShowValue(true);
		  getPage().turnOffLeapMouseControl();
		  time();
	}
	
	protected void setProgressBarValue(float value){
		slider.setValue(value);
	}
	
	 protected void objectDisposal(GAbstractControl object){
		  object.setVisible(false);
		  object.dispose();
	  }
	
	protected void time(){
		timer.scheduleAtFixedRate(new TimerTask() {
            int i = 62;//defined for a 60 second countdown
            public void run() {
            	i--;
                currentTime=i;
                if (i< 0)
                    timer.cancel();
            }
        }, 0, 1000);
	}
	
	protected void checkIfTimerExpired(){
		if(currentTime!=0)
			return;
		getPage().switchToGameOverGUI(userScore);
	}
	
	protected void incrementUserScore(){
		this.userScore+=1000;
	}
	
	protected int getCurrentUserScore(){
		return this.userScore;
	}
	
	protected void updateSignCharactersGUI(char currentLetter, String imageName){
		img=getPage().loadImage(imageName);
		getPage().image(img,136, 65, 657, 408);
		signInstruction.setText("Sign the letter: " + Character.toUpperCase(currentLetter) +" " + currentLetter);
		scoreTimerText.setText("Score:        " + userScore + "               Time left:" + currentTime);
	}
	
	@Override
	public void dispose(){
		super.dispose();
		objectDisposal(slider);
		objectDisposal(signInstruction);
		objectDisposal(scoreTimerText);
	}
}
