package processing.GUI;

import java.awt.Font;

import g4p_controls.G4P;
import g4p_controls.GTextField;
import processing.core.PApplet;
import processing.core.PImage;
import recording.HandData;

public class IntroductionGUI extends AbstractGeneralGUI{

	public IntroductionGUI(PApplet page) {
		super(page);
	}
	
	private GTextField introText;
	private PImage img;
	private final String[] introTextArray= new String[]{
			"Welcome to the Irish Sign Language Tutor through Leap Motion!",
			"Welcome to the Irish Sign Language Tutor through Leap Motion2!", 
			"Welcome to the Irish Sign Language Tutor through Leap Motion!3"};
	private int postionOfStringDisplayed=0;
     
	@Override
	protected void createGUI(){
		introText = new GTextField(getPage(),130, 212, 801, 89, G4P.SCROLLBARS_NONE);
		introText.setText(introTextArray[postionOfStringDisplayed]);
		introText.setFont(new Font("Dialog", Font.PLAIN, 20));
		introText.setOpaque(false);
		introText.setTextEditEnabled(false);
		img=getPage().loadImage("Leap_Gesture_Tap.png");
		getPage().image(img,350, 380, 138, 109);
		getPage().fill(0, 102, 153);
	    getPage().text("Use the keytap gesture to continue!",300, 551);
	}
	
	private void checkIfKeyTapped(){
		 if(new HandData().isTapped(getPage().getLeap()))
			 postionOfStringDisplayed++;
		 
		 if(postionOfStringDisplayed==introTextArray.length){
			 getPage().switchToMainMenuGUI();
		     return;
		 }
		 if(!introText.getText().equals(introTextArray[postionOfStringDisplayed])){
			 introText.setText(introTextArray[postionOfStringDisplayed]);
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	     
		}
	}
	
	@Override
	public void render() {
		super.render();
		checkIfKeyTapped();
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(introText);
	}
}
