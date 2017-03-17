package gui;

import java.awt.Font;

import button.Button;
import command.LeaderboardCommand;
import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GTextField;
import leaderboard.AlphabetHighScoreManager;
import leaderboard.HighScoreManager;
import leaderboard.NumbersHighScoreManager;
import processing.Page;
import processing.core.PApplet;

public final class GameOverGUI extends AbstractGeneralGUI{

	private final int score;
	private final GTextField userInputName; 
	private final Button submitButton; 
	private final GLabel label4; 
	private final GLabel label5; 
	private final HighScoreManager scoreManager;

	public GameOverGUI(final PApplet papplet,final int score,final boolean leaderboardFlag) {
		super(papplet);
		final Page page=getPage();
		this.score=score;
		if(leaderboardFlag)
			scoreManager= new NumbersHighScoreManager();
		else
			scoreManager=new AlphabetHighScoreManager();
		final int fontSize=35;
		userInputName = new GTextField(page, 23, 372, 597, 150, G4P.SCROLLBARS_NONE);
		userInputName.setOpaque(true);
		userInputName.setFocus(true);
		userInputName.setText(page.getUsername());
		userInputName.setFont(new Font("Monospaced", Font.PLAIN, 70));
		userInputName.addEventHandler(this, "userInputFieldEventHandle");
		submitButton = new Button(page, 704, 357, 220, 209,new LeaderboardCommand(page,this));
		submitButton.setText("Submit");
		submitButton.setTextBold();
		submitButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		submitButton.setFont(new Font("Monospaced", Font.PLAIN, 35));
		label4 = new GLabel(page, 227, 67, 353, 128);
		label4.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		label4.setText("You scored " + score +  " points!");
		label4.setTextBold();
		label4.setFont(new Font("Monospaced", Font.PLAIN,fontSize));
		label4.setOpaque(false);
		label5 = new GLabel(page, 42, 262, 500, 153);
		label5.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		label5.setText("What is your username?");
		label5.setTextBold();
		label5.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
		label5.setOpaque(false);
	}

	public void submitButtonClicked(){	
		scoreManager.addScore(userInputName.getText(), score);
		getPage().getGUIManager().setLeaderboardGUI(scoreManager);
	} 
	
	public void userInputFieldEventHandle(final GTextField source,final GEvent event) { 
		if(event.toString().equals("ENTERED"))
			submitButtonClicked();
	}

	@Override
	public void dispose() {
		objectDisposal(userInputName,submitButton,label4,label5);
	}

	@Override
	public void render() {
		super.render();
		handleMouseOverButton(submitButton);
	}
}
