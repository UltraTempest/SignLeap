package processing.GUI;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import processing.Page;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGeneralGUI{
	private GButton AlphabetButton; 
	private GButton NumbersButton;
	private Timer timer;
	boolean timerRunning=false;
	
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
	  getPage().turnOnLeapMouseControl();
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
		Page page=getPage();
		float x=AlphabetButton.getX();
		float y=AlphabetButton.getY();
		float height=AlphabetButton.getHeight();
		float width = AlphabetButton.getWidth();
		// Test if the cursor is over the box 
		  if (page.mouseX > x-width && page.mouseX < x+width && 
		      page.mouseY > y-height && page.mouseY < y+height) {
			  if(!timerRunning)
			  startTimer();
		  }
		  else if(timerRunning){
			  timer.cancel();
			  timerRunning=false;
		  }
	}
	
	protected void startTimer(){
		timer= new Timer();
		timerRunning=true;
		timer.scheduleAtFixedRate(new TimerTask() {
            int i = 5;//defined for a 5 second countdown
            public void run() {
            	i--;
                if (i< 0){
                    timer.cancel();
                   alphabetButtonPressed(null, null);
                }
            }
        }, 0, 1000);
	}
}
