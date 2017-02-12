package recording;

import java.util.Map;

import recording.AbstractHandData.Handedness;

public interface IHandData {
	public Map<String, Float> getHandPosition();
	public boolean checkIfHandPlacedOverLeap();
	public boolean isCorrectHandPlacedOverLeap(Handedness hand);
	public Handedness getHandedness();
}
