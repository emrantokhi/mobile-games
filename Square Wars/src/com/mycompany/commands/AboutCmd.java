package com.mycompany.commands;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AboutCmd extends Command {
	
	/**
	 * Constructor for the Command object.
	 * @param gw = GameWorld from which the commands are going to run from
	 */
	
	public AboutCmd(GameWorld gw) {
		super("About");  //name the command block
	}
	
	/**
	 * actionPerformed is overridden to run the specified command
	 * from the GameWorld.
	 */
	
	public void actionPerformed(ActionEvent evt) {   //Override the actionPerformed method in Command
		String info = "Name: Emran Tokhi\nCourse: CSC 133 Section 02"
				+ "\nVersion: 1.0.0";
		Dialog.show("Sili-Challenge:", info, "Back", null);
	}

}
