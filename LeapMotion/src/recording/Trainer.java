package recording;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import com.leapmotion.leap.Controller;

import database.SignedDB;
import processing.HandData;

public class Trainer {
	
	public static void main(String args[]){
		new GuessTrainer().train();
		//guess();
	}
}
	

class GuessTrainer{
	
	int NUM_SAMPLES = 100;
	long SAMPLE_DELAY = (long) 0.1;
	int NUM_FEATURES = 60;

	void guess() throws InterruptedException, IOException{
	    while(true){
	        guessChar();
	        Thread.sleep(1);
	    }
	}

	char getCharToTrain(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter char to train: ");
		char training_char = scan.nextLine().charAt(0);
		scan.close();
	    return Character.toLowerCase(training_char);
	}


	void trainChar(char training_char) throws InterruptedException{
	   Controller controller = new Controller();
	   HandData handData=new HandData();
	   for(int i=0; i< NUM_SAMPLES;i++){
	        Thread.sleep(SAMPLE_DELAY);
	        Map<String, Float> sample = handData.getHandPosition(controller);
	        while(sample.size()!=NUM_FEATURES){
	           System.out.println("Please place only right hand in view");
	            sample = handData.getHandPosition(controller);
	        }
	        System.out.println(sample);
	        new SignedDB().insertSignValues(training_char, sample, "isl_alpha_data");
	   }
	   System.out.println("Done training");
	}
	
	void train(){
	    while(true)
	    {
	        char training_char = getCharToTrain();
	        try {
				Thread.sleep(2);
				trainChar(training_char);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	}

		void guessChar() throws IOException{
	    
		//char prev = 'a';	
			
	    Controller controller = new Controller();
	    controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
	    
	    }
}
