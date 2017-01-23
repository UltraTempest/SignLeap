package classifier;

import java.io.File;
import java.util.Map;
import java.util.Random;

import recording.HandData.Handedness;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SignClassifier {

	public static final String language= "ISL";
	private Classifier classifier;
	private int numberOfFeatures;
	private Instances trainingSet;
	private Instances testingSet;

	public static void main(String args[]){
		new SignClassifier(Handedness.RIGHT, "num").evaluate();
	}

	public SignClassifier(Handedness hand, String type){
		final String filename=language +"_" +  hand + "_" + type + ".model";
		setupClassifier(filename);
	}

	private Instance createInstanceFromData(Map<String, Float> data){
		Instance sampleInstance = new DenseInstance(numberOfFeatures+1);
		for(int i=0; i<numberOfFeatures;i++){
			sampleInstance.setValue(i, data.get("feat"+i));
		}
		sampleInstance.setDataset(testingSet);
		return sampleInstance;
	}

	private void setupClassifier(String filename){
		try {
			DataSource source = new DataSource("SignData/TrainingData/numRight.arff");
			trainingSet= source.getDataSet();
			source= new DataSource("SignData/TestingData/numRight.arff");
			testingSet= source.getDataSet();
			numberOfFeatures=trainingSet.numAttributes()-1;
			trainingSet.setClassIndex(numberOfFeatures);
			testingSet.setClassIndex(numberOfFeatures);
			File f = new File(filename);
			if(f.exists() && !f.isDirectory())  
				// deserialize model
				classifier=(RandomForest) weka.core.SerializationHelper.read(filename);
			else{
				// train classifier
				classifier= new RandomForest();
				classifier.buildClassifier(trainingSet);

				// serialize model
				weka.core.SerializationHelper.write(filename, classifier);
			}
		} catch (Exception e) {
			System.err.println("Error during classifier building:"+ e);
		}
	}

	public double score(Map<String, Float> data, char c){
		Instance sampleInstance=createInstanceFromData(data);
		try {
			double[] fDistribution = classifier.distributionForInstance(sampleInstance);
			int position=getLabelDistributionPosition(String.valueOf(c), fDistribution);
			return fDistribution[position];
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return 0.0;
	}	

	private int getLabelDistributionPosition(String charToFind, double[] fDistribution){
		for(int i=0; i<fDistribution.length;i++){
			if(charToFind.equals(testingSet.classAttribute().value(i)))
				return i;
		}
		return -1;
	}

	public void evaluate(){	
		// training using a collection of classifiers (NaiveBayes, SMO (AKA SVM), KNN and Decision trees.)
		String[] algorithms = {"Random Forest", "nb","smo","knn","j48", "libSVM"};
		for(int w=0; w<algorithms.length;w++){
			if(algorithms[w].equals("nb"))
				classifier = new NaiveBayes();
			else if(algorithms[w].equals("smo"))
				classifier = new SMO();
			else if(algorithms[w].equals("knn"))
				classifier = new IBk();
			else if(algorithms[w].equals("j48"))
				classifier = new J48();
			else if(algorithms[w].equals("libSVM"))
				classifier = new LibSVM();

			System.out.println("==========================================================================");
			System.out.println("training using " + algorithms[w] + " classifier");

			//perform 10 fold cross validation
			try {
				Evaluation eval = new Evaluation(trainingSet);
				classifier.buildClassifier(trainingSet);
				eval.crossValidateModel(classifier, testingSet, 10, new Random(1));
				System.out.println(classifier);
				System.out.println(eval.toSummaryString());
				System.out.println(eval.toMatrixString());
				System.out.println(eval.toClassDetailsString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
