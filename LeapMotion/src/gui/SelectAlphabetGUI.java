package gui;

import button.ImageButton;
import command.TrainingAlphabetCommand;
import processing.core.PApplet;

public final class SelectAlphabetGUI extends AbstractSelectCharactersGUI{

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

	public SelectAlphabetGUI(final PApplet papplet) {
		super(papplet,new TrainingAlphabetCommand(papplet));
		imgButton1 = new ImageButton(this,14, 83, 150, 200,"a");
		imgButton2 = new ImageButton(this,197, 83, 150, 200,"b");
		imgButton3 = new ImageButton(this,387, 87, 150, 200,"c");
		imgButton4 = new ImageButton(this,571, 91, 150, 200,"d");
		imgButton5 = new ImageButton(this,774, 93, 150, 200,"e");
		imgButton6 = new ImageButton(this, 12, 304, 150, 200,"f");
		imgButton7 = new ImageButton(this, 194, 303, 150, 200,"g");
		imgButton8 = new ImageButton(this,385, 304, 150, 200,"h");
		imgButton9 = new ImageButton(this,571, 308, 150, 200,"i");
		imgButton10 = new ImageButton(this,775, 309, 150, 200,"j");
		//TODO complete alphabet select
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
