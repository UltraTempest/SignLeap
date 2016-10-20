package recording;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.SignedDB;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

@SuppressWarnings({"deprecation", "rawtypes"})
public class SignClassifier {
	
	private FastVector fvWekaAttributes;
	protected Classifier cls;
	protected Instances trainingSet;
	
	@SuppressWarnings({ "unchecked" })
		public SignClassifier(){
		SimpleEntry<List<ArrayList<Double>>, List<Character>> entry=new SignedDB().getAllData();
		List<ArrayList<Double>> data= entry.getKey();
		List<Character> target=entry.getValue();
		
		// Declare the feature vector
		 fvWekaAttributes = new FastVector(61);
		 for(int i=0;i<60;i++){
			fvWekaAttributes.addElement(new Attribute("feat"+i));
		 }
		 
		 // Declare the class attribute along with its values
		 FastVector fvClassVal = new FastVector();
		 for(Character i: target){
			 String character=String.valueOf(i);
			 if(!fvClassVal.contains(character))
			 fvClassVal.addElement(character);
		 }
		 
		 Attribute ClassAttribute = new Attribute("theClass", fvClassVal);
		 fvWekaAttributes.addElement(ClassAttribute);
		 
		 // Create an empty training set
		 this.trainingSet = new Instances("Rel", fvWekaAttributes, 10);
		 // Set class index
		 trainingSet.setClassIndex(60);
		 
		 for(int i=0; i<data.size();i++){
			// Create the instance
			 Instance trainingInstance = new DenseInstance(61);
			 for(int j=0; j<60;j++){
			 trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(j), data.get(i).get(j));
			 }
			 trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(60),String.valueOf(target.get(i)));
			 
			 // add the instance
			 trainingSet.add(trainingInstance);
		 }	 
		 
		 try {
			 File f = new File("ASL.model");
			 if(f.exists() && !f.isDirectory()) { 
				// deserialize model
				 cls= (Classifier) weka.core.SerializationHelper.read("ASL.model");
			 }
			 else{
				// train classifier
			//cls=new IBk();
			//cls=new NaiveBayes();
			//cls=new J48();
			cls=new LibSVM();
			cls.buildClassifier(trainingSet);
			// serialize model
			 weka.core.SerializationHelper.write("ASL.model", cls);
			 }
		} catch (Exception e) {
			System.err.println("Error during classifier building:"+ e);
		}
	}
	
	public String classify(Map<String, Float> data){
		try {
			 Instance sampleInstance = new DenseInstance(61);
			 	for(int i=0; i<60;i++){
			 		sampleInstance.setValue((Attribute)fvWekaAttributes.elementAt(i), data.get("feat"+i));
			 	}
			 	sampleInstance.setDataset(trainingSet);
			return trainingSet.classAttribute().value((int)  cls.classifyInstance(sampleInstance));
		} catch (Exception e) {
			System.err.println("Error during classifier building:" + e);
		}
		return null;
	}
	
	public double score(Map<String, Float> data, char c){
		try {
			 Instance sampleInstance = new DenseInstance(61);
			 	for(int i=0; i<60;i++){
			 		sampleInstance.setValue((Attribute)fvWekaAttributes.elementAt(i), data.get("feat"+i));
			 	}
			 	sampleInstance.setDataset(trainingSet); 	
			 	 return getProbabilityForChar(sampleInstance, c);
		} catch (Exception e) {
			System.err.println("Error during classifier building:" + e);
		}
		return 0.0;
	}
	
	private double getProbabilityForChar(Instance sampleInstance, char c) throws Exception{
			int position=trainingSet.classAttribute().indexOfValue(Character.toString(c));
			 // Get the likelihood of each classes
		 	 // fDistribution[0] is the probability of being “positive”
		 	 // fDistribution[1] is the probability of being “negative”
			double[] fDistribution = cls.distributionForInstance(sampleInstance);	
			return fDistribution[position];
	}
}
