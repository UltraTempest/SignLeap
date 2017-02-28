package gui;

import java.awt.Font;

import button.Button;
import command.IntroductionInfoCommand;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import processing.Page;
import processing.core.PApplet;

public final class IntroductionGUI extends AbstractGeneralGUI {

	private final GLabel introText;
	private final Button continueButton;
	private final String[] introTextArray = new String[] {
			"Welcome to the Irish Sign Language Tutor through Leap Motion!",
			"You will be given a series of signs and be scored based on how many you can complete in the time limit.",
			"In the next screen, you will given be a choice of signing either Numbers or Letters." };
	private int postionOfStringDisplayed = 0;

	public IntroductionGUI(final PApplet papplet) {
		super(papplet);
		final Page page = getPage();
		introText = new GLabel(page, 198, 25, 585, 400);
		introText.setText(introTextArray[postionOfStringDisplayed]);
		introText.setFont(new Font("Dialog", Font.PLAIN, 30));
		introText.setOpaque(false);
		continueButton = new Button(page, 226, 436, 500, 120, new IntroductionInfoCommand(page, this));
		continueButton.setText("Continue");
		continueButton.setTextBold();
		continueButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		continueButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		// TODO Introduction should give a sample of what the user will be tasked to do without a time limit.
		// TODO Information on ideal distance hands should be from the leap motion. Try to keep users hands on the leap and not away.
	}

	public void changeTextDisplayed() {
		postionOfStringDisplayed++;
	}

	public boolean isLastTextDisplayed() {
		return postionOfStringDisplayed == introTextArray.length - 1;
	}

	@Override
	public void render() {
		handleMouseOverButton(continueButton);
		introText.setText(introTextArray[postionOfStringDisplayed]);
	}

	@Override
	public void dispose() {
		objectDisposal(introText, continueButton);
	}
}
