package gui;

import java.awt.Font;

import button.Button;
import command.MenuUserNameCommand;
import command.SelectAlphabetCommand;
import command.SignAlphabetCommand;
import command.SubMenuCommand;
import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import processing.Page;
import processing.core.PApplet;

public final class MainMenuGUI extends AbstractMenuGUI implements ITextChanger{
	private final Button practiceButton;
	private final Button gameButton;
	private final GLabel usernameText;
	private final Button usernameButton;
	private final String[] usernames = new String[] {
			"PhonyPony",
			"FluffyVampire",
			"AirSeal" }; 
	private int usernameIndex = 0;
	
	public MainMenuGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		gameButton = new Button(page,144, 80, 685, 186,new SubMenuCommand(page, new SignAlphabetCommand(page)));
		gameButton.setText("Game");
		gameButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		gameButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);

		practiceButton = new Button(page,144, 331, 685, 186,new SubMenuCommand(page, new SelectAlphabetCommand(page)));
		practiceButton.setText("Practice");
		practiceButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		practiceButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		
		usernameText = new GLabel(page, 6, 540, 126, 87);
		usernameText.setTextAlign(GAlign.LEFT, GAlign.TOP);
		usernameText.setText("Your username is: " + usernames[usernameIndex]);
		page.setUsername(usernames[usernameIndex]);
		usernameText.setFont(new Font("Monospaced", Font.PLAIN, 16));
		usernameText.setOpaque(false);
		usernameButton = new Button(page, 131, 540, 181, 94,new MenuUserNameCommand(page, this));
		usernameButton.setText("Change");
		usernameButton.setTextBold();
		usernameButton.setFont(new Font("Monospaced", Font.PLAIN, 30));
	} 
	
	@Override
	public void changeTextDisplayed() {
		if(isLastTextDisplayed())
			usernameIndex=-1;
		usernameText.setText("Your username is: "+ usernames[++usernameIndex]);
		getPage().setUsername(usernames[usernameIndex]);
	}
	
	@Override
	public boolean isLastTextDisplayed() {
		return usernameIndex == usernames.length - 1;
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(practiceButton, gameButton, usernameButton, usernameText);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(practiceButton, gameButton,usernameButton);
	}
}
