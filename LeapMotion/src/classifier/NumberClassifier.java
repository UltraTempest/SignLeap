package classifier;

import recording.AbstractHandData.Handedness;

public final class NumberClassifier extends SignClassifier{

	public NumberClassifier(final Handedness hand) {
		super(hand, "num");
	}
}
