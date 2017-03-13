package gui;

import java.awt.Font;
import java.io.File;
import command.IntroductionCommand;
import command.MainMenuCommand;
import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import leaderboard.HighScoreManager;
import processing.Page;
import processing.core.PApplet;
import processing.core.PImage;
import recording.AbstractHandData.Handedness;
import recording.IHandData;
import recording.OneHandData;

public final class WelcomeGUI extends AbstractGeneralGUI{

	private final GLabel PreferredHandText; 
	private final PImage img;
	private final IHandData handData;

	public WelcomeGUI(final PApplet papplet) {
		super(papplet);
		final Page page = getPage();
		handData=new OneHandData(page.getLeap());
		G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		G4P.messagesEnabled(false);
		G4P.setMouseOverEnabled(false);
		PreferredHandText = new GLabel(page, 3, 497, 950, 139);
		PreferredHandText.setTextAlign(GAlign.CENTER, GAlign.BOTTOM);
		PreferredHandText.setText("Place your preferred hand over the Leap Motion to begin!");
		PreferredHandText.setFont(new Font("Dialog", Font.PLAIN, 58));
		PreferredHandText.setOpaque(false);
		img=page.loadImage("ISL_logo.png");
		page.image(img,31, 7, 886, 482);
	}

	private void changeStateIfRequired(){
		final Page page= getPage();
		if(handData.isHandPlacedOverLeap()){
			final Handedness hand=handData.getHandednessWithConfidence();
			if(hand==null) return;
			page.setHand(hand);
			if(!checkLeaderBoardFileExistence())
				new IntroductionCommand(page,0).process();
			else
				new MainMenuCommand(page).process();
		}
	}

	private boolean checkLeaderBoardFileExistence(){
		return new File(HighScoreManager.ALPHA_HIGHSCORE_FILE).exists() 
				|| new File(HighScoreManager.NUM_HIGHSCORE_FILE).exists();
	}

	@Override
	public void render() {
		changeStateIfRequired();
	}

	@Override
	public void dispose() {
		objectDisposal(PreferredHandText);
	}
}
