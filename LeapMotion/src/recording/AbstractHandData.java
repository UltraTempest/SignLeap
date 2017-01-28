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
	
	public AbstractHandData(Controller controller){
		AbstractHandData.controller=controller;
	}

	public boolean checkIfHandPlacedOverLeap(){
		return controller.frame().hands().count()>0 ? true : false;
	}

	public boolean checkIfCorrectHandPlacedOverLeap(Handedness hand){
		Handedness current = GetHandedness();
		return hand.equals(current);	
	}

	public Handedness GetHandedness() {
		Hand hand=controller.frame().hands().frontmost();
		if(hand.isValid())
			return hand.isLeft() ? Handedness.LEFT : Handedness.RIGHT;	
		else
			return Handedness.RIGHT;
	}
	
	protected Controller getLeap() {
		return controller;
	}

	protected List<Vector> getFingerList(){
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
