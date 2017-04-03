package classifier;

import recording.AbstractHandData.Handedness;

public final class NumberClassifier extends SignClassifier{

	private final static String type="num";
	
	public NumberClassifier(final Handedness hand) {
		super(hand, type);
	}
	
	public static void main(final String args[]) {
		new NumberClassifier(Handedness.LEFT).evaluate();
	}
	
	public static void initialise() {
		SignClassifier.initialise(Handedness.RIGHT, type);
		SignClassifier.initialise(Handedness.LEFT, type);
	}
	
//	@Override
//	public final double score(final Map<String, Float> data,final String expectedChar){
//		return 1.2*super.score(data, expectedChar);
//	}
}
