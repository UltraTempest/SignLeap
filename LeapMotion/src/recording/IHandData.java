package recording;

import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;

import recording.AbstractHandData.Handedness;

public interface IHandData {
	public Map<String, Float> getHandPosition(Controller controller);
	public boolean checkIfHandPlacedOverLeap(Controller controller);
	public boolean checkIfCorrectHandPlacedOverLeap(Controller controller, Handedness hand);
	public Handedness GetHandedness(Hand frontmost);
}
