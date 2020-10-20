package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.custom.BetterLabel;
import com.mycompany.interfaces.IIterator;

public class ScoreView extends Container implements Observer{
	
	private BetterLabel timeNum;
	private BetterLabel lives;
	private BetterLabel lastBase;
	private BetterLabel playerEnergyLevel;
	private BetterLabel playerDamageLevel;
	private BetterLabel soundValue;
	
	public ScoreView() {
		this.setLayout(new FlowLayout());
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBgColor(ColorUtil.rgb(189, 232, 238));
		Label time = new Label("Time: ");
		timeNum = new BetterLabel("000");
		Label livesLeft = new Label("Lives Left: ");
		lives = new BetterLabel("3");
		Label lastBaseReached = new Label("Player's Last Reached Base: ");
		lastBase = new BetterLabel("1");
		Label playerEnergy = new Label("Player Energy: ");
		playerEnergyLevel = new BetterLabel("00100");
		Label playerDamage = new Label("Player Damage: ");
		playerDamageLevel = new BetterLabel("0000");
		Label sound = new Label("Sound: ");
		soundValue = new BetterLabel("On");
		
		this.add(time);
		this.add(timeNum);
		this.add(livesLeft);
		this.add(lives);
		this.add(lastBaseReached);
		this.add(lastBase);
		this.add(playerEnergy);
		this.add(playerEnergyLevel);
		this.add(playerDamage);
		this.add(playerDamageLevel);
		this.add(sound);
		this.add(soundValue);
	}
	
	public void update(Observable o, Object arg) {
		if(o instanceof GameWorld) {
			GameWorld gw = (GameWorld) o;
			timeNum.setText(gw.getCurrClockTime()/1000 + ""); //divided by 1000 bc refresh happens every 20ms but we must show seconds
			lives.setText(gw.getLivesRemaining() + "");
			IIterator gameElements = gw.getGameObjectCollection().getIterator();
			while(gameElements.hasNext()) {
				Object holder = gameElements.getNext();
				if(holder instanceof PlayerCyborg) {
					PlayerCyborg player = (PlayerCyborg) holder;
					lastBase.setText(player.getLastBaseReached() + "");
					playerEnergyLevel.setText(player.getEnergyLevel() + "");
					playerDamageLevel.setText(player.getDamageLevel() + "");
					break;
				}
			}
			if(gw.getSound())
				soundValue.setText("On");
			else {
				soundValue.setText("Off");
			}
		}
		
	}

}
