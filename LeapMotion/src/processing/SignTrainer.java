package processing;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;

import classifier.SignClassifier;
import processing.core.PApplet;
import processing.core.PImage;
import recording.HandData.Handedness;
import recording.ITrainer;
import recording.OneHandTrainer;
import recording.TwoHandTrainer;

public class SignTrainer extends PApplet{

	public static final char charToTrain='a';
	public static final Handedness hand= Handedness.RIGHT;
	private final Controller controller = new Controller();
	private final String filePath="SignData/TrainingData/alpha"+ hand +".arff";
	private final String filePath2="SignData/TrainingData/num2.arff";
	private final ITrainer trainer = new OneHandTrainer(controller, charToTrain, hand,filePath);
	@SuppressWarnings("unused")
	private final ITrainer trainer2 = new TwoHandTrainer(controller, charToTrain, filePath2);

	private PImage charImage; 

	public static void main(String[] args) {
		PApplet.main("processing.SignTrainer");
	}

	public void settings(){
		size(600, 600);
	}

	public void setup(){ 
		controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		String filename=SignClassifier.language +  "/" + hand+"/" + charToTrain + ".jpg";
		charImage= loadImage(filename);
		background(230);
	}

	public void draw(){
		displayLeapImages();
		image(charImage,0, 301, 600, 300);
		trainer.train();
		//trainer2.train();
	}

	private void displayLeapImages(){
		Frame frame = controller.frame();
		if(frame.isValid()){
			ImageList images = frame.images();
			for(Image image : images)
			{
				PImage[] cameras = new PImage[2];
				//Processing PImage class
				PImage camera = cameras[image.id()];
				camera = createImage(image.width(), image.height(), RGB);
				camera.loadPixels();

				//Get byte array containing the image data from Image object
				byte[] imageData = image.data();

				//Copy image data into display object, in this case PImage defined in Processing
				for(int i = 0; i < image.width() * image.height(); i++){
					int r = (imageData[i] & 0xFF) << 16; //convert to unsigned and shift into place
					int g = (imageData[i] & 0xFF) << 8;
					int b = imageData[i] & 0xFF;
					camera.pixels[i] =  r | g | b; 
				}

				//Show the image
				camera.updatePixels();
				image(camera, 300 * image.id(), 0, 300, 300);  
			}
		}
	}
}	