package classifier;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.SignedDB;
import processing.SignTrainer;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

public class OneHandSignClassifier extends AbstractSignClassifier{
	
		public OneHandSignClassifier(String hand,String type){
			
		final String filename=language +"_" +  hand + "_" + type + ".model";
		SimpleEntry<List<ArrayList<Double>>, List<Character>> entry=null;
		if(type.equals("num"))
			entry=new SignedDB().getOneHandNumberData(language, hand,SignTrainer.ONE_HAND_NUM_FEATURES);
		else
			entry=new SignedDB().getOneHandAlphabetData(language, hand, SignTrainer.ONE_HAND_NUM_FEATURES);
		  
		setupClassifier(entry, SignTrainer.ONE_HAND_NUM_FEATURES, filename);
		}
		
		@SuppressWarnings("deprecation")
		@Override
		protected Instance createInstanceFromData(Map<String, Float> data){
			 Instance sampleInstance = new DenseInstance(SignTrainer.ONE_HAND_NUM_FEATURES+1);
			 	for(int i=0; i<SignTrainer.ONE_HAND_NUM_FEATURES;i++){
			 		sampleInstance.setValue((Attribute)fvWekaAttributes.elementAt(i), data.get("feat"+i));
			 	}
			 	sampleInstance.setDataset(trainingSet);
			 	sampleInstance.setClassMissing();
			 	return sampleInstance;
		}
}
