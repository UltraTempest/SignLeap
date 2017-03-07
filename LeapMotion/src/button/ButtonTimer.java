package button;

import java.util.Timer;
import java.util.TimerTask;

import command.ICommand;

public final class ButtonTimer {
	private int countdownVariable=-5;
	private Timer timer;
	private ICommand command;
	private boolean timerRunning=false;
	private final int period=90;
	private final int increment=5;
	private final int limit=100;

	public ButtonTimer(final ICommand command){
		this.command=command;
	}  
	
	public void setCommand(final ICommand command){
		this.command=command;
	}

	public void schuedule(){
		timer= new Timer();
		timerRunning=true;
		timer.scheduleAtFixedRate(new TimerTask() {
			public synchronized void run() {
				countdownVariable+=increment;
				if(countdownVariable>limit){
					cancel();
					cancelTask();
					command.process();
				}
			}
		},0, period);
	}

	public int getCountdown(){
		return countdownVariable;
	}
	
	public boolean isTimerRunning(){
		return timerRunning;
	}

	public void cancelTask(){
		timerRunning=false;
		countdownVariable=-5;
		timer.cancel();
		timer.purge();
	}
}
