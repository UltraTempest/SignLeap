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
import recording.IHandData;
import recording.OneHandData;

public class WelcomeGUI extends AbstractGeneralGUI{

	public WelcomeGUI(PApplet page) {
		super(page);
		handData=new OneHandData(((Page) page).getLeap());
	}

	private GLabel PreferredHandText; 
	private PImage img;
	private int frameCount;
	private final IHandData handData;
	
	private void changeStateIfRequired(){
		  Page page= getPage();
		  if(handData.checkIfHandPlacedOverLeap()){
			  frameCount++;
			  if(frameCount<50)
				  return;
			  page.setHand(handData.GetHandedness());
			  if(!checkLeaderBoardFileExistence())
				  new IntroductionCommand(page).process();
			  else
				  new MainMenuCommand(page).process();
		  }
	}
	
	private boolean checkLeaderBoardFileExistence(){
		return new File(HighScoreManager.HIGHSCORE_FILE).exists();
	}
	
	@Override
	protected void createGUI(){
		  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		  Page page = getPage();
		  page.getSurface().setTitle("Sketch Window");  
		  PreferredHandText = new GLabel(page, 3, 497, 950, 139);
		  PreferredHandText.setTextAlign(GAlign.CENTER, GAlign.BOTTOM);
		  PreferredHandText.setText("Place your preferred hand over the Leap Motion to begin!");
		  PreferredHandText.setFont(new Font("Dialog", Font.PLAIN, 58));
		  PreferredHandText.setOpaque(false);
		  String logoFile="ISL_logo.png";
		  img=page.loadImage(logoFile);
		  page.image(img,31, 7, 886, 482);
		}
	
	@Override
	public boolean isWarningRequired(){
		return false;
	}

	@Override
	public void render() {
		super.render();
		changeStateIfRequired();
	}

	@Override
	public void dispose() {
		objectDisposal(PreferredHandText);
	}
}
