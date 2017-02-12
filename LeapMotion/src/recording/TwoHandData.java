package recording;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Vector;

public final class TwoHandData extends AbstractHandData{

	public TwoHandData(final Controller controller) {
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
	@Override
	public final Map<String, Float> getHandPosition()
	{
		final Controller controller=getLeap();
		final Frame frame = controller.frame();

		while (frame.fingers().isEmpty())
			return null;

		final List<Vector> fingerBones=getFingerList();

		final HandList hands = controller.frame().hands();
		final Vector[] handCentres = new Vector[2];
		int x=0;
		for (Hand hand : hands){
			handCentres[x] = hand.palmPosition();
			x++;
		}

		if(handCentres[1]==null)
			return null;

		final Map<String,Float> calibratedFingerBones = new
				LinkedHashMap<String,Float>();
		Vector handCentre=handCentres[0];
		Vector normalizedJoint;

		for(int i=0; i< fingerBones.size();i++){
			if(i>=20)
				handCentre=handCentres[1];
			normalizedJoint = fingerBones.get(i).minus(handCentre);

			for(int j=0; j<3;j++)
				if(j==0)
					calibratedFingerBones.put("feat" + Integer.toString(i*3+j),
							normalizedJoint.getX());
				else if(j==1)
					calibratedFingerBones.put("feat" + Integer.toString(i*3+j),
							normalizedJoint.getY());
				else
					calibratedFingerBones.put("feat" + Integer.toString(i*3+j),
							normalizedJoint.getZ());
		}

		return calibratedFingerBones;
	}

}
