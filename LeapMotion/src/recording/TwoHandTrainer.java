package recording;

import java.nio.file.Paths;
import com.leapmotion.leap.Controller;


public class TwoHandTrainer extends AbstractHandTrainer implements ITrainer{

	public static final int TWO_HAND_NUM_FEATURES = 120;
	private static char[] num="6789".toCharArray();

	public TwoHandTrainer(Controller controller){
		super(new TwoHandData(controller), num);
		numSamples=250;
		this.filePath=Paths.get("SignData/TrainingData/num2.arff");
		//this.filePath=Paths.get("SignData/TestingData/num2.arff");
		size=TWO_HAND_NUM_FEATURES;
	}
}