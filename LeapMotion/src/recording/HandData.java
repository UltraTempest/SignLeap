package recording;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
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
	public Map<String, Float> getOneHandPosition(Controller controller)
	{
	    Frame frame = controller.frame();
	    while (frame.fingers().isEmpty())
	    	return null;
	    
	    List<Vector> fingerBones=getFingerList(controller);
	    
	    // possible issue when more than one hand
	    HandList hands = controller.frame().hands();
	    Vector handCentre = null;
	    for (Hand hand : hands){
	        handCentre = hand.palmPosition();
	    }
	    
	    if(handCentre==null)
	    	return null;

	    Map<String,Float> calibratedFingerBones = new LinkedHashMap<String,Float>();
	    	for(int i=0; i< fingerBones.size();i++){
	        Vector normalizedJoint = fingerBones.get(i).minus(handCentre);
	        for(int j=0; j<3;j++)
	        	if(j==0)
	            calibratedFingerBones.put("feat" + Integer.toString(i*3+j),normalizedJoint.getX());
	            else if(j==1)
	            	calibratedFingerBones.put("feat" + Integer.toString(i*3+j),normalizedJoint.getY());
	            else
	            	calibratedFingerBones.put("feat" + Integer.toString(i*3+j),normalizedJoint.getZ());
	    	}
	            		
	    return calibratedFingerBones;
	}
	
	public Map<String, Float> getTwoHandsPosition(Controller controller)
	{
	    Frame frame = controller.frame();
	    while (frame.fingers().isEmpty())
	    	return null;

	    List<Vector> fingerBones=getFingerList(controller);

	    // possible issue when more than one hand
	    HandList hands = controller.frame().hands();
	    Vector[] handCentres = new Vector[2];
	    int x=0;
	    for (Hand hand : hands){
	        handCentres[x] = hand.palmPosition();
	        x++;
	    }
	    
	    if(handCentres[1]==null)
	    	return null;

	    Map<String,Float> calibratedFingerBones = new LinkedHashMap<String,Float>();
	    Vector handCentre=handCentres[0];
	    	for(int i=0; i< fingerBones.size();i++){
	    		if(i>=20)
	    			handCentre=handCentres[1];
	        Vector normalizedJoint = fingerBones.get(i).minus(handCentre);
	        for(int j=0; j<3;j++)
	        	if(j==0)
	            calibratedFingerBones.put("feat" + Integer.toString(i*3+j),normalizedJoint.getX());
	            else if(j==1)
	            	calibratedFingerBones.put("feat" + Integer.toString(i*3+j),normalizedJoint.getY());
	            else
	            	calibratedFingerBones.put("feat" + Integer.toString(i*3+j),normalizedJoint.getZ());
	    	}
	            		
	    return calibratedFingerBones;
	}
	
	   public boolean isTapped(Controller controller){
		   GestureList gestures=controller.frame().gestures();
		   for(Gesture gesture: gestures){
		        if(gesture.type().equals(Gesture.Type.TYPE_KEY_TAP) || gesture.type().equals(Gesture.Type.TYPE_CIRCLE))
		        return true;
		   }
		    return false;
	   }
	   
	   public boolean checkIfHandPlacedOverLeap(Controller controller){
			  return controller.frame().hands().count()>0 ? true : false;
	   }
	   
	   public boolean checkIfCorrectHandPlacedOverLeap(Controller controller, Handedness hand){
		   Handedness current = GetHandedness(controller.frame().hands().frontmost());
		   return hand.equals(current);	
	   }
	
	private List<Vector> getFingerList(Controller controller){
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
	    LEFT ("LEFT"),
	    RIGHT ("RIGHT");

	    private final String name;       

	    private Handedness(String s) {
	        name = s;
	    }

	    public String toString() {
	       return this.name;
	    }
	}

	public Handedness GetHandedness(Hand hand) {
		if(hand.isValid())
		return hand.isLeft() ? Handedness.LEFT : Handedness.RIGHT;	
		else
			return null;
	}
}
