package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;

public class PauseCmd extends Command {
	
	private Game game;
	
	public PauseCmd(Game game) {
		super("Pause");
		this.game = game;
	}
	
	public void actionPerformed(ActionEvent evt) {
		game.setPaused(true);
		game.stopTimer();
		game.commandSet();
		System.out.println("GAME PAUSED.");
	}

}
