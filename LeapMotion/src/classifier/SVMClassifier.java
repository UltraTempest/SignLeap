package classifier;

import java.io.File;
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
import recording.SignTrainer;

public class SVMClassifier{
	private static final String language= "ISL";
	svm_problem prob;
	svm_model model;
	double[][] train;
	
	public static void main(String args[]){
		SVMClassifier classifier = new SVMClassifier();
		classifier.evaluateLoop();
	}
	
	public SVMClassifier(){
		
		final String filename=language + "svm.model";
		SimpleEntry<List<ArrayList<Double>>, List<Character>> entry=null;
		//entry=new SignedDB().getOneHandAlphabetData(language, "right", SignTrainer.ONE_HAND_NUM_FEATURES);
		entry=new SignedDB().getOneHandNumberData(language, "right", SignTrainer.ONE_HAND_NUM_FEATURES);
		setupClassifier(entry, SignTrainer.ONE_HAND_NUM_FEATURES, filename);
	}
	
	public void evaluateLoop(){
		for(int i=0;i<train.length; i++)
		evaluate(train[i]);
	}

        protected void setupClassifier(SimpleEntry<List<ArrayList<Double>>, List<Character>> entry, final int features, final String filename){
			
			List<ArrayList<Double>> data= entry.getKey();
			List<Character> target=entry.getValue();
			 train = new double[target.size()][]; 
			 //train = new double[1000][]; 
			 int trainPosition=0;
			 for(int i=0; i<data.size();i++){
				// Create the instance
				 double[] array = new double[features+1];
				 int position=0;
				 array[position]=getCharForNumber(target.get(i));
				 for(int j=0; j<features;j++){
				     position++;
					 array[position]=data.get(i).get(j);
				 }
//				 double num=0.0;
//				 num=getCharForNumber(target.get(i));
				 train[trainPosition]=array;
				 trainPosition++;
				 
				 // add the instance
				// if(num==1.0){
//					 train[trainPosition]=array;
//					 trainPosition++;
				// }
	
		  }
			 
			 try {
				 File f = new File(filename);
				 if(f.exists() && !f.isDirectory()) { 
					// deserialize model
					 model= svm.svm_load_model(filename);
				 }
				 else{
					 model = svmTrain(train);
					 svm.svm_save_model(filename,model);
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
		
		public double evaluate(double[] features) 
		{
            svm_node[] nodes = createNodesFromFeatureArray(features);
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
		
		public double getProbabilityForInstance(Map<String, Float> data, char symbol){
			    double[] features =createFeatureArrayFromData(data, symbol);
			    svm_node[] nodes=createNodesFromFeatureArray(features);
			    int totalClasses = 5;      
			    int[] labels = new int[totalClasses];
			    svm.svm_get_labels(model,labels);

			    double[] prob_estimates = new double[totalClasses];
			    svm.svm_predict_probability(model, nodes, prob_estimates);          
			    return prob_estimates[(int) features[0]-1];
		   }
		
		private svm_node[] createNodesFromFeatureArray(double[] features){
			 svm_node[] nodes = new svm_node[features.length-1];
			    for (int i = 1; i < features.length; i++)
			    {
			        svm_node node = new svm_node();
			        node.index = i;
			        node.value = features[i];

			        nodes[i-1] = node;
			    }
			    return nodes;
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
		   // param.svm_type = svm_parameter.ONE_CLASS;
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
		
		private double[] createFeatureArrayFromData(Map<String, Float> data, char symbol){
			int arraySize=SignTrainer.ONE_HAND_NUM_FEATURES+1;
			 double[] features = new double[arraySize];
			 features[0]=getCharForNumber(symbol);
			 	for(int i=1; i<arraySize;i++){
			 		int num=i-1;
			 		features[i]=data.get("feat"+num);
			 	}
			 	return features;
		}
}

