package processing.GUI;

import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GPanel;
import g4p_controls.GTextField;
import leaderboard.HighScoreManager;
import processing.core.PApplet;

public class GameOverGUI extends AbstractGeneralGUI{
	
	private int score=0;
	
	public GameOverGUI(PApplet page, int score) {
		super(page);
		this.score=score;
	}

	private GPanel gameOverPanel; 
	private GTextField userInputName; 
	private GButton submitButton; 
	private GLabel label1; 
	private GLabel label2; 
	private GLabel label3; 


	public void submitButtonClicked(GButton source, GEvent event) {
		new HighScoreManager().addScore(userInputName.getText(), score);
		getPage().switchToLeaderboardGUI();
	} 


	// Create all the GUI controls. 
	@Override
	protected void createGUI(){
	  gameOverPanel = new GPanel(getPage(), 245, 182, 442, 232, "                                                             Game Over!");
	  gameOverPanel.setCollapsible(false);
	  gameOverPanel.setText("                                                             Game Over!");
	  gameOverPanel.setTextBold();
	  gameOverPanel.setLocalColorScheme(GCScheme.CYAN_SCHEME);
	  gameOverPanel.setOpaque(true);
	  userInputName = new GTextField(getPage(), 71, 148, 192, 21, G4P.SCROLLBARS_NONE);
	  userInputName.setOpaque(true);
	  submitButton = new GButton(getPage(), 294, 148, 63, 23);
	  submitButton.setText("Submit");
	  submitButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
	  submitButton.addEventHandler(this, "submitButtonClicked");
	  label1 = new GLabel(getPage(), 87, 61, 233, 23);
	  label1.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
	  label1.setText("You scored " + score +  " points!");
	  label1.setOpaque(false);
	  label2 = new GLabel(getPage(), 87, 88, 235, 20);
	  label2.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
	  label2.setText("What is your name?");
	  label2.setOpaque(false);
	  label3 = new GLabel(getPage(), 65, 126, 52, 23);
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

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(gameOverPanel);
	}
}
