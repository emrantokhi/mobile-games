package com.mycompany.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class LeftCmd extends Command {
	
	private GameWorld gw;  //Import the GameWorld that commands are going to be run from
	
	/**
	 * Constructor for the Command object.
	 * @param gw = GameWorld from which the commands are going to run from
	 */
	
	public LeftCmd(GameWorld gw) {
		super("Left");  //name the command block
		this.gw = gw;         //set the gameworld variable
	}
	
	/**
	 * actionPerformed is overridden to run the specified command
	 * from the GameWorld.
	 */
	
	public void actionPerformed(ActionEvent evt) {   //Override the actionPerformed method in Command
		gw.left();     //Run GameWorld method for specified command
		gw.printGameObjects(); //Print the gameObjects onto the console
	}

}
