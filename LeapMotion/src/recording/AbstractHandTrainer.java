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
	protected final char trainingChar; 
	private int count =0;
	protected Path filePath;
	protected int size;

	protected AbstractHandTrainer(Controller controller, char trainingChar){
		this.controller=controller;
		this.trainingChar = trainingChar;
	}

	private String formatString(Collection<Float> sample){
		StringBuilder sb = new StringBuilder();
		for(Float i: sample){
			sb.append(i+",");
		}
		return sb.toString();	
	}

	protected void training(Map<String, Float> sample) throws InterruptedException{
		if(count==numSamples){
			System.out.println("Done training");
			System.exit(0);
		}
		if(sample==null || sample.size()!=size)
			return;
		String toInsert = "\n" + formatString(sample.values())+String.valueOf(trainingChar);
		try {
			Files.write(filePath, toInsert.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Inserted " + trainingChar +" no." + count + ": " + sample);
		count++;
	}
}
