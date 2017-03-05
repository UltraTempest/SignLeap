package button;

import command.ICommand;
import g4p_controls.GButton;
import processing.core.PApplet;

public final class Button extends GButton implements IButton{
	private ICommand command;
	private final ButtonTimer bTimer;

	public Button(final PApplet page,final  float arg1,final float arg2,final float arg3,final float arg4,
			final ICommand command){
		super(page, arg1, arg2, arg3, arg4);
		this.command=command;
		bTimer=new ButtonTimer(command);
		addEventHandler(page, "handleButtonEvents");
	}

	@Override
	public ICommand getCommand(){
		return command;
	}

	public void setCommand(final ICommand command){
		this.command=command;
		bTimer.setCommand(command);
	}

	@Override
	public boolean isTimerRunning(){
		return bTimer.isTimerRunning();
	}

	@Override
	public void cancelTimerTask(){
		bTimer.cancelTask();
	}

	@Override
	public void startCountdown(){
		bTimer.schuedule();
	}

	@Override
	public int getCountdown(){
		return bTimer.getCountdown();
	}

	@Override
	public boolean isMouseOver(){
		if(!isVisible())
			return false;
		final PApplet page= getPApplet();
		final float buttonX=getX();
		final float buttonY=getY();
		final float buttonHeight=getHeight();
		final float buttonWidth = getWidth();

		return (buttonX <= page.mouseX && page.mouseX <= buttonX+buttonWidth && 
				buttonY <= page.mouseY && page.mouseY <= buttonY+buttonHeight);
	}
}
