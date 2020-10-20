package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;
import com.mycompany.a3.MapView;

public class PlayCmd extends Command{

private Game game;
private MapView mv;
	
	public PlayCmd(Game game, MapView mv) {
		super("Play");
		this.game = game;
		this.mv = mv;
	}
	
	public void actionPerformed(ActionEvent evt) {
		mv.unselect(); //Unselects all the objects that have been selected in Map
		game.setPaused(false);
		game.commandSet();
		game.startTimer();
		System.out.println("GAME RESUMED.");
	}
	
}
