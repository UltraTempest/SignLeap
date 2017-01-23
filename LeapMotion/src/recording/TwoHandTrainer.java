package recording;

import java.nio.file.Paths;
import com.leapmotion.leap.Controller;


public class TwoHandTrainer extends AbstractHandTrainer implements ITrainer{

	public static final int TWO_HAND_NUM_FEATURES = 120;

	public TwoHandTrainer(Controller controller,char trainingChar, String filePath){
		super(controller, trainingChar);
		numSamples=250;
		this.filePath=Paths.get(filePath);
		size=TWO_HAND_NUM_FEATURES;
	}

	public void train(){
		try {
			training(handData.getTwoHandsPosition(controller));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}