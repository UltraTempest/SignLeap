package trainer;

import java.nio.file.Paths;

import com.leapmotion.leap.Controller;

import recording.AbstractHandData.Handedness;


public final class NumberTrainer extends OneHandTrainer{

	private final static String[] numbers={"1","2","3","4","5"};

	public NumberTrainer(final Controller controller,final Handedness hand) {
		super(controller,numbers,hand);
		numSamples=250*2;
		trainPath=Paths.get("SignData/TrainingData/num"+ hand +".arff");
		testPath=Paths.get("SignData/TestingData/num"+ hand +".arff");
	}
}