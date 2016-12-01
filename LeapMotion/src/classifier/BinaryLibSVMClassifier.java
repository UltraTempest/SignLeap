package classifier;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import database.SignedDB;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

@SuppressWarnings("deprecation")
public class BinaryLibSVMClassifier {
		
		public static final String language= "ISL";
		private FastVector<Attribute> fvWekaAttributes;
		private final Map<String, Classifier> classifierMap= new HashMap<String, Classifier>();
		private final Map<String, Instances> trainingSetMap= new HashMap<String, Instances>();
		private int numberOfFeatures;
		
		public static void main(String args[]){
				new BinaryLibSVMClassifier("right", "num", 60);
				//new BinaryLibSVMClassifier("right", "alpha", 60);
		}
		
		
		public BinaryLibSVMClassifier(String hand, String type, int numberOfFeatures){
			final String filename=language +"_" +  hand + "_" + type;
			SimpleEntry<List<ArrayList<Double>>, List<Character>> entry=null;
			   if(type.equals("num"))
				entry=new SignedDB().getOneHandNumberData(language, hand,numberOfFeatures);
			   else
				entry=new SignedDB().getOneHandAlphabetData(language, hand, numberOfFeatures);
			   this.numberOfFeatures=numberOfFeatures;
			   setupClassifier(entry,filename);
			}
			
			private Instance createInstanceFromData(Map<String, Float> data, String c){
				Instance sampleInstance = new DenseInstance(numberOfFeatures+1);
				 	for(int i=0; i<numberOfFeatures;i++){
				 		sampleInstance.setValue(fvWekaAttributes.elementAt(i), data.get("feat"+i));
				 	}
				 	sampleInstance.setDataset(trainingSetMap.get(c));
				 	return sampleInstance;
			}
		
			private void setupClassifier(SimpleEntry<List<ArrayList<Double>>, List<Character>> entry, String filename){
			
			final String originalFilename=filename;
			List<ArrayList<Double>> data= entry.getKey();
			List<Character> target=entry.getValue();
		
			declareFeatureVector(); 
			final List<String> classValueList = new ArrayList<String>();
			
			 // Declare the class attribute along with its values
			 FastVector<String> fvClassVal = new FastVector<String>();
			 fvClassVal.addElement("target");
			 fvClassVal.addElement("outlier");
			 Attribute ClassAttribute = new Attribute("theClass", fvClassVal);
			 fvWekaAttributes.addElement(ClassAttribute);
			
			 for(Character i: target){
				 String character=String.valueOf(i);
				 if(!classValueList.contains(character)){
				 classValueList.add(character);
				 }
			 }
			 
			 Instances trainingSet=createTrainingset();
			 
			 for(String modelVal: classValueList){
			 for(int i=0; i<data.size();i++){
				// Create the instance
				 Instance trainingInstance = new DenseInstance(numberOfFeatures+1);
				 for(int j=0; j<numberOfFeatures;j++){
				 trainingInstance.setValue(fvWekaAttributes.elementAt(j), data.get(i).get(j));
				 }
				 String currentClassVal=String.valueOf(target.get(i));
				 if(currentClassVal.equals(modelVal))
					trainingInstance.setValue(fvWekaAttributes.elementAt(numberOfFeatures),"target");
				else
					trainingInstance.setValue(fvWekaAttributes.elementAt(numberOfFeatures),"outlier");
				 
				 // add the instance
				 trainingSet.add(trainingInstance);
		  }
			 try {
				 filename=originalFilename + modelVal + ".model";
				 File f = new File(filename);
				 if(f.exists() && !f.isDirectory()) { 
					// deserialize model
					 classifierMap.put(modelVal,(Classifier) weka.core.SerializationHelper.read(filename));
				 }
				 else{
				// train classifier
				Classifier classifier=new IBk();
//				LibSVM classifier=new LibSVM();
//				classifier.setWeights("4 1");
//				String[] options = {"-S", "0", "-K", "2", "-D", "3", "-G", "0.0001", "-C", "50", "-B", "-V", "-W", "1 4"}; 
//				classifier.setOptions( options );
				classifier.buildClassifier(trainingSet);
				classifierMap.put(modelVal, classifier);
				
				// serialize model
				 weka.core.SerializationHelper.write(filename, classifier);
				 }
				 trainingSetMap.put(modelVal, trainingSet);
			} catch (Exception e) {
				System.err.println("Error during classifier building:"+ e);
			}
		 }
		}
		
		public double score(Map<String, Float> data, char c){
			String charToFind=String.valueOf(c);
			Instance sampleInstance=createInstanceFromData(data, charToFind);
			//int position=trainingSetMap.get(charToFind).classAttribute().indexOfValue("target");
			int position=0;
			try {
				double[] fDistribution = classifierMap.get(charToFind).distributionForInstance(sampleInstance);
				double i=classifierMap.get(charToFind).classifyInstance(sampleInstance);
				sampleInstance.setClassValue(i);
				return fDistribution[position];
			} catch (Exception e) {
				e.printStackTrace();
			}	
			return 0.0;
		}	
		
		private Instances createTrainingset(){
			 // Create an empty training set
			 Instances trainingSet = new Instances("Rel", fvWekaAttributes, 10);
			 // Set class index
			 trainingSet.setClassIndex(numberOfFeatures);
			 return trainingSet;
		}
		
		private void declareFeatureVector(){
			// Declare the feature vector
			 fvWekaAttributes = new FastVector<Attribute>(numberOfFeatures+1);
			 for(int i=0;i<numberOfFeatures;i++){
				fvWekaAttributes.addElement(new Attribute("feat"+i));
			 }
		}
}
