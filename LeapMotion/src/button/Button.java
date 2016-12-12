package button;

import command.ICommand;
import g4p_controls.GButton;
import processing.core.PApplet;

public class Button extends GButton{
    private ICommand command;
    private boolean timerRunning=false;
    private ButtonTimer bTimer;
	
	public Button(PApplet arg0, float arg1, float arg2, float arg3, float arg4, ICommand command) {
		super(arg0, arg1, arg2, arg3, arg4);
		setCommand(command);
	}
	
    public void setCommand(ICommand command){
    	this.command=command;
    }
    
    public ICommand getCommand(){
    	return this.command;
    }
    
    public boolean isTimerRunning(){
    	return timerRunning;
    }
    
    public void setTimerVariables(int period, double increment, double limit){
    	bTimer=new ButtonTimer(period, command, increment, limit);
    }
    
    public void cancelTimerTask(){
    	bTimer.cancel();
    	timerRunning=false;
    }
    
    public void startCountdown(){
    	bTimer.schuedule();
    	timerRunning=true;
    }
    
    public double getCountdown(){
    	return this.bTimer.getCountdown();
    }
    
    public boolean isMouseOver(PApplet page){
		float buttonX=getX();
		float buttonY=getY();
		float buttonHeight=getHeight();
		float buttonWidth = getWidth();

		return (buttonX <= page.mouseX && page.mouseX <= buttonX+buttonWidth && 
				buttonY <= page.mouseY && page.mouseY <= buttonY+buttonHeight);
    }
}
