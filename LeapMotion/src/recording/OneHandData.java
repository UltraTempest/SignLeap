package recording;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OneHandData extends AbstractHandData{

	public OneHandData(final Controller controller) {
		super(controller);
	}

	/**
	gets the current frame from controller
	for each finger, stores the topmost end of each bone (4 points)
	adjusts bone location relativity by subtracting the center of the palm
	returns the adjusted bone locations in the form:
	{feat0=some_float, feat1=some_float, ... feat59=some_float}
	 * @param Leap motion Controller
	 * @return Map<String, Float>
	 **/
	public final Map<String, Float> getHandPosition()
	{
		final Controller controller=getLeap();
		final Frame frame = controller.frame();

		if (frame.fingers().isEmpty())
			return null;

		final List<Vector> fingerBones=getFingerList();

		final HandList hands = controller.frame().hands();
		Vector handCentre = null;
		for (Hand hand : hands){
			handCentre = hand.palmPosition();
		}

		if(handCentre==null)
			return null;

		final Map<String,Float> calibratedFingerBones = new HashMap<String,Float>();
		Vector normalizedJoint;
		int w;
		for(int i=0; i< fingerBones.size();i++){
			normalizedJoint = fingerBones.get(i).minus(handCentre);
			w=i*3;
			for(int j=0; j<3;j++)
				if(j==0)
					calibratedFingerBones.put("feat" + 
							Integer.toString(w+j),normalizedJoint.getX());
				else if(j==1)
					calibratedFingerBones.put("feat" + 
							Integer.toString(w+j),normalizedJoint.getY());
				else
					calibratedFingerBones.put("feat" + 
							Integer.toString(w+j),normalizedJoint.getZ());
		}

		return calibratedFingerBones;
	}
}
