package processing.GUI;

import java.awt.Font;

import com.leapmotion.leap.Frame;

import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
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
	
	private void checkIfHandPlacedOverLeap(){
		  Frame frame = getPage().getLeap().frame();
		  if(frame.hands().count()>0){
			  getPage().setHand(new HandData().GetHandedness(frame.hands().frontmost()));
			  getPage().stateSwitch(new GUIFactory(getPage()).createMainMenuGUI());
		  }
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
		checkIfHandPlacedOverLeap();
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(PreferredHandText);
	}
}
