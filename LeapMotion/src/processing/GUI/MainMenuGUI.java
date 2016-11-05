package processing.GUI;

import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import processing.GUIFactory;
import processing.Page;
import processing.StateProperties;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGUI{
	private GButton AlphabetButton; 
	private GButton NumbersButton;
	
	public MainMenuGUI(PApplet page) {
		super(page);
	} 

	public void alphabetButtonPressed(GButton source, GEvent event) { 
		((Page) getPage()).stateSwitch(StateProperties.stateSignAlphabet, new GUIFactory(getPage()).createSignAlphabetGUI());
	} 

	public void numbersButtonPressed(GButton source, GEvent event) {
		((Page) getPage()).stateSwitch(StateProperties.stateSignNumbers, new GUIFactory(getPage()).createSignNumbersGUI());
	} 
	
	  @Override
	  protected void createGUI(){
	  AlphabetButton = new GButton(getPage(), 228, 111, 449, 142);
	  AlphabetButton.setText("Alphabet");
	  AlphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
	  AlphabetButton.addEventHandler(this, "alphabetButtonPressed");
	  NumbersButton = new GButton(getPage(), 245, 371, 449, 142);
	  NumbersButton.setText("Numbers");
	  NumbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
	  NumbersButton.addEventHandler(this, "numbersButtonPressed");
	}

	@Override
	public void dispose() {
		super.dispose();
		AlphabetButton.setVisible(false);
		NumbersButton.setVisible(false);
		AlphabetButton.dispose();
		NumbersButton.dispose();
		AlphabetButton=null;
		NumbersButton=null;
	}
}
