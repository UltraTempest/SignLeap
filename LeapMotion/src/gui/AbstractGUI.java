package gui;

import g4p_controls.GAbstractControl;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractGUI implements IGUI{
	private static PApplet page;
	private boolean rendered=false;

	public AbstractGUI(PApplet page){
		AbstractGUI.page=page;
		((Page) page).setDefaultBackground();
	}

	protected Page getPage(){
		return (Page) AbstractGUI.page;
	}

	protected void objectDisposal(GAbstractControl object){
		object.setVisible(false);
		object.dispose();
	}

	@Override
	public boolean isWarningRequired(){
		return true;
	}

	protected void createGUI(){
		//To be implemented by subclass  
	}

	@Override
	public void render(){
		if(rendered==false){
			createGUI();
			rendered=true;
		}
	}

}
