package controller;

/******************************************************************************\
 * Author: Alberto Vaccari
 * LeapMouse.java
 * 
 * This app simulates a mouse, based on the Sample.java for LeapMotion
 *           
 *           
\******************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.leapmotion.leap.*;

class LeapMouse {

	public static void main(String[] args) throws IOException {

		// Create a sample listener and controller
		LeapMouseListener listener = new LeapMouseListener();
		Controller controller = new Controller();
		//  controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
		//  controller.enableGesture( Gesture.Type.TYPE_SWIPE );
		//  controller.enableGesture( Gesture.Type.TYPE_CIRCLE );

		System.out.println("Do you want to enable Debug Mode? Y/N (Default: Disabled)");

		try{

			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

			if(bufferRead.readLine().equalsIgnoreCase("Y"))
			{
				listener.setDebug(true);
				System.out.println("Debug Mode Enabled.\n");
			}
			else
				System.out.println("Default: Debug Mode Disabled.\n");


			System.out.println("Do you want to use Key Tap (0) or Finger Tap (1)? 0/1 (Default: Key Tap)");

			if(bufferRead.readLine().equals("1")){
				listener.setClickType(1);
				System.out.println("Finger Tap Disabled.\n");
			}
			else
				System.out.println("Default: Key Tap Enabled.\n");

			System.out.println("Do you want to use Calibrated Screen for pointing (Leap must be calibrated)? - Supports only 1 Screen - Y/N (Default: Calibrated Screen)");

			if(bufferRead.readLine().equalsIgnoreCase("N")){
				listener.setCalibratedScren(false);
				System.out.println("Using Leap Relative Positioning.\n");
			}
			else
				System.out.println("Default: Using Calibrated Screen.\n");


		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		// Have the sample listener receive events from the controller
		controller.addListener(listener);

		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the sample listener when done
		controller.removeListener(listener);     

	}
}
