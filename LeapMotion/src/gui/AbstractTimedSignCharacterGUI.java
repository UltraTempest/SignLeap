package gui;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import classifier.SignClassifier;
import command.GameOverCommand;
import g4p_controls.GTextField;
import processing.core.PApplet;

public abstract class AbstractTimedSignCharacterGUI extends AbstractSignCharacterGUI{

	private String userScore="0000";
	private final Timer timer = new Timer();
	private final GTextField scoreTimerText;
	private final String scoreText="Score:        %1$s           Time left:%2$d";

	public AbstractTimedSignCharacterGUI(final PApplet page,final SignClassifier signClassifier,final String[] array){
		super(page, signClassifier, array);
		scoreTimerText = new GTextField(page,  300, 6, 300, 28);
		scoreTimerText.setText("Score:0     Time left:60");
		scoreTimerText.setFont(new Font("Dialog", Font.PLAIN, 18));
		scoreTimerText.setOpaque(false);
		scoreTimerText.setTextEditEnabled(false);
		time(array[0].matches("-?\\d+(\\.\\d+)?"));  //match a number with optional '-' and decimal.);
	}

	private void time(final boolean leaderboardFlag){
		timer.scheduleAtFixedRate(new TimerTask() {
			private int i = 61;//defined for a 60 second countdown
			public void run() {
				scoreTimerText.setText(String.format(scoreText, userScore,--i));
				if (i<0){
					timer.cancel();
					new GameOverCommand(getPage(), Integer.valueOf(userScore),leaderboardFlag).process();
				}
			}
		}, 0, 1000);
	}

	private void incrementUserScore(){
		userScore= Integer.valueOf((Integer.parseInt(userScore)+1000)).toString();
	}

	@Override
	protected void updateSignCharactersGUI(final String currentCharacter, final String imageName){
		super.updateSignCharactersGUI(currentCharacter, imageName);
	}


	@Override
	protected void displayNextCharacter(){
		if(attempts>0)
			incrementUserScore();
		super.displayNextCharacter();
		//To be implemented by subclass
	}

	@Override
	public final void render(){
		super.render();
	}

	@Override
	public final void dispose(){
		super.dispose();
		objectDisposal(signInstruction, scoreTimerText);
	}
}
