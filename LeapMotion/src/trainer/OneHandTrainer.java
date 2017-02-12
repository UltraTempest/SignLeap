package trainer;

import com.leapmotion.leap.Controller;

import recording.OneHandData;
import recording.AbstractHandData.Handedness;

public abstract class OneHandTrainer extends AbstractHandTrainer implements
ITrainer{

	public static final int ONE_HAND_NUM_FEATURES = 60;

	protected OneHandTrainer(final Controller controller,final String[] array,
			final Handedness hand){
		super(new OneHandData(controller),array,hand);
		size=ONE_HAND_NUM_FEATURES;
	}
}