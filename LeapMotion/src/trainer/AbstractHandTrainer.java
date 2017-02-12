package trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Map;

import recording.IHandData;
import recording.AbstractHandData.Handedness;

public abstract class AbstractHandTrainer{
	private final Handedness hand;
	protected int numSamples;
	protected String trainingChar; 
	private int count =0;
	protected Path trainPath;
	protected Path testPath;
	protected int size;
	private final String[] array;
	private int currentPosition=0;
	private final IHandData handData;

	protected AbstractHandTrainer(final IHandData handData,final String[] array, 
			final Handedness hand){
		this.hand=hand;
		this.handData=handData;
		this.array=array;
		trainingChar=array[currentPosition];
	}

	private final String formatString(final Collection<Float> sample){
		final StringBuilder sb = new StringBuilder();
		for(Float i: sample){
			sb.append(i+",");
		}
		return sb.toString();	
	}

	protected String training(final Map<String, Float> sample) throws InterruptedException{
		if(count==numSamples){
			System.out.println("Done training " + trainingChar);
			currentPosition++;

			if(currentPosition==array.length)
				System.exit(0);

			count=0;
			trainingChar=array[currentPosition];
			return trainingChar;
		}

		if(sample==null || sample.size()!=size){
			System.out.println("Not enough hand(s) detected");
			return trainingChar;
		}

		if(hand!=null && !handData.isCorrectHandPlacedOverLeap(hand)){
			System.out.println("Wrong hand used");
			return trainingChar;
		}

		String toInsert = "\n" + formatString(sample.values())+ trainingChar;

		try {

			if(count>=numSamples/2)
				Files.write(testPath, toInsert.getBytes(), StandardOpenOption.APPEND);
			else
				Files.write(trainPath, toInsert.getBytes(), StandardOpenOption.APPEND);

		} catch (final IOException e) {
			e.printStackTrace();
		}
		System.out.println("Inserted " + trainingChar +" no." + count + ": " + sample);
		count++;
		return trainingChar;
	}

	public final String train(){
		try {
			return training(handData.getHandPosition());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}

	public final String getCurrentCharacter(){
		return array[currentPosition];
	}
}
