package gui;

import g4p_controls.GAbstractControl;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractGUI implements IGUI{
	private static Page page;
	
	public AbstractGUI(final PApplet page){
		AbstractGUI.page=(Page) page;
		AbstractGUI.page.setDefaultBackground();
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
