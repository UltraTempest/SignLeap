package gui;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import classifier.SignClassifier;
import command.GameOverCommand;
import g4p_controls.GTextField;
import processing.core.PApplet;

public abstract class AbstractTimedSignCharacterGUI extends AbstractSignCharacterGUI{
	
	private int userScore;
	private final Timer timer = new Timer();
	private int currentTime;
	private final GTextField scoreTimerText;
	
	public AbstractTimedSignCharacterGUI(final PApplet page,final SignClassifier signClassifier,final String[] array) {
		super(page, signClassifier, array);
		scoreTimerText = new GTextField(page, 259, 6, 331, 20);
		scoreTimerText.setText("Score:                       Time left:");
		scoreTimerText.setOpaque(false);
		scoreTimerText.setFont(new Font("Dialog", Font.PLAIN, 16));
		scoreTimerText.setTextEditEnabled(false);
		time();
		//TODO pause timer if hand not over leap. Display PAUSED on screen
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
	
	@Override
	protected void updateSignCharactersGUI(final String currentCharacter, final String imageName){
		super.updateSignCharactersGUI(currentCharacter, imageName);
		scoreTimerText.setText("Score:        " + userScore +
				"               Time left:" + currentTime);
	}
	
	@Override
	protected void displayNextCharacter(){
		super.displayNextCharacter();
		incrementUserScore();
		//To be implemented by subclass
	}
	
	@Override
	public final void dispose(){
		super.dispose();
		objectDisposal(signInstruction, scoreTimerText);
	}
}
