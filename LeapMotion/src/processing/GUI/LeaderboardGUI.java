package processing.GUI;

import java.util.ArrayList;

import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import leaderboard.HighScoreManager;
import leaderboard.Score;
import processing.core.PApplet;

public class LeaderboardGUI extends AbstractGeneralGUI{
	
	private GButton homeButton;
	
	public LeaderboardGUI(PApplet page) {
		super(page);
	}
	
	public void homeButtonClicked(GButton button, GEvent event){
		getPage().switchToMainMenuGUI();
	}
	
	@Override
	public boolean isWarningRequired(){
		return false;
	}
	
	@Override
	protected void createGUI(){
		  getPage().background(230);
		  homeButton = new GButton(getPage(), 736, 38, 131, 53);
		  homeButton.setText("Home");
		  homeButton.setTextBold();
		  homeButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		  homeButton.setEnabled(true);
		  homeButton.addEventHandler(this, "homeButtonClicked");
		  getPage().textSize(50);
		  getPage().text("Leaderboard", 50, 60);
		  getPage().textSize(40);
		  getPage().text("Name", 50, 180);
		  getPage().text("Score", 500, 180);
		  getPage().textSize(30);
		  int position=240;
		  getPage().line(0, position-45, 575, position-45);
		  ArrayList<Score> scores= new HighScoreManager().getScores();
		  for(int i=0; i< scores.size();i++) {
			  Score score=scores.get(i);
			  position=240+60*i;
			  getPage().text(score.getName(), 50, 240+60*i);
			  getPage().text(score.getScore(), 500, 240+60*i);
			  getPage().line(0, position+10, 575, position+10);
		  }
		  getPage().turnOnLeapMouseControl();
	}
	
	@Override
	public void dispose(){
		super.dispose();
		objectDisposal(homeButton);
	}
	
}
