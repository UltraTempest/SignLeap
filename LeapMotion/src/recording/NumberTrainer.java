package recording;

import java.nio.file.Paths;

import com.leapmotion.leap.Controller;

import recording.HandData.Handedness;

public class NumberTrainer extends OneHandTrainer{

	private static char[] num="12345".toCharArray();

	public NumberTrainer(Controller controller, Handedness hand) {
		super(controller,num);
		numSamples=250;
		this.filePath=Paths.get("SignData/TrainingData/num"+ hand +".arff");
		//this.filePath=Paths.get("SignData/TestingData/num"+ hand +".arff");
	}
}