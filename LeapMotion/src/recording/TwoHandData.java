package recording;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Vector;

public class TwoHandData extends AbstractHandData{

	@Override
	public Map<String, Float> getHandPosition(Controller controller)
	{
		Frame frame = controller.frame();
		while (frame.fingers().isEmpty())
			return null;

		List<Vector> fingerBones=getFingerList(controller);

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

}
