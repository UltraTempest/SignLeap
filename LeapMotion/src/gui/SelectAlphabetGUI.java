package gui;

import button.Button;
import button.ImageButton;
import command.SelectAlphabetSwipeCommand;
import command.TrainingAlphabetCommand;
import processing.core.PApplet;

public final class SelectAlphabetGUI extends AbstractSelectCharactersGUI{

	private final Button swipeLeftButton;
	private final Button swipeRightButton;
	private int state=0;

	private final ImageButton imgButton1; 
	private final ImageButton imgButton2; 
	private final ImageButton imgButton3; 
	private final ImageButton imgButton4; 
	private final ImageButton imgButton5; 
	private final ImageButton imgButton6; 
	private final ImageButton imgButton7; 
	private final ImageButton imgButton8;  

	public SelectAlphabetGUI(final PApplet papplet) {
		super(papplet);
		selectButton.setCommand(new TrainingAlphabetCommand(this));
		imgButton1 = new ImageButton(this,81, 60, 190, 228,"a");
		imgButton2 = new ImageButton(this,283, 60, 190, 228,"b");
		imgButton3 = new ImageButton(this,485, 60, 190, 228,"c");
		imgButton4 = new ImageButton(this,686, 60, 190, 228,"d");
		imgButton5 = new ImageButton(this,81, 303, 190, 228,"e");
		imgButton6 = new ImageButton(this,283, 303, 190, 228,"f");
		imgButton7 = new ImageButton(this,485, 303, 190, 228,"g");
		imgButton8 = new ImageButton(this,686, 303, 190, 228,"h");

		swipeLeftButton = new Button(papplet, 2, 12, 66, 515, new SelectAlphabetSwipeCommand(this,false));
		swipeLeftButton.setText("<--");
		swipeLeftButton.setTextBold();
		swipeLeftButton.setTextItalic();
		swipeLeftButton.setVisible(false);

		swipeRightButton = new Button(papplet, 885, 12, 66, 515,new SelectAlphabetSwipeCommand(this,true));
		swipeRightButton.setText("-->");
		swipeRightButton.setTextBold();
		swipeRightButton.setTextItalic();
	}

	public void swipe(final boolean right){
		int nextState;
		if(right)
			nextState=state+1;
		else
			nextState=state-1;
		switch (nextState) {
		case 0: setPageZero();
		break;
		case 1:  setPageOne();
		break;
		case 2:  setPageTwo();
		break;
		case 3:  setPageThree();
		break;
		default: return;
		}
		
	}
	
	private void setPageZero(){
		state=0;
		imgButton1.changeImage("a");
		imgButton2.changeImage("b");
		imgButton3.changeImage("c");
		imgButton4.changeImage("d");
		imgButton5.changeImage("e");
		imgButton6.changeImage("f");
		imgButton7.changeImage("g");
		imgButton8.changeImage("h");
		
		swipeLeftButton.setVisible(false);
	}

	private void setPageOne(){
		state=1;
		imgButton1.changeImage("i");
		imgButton2.changeImage("j");
		imgButton3.changeImage("k");
		imgButton4.changeImage("l");
		imgButton5.changeImage("m");
		imgButton6.changeImage("n");
		imgButton7.changeImage("o");
		imgButton8.changeImage("p");
		
		swipeLeftButton.setVisible(true);
	}

	private void setPageTwo(){
		if(state==3){
			imgButton3.setVisible(true);
			imgButton4.setVisible(true);
			imgButton5.setVisible(true);
			imgButton6.setVisible(true);
			imgButton7.setVisible(true);
			imgButton8.setVisible(true);
		}
		state=2;
		imgButton1.changeImage("q");
		imgButton2.changeImage("r");
		imgButton3.changeImage("s");
		imgButton4.changeImage("t");
		imgButton5.changeImage("u");
		imgButton6.changeImage("v");
		imgButton7.changeImage("w");
		imgButton8.changeImage("x");
		
		swipeRightButton.setVisible(true);
	}

	private void setPageThree(){
		state=3;
		imgButton1.changeImage("y");
		imgButton2.changeImage("z");
		imgButton3.setVisible(false);
		imgButton4.setVisible(false);
		imgButton5.setVisible(false);
		imgButton6.setVisible(false);
		imgButton7.setVisible(false);
		imgButton8.setVisible(false);
		
		swipeRightButton.setVisible(false);
	}


	@Override
	public void render(){
		super.render();
		handleMouseOverButton(imgButton1, imgButton2, imgButton3, imgButton4, 
				imgButton5,imgButton6, imgButton7, imgButton8, swipeRightButton,swipeLeftButton);
	}

	@Override
	public void dispose() {
		super.dispose();
		objectDisposal(imgButton1, imgButton2, imgButton3, imgButton4, 
				imgButton5,imgButton6, imgButton7, imgButton8,swipeRightButton, swipeLeftButton);
	}
}
