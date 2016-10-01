package processing;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Vector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.leapmotion.leap.Bone;

public class HandData {
	/**
	gets the current frame from controller
	for each finger, stores the topmost end of each bone (4 points)
	adjusts bone location relativity by subtracting the center of the palm
	returns the adjusted bone locations in the form:
	{feat0=some_float, feat1=some_float, ... feat59=some_float}
	 * @param Leap motion Controller
	 * @return Map<String, Float>
	**/
	public Map<String, Float> getHandPosition(Controller controller)
	{
	    Frame frame = controller.frame();
	    while (frame.fingers().isEmpty())
	        frame = controller.frame();

	    FingerList fingers = controller.frame().fingers();
	    List<Vector>finger_bones = new ArrayList<Vector>();
		for (Finger finger:fingers){
	        finger_bones.add(finger.bone(Bone.Type.TYPE_METACARPAL).nextJoint());
	        finger_bones.add(finger.bone(Bone.Type.TYPE_PROXIMAL).nextJoint());
	        finger_bones.add(finger.bone(Bone.Type.TYPE_INTERMEDIATE).nextJoint());
	        finger_bones.add(finger.bone(Bone.Type.TYPE_DISTAL).nextJoint());
		}

	    // possible issue when more than one hand
	    HandList hands = controller.frame().hands();
	    Vector hand_center = null;
	    for (Hand hand : hands){
	        hand_center = hand.palmPosition();
	    }
	    
	    if(hand_center==null)
	    	return null;

	    Map<String,Float> calibrated_finger_bones = new LinkedHashMap<String,Float>();
	    	for(int i=0; i< finger_bones.size();i++){
	        Vector normalized_joint = (finger_bones.get(i).minus(hand_center));
	        for(int j=0; j<3;j++)
	        	if(j==0)
	            calibrated_finger_bones.put("feat" + Integer.toString(i*3+j),normalized_joint.getX());
	            else if(j==1)
	            	calibrated_finger_bones.put("feat" + Integer.toString(i*3+j),normalized_joint.getY());
	            else
	            	calibrated_finger_bones.put("feat" + Integer.toString(i*3+j),normalized_joint.getZ());
	    	}
	            		
	    return calibrated_finger_bones;
	}
}
