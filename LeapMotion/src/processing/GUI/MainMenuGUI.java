package processing.GUI;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import command.AlphabetGUICommand;
import command.EButton;
import command.ICommand;
import command.NumberGUICommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public class MainMenuGUI extends AbstractGeneralGUI{
	private EButton AlphabetButton; 
	private EButton NumbersButton;
	private Timer timer;
	
	public MainMenuGUI(PApplet page) {
		super(page);
	} 
	
	  @Override
	  protected void createGUI(){
	  Page page=getPage();
	  AlphabetButton = new EButton(page, 245, 111, 449, 142, new AlphabetGUICommand(page));
	  AlphabetButton.setText("Alphabet");
	  AlphabetButton.setFont(new Font("Dialog", Font.PLAIN, 30));
	  AlphabetButton.setLocalColorScheme(GCScheme.CYAN_SCHEME); 
	  AlphabetButton.addEventHandler(page, "handleButtonEvents");
	  NumbersButton = new EButton(page, 245, 371, 449, 142, new NumberGUICommand(page));
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
		Page page=getPage();
		checkIfMouseOverButton(AlphabetButton, page);
        checkIfMouseOverButton(NumbersButton, page);
	}
	
	private void checkIfMouseOverButton(EButton button, Page page){
		if(page.mouseX==0 && page.mouseY==0)
			return;
		float buttonX=button.getX();
		float buttonY=button.getY();
		float buttonHeight=button.getHeight();
		float buttonWidth = button.getWidth();

		if (buttonX <= page.mouseX && page.mouseX <= buttonX+buttonWidth && 
				buttonY <= page.mouseY && page.mouseY <= buttonY+buttonHeight) {
			if(!button.isTimerRunning()){
				startTimer(button.getCommand());
				button.setTimerRunning(true);
			}
		
				  return;
		}
		////				page.background(230);			  
		////			    page.fill(255);
		////			    page.textAlign(PConstants.LEFT);
		////			    page.text ("LOADING " + ((page.frameCount%301) / 3) + "%", 50, 130);
		////			    page.rect(48, 138, 204, 24);
		////			    page.fill(0);
		////			    int fillX = ((page.frameCount%301) / 3 * 2);
		////			    page.rect(250, 140, fillX-200, 20);
		//		  }
				 if(button.isTimerRunning()){
					  timer.cancel();
					  timer.purge();
					  button.setTimerRunning(false);
				  }
	}
	
	protected void startTimer(ICommand command){
		timer= new Timer();
		timer.schedule(new TimerTask() {
			private final ICommand task = command;
			public void run() {
			 this.cancel();
             task.process();
			}
        }, 3000);
	}
}
