package gui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import button.Button;
import command.SubMenuCommand;
import command.SelectAlphabetCommand;
import g4p_controls.GCScheme;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractSelectCharactersGUI extends AbstractGeneralGUI implements IImageSelectorGUI{

	private final Button backButton; 
	protected final Button selectButton; 
	private final List<String> signList = new ArrayList<String>();

	public AbstractSelectCharactersGUI(final PApplet page) {
		super(page);

		backButton = new Button(page,3, 535, 325, 90,new SubMenuCommand(page,new SelectAlphabetCommand(page)));
		backButton.setText("<--");
		backButton.setTextBold();
		backButton.setTextItalic();
		backButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		backButton.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
		
		selectButton = new Button(page, 335, 535, 325, 90, null);
		selectButton.setText("Select (0)");
		selectButton.setTextBold();
		selectButton.setFont(new Font("Dialog", Font.PLAIN, 25));
		selectButton.setLocalColorScheme(GCScheme.GREEN_SCHEME);
	}

	@Override
	public void render(){
		selectButton.setText(String.format("Select (%s)",signList.size()));
		handleMouseOverButton(backButton, selectButton);
	}

	@Override
	public void dispose() {
		objectDisposal(backButton, selectButton);
	}

	
	public String[] getSigns(){
		return signList.toArray(new String[signList.size()]);
	}
	
	@Override
	public boolean containSign(final String s){
		return signList.contains(s);
	}
	
	@Override
	public void addSign(final String s) {
		signList.add(s);
	}
	
	@Override
	public void removeSign(final String s) {
		if(signList.contains(s))
		signList.remove(s);
	}

	@Override
	public Page getPApplet() {
		return getPage();
	}
}
