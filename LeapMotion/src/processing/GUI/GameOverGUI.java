package processing.GUI;

import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GPanel;
import g4p_controls.GTextField;
import processing.core.PApplet;

public class GameOverGUI extends AbstractGUI{
	
	public GameOverGUI(PApplet page) {
		super(page);
	}

	private GPanel gameOverPanel; 
	private GTextField userInputName; 
	private GButton submitButton; 
	private GLabel label1; 
	private GLabel label2; 
	private GLabel label3; 


	public void button1_click1(GButton source, GEvent event) { //_CODE_:button1:825371:
	  PApplet.println("button1 - GButton >> GEvent." + event + " @ " + getPage().millis());
	} //_CODE_:button1:825371:


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
	  submitButton.addEventHandler(this, "button1_click1");
	  label1 = new GLabel(getPage(), 87, 61, 233, 23);
	  label1.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
	  label1.setText("You scored 1200 points!");
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
		gameOverPanel.dispose();
		gameOverPanel=null;
	}
}
