package button;

import java.util.Timer;
import java.util.TimerTask;

import command.ICommand;

public final class ButtonTimer {
	private double countdownVariable;
	private Timer timer;
	private final int period;
	private final ICommand command;
	private final double increment;
	private final double limit;

	public ButtonTimer(final int period,final ICommand command,
			final double increment,final double limit){
		this.period=period;
		this.command=command;
		this.increment=increment;
		this.limit=limit;
	}  

	public final void schuedule(){
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

	public final double getCountdown(){
		return this.countdownVariable;
	}

	public final void cancel(){
		countdownVariable=0.0;
		timer.cancel();
		timer.purge();
	}
}
