package classifier;

import java.util.Map;

import recording.AbstractHandData.Handedness;
import weka.core.Instance;

public final class AlphabetClassifier extends SignClassifier{

	private final static String type="alpha";

	public AlphabetClassifier(final Handedness hand) {
		super(hand, type);
	}

	public static void main(final String args[]) {
		new AlphabetClassifier(Handedness.RIGHT).evaluate();
	}

	public static void initialise() {
		SignClassifier.initialise(Handedness.RIGHT, type);
		SignClassifier.initialise(Handedness.LEFT, type);
	}

	@Override
	public final double score(final Map<String, Float> data,final String expectedChar){
		final Instance sampleInstance=createInstanceFromData(data);
		try {
			final double[] fDistribution = classifier.distributionForInstance(sampleInstance);
			double probabilityForExpected=getProbabilityForClass(expectedChar, fDistribution);
			final String classified=classify(fDistribution,sampleInstance);
			System.out.println("Expected: "+ expectedChar + " : " + probabilityForExpected);
			if(classified.equals(expectedChar)||probabilityForExpected>0.2)
				while(probabilityForExpected<0.5)
					probabilityForExpected+=0.3;
			probabilityForExpected=rollingTotal(probabilityForExpected);
			System.out.println("Rolling Average: " + probabilityForExpected + "\n");
			return 1.2*probabilityForExpected;
		} catch (final Exception e) {
			e.printStackTrace();
		}	
		return 0.0;
	}	
}