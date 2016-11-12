package recording;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.AbstractMap.SimpleEntry;

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
public abstract class AbstractSignClassifier {
		
		public static final String language= "ISL";
		protected FastVector<Attribute> fvWekaAttributes;
		protected Classifier classifier;
		protected Instances trainingSet;
		
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
			 trainingSet.setClassIndex(features);
			 
			 for(int i=0; i<data.size();i++){
				// Create the instance
				 Instance trainingInstance = new DenseInstance(features+1);
				 for(int j=0; j<features;j++){
				 trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(j), data.get(i).get(j));
				 }
				 trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(features),String.valueOf(target.get(i)));
				 
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
//				classifier=(Classifier) Class.forName(
//			             "weka.classifiers.functions.LibSVM" ).newInstance();
//				String options = ( "-S 0 -K 0 -D 3 -G 0.0001 -R 0.0 -N 0.5 -M 40.0 -C 50 -E 0.001 -P 0.1" );
//				String[] optionsArray = options.split( " " );
//				    ((AbstractClassifier) classifier).setOptions( optionsArray );
					 classifier=new IBk();
				//classifier=new NaiveBayes();
				//classifier=new J48();
				//classifier= new SMO();
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
		
		protected Instance createInstanceFromData(Map<String, Float> data){
			//To be implemented by subclass
			return null;
		}
		
		public Instances getTrainingSet(){
			return trainingSet;
		}
}