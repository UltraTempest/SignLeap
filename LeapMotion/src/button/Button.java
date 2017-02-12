package button;

import command.ICommand;
import g4p_controls.GButton;
import processing.core.PApplet;

public class Button extends GButton{
    private final ICommand command;
    private boolean timerRunning=false;
    private ButtonTimer bTimer;
	
	public Button(final PApplet arg0,final  float arg1,final float arg2,
			final float arg3,final float arg4,final ICommand command) {
		super(arg0, arg1, arg2, arg3, arg4);
		this.command=command;
		setTimerVariables(100, 0.05, 1.0);
	}
    
    public ICommand getCommand(){
    	return this.command;
    }
    
    public boolean isTimerRunning(){
    	return timerRunning;
    }
    
    private void setTimerVariables(final int period,final double increment,final double limit){
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
    
    public boolean isMouseOver(final PApplet page){
		final float buttonX=getX();
		final float buttonY=getY();
		final float buttonHeight=getHeight();
		final float buttonWidth = getWidth();

		return (buttonX <= page.mouseX && page.mouseX <= buttonX+buttonWidth && 
				buttonY <= page.mouseY && page.mouseY <= buttonY+buttonHeight);
    }
}
