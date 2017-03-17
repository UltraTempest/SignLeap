package gui;

import java.awt.Font;

import button.Button;
import command.IntroductionInfoCommand;
import command.IntroductionSignCommand;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import processing.Page;
import processing.core.PApplet;

public final class IntroductionGUI extends AbstractGeneralGUI implements ITextChanger{

	private final GLabel introText;
	private final Button continueButton;
	private final String[] introTextArray = new String[] {
			"Use you hand to control the cursor, hover over the buttons long enough to press them. Try it now!",
			"Excellent. Welcome to the Irish Sign Language Tutor through Leap Motion!",
			"You will be given a series of signs and be scored based on how many you can complete in the time limit.",
			"Here is a quick of example of what to expect.",
	"You are now ready to begin!"};
	private int introTextIndex;

	public IntroductionGUI(final PApplet papplet,final int introTextIndex) {
		super(papplet);
		this.introTextIndex=introTextIndex;
		final Page page = getPage();
		introText = new GLabel(page, 198, 25, 650, 400);
		introText.setText(introTextArray[introTextIndex]);
		introText.setFont(new Font("Dialog", Font.PLAIN, 30));
		introText.setOpaque(false);
		continueButton = new Button(page, 226, 436, 500, 120, new IntroductionInfoCommand(page, this));
		continueButton.setText("Continue");
		continueButton.setTextBold();
		continueButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		continueButton.setFont(new Font("Dialog", Font.PLAIN, 30));
	}

	@Override
	public void changeTextDisplayed() {
		if(introTextIndex==3)
			new IntroductionSignCommand(getPage()).process();
		else
			introText.setText(introTextArray[++introTextIndex]);
	}

	@Override
	public boolean isLastTextDisplayed() {
		return introTextIndex == introTextArray.length - 1;
	}

	@Override
	public void render() {
		super.render();
		handleMouseOverButton(continueButton);	
	}

	@Override
	public void dispose() {
		objectDisposal(introText, continueButton);
	}
}
