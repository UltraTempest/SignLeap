package recording;

import com.leapmotion.leap.Controller;

public abstract class OneHandTrainer extends AbstractHandTrainer implements ITrainer{

	public static final int ONE_HAND_NUM_FEATURES = 60;
	private final IHandData handData;

	public OneHandTrainer(Controller controller,char[] array){
		super(controller,array);
		size=ONE_HAND_NUM_FEATURES;
		handData=new OneHandData();
	}

	public char train(){
		try {
			return training(handData.getHandPosition(controller));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return '1';
	}
}