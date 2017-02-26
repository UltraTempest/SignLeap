package gui;

import java.awt.Font;

import button.Button;
import command.SignAlphabetCommand;
import command.TrainingAlphabetCommand;
import command.SubMenuCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public final class MainMenuGUI extends AbstractMenuGUI{
	private final Button practiceButton;
	private final Button gameButton;

	public MainMenuGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		gameButton = new Button(page, 100, 80, 685, 186,new SubMenuCommand(page, new SignAlphabetCommand(page)));
		gameButton.setText("Game");
		gameButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		gameButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		
		practiceButton = new Button(page, 100, 330, 685, 186,new SubMenuCommand(page, new TrainingAlphabetCommand(page)));
		practiceButton.setText("Practice");
		practiceButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		practiceButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
	} 

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(practiceButton, gameButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(practiceButton, gameButton);
	}
}
