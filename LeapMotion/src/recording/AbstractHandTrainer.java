package recording;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Map;

import com.leapmotion.leap.Controller;

public abstract class AbstractHandTrainer{
	protected int numSamples;
	protected final Controller controller;
	protected final HandData handData = new HandData();
	protected char trainingChar; 
	private int count =0;
	protected Path filePath;
	protected int size;
	private final char[] array;
	private int position=0;

	protected AbstractHandTrainer(Controller controller,char[] array){
		this.controller=controller;
		this.array=array;
		trainingChar=array[position];
	}

	private String formatString(Collection<Float> sample){
		StringBuilder sb = new StringBuilder();
		for(Float i: sample){
			sb.append(i+",");
		}
		return sb.toString();	
	}

	protected char training(Map<String, Float> sample) throws InterruptedException{
		if(count==numSamples){
			System.out.println("Done training " + trainingChar);
			position++;

			if(position==array.length)
				System.exit(0);

			count=0;
			trainingChar=array[position];
			return trainingChar;
		}

		if(sample==null || sample.size()!=size){
			System.out.println("Not enough hand(s) detected");
			return trainingChar;
		}

		String toInsert = "\n" + formatString(sample.values())+String.valueOf(trainingChar);

		try {
			Files.write(filePath, toInsert.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Inserted " + trainingChar +" no." + count + ": " + sample);
		count++;
		return trainingChar;
	}
}
