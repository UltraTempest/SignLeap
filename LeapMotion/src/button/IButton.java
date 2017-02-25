package button;

import command.ICommand;

public interface IButton {
	public ICommand getCommand(); 
	public boolean isTimerRunning();
	public void cancelTimerTask();
	public void startCountdown(); 
	public int getCountdown();  
	public boolean isMouseOver();
}
