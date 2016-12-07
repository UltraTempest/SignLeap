package processing.GUI;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import Command.AlphabetGUISwitchCommand;
import Command.EButton;
import Command.ICommand;
import Command.NumberGUISwitchCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGeneralGUI{
	private EButton AlphabetButton; 
	private EButton NumbersButton;
	private Timer timer;
	boolean timerRunning=false;
	
	public MainMenuGUI(PApplet page) {
		super(page);
	} 
	
	  @Override
	  protected void createGUI(){
	  Page page=getPage();
	  AlphabetButton = new EButton(page, 245, 111, 449, 142, new AlphabetGUISwitchCommand(page));
	  AlphabetButton.setText("Alphabet");
	  AlphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
	  AlphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 
	  AlphabetButton.addEventHandler(page, "handleButtonEvents");
	  NumbersButton = new EButton(page, 245, 371, 449, 142, new NumberGUISwitchCommand(page));
	  NumbersButton.setText("Numbers");
	  NumbersButton.setFont(new Font("Dialog", Font.PLAIN, 30));
	  NumbersButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
	  NumbersButton.addEventHandler(page, "handleButtonEvents");
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
        if(!checkIfMouseOverButton(AlphabetButton))
        checkIfMouseOverButton(NumbersButton);
	}
	
	private boolean checkIfMouseOverButton(EButton button){
		Page page=getPage();
		float x=button.getX();
		float y=button.getY();
		float height=button.getHeight();
		float width = button.getWidth();
		// Test if the cursor is over the box 
		  if (page.mouseX > x-width && page.mouseX < x+width && 
		      page.mouseY > y-height && page.mouseY < y+height) {
			  if(!timerRunning){
			  startTimer(button.getCommand());
			  }
//			    page.fill(255);
//			    page.textAlign(PConstants.LEFT);
//			    page.text ("LOADING " + ((page.frameCount%301) / 3) + "%", 50, 130);
//			    page.rect(48, 138, 204, 24);
//			    page.fill(0);
//			    int fillX = ((page.frameCount%301) / 3 * 2);
//			    page.rect(250, 140, fillX-200, 20);
			    return true;
		  }
		  else if(timerRunning){
			  timer.cancel();
			  timerRunning=false;
			 // page.background(230);
		  }
		  return false;
	}
	
	protected void startTimer(ICommand command){
		timer= new Timer();
		timerRunning=true;
		timer.scheduleAtFixedRate(new TimerTask() {
            int i = 5;//defined for a 5 second countdown
            public void run() {
            	i--;
                if (i< 0){
                    timer.cancel();
                    command.process();
                }
            }
        }, 0, 1000);
	}
}
