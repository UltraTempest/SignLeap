package classifier;

import recording.AbstractHandData.Handedness;

public final class NumberClassifier extends SignClassifier{

	public NumberClassifier(final Handedness hand) {
		super(hand, "num");
	}
	
	public final static void main(final String args[]) {
		new NumberClassifier(Handedness.LEFT).evaluate();
	}
}
