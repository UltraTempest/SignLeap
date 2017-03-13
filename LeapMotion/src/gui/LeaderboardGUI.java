package gui;

import java.awt.Font;
import java.util.ArrayList;

import button.Button;
import command.MainMenuCommand;
import g4p_controls.GCScheme;
import leaderboard.HighScoreManager;
import leaderboard.Score;
import processing.Page;
import processing.core.PApplet;

public final class LeaderboardGUI extends AbstractGeneralGUI{

	private final Button homeButton;
	private final ArrayList<Score> scores;

	public LeaderboardGUI(final PApplet papplet,final  HighScoreManager scoreManager) {
		super(papplet);
		final Page page = getPage();
		scores= scoreManager.getScores();
		homeButton = new Button(page, 730, 5, 200, 142, new MainMenuCommand(page));
		homeButton.setText("Home");
		homeButton.setTextBold();
		homeButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		homeButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
		renderLeaderBoard();
	}

	private void renderLeaderBoard(){
		final Page page=getPage();
		page.fill(PApplet.RGB);
		page.textSize(50);
		page.text("Leaderboard", 50, 130);
		page.textSize(40);
		page.text("Name", 50, 180);
		page.text("Score", 500, 180);
		page.textSize(30);
		int position=240;
		page.line(0, position-45, 575, position-45);
		for(int i=0; i< scores.size();i++) {
			final Score score=scores.get(i);
			position=240+60*i;
			page.text(score.getName(), 50, 240+60*i);
			page.text(score.getScore(), 500, 240+60*i);
			page.line(0, position+10, 575, position+10);
		}
		page.setTextSizeToDefault();
	}

	@Override
	public void dispose(){
		objectDisposal(homeButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(homeButton);
		renderLeaderBoard();
	}
}
