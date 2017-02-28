package gui;

import java.awt.Font;

import button.Button;
import command.SignAlphabetCommand;
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

	public SubMenuGUI(final PApplet papplet, final ICommand commandAlpha) {
		super(papplet);
		final Page page=getPage();
		ICommand commandNum;
		if(commandAlpha instanceof SignAlphabetCommand)
			commandNum = new SignNumberCommand(page);
		else
			commandNum = new SelectNumbersCommand(page);

		alphabetButton = new Button(page,144, 80, 685, 186,commandAlpha);
		alphabetButton.setText("Alphabet");
		alphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		alphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 

		numbersButton = new Button(page,144, 331, 685, 186, commandNum);
		numbersButton.setText("Numbers");
		numbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		numbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);

		backButton = new Button(page, 9, 541, 289, 93, new MainMenuCommand(page));
		backButton.setText("<--");
		backButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		backButton.setTextBold();
		backButton.setTextItalic();
		backButton.setLocalColorScheme(GCScheme.RED_SCHEME);
	} 

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(alphabetButton, numbersButton, backButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(alphabetButton,numbersButton,backButton);
	}
}
