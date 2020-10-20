package com.mycompany.commands;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

public class ExitCmd extends Command {
	
	/**
	 * Constructor for the Command object.
	 * @param gw = GameWorld from which the commands are going to run from
	 */
	
	public ExitCmd() {
		super("Exit");  //name the command block
	}
	
	/**
	 * actionPerformed is overridden to run the specified command
	 * from the GameWorld.
	 */
	
	public void actionPerformed(ActionEvent evt) {   //Override the actionPerformed method in Command
		boolean close = Dialog.show("Sili-Challenge:", "Are you sure you want to quit?", "Yes", "No");
		if(close)
			Display.getInstance().exitApplication();
	}

}
