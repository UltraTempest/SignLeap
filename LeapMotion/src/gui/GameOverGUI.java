package gui;

import command.LeaderboardCommand;
import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GPanel;
import g4p_controls.GTextField;
import leaderboard.HighScoreManager;
import processing.Page;
import processing.core.PApplet;

public final class GameOverGUI extends AbstractGeneralGUI{

	private final int score;
	private final GPanel gameOverPanel; 
	private final GTextField userInputName; 
	private final GButton submitButton; 
	private final GLabel label1; 
	private final GLabel label2; 
	private final GLabel label3; 

	public GameOverGUI(final PApplet papplet,final int score) {
		super(papplet);
		final Page page=getPage();
		this.score=score;
		page.setDefaultBackground();
		gameOverPanel = new GPanel(page, 245, 182, 442, 232, "                                                        Game Over!");
		gameOverPanel.setDraggable(false);
		gameOverPanel.setCollapsible(false);
		gameOverPanel.setTextBold();
		gameOverPanel.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		gameOverPanel.setOpaque(true);
		gameOverPanel.clearDragArea();
		userInputName = new GTextField(page, 71, 148, 192, 21, G4P.SCROLLBARS_NONE);
		userInputName.setOpaque(true);
		userInputName.addEventHandler(this, "userInputFieldEventHandle");
		submitButton = new GButton(page, 294, 148, 63, 23);
		submitButton.setText("Submit");
		submitButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		submitButton.addEventHandler(this, "submitButtonClicked");
		label1 = new GLabel(page, 87, 61, 233, 23);
		label1.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		label1.setText("You scored " + score +  " points!");
		label1.setOpaque(false);
		label2 = new GLabel(page, 87, 88, 235, 20);
		label2.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		label2.setText("What is your name?");
		label2.setOpaque(false);
		label3 = new GLabel(page, 65, 126, 52, 23);
		label3.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		label3.setText("Name:");
		label3.setTextBold();
		label3.setOpaque(false);
		gameOverPanel.addControl(userInputName);
		gameOverPanel.addControl(submitButton);
		gameOverPanel.addControl(label1);
		gameOverPanel.addControl(label2);
		gameOverPanel.addControl(label3);
	}

	public void submitButtonClicked(final GButton source,final GEvent event){
		new HighScoreManager().addScore(userInputName.getText(), score);
		new LeaderboardCommand(getPage()).process();
	} 

	public void userInputFieldEventHandle(final GTextField source,
			final GEvent event) { 
		if(event.toString().equals("ENTERED"))
			submitButtonClicked(null, null);
	}

	@Override
	public void dispose() {
		objectDisposal(gameOverPanel);
	}

	@Override
	public void render() {
		//No action needed
	}
}
