package processing.GUI;

import java.awt.Font;

import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGeneralGUI{
	private GButton AlphabetButton; 
	private GButton NumbersButton;
	
	public MainMenuGUI(PApplet page) {
		super(page);
	} 

	public void alphabetButtonPressed(GButton source, GEvent event) { 
		getPage().switchToSignAlphabetGUI();
	} 

	public void numbersButtonPressed(GButton source, GEvent event) {
		getPage().switchToSignNumbersGUI();
	} 
	
	  @Override
	  protected void createGUI(){
	  AlphabetButton = new GButton(getPage(), 245, 111, 449, 142);
	  AlphabetButton.setText("Alphabet");
	  AlphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
	  AlphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 
	  AlphabetButton.addEventHandler(this, "alphabetButtonPressed");
	  NumbersButton = new GButton(getPage(), 245, 371, 449, 142);
	  NumbersButton.setText("Numbers");
	  NumbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
	  NumbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
	  NumbersButton.addEventHandler(this, "numbersButtonPressed");
	}

	@Override
	public void dispose() {
		super.dispose();
        objectDisposal(AlphabetButton);
        objectDisposal(NumbersButton);
	}
}
