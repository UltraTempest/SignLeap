package processing.GUI;

import java.awt.Font;

import button.Button;
import command.IntroductionInfoCommand;
import g4p_controls.G4P;
import g4p_controls.GCScheme;
import g4p_controls.GTextField;
import processing.Page;
import processing.core.PApplet;
import processing.core.PImage;

public class IntroductionGUI extends AbstractGeneralGUI{

	public IntroductionGUI(PApplet page) {
		super(page);
	}

	private GTextField introText;
	private PImage img;
	private Button continueButton;
	private final String[] introTextArray= new String[]{
			"Welcome to the Irish Sign Language Tutor through Leap Motion!",
			"Welcome to the Irish Sign Language Tutor through Leap Motion2!", 
	"Welcome to the Irish Sign Language Tutor through Leap Motion!3"};
	private int postionOfStringDisplayed=0;

	@Override
	protected void createGUI(){
		Page page=getPage();
		introText = new GTextField(page,130, 212, 801, 89, G4P.SCROLLBARS_NONE);
		introText.setText(introTextArray[postionOfStringDisplayed]);
		introText.setFont(new Font("Dialog", Font.PLAIN, 22));
		introText.setOpaque(false);
		introText.setTextEditEnabled(false);
		continueButton = new Button(page, 226, 436, 478, 109, new IntroductionInfoCommand(page, this));
		continueButton.setText("Continue");
		continueButton.setTextBold();
		continueButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		continueButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		continueButton.addEventHandler(page, "handleButtonEvents");
		img=page.loadImage("Leap_Gesture_Tap.png");
		page.image(img,350, 380, 138, 109);
		page.fill(0, 102, 153);
		page.text("Use the keytap gesture to continue!",300, 551);
		page.turnOnLeapMouseControl();
	}

	public void changeTextDisplayed(){
		postionOfStringDisplayed++;
	}
	
	public boolean isLastTextDisplayed(){
		return postionOfStringDisplayed==introTextArray.length-1;
	}
	
	@Override
	public void render(){
		super.render();
		checkIfMouseOverButton(continueButton);
		introText.setText(introTextArray[postionOfStringDisplayed]);
	}

	@Override
	public void dispose() {
		objectDisposal(introText);
		objectDisposal(continueButton);
	}
}
