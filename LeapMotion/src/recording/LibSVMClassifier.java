package recording;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.AbstractMap.SimpleEntry;

import database.SignedDB;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.WekaPackageManager;

@SuppressWarnings("deprecation")
public class LibSVMClassifier {
		
		public static final String language= "ISL";
		protected FastVector<Attribute> fvWekaAttributes;
		protected Classifier classifier;
		protected Instances trainingSet;
		
		public static void main(String args[]){
				new LibSVMClassifier();
		}
		
		
		public LibSVMClassifier(){
			String hand="right";
			String type = "num";
			final String filename=language +"_" +  hand + "_" + type + ".model";
			SimpleEntry<List<ArrayList<Double>>, List<Character>> entry=null;
				entry=new SignedDB().getOneHandNumberData(language, hand,OneHandTrainer.ONE_HAND_NUM_FEATURES);
				//entry=new SignedDB().getOneHandAlphabetData(language, hand, OneHandTrainer.ONE_HAND_NUM_FEATURES);
			  
			setupClassifier(entry, OneHandTrainer.ONE_HAND_NUM_FEATURES, filename);
			}
			
			protected Instance createInstanceFromData(Map<String, Float> data){
				 Instance sampleInstance = new DenseInstance(OneHandTrainer.ONE_HAND_NUM_FEATURES+1);
				 	for(int i=0; i<OneHandTrainer.ONE_HAND_NUM_FEATURES;i++){
				 		sampleInstance.setValue((Attribute)fvWekaAttributes.elementAt(i), data.get("feat"+i));
				 	}
				 	sampleInstance.setDataset(trainingSet);
				 	sampleInstance.setClassMissing();
				 	return sampleInstance;
			}
		
protected void setupClassifier(SimpleEntry<List<ArrayList<Double>>, List<Character>> entry, final int features, final String filename){
			
			List<ArrayList<Double>> data= entry.getKey();
			List<Character> target=entry.getValue();
			
			// Declare the feature vector
			 fvWekaAttributes = new FastVector<Attribute>(features+1);
			 for(int i=0;i<features;i++){
				fvWekaAttributes.addElement(new Attribute("feat"+i));
			 }
			 
			 // Declare the class attribute along with its values
			 FastVector<String> fvClassVal = new FastVector<String>();
//			 for(Character i: target){
//				 String character=String.valueOf(i);
//				 if(!fvClassVal.contains(character))
//				 fvClassVal.addElement(character);
//			 }
			 fvClassVal.add("1");
			 fvClassVal.add("-1");
			 
			 Attribute ClassAttribute = new Attribute("theClass", fvClassVal);
			 fvWekaAttributes.addElement(ClassAttribute);
			 
			 // Create an empty training set
			 this.trainingSet = new Instances("Rel", fvWekaAttributes, 10);
			 // Set class index
			 trainingSet.setClassIndex(features);
			 
			 for(int i=0; i<data.size();i++){
				// Create the instance
				 Instance trainingInstance = new DenseInstance(features+1);
				 for(int j=0; j<features;j++){
				 trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(j), data.get(i).get(j));
				 }
				 double num=Character.getNumericValue(target.get(i));
				if(num==1.0)
					trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(features),"1");
				else
					trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(features),"-1");
				 //trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(features),String.valueOf(target.get(i)));
				 
				 // add the instance
				 trainingSet.add(trainingInstance);
		  }
			 
			 try {
				 WekaPackageManager.loadPackages( false, true, false );
				 File f = new File(filename);
				 if(f.exists() && !f.isDirectory()) { 
					// deserialize model
					 classifier= (Classifier) weka.core.SerializationHelper.read(filename);
				 }
				 else{
				// train classifier
				classifier=(Classifier) Class.forName(
			             "weka.classifiers.functions.LibSVM" ).newInstance();
				String options = ( "-S 0 -K 0 -D 3 -G 0.0001 -R 0.0 -N 0.5 -M 40.0 -C 50 -E 0.001 -P 0.1 -B -V" );
				String[] optionsArray = options.split( " " );
				    ((AbstractClassifier) classifier).setOptions( optionsArray );
				classifier.buildClassifier(trainingSet);
				// serialize model
				 weka.core.SerializationHelper.write(filename, classifier);
				 }
			} catch (Exception e) {
				System.err.println("Error during classifier building:"+ e);
			}
		}
		
		public void evaluate() throws Exception{	
			// training using a collection of classifiers (NaiveBayes, SMO (AKA SVM), KNN and Decision trees.)
	        String[] algorithms = {"nb","smo","knn","j48"};
	        for(int w=0; w<algorithms.length;w++){
	            if(algorithms[w].equals("nb"))
	            classifier = new NaiveBayes();
	            if(algorithms[w].equals("smo"))
	            classifier = new SMO();
	            if(algorithms[w].equals("knn"))
	            classifier = new IBk();
	            if(algorithms[w].equals("j48"))
	            classifier = new J48();

	        System.out.println("==========================================================================");
	        System.out.println("training using " + algorithms[w] + " classifier");

	        Evaluation eval = new Evaluation(trainingSet);
	        //perform 10 fold cross validation
	        eval.crossValidateModel(classifier, trainingSet, 10, new Random(1));
	        String output = eval.toSummaryString();
	        System.out.println(output);

	        String classDetails = eval.toClassDetailsString();
	        System.out.println(classDetails);

	        }
		}
		
		public double classifyInstance(Instance data){
				try {
					return classifier.classifyInstance(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0.0;
		}
		
		public String classify(Map<String, Float> data){
			try {
				return trainingSet.classAttribute().value((int)  classifier.classifyInstance(createInstanceFromData(data)));
			} catch (Exception e) {
				System.err.println("Error during classifier building:" + e);
			}
			return null;
		}
		
		public double score(Map<String, Float> data, char c){
			try {
				return getProbabilityForChar(createInstanceFromData(data), c);
			} catch (Exception e) {
				System.err.println("Error during classifier building:" + e);
			}
			return 0.0;
		}
		
		private double getProbabilityForChar(Instance sampleInstance, char c) throws Exception{
				int position=trainingSet.classAttribute().indexOfValue(Character.toString(c));
				double[] fDistribution = classifier.distributionForInstance(sampleInstance);	
				return fDistribution[position];
		}
		
		public Instances getTrainingSet(){
			return trainingSet;
		}
}
