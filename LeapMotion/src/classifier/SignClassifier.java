package classifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Random;

import recording.AbstractHandData.Handedness;
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

public class SignClassifier implements ISignClassifier{

	public static final String language= "ISL";
	protected Classifier classifier;
	private int numberOfFeatures;
	private Instances trainingSet;
	private Instances testingSet;
	private final int movingAverageFilterPeriods=5;
	private MovingAverageFilter move;

	public static void main(String args[]){
		//new SignClassifier(null, "num2").evaluate();
		new SignClassifier(Handedness.RIGHT, "alpha").evaluate();
	}

	public static void initialise(final Handedness hand, final String type){		
		String name;
		if(hand!=null)
			name=type +hand;
		else
			name=type;
		final String filename="Classifiers/"+language +name+ ".model";
		final File f = new File(filename);
		createParentDirectory(f);
		if(f.exists() && !f.isDirectory()) return;  
		try {
			final DataSource source = new DataSource("SignData/TrainingData/"+name+ ".arff");
			final Instances trainingSet= source.getDataSet();
			final int numberOfFeatures=trainingSet.numAttributes()-1;
			trainingSet.setClassIndex(numberOfFeatures);
			final Classifier classifier= new RandomForest();
			//trainingSet=PCA(trainingSet);
			classifier.buildClassifier(trainingSet);
			// serialize model
			weka.core.SerializationHelper.write(filename, classifier);
		} catch (final Exception e) {
			System.err.println("Error during classifier building:"+ e);
		}
	}

	private static void createParentDirectory(final File f) {
		final File parentDir = f.getParentFile();
		if(! parentDir.exists()) 
			parentDir.mkdirs();
	}

	public SignClassifier(final Handedness hand, final String type){
		resetRollingAverage();
		String name;
		if(hand!=null)
			name=type +hand;
		else
			name=type;
		final String filename="Classifiers/"+language +name+ ".model";
		try {
			DataSource source = new DataSource("SignData/TrainingData/"+name+ ".arff");
			trainingSet= source.getDataSet();
			source= new DataSource("SignData/TestingData/"+name+".arff");
			testingSet= source.getDataSet();
			numberOfFeatures=trainingSet.numAttributes()-1;
			trainingSet.setClassIndex(numberOfFeatures);
			testingSet.setClassIndex(numberOfFeatures);
			final File f = new File(filename);
			createParentDirectory(f);
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
		} catch (final Exception e) {
			System.err.println("Error during classifier building:"+ e);
		}
	}

	protected final Instance createInstanceFromData(final Map<String, Float> data){
		final Instance sampleInstance = new DenseInstance(numberOfFeatures+1);
		for(int i=0; i<numberOfFeatures;i++){
			sampleInstance.setValue(i, data.get("feat"+i));
		}
		sampleInstance.setDataset(testingSet);
		return sampleInstance;
	}

	protected final String classify(final Map<String, Float> data,final String previousChar){
		final Instance sampleInstance=createInstanceFromData(data);
		try {
			final double i = classifier.classifyInstance(sampleInstance);
			final double[] fDistribution = classifier.distributionForInstance(
					sampleInstance);
			System.out.println("Actual: " + testingSet.classAttribute().value(
					(int) i) +" : " + fDistribution[(int) i]);
			return testingSet.classAttribute().value((int) i);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected final String classify(final double[] fDistribution,
			final Instance sampleInstance){
		try {
			final double i = classifier.classifyInstance(sampleInstance);
			System.out.println("Actual: " + testingSet.classAttribute().value(
					(int) i) +" : " + fDistribution[(int) i]);
			return testingSet.classAttribute().value((int) i);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public double score(final Map<String, Float> data,final String expectedChar){
		final Instance sampleInstance=createInstanceFromData(data);
		try {
			final double[] fDistribution = classifier.distributionForInstance(sampleInstance);
			double probabilityForExpected=getProbabilityForClass(expectedChar, fDistribution);
			final String classified=classify(fDistribution,sampleInstance);
			System.out.println("Expected: "+ expectedChar + " : " + probabilityForExpected);
			if(classified.equals(expectedChar)||probabilityForExpected>0.3)
				while(probabilityForExpected<0.5)
					probabilityForExpected+=0.3;
			probabilityForExpected=rollingTotal(probabilityForExpected);
			System.out.println("Rolling Average: " + probabilityForExpected + "\n");
			return probabilityForExpected;
		} catch (final Exception e) {
			e.printStackTrace();
		}	
		return 0.0;
	}	

	protected final double rollingTotal(final double d) {
		move.add(d);
		return move.getAverage();
	}

	public final void resetRollingAverage(){
		move=new MovingAverageFilter(movingAverageFilterPeriods);
	}

	private int getLabelDistributionPosition(final String charToFind,
			final double[] fDistribution){
		for(int i=0; i<fDistribution.length;++i){
			if(charToFind.equals(testingSet.classAttribute().value(i)))
				return i;
		}
		return -1;
	}

	protected final double getProbabilityForClass(final String charToFind,final double[] fDistribution){
		final int position=getLabelDistributionPosition(charToFind, fDistribution);
		return fDistribution[position];
	}

	public final void evaluate(){	
		// training using a collection of classifiers (NaiveBayes, SMO (AKA SVM), KNN and Decision trees.)
		final String[] algorithms = {"nb","smo","knn","j48", "libSVM","Random Forest"};
		try {
			final FileWriter fw= new FileWriter("Evaluation.txt");
			@SuppressWarnings("resource")
			final BufferedWriter bw = new BufferedWriter(fw);
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
				else if(algorithms[w].equals("Random Forest"))
					classifier = new RandomForest();
				System.out.println("==========================================================================");
				System.out.println("training using " + algorithms[w] + " classifier");
				bw.write("==========================================================================");
				bw.write("training using " + algorithms[w] + " classifier");
				bw.write("==========================================================================");
				//perform 10 fold cross validation

				final Evaluation eval = new Evaluation(trainingSet);
				classifier.buildClassifier(trainingSet);
				eval.crossValidateModel(classifier, testingSet, 10, new Random(1));
				//				System.out.println(classifier.toString());
				//				System.out.println(eval.toSummaryString());
				//				System.out.println(eval.toMatrixString());
				//				System.out.println(eval.toClassDetailsString());
				bw.write(classifier.toString());
				bw.write(eval.toSummaryString());
				bw.write(eval.toMatrixString());
				bw.write(eval.toClassDetailsString());
			}
		}catch(final Exception e){
			e.printStackTrace();
		}
	}
}
