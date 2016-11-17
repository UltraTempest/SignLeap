package recording;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import database.SignedDB;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.WekaPackageManager;



@SuppressWarnings("deprecation")
public class SingleClassifier{
	public static void main(String args[]){
		SVMClassifier classifier = new SVMClassifier();
		classifier.evaluateLoop();
		//classifier.save();
	}
}

@SuppressWarnings("deprecation")
class SVMClassifier extends AbstractSignClassifier{
	
	svm_problem prob;
	svm_model model;
	double[][] train;
	
	public SVMClassifier(){
		
		final String filename=language + ".model";
		SimpleEntry<List<ArrayList<Double>>, List<Character>> entry=null;
		//entry=new SignedDB().getOneHandAlphabetData(language, "right", OneHandTrainer.ONE_HAND_NUM_FEATURES);
		entry=new SignedDB().getOneHandNumberData(language, "right", OneHandTrainer.ONE_HAND_NUM_FEATURES);
		  
		setupClassifier(entry, OneHandTrainer.ONE_HAND_NUM_FEATURES, filename);
		}
	
	public void evaluateLoop(){
		for(int i=0;i<1000; i++)
		evaluate(train[i], model);
	}
		
		@Override
		protected Instance createInstanceFromData(Map<String, Float> data){
			 Instance sampleInstance = new DenseInstance(OneHandTrainer.ONE_HAND_NUM_FEATURES+1);
			 	for(int i=0; i<OneHandTrainer.ONE_HAND_NUM_FEATURES;i++){
			 		sampleInstance.setValue((Attribute)fvWekaAttributes.elementAt(i), data.get("feat"+i));
			 	}
			 	sampleInstance.setDataset(trainingSet);
			 	sampleInstance.setClassMissing();
			 	return sampleInstance;
		}
		
		@Override
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
			 
			 train = new double[target.size()][]; 
			 int trainPosition=0;
			 for(int i=0; i<data.size();i++){
				// Create the instance
				 Instance trainingInstance = new DenseInstance(features+1);
				 double[] array = new double[features+1];
				 int position=0;
				 array[position]=getCharForNumber(target.get(i));
				 for(int j=0; j<features;j++){
				     trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(j), data.get(i).get(j));
				     position++;
					 array[position]=data.get(i).get(j);
				 }
				 trainingInstance.setValue((Attribute)fvWekaAttributes.elementAt(features),String.valueOf(target.get(i)));
				 train[trainPosition]=array;
				 trainPosition++;
				 
				 // add the instance
				 trainingSet.add(trainingInstance);
		  }
			 
			 try {
				 model= svm.svm_load_model("svm.model");
				 if(model==null)
				 model = svmTrain(train);
				 
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
				String options = ( "-S 1 -K 0 -D 3 -G 0.0001 -R 0.0 -N 0.5 -M 40.0 -C 50 -E 0.001 -P 0.1" );
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
		
		public double predict(){
			svm_node x[] = prob.x[0]; // let's try the first data pt in problem
			double[] prob_estimates = new double[1]; 
			double i=svm.svm_predict_probability(model, x, prob_estimates);
			System.out.println(i);
			return i;
		}
		
		public double evaluate(double[] features, svm_model model) 
		{
		    svm_node[] nodes = new svm_node[features.length-1];
		    for (int i = 1; i < features.length; i++)
		    {
		        svm_node node = new svm_node();
		        node.index = i;
		        node.value = features[i];

		        nodes[i-1] = node;
		    }

		    int totalClasses = 5;       
		    int[] labels = new int[totalClasses];
		    svm.svm_get_labels(model,labels);

		    double[] prob_estimates = new double[totalClasses];
		    double v = svm.svm_predict_probability(model, nodes, prob_estimates);

		    for (int i = 0; i < totalClasses; i++){
		        System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
		    }
		    System.out.println("(Actual:" + features[0] + " Prediction:" + v + ")");            

		    return v;
		}
		
		public void save(){
			try {
				svm.svm_save_model("svm.model",model);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private svm_model svmTrain(double[][] train) {
		    prob = new svm_problem();
		    int dataCount = train.length;
		    prob.y = new double[dataCount];
		    prob.l = dataCount;
		    prob.x = new svm_node[dataCount][];     

		    for (int i = 0; i < dataCount; i++){            
		        double[] features = train[i];
		        prob.x[i] = new svm_node[features.length-1];
		        for (int j = 1; j < features.length; j++){
		            svm_node node = new svm_node();
		            node.index = j;
		            node.value = features[j];
		            prob.x[i][j-1] = node;
		        }           
		        prob.y[i] = features[0];
		    }               

		    svm_parameter param = new svm_parameter();
		    param.probability = 1;
		    param.gamma = 0.5;
		    param.nu = 0.5;
		    param.C = 1;
		    param.svm_type = svm_parameter.C_SVC;
		    param.kernel_type = svm_parameter.LINEAR;       
		    param.cache_size = 20000;
		    param.eps = 0.001;      

		    model = svm.svm_train(prob, param);
		    return model;
		}
		
		private double getCharForNumber(char a) {
			if(Character.isDigit(a))
				return Character.getNumericValue(a);
		    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		    for(int i=0; i<alphabet.length;i++){
		    	if(alphabet[i]==a)
		    		return i;
		    }
		    return 27.0;
		}
}
