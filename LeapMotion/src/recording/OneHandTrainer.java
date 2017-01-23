package recording;

import java.nio.file.Paths;

import com.leapmotion.leap.Controller;
import recording.HandData.Handedness;

public class OneHandTrainer extends AbstractHandTrainer implements ITrainer{

	public static final int ONE_HAND_NUM_FEATURES = 60;

	public OneHandTrainer(Controller controller, char trainingChar, Handedness hand,String filePath){
		super(controller,trainingChar);
		numSamples=250;
		this.filePath=Paths.get(filePath);
		size=ONE_HAND_NUM_FEATURES;
	}

	public void train(){
		try {
			training(handData.getOneHandPosition(controller));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}