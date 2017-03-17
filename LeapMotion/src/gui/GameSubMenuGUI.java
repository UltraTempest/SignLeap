package gui;

import java.awt.Font;

import button.Button;
import command.SignAlphabetCommand;
import command.ICommand;
import command.LeaderboardCommand;
import command.SubMenuCommand;
import g4p_controls.GCScheme;
import leaderboard.AlphabetHighScoreManager;
import leaderboard.HighScoreManager;
import leaderboard.NumbersHighScoreManager;
import processing.Page;
import processing.core.PApplet;

public final class GameSubMenuGUI extends AbstractMenuGUI{
	private final Button playButton; 
	private final Button leaderboardButton;
	private final Button backButton;

	public GameSubMenuGUI(final PApplet papplet, final ICommand command) {
		super(papplet);
		final Page page=getPage();

		playButton = new Button(page,144, 80, 685, 186,command);
		playButton.setText("Play");
		playButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		playButton.setLocalColorScheme(GCScheme.GREEN_SCHEME); 
		
		HighScoreManager scoreManager;
		if(command instanceof SignAlphabetCommand)
			scoreManager = new AlphabetHighScoreManager();
		else
			scoreManager = new NumbersHighScoreManager();
		
		leaderboardButton = new Button(page,144, 312, 685, 186, new LeaderboardCommand(page, scoreManager));
		leaderboardButton.setText("Leaderboard");
		leaderboardButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		leaderboardButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);

		backButton = new Button(page, 5, 516, 320, 113, new SubMenuCommand(page,command));
		backButton.setText("Back");
		backButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		backButton.setTextBold();
		backButton.setTextItalic();
		backButton.setLocalColorScheme(GCScheme.RED_SCHEME);
	} 

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(backButton, playButton, leaderboardButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(backButton, playButton,leaderboardButton);
	}
}
