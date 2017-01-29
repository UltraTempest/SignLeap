package recording;

import java.util.Map;

import recording.AbstractHandData.Handedness;

public interface IHandData {
	public Map<String, Float> getHandPosition();
	public boolean checkIfHandPlacedOverLeap();
	public boolean checkIfCorrectHandPlacedOverLeap(Handedness hand);
	public Handedness getHandedness();
}
