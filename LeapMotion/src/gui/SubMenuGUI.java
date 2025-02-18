package gui;

import java.awt.Font;

import button.Button;
import command.SignAlphabetCommand;
import command.GameSubMenuCommand;
import command.ICommand;
import command.MainMenuCommand;
import command.SignNumberCommand;
import command.SelectNumbersCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public final class SubMenuGUI extends AbstractMenuGUI{
	private final Button alphabetButton; 
	private final Button numbersButton;
	private final Button backButton;

	public SubMenuGUI(final PApplet papplet,ICommand commandAlpha) {
		super(papplet);
		final Page page=getPage();
		ICommand commandNum;
		if(commandAlpha instanceof SignAlphabetCommand){
			commandNum = new GameSubMenuCommand(page, new SignNumberCommand(page));
			commandAlpha= new GameSubMenuCommand(page, commandAlpha);
		}
		else
			commandNum = new SelectNumbersCommand(page);

		alphabetButton = new Button(page,144, 80, 685, 186,commandAlpha);
		alphabetButton.setText("Alphabet");
		alphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		alphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 

		numbersButton = new Button(page,144, 312, 685, 186, commandNum);
		numbersButton.setText("Numbers");
		numbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		numbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);

		backButton = new Button(page, 5, 516, 320, 113, new MainMenuCommand(page));
		backButton.setText("Back");
		backButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		backButton.setTextBold();
		backButton.setTextItalic();
		backButton.setLocalColorScheme(GCScheme.RED_SCHEME);
	} 

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(backButton, alphabetButton, numbersButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(backButton, alphabetButton,numbersButton);
	}
}
