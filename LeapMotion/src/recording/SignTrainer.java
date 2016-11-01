package recording;

import java.util.Map;
import java.util.Scanner;

import com.leapmotion.leap.Controller;

import database.SignedDB;

public class SignTrainer {
	
	public static void main(String args[]){
		new Trainer().train();
	}
}	

class Trainer{
	
	private final int NUM_SAMPLES = 1000;
	private final long SAMPLE_DELAY = (long) 0.1;
	public static final int NUM_FEATURES = 60;

	private char getCharToTrain(Scanner scan){
		System.out.println("Enter char to train: ");
		char training_char = scan.nextLine().charAt(0);
	    return Character.toLowerCase(training_char);
	}
	
	private String getHandUsed(Scanner scan){
		System.out.println("Hand to be used? r/l?");
		char handChar = scan.nextLine().charAt(0);
		scan.close();
	    return handChar=='r'? "right" : "left";
	}


	public void trainChar(char training_char, String hand) throws InterruptedException{
	   Controller controller = new Controller();
	   HandData handData=new HandData();
	   for(int i=0; i< NUM_SAMPLES;i++){
	        Thread.sleep(SAMPLE_DELAY);
	        Map<String, Float> sample = handData.getHandPosition(controller);
	        while(sample==null || sample.size()!=NUM_FEATURES){
	           System.out.println("Please place only " + hand + " hand in view");
	            sample = handData.getHandPosition(controller);
	        }
	        System.out.println("Inserted " + training_char +" no." + i + ": " + sample);
	        new SignedDB().insertSignValues("isl_" + hand + "_data.db", training_char, sample, "alpha_data");
	   }
	   System.out.println("Done training");
	}
	
	public void train(){
		Scanner scan = new Scanner(System.in);
	        char training_char = getCharToTrain(scan);
	        String hand=getHandUsed(scan);
	        try {
				trainChar(training_char,hand);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}