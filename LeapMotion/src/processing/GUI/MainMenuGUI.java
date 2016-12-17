package processing.GUI;

import java.awt.Font;

import button.Button;
import command.AlphabetGUICommand;
import command.NumberGUICommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGeneralGUI{
	private Button alphabetButton; 
	private Button numbersButton;

	public MainMenuGUI(PApplet page) {
		super(page);
	} 

	@Override
	protected void createGUI(){
		Page page=getPage();
		alphabetButton = new Button(page, 245, 111, 449, 142, new AlphabetGUICommand(page));
		alphabetButton.setText("Alphabet");
		alphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		alphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 
		alphabetButton.addEventHandler(page, "handleButtonEvents");
		numbersButton = new Button(page, 245, 371, 449, 142, new NumberGUICommand(page));
		numbersButton.setText("Numbers");
		numbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		numbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		numbersButton.addEventHandler(page, "handleButtonEvents");
		page.turnOnLeapMouseControl();
	}

	@Override
	public void dispose() {
		objectDisposal(alphabetButton);
		objectDisposal(numbersButton);
	}

	@Override
	public void render(){
		super.render();
		checkIfMouseOverButton(alphabetButton);
		checkIfMouseOverButton(numbersButton);
	}
}
