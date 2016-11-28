package classifier;

import java.util.Map;

import database.SignedDB;
import recording.SignTrainer;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

public class TwoHandSignClassifier extends AbstractSignClassifier{
	
	@SuppressWarnings({ })
	public TwoHandSignClassifier(String hand){
		
    String filename=language +"_two_hand.model";
	setupClassifier(new SignedDB().getTwoHandData(language, hand, SignTrainer.TW0_HAND_NUM_FEATURES), SignTrainer.TW0_HAND_NUM_FEATURES, filename);
	
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected Instance createInstanceFromData(Map<String, Float> data){
		 Instance sampleInstance = new DenseInstance(SignTrainer.TW0_HAND_NUM_FEATURES+1);
		 	for(int i=0; i<SignTrainer.TW0_HAND_NUM_FEATURES;i++){
		 		sampleInstance.setValue((Attribute)fvWekaAttributes.elementAt(i), data.get("feat"+i));
		 	}
		 	sampleInstance.setDataset(trainingSet);
		 	sampleInstance.setClassMissing();
		 	return sampleInstance;
	}
}
