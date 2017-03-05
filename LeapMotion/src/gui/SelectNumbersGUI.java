package gui;

import button.ImageButton;
import command.TrainingNumbersCommand;
import processing.core.PApplet;

public final class SelectNumbersGUI extends AbstractSelectCharactersGUI{

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

	public SelectNumbersGUI(final PApplet papplet) {
		super(papplet);
		if(papplet==null)
			System.out.println("here");
		selectButton.setCommand(new TrainingNumbersCommand(this));
		imgButton1 = new ImageButton(this,14, 83, 150, 200,"1");
		imgButton2 = new ImageButton(this,197, 83, 150, 200,"2");
		imgButton3 = new ImageButton(this,387, 83, 150, 200,"3");
		imgButton4 = new ImageButton(this,571, 83, 150, 200,"4");
		imgButton5 = new ImageButton(this,774, 83, 150, 200,"5");
		imgButton6 = new ImageButton(this, 12, 303, 150, 200,"6");
		imgButton7 = new ImageButton(this, 194, 303, 150, 200,"7");
		imgButton8 = new ImageButton(this,385, 303, 150, 200,"8");
		imgButton9 = new ImageButton(this,571, 303, 150, 200,"9");
		imgButton10 = new ImageButton(this,775, 303, 150, 200,"10");
	}

	@Override
	public void render(){
		super.render();
		handleMouseOverButton(imgButton1, imgButton2, imgButton3, imgButton4, 
				imgButton5,imgButton6, imgButton7, imgButton8, imgButton9,imgButton10);
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(imgButton1, imgButton2, imgButton3, imgButton4, 
				imgButton5,imgButton6, imgButton7, imgButton8, imgButton9,imgButton10);
	}
}
