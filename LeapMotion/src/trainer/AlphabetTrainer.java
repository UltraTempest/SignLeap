package trainer;

import java.nio.file.Paths;

import com.leapmotion.leap.Controller;

import recording.AbstractHandData.Handedness;
public final class AlphabetTrainer extends OneHandTrainer{

	public final static String[] alphabet = {"a","b","c","d","e","f","g","h","i",
			"j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

	//public final static String[] alphabet = {"u","v","w","x","y","z"};

	public AlphabetTrainer(final Controller controller,final Handedness hand) {
		super(controller,alphabet,hand);
		numSamples=150*2;
		trainPath=Paths.get("SignData/TrainingData/alpha"+ hand +".arff");
		testPath=Paths.get("SignData/TestingData/alpha"+ hand +".arff");
	}
}
