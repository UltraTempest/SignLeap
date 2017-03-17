package gui;

import g4p_controls.GAbstractControl;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractGUI implements IGUI{
	private static Page page;
	private boolean rendered=false;
	
	public AbstractGUI(final PApplet page){
		AbstractGUI.page=(Page) page;
		AbstractGUI.page.setDefaultBackground();
	}
	
	@Override
	public void render(){
		if(!rendered){
			getPage().setDefaultBackground();
			rendered=true;
		}
	}

	protected final Page getPage(){
		return AbstractGUI.page;
	}

	protected final void objectDisposal(final GAbstractControl... objects){
		for(final GAbstractControl object:objects){
			object.setVisible(false);
			object.dispose();
		}
	}
}
