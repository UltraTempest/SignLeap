package button;

import java.util.Timer;
import java.util.TimerTask;

import command.ICommand;

public class ButtonTimer {
	private double countdownVariable;
	private Timer timer;
	private int period;
	private ICommand command;
	private double increment;
	private double limit;
	
	public ButtonTimer(int period, ICommand command, double increment, double limit){
		this.period=period;
		this.command=command;
		this.increment=increment;
		this.limit=limit;
	}  

	public void schuedule(){
		timer= new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				countdownVariable+=increment;
				if(countdownVariable>limit){
					this.cancel();
					command.process();
				}
			}
		},0, period);
	}

	public double getCountdown(){
		return this.countdownVariable;
	}

	public void cancel(){
		countdownVariable=0.0;
		timer.cancel();
		timer.purge();
	}
}
