package com.mycompany.a3;
import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.interfaces.IStrategy;

public class NonPlayerCyborg extends Cyborg{
	
	private IStrategy strategy;
	private ArrayList<GameObject> collisionList;
	private GameWorld gw;
	
	/**
	 * Passes on the initial values to Cyborg to set.
	 * super(int size, int color, float initialX, float initialY,
			int heading, int speed, int steeringDirection, int maximumSpeed,
			int energyLevel, int energyConsumptionRate, int maxDamageLevel,
			int damageLevel, int lastBaseReached)
	 * @param initialX = initial X position on map
	 * @param initialY = initial Y position on map
	 */
	
	public NonPlayerCyborg(float initialX, float initialY, GameWorld gw) {
		super(75, ColorUtil.rgb(255,0,0), initialX, initialY, 0, 150,
				0, 150, 99999, 0, 300, 0, 1);
		this.gw = gw;
		collisionList = new ArrayList<GameObject>();
	}
	
	public void move(int elapsedMilliSecs) {
		float dist = (float) (this.getSpeed() * (elapsedMilliSecs/1000.0));
		float newX = (float) (this.getX() + (Math.cos(Math.toRadians((90 - this.getHeading()))) * dist));  //From equation on slide 16 of CH11
		float newY = (float) (this.getY() + (Math.sin(Math.toRadians((90 - this.getHeading()))) * dist));
		if(newX + getSize()/2 > Game.getMapWidth()) {                                //Error checking starts, if X is > width of GW, don't
			newX = Game.getMapWidth() - getSize()/2;                                                 //allow it to go any further.
		} else if(newX - getSize()/2 < 0) {
			newX = getSize()/2;
		}
		if(newY + getSize()/2 > Game.getMapHeight()) {                               //If y > height of GW, don't allow it to go any further
			newY = Game.getMapHeight() - getSize()/2;
		} else if(newY - getSize()/2 < 0) {                                            //0,0 is origin, so cannot go further than that either
			newY = getSize()/2;
		}
		this.setLocation(newX, newY);    
		this.setHeading(this.getHeading() + this.getSteeringDirection());   //change the heading based on the steeringDirection 
		if(this.getHeading() > 359) {                             //Make sure that heading does not go above 359 or -359
			this.setHeading(359);                                 //+ values are clockwise, - values are counterclock wise
		} else if(this.getHeading() < 0) {
			this.setHeading(0);
		}
		
	}
	
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void invokeStrategy() {
		strategy.apply();
	}
	
	public IStrategy getStrategy() {
		return strategy;
	}
	
	public void consumeEnergy() {
		if(gw.getCurrClockTime() % 1000 == 0)
			setEnergyLevel(getEnergyLevel() - getEnergyConsumptionRate());
	}
	
	/**
	 * Whenever the cyborg takes any damage, a color fader method is incurred.
	 * I found that when the red and blue values are increased while green stays
	 * where it is at, the green color becomes lighter.
	 */
	
	public void cyborgColorFader() {
		this.setColor(ColorUtil.rgb(255, this.getDamageLevel() + 30, this.getDamageLevel() + 30));
	}
	
	
	/**
	 * Handle the collisions if NPC collides with another NPC or player
	 */
	public void handleCollision(GameObject gameObject) {
		 if(!collisionList.contains(gameObject)) { //else if it does not contain the object
			gw.cyborgCollide(gameObject, this); //the collision method will handle it if the colliding obj does not do a base collide 
			collisionList.add(gameObject);
		}
	}
	
	public void removeFromList(GameObject object) {
		if(collisionList.contains(object)) 
			collisionList.remove(object);
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		g.drawRect((int)((getX() - getSize()/2) + pCmpRelPrnt.getX()), (int)((getY() - getSize()/2) + pCmpRelPrnt.getY()), getSize(), getSize(), 5);
	}
	
	/**
	 * toString method overriden to print this statement out instead.
	 */
	
	public String toString() {
		return "Non-Player Cyborg:   loc=" + Math.round(this.getX() * 10.0) / 10.0   //To show one decimal place
				+ "," + Math.round(this.getY() * 10.0) / 10.0 
				+ "    color=[" 
				+ ColorUtil.red(this.getColor()) + ","                    //show what the color is in terms of red, green, and blue
				+ ColorUtil.green(this.getColor()) + ","
				+ ColorUtil.blue(this.getColor()) + "]    heading="
				+ this.getHeading() + "    speed=" + this.getSpeed()
				+ "    size=" + this.getSize() + "\n\t\tmaxSpeed=" + this.getMaximumSpeed()
				+ "    steeringDirection=" + this.getSteeringDirection() 
				+ "    energyLevel=" + this.getEnergyLevel() + "    "
				+ "damageLevel=" + this.getDamageLevel() 
				+ "\n\t\t" + strategy + "    last base reached: " + this.getLastBaseReached();
	}

}
