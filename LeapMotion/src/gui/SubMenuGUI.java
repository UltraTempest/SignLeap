package gui;

import java.awt.Font;

import button.Button;
import command.SignAlphabetCommand;
import command.ICommand;
import command.SignNumberCommand;
import command.SelectNumbersCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public final class SubMenuGUI extends AbstractMenuGUI{
	private final Button alphabetButton; 
	private final Button numbersButton;

	public SubMenuGUI(final PApplet papplet, final ICommand commandAlpha) {
		super(papplet);
		final Page page=getPage();
		ICommand commandNum;
		if(commandAlpha instanceof SignAlphabetCommand)
			commandNum = new SignNumberCommand(page);
		else
			commandNum = new SelectNumbersCommand(page);

		alphabetButton = new Button(page, 100, 80, 685, 186,commandAlpha);
		alphabetButton.setText("Alphabet");
		alphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		alphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 

		numbersButton = new Button(page, 100, 330, 685, 186, commandNum);
		numbersButton.setText("Numbers");
		numbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		numbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		
		//TODO Add back button
	} 

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(alphabetButton, numbersButton);
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(alphabetButton,numbersButton);
	}
}
