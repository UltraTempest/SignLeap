package processing.GUI;

import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import processing.GUIHandler;
import processing.Page;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGUI{
	private GButton AlphabetButton; 
	private GButton NumbersButton;
	private String hand;
	
	public MainMenuGUI(PApplet page, String hand) {
		super(page);
		this.hand=hand;
	} 

	public void alphabetButtonPressed(GButton source, GEvent event) { 
		((Page) getPage()).stateSwitch(Page.stateSignAlphabet, new GUIHandler(getPage()).getSignAlphabetGUI(hand));
	} 

	public void numbersButtonPressed(GButton source, GEvent event) {
		((Page) getPage()).stateSwitch(Page.stateSignNumbers, new GUIHandler(getPage()).getSignNumbersGUI(hand));
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
