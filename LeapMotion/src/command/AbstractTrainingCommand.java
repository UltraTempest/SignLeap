package command;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public abstract class AbstractTrainingCommand extends AbstractCommand{

	private final List<String> signList = new ArrayList<String>();

	public AbstractTrainingCommand(final PApplet page) {
		super(page);
	}
	
	protected String[] getSigns(){
		return signList.toArray(new String[getNumberOfSigns()]);
	}

	public void addSign(final String s) {
		signList.add(s);
	}

	public void removeSign(final String s) {
		if(signList.contains(s))
		signList.remove(s);
	}
	
	public int getNumberOfSigns(){
		return signList.size();
	}

}
