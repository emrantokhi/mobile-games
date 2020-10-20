package com.mycompany.custom;
import com.codename1.ui.Component;
import com.codename1.ui.Label;

public class BetterLabel extends Label {

	/**
	 * Labels that just look better, added custom
	 * looks.
	 * @param s
	 */
	public BetterLabel(String s) {
		super(s);
		this.getAllStyles().setAlignment(LEFT);
		this.getAllStyles().setPadding(Component.LEFT, 3);
		this.getAllStyles().setPadding(Component.RIGHT, 3);
		this.getAllStyles().setUnderline(true);
		
	}
}
