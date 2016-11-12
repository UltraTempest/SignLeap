package processing.GUI;

import java.awt.Font;
import java.io.File;

import com.leapmotion.leap.Controller;
import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import leaderboard.HighScoreManager;
import processing.GUIFactory;
import processing.core.PApplet;
import processing.core.PImage;
import recording.HandData;

public class WelcomeGUI extends AbstractGeneralGUI{

	public WelcomeGUI(PApplet page) {
		super(page);
	}

	private GLabel PreferredHandText; 
	private PImage img;
	
	private void changeStateIfRequired(){
		  HandData handInfo = new HandData();
		  Controller leap = getPage().getLeap();
		  if(handInfo.checkIfHandPlacedOverLeap(getPage().getLeap())){
			  getPage().setHand(handInfo.GetHandedness(leap.frame().hands().frontmost()));
			  if(!checkLeaderBoardFileExistence())
			  getPage().stateSwitch(new GUIFactory(getPage()).createIntroductionGUI());
			  else
				  getPage().stateSwitch(new GUIFactory(getPage()).createMainMenuGUI());
		  }
	}
	
	private boolean checkLeaderBoardFileExistence(){
		return new File(HighScoreManager.HIGHSCORE_FILE).exists();
	}
	
	@Override
	protected void createGUI(){
		  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		  getPage().getSurface().setTitle("Sketch Window");  
		  PreferredHandText = new GLabel(getPage(), 3, 497, 950, 139);
		  PreferredHandText.setTextAlign(GAlign.CENTER, GAlign.BOTTOM);
		  PreferredHandText.setText("Place your preferred hand over the Leap Motion to begin!");
		  PreferredHandText.setFont(new Font("Dialog", Font.PLAIN, 58));
		  PreferredHandText.setOpaque(false);
		  String logoFile="ISL_logo.png";
		  img=getPage().loadImage(logoFile);
		  getPage().image(img,31, 7, 886, 482);
		}

	@Override
	public void render() {
		super.render();
		changeStateIfRequired();
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(PreferredHandText);
	}
}
