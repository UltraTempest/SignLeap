package classifier;

import java.util.Map;

import recording.AbstractHandData.Handedness;
import weka.core.Instance;

public final class AlphabetClassifier extends SignClassifier{

	private final static String type="alpha";

	public AlphabetClassifier(final Handedness hand) {
		super(hand, type);
	}

	public final static void main(final String args[]) {
		new AlphabetClassifier(Handedness.RIGHT).evaluate();
	}

	public final static void initialise() {
		SignClassifier.initialise(Handedness.RIGHT, type);
		//nSignClassifier.initialise(Handedness.LEFT, type);
	}

	@Override
	public final double score(final Map<String, Float> data,final String expectedChar){
		final Instance sampleInstance=createInstanceFromData(data);
		try {
			final double[] fDistribution = classifier.distributionForInstance(sampleInstance);
			final double probabilityForExpected=getProbabilityForClass(expectedChar,fDistribution);
			final String classified=classify(fDistribution,sampleInstance);
			System.out.println("Expected: "+ expectedChar + " : "+ probabilityForExpected);
			double rollingAverage=rollingTotal(probabilityForExpected);
			System.out.println("Rolling Average: " + rollingAverage + "\n");
			if((classified.equals(expectedChar) && rollingAverage<0.5)||
					probabilityForExpected>0.2){
				while(rollingAverage<0.5){
					rollingAverage+=0.1;
				}
				rollingAverage=rollingTotal(rollingAverage);
			}
			return rollingAverage;
		} catch (final Exception e) {
			e.printStackTrace();
		}	
		return 0.0;
	}	
}