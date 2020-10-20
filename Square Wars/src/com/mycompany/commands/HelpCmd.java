package com.mycompany.commands;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCmd extends Command {
	
	/**
	 * Constructor for the Command object.
	 * @param gw = GameWorld from which the commands are going to run from
	 */
	
	public HelpCmd() {
		super("Help");  //name the command block
	}
	
	/**
	 * actionPerformed is overridden to run the specified command
	 * from the GameWorld.
	 */
	
	public void actionPerformed(ActionEvent evt) {   //Override the actionPerformed method in Command
		String helpInfo = "A = Accelerate\nB = Brake\nL = Left Turn"
				+ "R = Right Turn\nC = Enemy Cyborg Collision\n"
				+ "1-9 = Base Collision\nE = Energy Collision\n"
				+ "G = Drone Collision\nT = Tick, Clock Forward 1 Tick\n"
				+ "S = Change Strategy";
		Dialog.show("Help:", helpInfo, "Ok", null);
	}

}
