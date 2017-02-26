package gui;

import java.awt.Font;
import button.Button;
import command.MainMenuCommand;
import command.AbstractTrainingCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractSelectCharactersGUI extends AbstractGeneralGUI implements IImageSelectorGUI{

	private final Button backButton; 
	private final Button selectButton; 
	private final AbstractTrainingCommand command;

	public AbstractSelectCharactersGUI(final PApplet papplet, final AbstractTrainingCommand command) {
		super(papplet);
		final Page page=getPage();
		this.command= command;

		backButton = new Button(page,3, 535, 325, 90, new MainMenuCommand(page));
		backButton.setText("Back");
		backButton.setTextBold();
		backButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		backButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		
		selectButton = new Button(page, 335, 535, 325, 90, this.command);
		selectButton.setText("Select (0)");
		selectButton.setTextBold();
		selectButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		selectButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
	}

	@Override
	public void render(){
		selectButton.setText(String.format("Select (%s)",command.getNumberOfSigns()));
		selectButton.setCommand(command);
		handleMouseOverButton(backButton, selectButton);
	}

	@Override
	public void dispose() {
		objectDisposal(backButton, selectButton);
	}

	@Override
	public void addSign(final String s) {
		command.addSign(s);
	}
	
	@Override
	public void removeSign(final String s) {
		command.removeSign(s);
	}

	@Override
	public Page getPApplet() {
		return getPage();
	}
}
