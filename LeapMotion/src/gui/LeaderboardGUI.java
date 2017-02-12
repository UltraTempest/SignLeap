package gui;

import java.util.ArrayList;

import button.Button;
import command.MainMenuCommand;
import g4p_controls.GCScheme;
import leaderboard.HighScoreManager;
import leaderboard.Score;
import processing.Page;
import processing.core.PApplet;

public final class LeaderboardGUI extends AbstractGeneralGUI{

	private Button homeButton;

	public LeaderboardGUI(final PApplet page) {
		super(page);
	}

	@Override
	protected void createGUI(){
		final Page page = getPage();
		homeButton = new Button(page, 736, 38, 131, 53, new MainMenuCommand(page));
		homeButton.setText("Home");
		homeButton.setTextBold();
		homeButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		homeButton.addEventHandler(page, "handleButtonEvents");
		renderLeaderBoard(page);
		page.turnOnLeapMouseControl();
	}

	private void renderLeaderBoard(final PApplet page){
		page.fill(PApplet.RGB);
		page.textSize(50);
		page.text("Leaderboard", 50, 130);
		page.textSize(40);
		page.text("Name", 50, 180);
		page.text("Score", 500, 180);
		page.textSize(30);
		int position=240;
		page.line(0, position-45, 575, position-45);
		final ArrayList<Score> scores= new HighScoreManager().getScores();
		for(int i=0; i< scores.size();i++) {
			final Score score=scores.get(i);
			position=240+60*i;
			page.text(score.getName(), 50, 240+60*i);
			page.text(score.getScore(), 500, 240+60*i);
			page.line(0, position+10, 575, position+10);
		}
	}

	@Override
	public void dispose(){
		objectDisposal(homeButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(homeButton);
		renderLeaderBoard(getPage());
	}
}
