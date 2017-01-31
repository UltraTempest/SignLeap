package classifier;

import java.util.Map;

import recording.AbstractHandData.Handedness;
import weka.core.Instance;

public class AlphabetClassifier extends SignClassifier{

	public AlphabetClassifier(Handedness hand) {
		super(hand, "alpha");
	}
	
	@Override
	public double score(Map<String, Float> data,final String expectedChar){
		Instance sampleInstance=createInstanceFromData(data);
		try {
			double[] fDistribution = classifier.distributionForInstance(sampleInstance);
			double probabilityForExpected=getProbabilityForClass(expectedChar, fDistribution);
			String classified=classify(data,expectedChar);
			System.out.println("Expected: "+ expectedChar + " : " + probabilityForExpected);
			double rollingAverage=rollingTotal(probabilityForExpected);
			System.out.println("Rolling Average: " + rollingAverage);
			System.out.println();
			if((classified.equals(expectedChar) && rollingAverage<0.5)||probabilityForExpected>0.15){
				while(rollingAverage<0.9){
					rollingAverage+=0.1;
				}
			 rollingAverage=rollingTotal(rollingAverage);
			}
			return rollingAverage;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return 0.0;
	}	
	
	@Override
	public void resetRollingAverage(){
		move=new MovingAverageFilter(movingAverageFilterPeriods);
	}
}