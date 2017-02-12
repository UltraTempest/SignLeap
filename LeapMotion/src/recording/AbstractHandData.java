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

	private static Controller controller;

	public AbstractHandData(final Controller controller){
		AbstractHandData.controller=controller;
	}

	public final boolean checkIfHandPlacedOverLeap(){
		return controller.frame().hands().count()>0 ? true : false;
	}

	public final boolean isCorrectHandPlacedOverLeap(Handedness hand){
		Handedness current = getHandedness();
		return hand.equals(current);	
	}

	public final Handedness getHandedness() {
		final Hand hand=controller.frame().hands().frontmost();
		if(hand.isValid())
			return hand.isLeft() ? Handedness.LEFT : Handedness.RIGHT;	
		else
			return null;
	}

	protected final Controller getLeap() {
		return controller;
	}

	protected final List<Vector> getFingerList(){
		final FingerList fingers = controller.frame().fingers();
		final List<Vector>fingerBones = new ArrayList<Vector>();
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

		private Handedness(final String s) {
			name = s;
		}

		public final String toString() {
			return this.name;
		}
	}
}
