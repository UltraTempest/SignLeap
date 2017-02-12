package gui;

import g4p_controls.GAbstractControl;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractGUI implements IGUI{
	private static PApplet page;
	private boolean rendered=false;

	public AbstractGUI(final PApplet page){
		AbstractGUI.page=page;
		((Page) AbstractGUI.page).setDefaultBackground();
	}

	protected final Page getPage(){
		return (Page) AbstractGUI.page;
	}

	protected final void objectDisposal(final GAbstractControl... objects){
		for(GAbstractControl object:objects){
			object.setVisible(false);
			object.dispose();
		}
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
