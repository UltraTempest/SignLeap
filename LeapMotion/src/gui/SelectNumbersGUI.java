package gui;

import java.awt.Font;

import button.Button;
import button.ImageButton;
import classifier.SignClassifier;
import command.MainMenuCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public final class SelectNumbersGUI extends AbstractGeneralGUI{

	private final ImageButton imgButton1; 
	private final ImageButton imgButton2; 
	private final ImageButton imgButton3; 
	private final ImageButton imgButton4; 
	private final ImageButton imgButton5; 
	private final ImageButton imgButton6; 
	private final ImageButton imgButton7; 
	private final ImageButton imgButton8; 
	private final ImageButton imgButton9; 
	private final ImageButton imgButton10; 
	private final Button backButton; 
	private final Button selectButton; 

	public SelectNumbersGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		final String imageName= SignClassifier.language+"/" + getPage().getHand() 
				+"/%s"+ AbstractSignCharacterGUI.imageType;
		imgButton1 = new ImageButton(page, 14, 83, 150, 200, String.format(imageName,"1"));
		imgButton1.addEventHandler(page, "handleButtonEvents");
		imgButton2 = new ImageButton(page, 197, 83, 150, 200, String.format(imageName,"2"));
		imgButton2.addEventHandler(page, "handleButtonEvents");
		imgButton3 = new ImageButton(page, 571, 91, 150, 200, String.format(imageName,"3") );
		imgButton3.addEventHandler(page, "handleButtonEvents");
		imgButton4 = new ImageButton(page, 387, 87, 150, 200,  String.format(imageName,"4") );
		imgButton4.addEventHandler(page, "handleButtonEvents");
		imgButton5 = new ImageButton(page, 774, 93, 150, 200,  String.format(imageName,"5"));
		imgButton5.addEventHandler(page, "handleButtonEvents");
		imgButton6 = new ImageButton(page, 12, 304, 150, 200,  String.format(imageName,"6") );
		imgButton6.addEventHandler(page, "handleButtonEvents");
		imgButton7 = new ImageButton(page, 194, 303, 150, 200,  String.format(imageName,"7"));
		imgButton7.addEventHandler(page, "handleButtonEvents");
		imgButton8 = new ImageButton(page, 385, 304, 150, 200,  String.format(imageName,"8") );
		imgButton8.addEventHandler(page, "handleButtonEvents");
		imgButton9 = new ImageButton(page, 571, 308, 150, 200,  String.format(imageName,"9") );
		imgButton9.addEventHandler(page, "handleButtonEvents");
		imgButton10 = new ImageButton(page, 775, 309, 150, 200,  String.format(imageName,"10"));
		imgButton10.addEventHandler(page, "handleButtonEvents");

		backButton = new Button(page,3, 535, 325, 90, new MainMenuCommand(page));
		backButton.setText("Back");
		backButton.setTextBold();
		backButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		backButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		backButton.addEventHandler(page, "handleButtonEvents");

		selectButton = new Button(page, 335, 535, 325, 90, new MainMenuCommand(page));
		selectButton.setText("Select");
		selectButton.setTextBold();
		selectButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		selectButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		selectButton.addEventHandler(page, "handleButtonEvents");
	}

	@Override
	public void render(){
		handleMouseOverButton(imgButton1, imgButton2, imgButton3, imgButton4, 
				imgButton5,imgButton6, imgButton7, imgButton8, imgButton9,
				imgButton10,backButton, selectButton);
	}

	@Override
	public void dispose() {
		objectDisposal(imgButton1, imgButton2, imgButton3, imgButton4, 
				imgButton5,imgButton6, imgButton7, imgButton8, imgButton9,
				imgButton10,backButton, selectButton);
	}
}
