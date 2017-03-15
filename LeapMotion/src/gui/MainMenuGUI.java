package gui;

import java.awt.Font;
import java.util.Arrays;

import button.Button;
import command.MenuUserNameCommand;
import command.SelectAlphabetCommand;
import command.SignAlphabetCommand;
import command.SubMenuCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public final class MainMenuGUI extends AbstractMenuGUI implements ITextChanger{
	private final Button practiceButton;
	private final Button gameButton;
	private final String userNameText="Your username is: %s. Change?";
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

		practiceButton = new Button(page,144, 312, 685, 186,new SubMenuCommand(page, new SelectAlphabetCommand(page)));
		practiceButton.setText("Practice");
		practiceButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		practiceButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);

		String currentUserName=page.getUsername();
		if(currentUserName==null){
			currentUserName=usernames[usernameIndex];
			page.setUsername(currentUserName);
		}else
			usernameIndex=Arrays.asList(usernames).indexOf(currentUserName);
		usernameButton = new Button(page, 5, 516, 320, 113,new MenuUserNameCommand(page, this));
		usernameButton.setText(String.format(userNameText, currentUserName));
		usernameButton.setTextBold();
		usernameButton.setFont(new Font("Monospaced", Font.PLAIN, 30));
	} 

	@Override
	public void changeTextDisplayed() {
		if(isLastTextDisplayed())
			usernameIndex=-1;
		final String currentUserName=usernames[++usernameIndex];
		usernameButton.setText(String.format(userNameText,currentUserName));
		usernameButton.setTextBold();
		getPage().setUsername(currentUserName);
	}

	@Override
	public boolean isLastTextDisplayed() {
		return usernameIndex == usernames.length - 1;
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(practiceButton, gameButton, usernameButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(practiceButton, gameButton,usernameButton);
	}
}
