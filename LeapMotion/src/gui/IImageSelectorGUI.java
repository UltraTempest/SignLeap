package gui;

import processing.Page;

public interface IImageSelectorGUI {
	public void addSign(String s);
	public void removeSign(String s);
	public boolean containSign(String s);
	public Page getPApplet();
}
