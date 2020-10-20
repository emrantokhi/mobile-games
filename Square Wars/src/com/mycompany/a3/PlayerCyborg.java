package com.mycompany.a3;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class PlayerCyborg extends Cyborg {
	
	/**
	 * To implement the Singleton design pattern, there is a static
	 * variable that keeps track of the current and only player.
	 */
	private ArrayList<GameObject> collisionList;
	private static PlayerCyborg player;
	private GameWorld gw;
	
	/**
	 * Constructor private so that no outside instance can create 
	 * another playercyborg.
	 * Passes on the initial values to Cyborg to set.
	 * super(int size, int color, float initialX, float initialY,
			int heading, int speed, int steeringDirection, int maximumSpeed,
			int energyLevel, int energyConsumptionRate, int maxDamageLevel,
			int damageLevel, int lastBaseReached)
	 * @param initialX = initial X position on map
	 * @param initialY = initial Y position on map
	 */
	
	private PlayerCyborg(float initialX, float initialY, GameWorld gw) {
		super(50, ColorUtil.MAGENTA, initialX, initialY, 180, 75,
				0, 150, 100, 2, 100, 0, 1);
		collisionList = new ArrayList<GameObject>();
		this.gw = gw;
	}
	
	/**
	 * To fully implement singleton, there must be a way to return the current
	 * player, if there is not one, then create one.
	 * @param initialX
	 * @param initialY
	 * @return the player cyborg
	 */
	
	public static PlayerCyborg getPlayerCyborg(float initialX, float initialY, GameWorld gw) {
		if (player == null)
			player = new PlayerCyborg(initialX, initialY, gw);
		else {   //Reset the single player's stats back to its original state
			player.setHeading(180);
			player.setSpeed(50);
			player.setSteeringDirection(0);
			player.setEnergyLevel(100);
			player.setDamageLevel(0);
			player.setLastBaseReached(1);
			player.setLocation(initialX, initialY);
		}
		return player;
	}
	
	
	/**
	 * Collision handling method for the Player
	 */
	
	public void handleCollision(GameObject gameObject) {
		 if(!collisionList.contains(gameObject)) { //else if it does not contain the object
			collisionList.add(gameObject);
		}
	}
	
	public void removeFromList(GameObject object) {
		if(collisionList.contains(object)) 
			collisionList.remove(object);
	}
	
	/**
	 * Whenever the cyborg takes any damage, a color fader method is incurred.
	 * I found that when the red and blue values are increased while green stays
	 * where it is at, the green color becomes lighter.
	 */
	
	public void cyborgColorFader() {
		this.setColor(ColorUtil.rgb(255, (int) (this.getDamageLevel() * 2.5), 255));
	}
	
	
	/**
	 * Every 1 second reduce the energy but its consumption rate
	 */
	public void consumeEnergy() {
		if(gw.getCurrClockTime() % 1000 == 0)
			setEnergyLevel(getEnergyLevel() - getEnergyConsumptionRate());
	}
	
	/**
	 * Move method for cyborg, taken from page 9 of the assignment.
	 * It makes sure that if the x or y values try to go over the 
	 * borders of the gameworld, it'll not allow it to go any further.
	 * IMPORTANT: Also allows heading to change even if speed is 0.
	 * You can still turn in place before you move is my thought process.
	 */
	
	public void move(int elapsedMilliSecs) { 
		if(isAlive() && this.getSpeed() != 0) {
			float dist = (float) (this.getSpeed() * (elapsedMilliSecs/1000.0));
			float newX = (float) (this.getX() + (Math.cos(Math.toRadians((90 - this.getHeading()))) * dist));  //From equation on slide 16 of CH11
			float newY = (float) (this.getY() + (Math.sin(Math.toRadians((90 - this.getHeading()))) * dist));
			if(newX + getSize()/2 > Game.getMapWidth()) {       //Error checking starts, if right of Obj is > width of GW, don't
				newX = Game.getMapWidth() - getSize()/2;        //allow it to go any further.
			} else if(newX - getSize()/2 < 0) {              //If left of obj < origin set X so that its center is the distance between the center and the right of the map
				newX = getSize()/2;
			}
			if(newY + getSize()/2 > Game.getMapHeight()) {                          //If y > height of GW, don't allow it to go any further
				newY = Game.getMapHeight() - getSize()/2;
			} else if(newY - getSize()/2 < 0) {                                    //0,0 is origin, so cannot go further than that either
				newY = getSize()/2;                          
			}
			this.setLocation(newX, newY);                  
		}
		this.setHeading(this.getHeading() + this.getSteeringDirection());   //change the heading based on the steeringDirection 
		if(this.getHeading() > 359) {                             //Make sure that heading does not go above 359 or -359
			this.setHeading(359);                                 //+ values are clockwise, - values are counterclock wise
		} else if(this.getHeading() < 0) {
			this.setHeading(0);
		}
	}  
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		g.fillRect((int)((getX() - getSize()/2) + pCmpRelPrnt.getX()), (int)((getY() - getSize()/2) + pCmpRelPrnt.getY()), getSize(), getSize());
	}

	/**
	 * toString method overriden to print this statement out instead.
	 */
	
	public String toString() {
		return "Player Cyborg:   loc=" + Math.round(this.getX() * 10.0) / 10.0   //To show one decimal place
				+ "," + Math.round(this.getY() * 10.0) / 10.0 
				+ "    color=[" 
				+ ColorUtil.red(this.getColor()) + ","                    //show what the color is in terms of red, green, and blue
				+ ColorUtil.green(this.getColor()) + ","
				+ ColorUtil.blue(this.getColor()) + "]    heading="
				+ this.getHeading() + "    speed=" + this.getSpeed()
				+ "    size=" + this.getSize() + "\n\t\tmaxSpeed=" + this.getMaximumSpeed()
				+ "    steeringDirection=" + this.getSteeringDirection() 
				+ "    energyLevel=" + this.getEnergyLevel() + "    "
				+ "damageLevel=" + this.getDamageLevel();
	}

}
