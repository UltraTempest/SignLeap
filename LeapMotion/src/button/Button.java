package button;

import command.ICommand;
import g4p_controls.GButton;
import processing.core.PApplet;

public final class Button extends GButton{
    private final ICommand command;
    private boolean timerRunning=false;
    private final ButtonTimer bTimer;
	
	public Button(final PApplet arg0,final  float arg1,final float arg2,
			final float arg3,final float arg4,final ICommand command) {
		super(arg0, arg1, arg2, arg3, arg4);
		this.command=command;
		bTimer=new ButtonTimer(100, command, 0.05, 1.0);
	}
    
    public final ICommand getCommand(){
    	return this.command;
    }
    
    public final boolean isTimerRunning(){
    	return timerRunning;
    }
    
    public final void cancelTimerTask(){
    	bTimer.cancel();
    	timerRunning=false;
    }
    
    public final void startCountdown(){
    	bTimer.schuedule();
    	timerRunning=true;
    }
    
    public final double getCountdown(){
    	return this.bTimer.getCountdown();
    }
    
    public final boolean isMouseOver(final PApplet page){
		final float buttonX=getX();
		final float buttonY=getY();
		final float buttonHeight=getHeight();
		final float buttonWidth = getWidth();

		return (buttonX <= page.mouseX && page.mouseX <= buttonX+buttonWidth && 
				buttonY <= page.mouseY && page.mouseY <= buttonY+buttonHeight);
    }
}
