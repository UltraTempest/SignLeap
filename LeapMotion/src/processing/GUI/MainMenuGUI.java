package processing.GUI;

import java.awt.Font;

import button.Button;
import command.AlphabetGUICommand;
import command.NumberGUICommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGeneralGUI{
	private Button AlphabetButton; 
	private Button NumbersButton;

	public MainMenuGUI(PApplet page) {
		super(page);
	} 

	@Override
	protected void createGUI(){
		Page page=getPage();
		AlphabetButton = new Button(page, 245, 111, 449, 142, new AlphabetGUICommand(page));
		AlphabetButton.setText("Alphabet");
		AlphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		AlphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 
		AlphabetButton.addEventHandler(page, "handleButtonEvents");
		AlphabetButton.setTimerVariables(100, 0.05, 1.0);
		NumbersButton = new Button(page, 245, 371, 449, 142, new NumberGUICommand(page));
		NumbersButton.setText("Numbers");
		NumbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		NumbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		NumbersButton.addEventHandler(page, "handleButtonEvents");
		NumbersButton.setTimerVariables(100, 0.05, 1.0);
		page.turnOnLeapMouseControl();
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(AlphabetButton);
		objectDisposal(NumbersButton);
	}

	@Override
	public void render(){
		super.render();
		checkIfMouseOverButton(AlphabetButton);
		checkIfMouseOverButton(NumbersButton);
	}
}
