package trainer;

import java.nio.file.Paths;
import com.leapmotion.leap.Controller;

import recording.TwoHandData;


public class TwoHandTrainer extends AbstractHandTrainer implements ITrainer{

	public static final int TWO_HAND_NUM_FEATURES = 120;
	private final  static String[] numbers={"6","7","8","9","10"};

	public TwoHandTrainer(final Controller controller){
		super(new TwoHandData(controller), numbers,null);
		numSamples=250*2;
		testPath=Paths.get("SignData/TrainingData/num2.arff");
		trainPath=Paths.get("SignData/TestingData/num2.arff");
		size=TWO_HAND_NUM_FEATURES;
	}
}