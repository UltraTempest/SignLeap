package gui;

import g4p_controls.GAbstractControl;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractGUI implements IGUI{
	private static PApplet page;

	public AbstractGUI(final PApplet page){
		AbstractGUI.page=page;
		((Page)page).setDefaultBackground();
	}
	
	@Override
	public void render(){
		getPage().renderLeapWarning();
	}

	protected final Page getPage(){
		return (Page) AbstractGUI.page;
	}

	protected final void objectDisposal(final GAbstractControl... objects){
		for(final GAbstractControl object:objects){
			object.setVisible(false);
			object.dispose();
		}
	}
}
