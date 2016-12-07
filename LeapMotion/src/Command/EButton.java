package Command;

import g4p_controls.GButton;
import processing.core.PApplet;

public class EButton extends GButton{
    private ICommand command;
	
	public EButton(PApplet arg0, float arg1, float arg2, float arg3, float arg4, ICommand command) {
		super(arg0, arg1, arg2, arg3, arg4);
		setCommand(command);
	}
	
    public void setCommand(ICommand command){
    	this.command=command;
    }
    
    public ICommand getCommand(){
    	return this.command;
    }
}
