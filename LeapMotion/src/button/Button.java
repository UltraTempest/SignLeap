package button;

import command.ICommand;
import g4p_controls.GButton;
import processing.core.PApplet;

public final class Button extends GButton implements IButton{
    private final ICommand command;
    private boolean timerRunning=false;
    private final ButtonTimer bTimer;
	
	public Button(final PApplet arg0,final  float arg1,final float arg2,
			final float arg3,final float arg4,final ICommand command) {
		super(arg0, arg1, arg2, arg3, arg4);
		this.command=command;
		bTimer=new ButtonTimer(100, command, 0.05, 1.0);
	}
    
	@Override
    public ICommand getCommand(){
    	return command;
    }
    
	@Override
    public boolean isTimerRunning(){
    	return timerRunning;
    }
    
	@Override
    public void cancelTimerTask(){
    	bTimer.cancel();
    	timerRunning=false;
    }
    
	@Override
    public void startCountdown(){
    	bTimer.schuedule();
    	timerRunning=true;
    }
    
	@Override
    public double getCountdown(){
    	return bTimer.getCountdown();
    }
    
	@Override
    public boolean isMouseOver(){
    	final PApplet page= getPApplet();
		final float buttonX=getX();
		final float buttonY=getY();
		final float buttonHeight=getHeight();
		final float buttonWidth = getWidth();

		return (buttonX <= page.mouseX && page.mouseX <= buttonX+buttonWidth && 
				buttonY <= page.mouseY && page.mouseY <= buttonY+buttonHeight);
    }
}
