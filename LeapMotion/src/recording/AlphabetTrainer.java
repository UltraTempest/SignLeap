package recording;

import java.nio.file.Paths;

import com.leapmotion.leap.Controller;

import recording.AbstractHandData.Handedness;


public class AlphabetTrainer extends OneHandTrainer{

	private static char[] alpha="abcdefghijklmnopqrstuvwxyz".toCharArray();

	public AlphabetTrainer(Controller controller, Handedness hand) {
		super(controller,alpha);
		numSamples=100;
		this.filePath=Paths.get("SignData/TrainingData/alpha"+ hand +".arff");
		//this.filePath=Paths.get("SignData/TestingData/alpha"+ hand +".arff");
	}
}
