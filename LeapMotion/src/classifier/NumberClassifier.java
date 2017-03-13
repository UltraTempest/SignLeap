package classifier;

import recording.AbstractHandData.Handedness;

public final class NumberClassifier extends SignClassifier{

	private final static String type="num";
	
	public NumberClassifier(final Handedness hand) {
		super(hand, type);
	}
	
	public final static void main(final String args[]) {
		new NumberClassifier(Handedness.LEFT).evaluate();
	}
	
	public final static void initialise() {
		SignClassifier.initialise(Handedness.RIGHT, type);
		SignClassifier.initialise(Handedness.LEFT, type);
	}
}
