package command;

import java.util.ArrayList;
import java.util.List;

import gui.NumberTrainingGUI;
import processing.core.PApplet;

public final class TrainingCommand extends AbstractCommand{

	private final List<String> signList = new ArrayList<String>();

	public TrainingCommand(final PApplet page) {
		super(page);
	}

	@Override
	public void process() {
		executeCommand(new NumberTrainingGUI(getPage(), signList.toArray(new String[signList.size()])));
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
