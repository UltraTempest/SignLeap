package test;

import static org.junit.Assert.*;

import org.junit.Test;

import recording.HandData.Handedness;
import recording.SignClassifier;
import weka.core.Instance;

public class SignClassifierTest{
	
	@Test
	public void rightClassificationTest(){
		classificationTest(Handedness.RIGHT);
	}
	
	@Test
	public void leftClassificationTest(){
		classificationTest(Handedness.LEFT);
	}
	
	public void classificationTest(Handedness hand){
		 SignClassifier signCls=new SignClassifier(hand.toString());
		 /* Counters for correct and wrong predictions. */
		 int correct = 0, wrong = 0;
		 /* Classify all instances and check with the correct class values */
		 for (Instance inst : signCls.getTrainingSet()) {
		     double predictedClassValue = signCls.classifyInstance(inst);
		     double realClassValue = inst.classValue();
		     if (predictedClassValue==realClassValue)
		         correct++;
		     else
		         wrong++;
		 }
		 try {
			signCls.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 System.out.println("Classifier got " + correct + " right and " + wrong + " wrong" );
		 if(wrong>100)
			 fail("Failed");
	}
}