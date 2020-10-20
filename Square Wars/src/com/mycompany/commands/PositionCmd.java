package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCmd extends Command {

	private static boolean clicked;

	
	public PositionCmd() {
		super("Position");
		clicked = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		clicked = true;
	}

	public static boolean isClicked() {
		return clicked;
	}
	
	public static void setClicked(boolean clicked) {
		PositionCmd.clicked = clicked;
	}

}
