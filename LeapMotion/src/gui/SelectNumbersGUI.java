package gui;

import java.awt.Font;
import button.Button;
import button.ImageButton;
import command.MainMenuCommand;
import command.TrainingCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public final class SelectNumbersGUI extends AbstractGeneralGUI implements IImageSelectorGUI{

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
	private final TrainingCommand command;

	public SelectNumbersGUI(final PApplet papplet) {
		super(papplet);
		final Page page=getPage();
		command= new TrainingCommand(page);
		imgButton1 = new ImageButton(this,14, 83, 150, 200,"1");
		imgButton2 = new ImageButton(this,197, 83, 150, 200,"2");
		imgButton3 = new ImageButton(this,387, 87, 150, 200,"3");
		imgButton4 = new ImageButton(this,571, 91, 150, 200,"4");
		imgButton5 = new ImageButton(this,774, 93, 150, 200,"5");
		imgButton6 = new ImageButton(this, 12, 304, 150, 200,"6");
		imgButton7 = new ImageButton(this, 194, 303, 150, 200,"7");
		imgButton8 = new ImageButton(this,385, 304, 150, 200,"8");
		imgButton9 = new ImageButton(this,571, 308, 150, 200,"9");
		imgButton10 = new ImageButton(this,775, 309, 150, 200,"10");

		backButton = new Button(page,3, 535, 325, 90, new MainMenuCommand(page));
		backButton.setText("Back");
		backButton.setTextBold();
		backButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		backButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		
		selectButton = new Button(page, 335, 535, 325, 90, command);
		selectButton.setText("Select (0)");
		selectButton.setTextBold();
		selectButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		selectButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
	}

	@Override
	public void render(){
		selectButton.setText(String.format("Select (%s)",command.getNumberOfSigns()));
		selectButton.setCommand(command);
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

	@Override
	public void addSign(final String s) {
		command.addSign(s);
	}
	
	@Override
	public void removeSign(final String s) {
		command.removeSign(s);
	}

	@Override
	public Page getPApplet() {
		return getPage();
	}
}
