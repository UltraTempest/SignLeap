package processing.GUI;

import java.awt.Font;

import g4p_controls.G4P;
import g4p_controls.GTextField;
import processing.Page;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class AbstractSignCharacterGUI extends AbstractGUI{
	
	protected final String imageType=".jpg";
	protected int currentLetterPosition=0;
	private PImage img;
	protected GTextField signInstruction;
	private GTextField scoreTimerText;
	protected final String hand;
	
	public AbstractSignCharacterGUI(PApplet page, String hand) {
		super(page);
		this.hand=hand;
	}
	
	@Override
	protected void createGUI(){
		  signInstruction = new GTextField(getPage(), 217, 513, 492, 81, G4P.SCROLLBARS_NONE);
		  signInstruction.setOpaque(false);
		  signInstruction.setFont(new Font("Dialog", Font.PLAIN, 58));
		  signInstruction.setTextEditEnabled(false);
		  scoreTimerText = new GTextField(getPage(), 259, 28, 331, 20);
		  scoreTimerText.setText("Score:                       Time left:");
		  scoreTimerText.setOpaque(false);
		  scoreTimerText.setFont(new Font("Dialog", Font.PLAIN, 16));
	}
	
	protected void updateSignCharactersGUI(char currentLetter, String imageName){
		img=getPage().loadImage(imageName);
		getPage().image(img,136, 65, 657, 408);
		signInstruction.setText("Sign the letter: " + Character.toUpperCase(currentLetter) +" " + currentLetter);
		scoreTimerText.setText("Score:                       Time left:" + ((Page) getPage()).time());
	}
	
	@Override
	public void dispose(){
		super.dispose();
		signInstruction.setVisible(false);
		scoreTimerText.setVisible(false);
		signInstruction.dispose();
		scoreTimerText.dispose();
		signInstruction=null;
		scoreTimerText=null;
	}

}
