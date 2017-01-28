package recording;

import java.util.ArrayList;
import java.util.List;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

public abstract class AbstractHandData implements IHandData{

	public boolean checkIfHandPlacedOverLeap(Controller controller){
		return controller.frame().hands().count()>0 ? true : false;
	}

	public boolean checkIfCorrectHandPlacedOverLeap(Controller controller, Handedness hand){
		Handedness current = GetHandedness(controller.frame().hands().frontmost());
		return hand.equals(current);	
	}

	public Handedness GetHandedness(Hand hand) {
		if(hand.isValid())
			return hand.isLeft() ? Handedness.LEFT : Handedness.RIGHT;	
		else
			return null;
	}

	protected List<Vector> getFingerList(Controller controller){
		FingerList fingers = controller.frame().fingers();
		List<Vector>fingerBones = new ArrayList<Vector>();
		for (Finger finger:fingers){
			fingerBones.add(finger.bone(Bone.Type.TYPE_METACARPAL).nextJoint());
			fingerBones.add(finger.bone(Bone.Type.TYPE_PROXIMAL).nextJoint());
			fingerBones.add(finger.bone(Bone.Type.TYPE_INTERMEDIATE).nextJoint());
			fingerBones.add(finger.bone(Bone.Type.TYPE_DISTAL).nextJoint());
		}
		return fingerBones;
	}

	public enum Handedness {
		LEFT ("Left"),
		RIGHT ("Right");

		private final String name;       

		private Handedness(String s) {
			name = s;
		}

		public String toString() {
			return this.name;
		}
	}
}
