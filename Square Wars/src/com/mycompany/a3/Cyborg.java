package com.mycompany.a3;
import com.mycompany.interfaces.ISteerable;


public abstract class Cyborg extends Moveable implements ISteerable {
	
	/**
	 * I added an extra value, maxDamageLevel to the cyborg. I
	 * added it to the cyborg instead of the GameWorld because
	 * other cyborgs can be introduced that may have a higher 
	 * threshold for damage (: 
	 */
	
	private int steeringDirection;                                      
	private int maximumSpeed;
	private int energyLevel;
	private int energyConsumptionRate;
	private int maxDamageLevel;
	private int damageLevel;
	private int lastBaseReached;     
	
	/**
	 * Constructor for Cyborg class. Always initialized to be size 25,
	 * always a green color, and positions will be manually passed through.
	 * The positions that will be passed through are base #1's, which will
	 * be created in GameWorld.
	 */
	
	public Cyborg(int size, int color, float initialX, float initialY,
			int heading, int speed, int steeringDirection, int maximumSpeed,
			int energyLevel, int energyConsumptionRate, int maxDamageLevel,
			int damageLevel, int lastBaseReached) {
		super(size, color, heading, speed, initialX, initialY);
		this.steeringDirection = steeringDirection;										
		this.maximumSpeed = maximumSpeed;                                                          
		this.energyLevel = energyLevel;                                                          
		this.energyConsumptionRate = energyConsumptionRate;                                                  
		this.maxDamageLevel = maxDamageLevel;                                                       
		this.damageLevel = damageLevel;                                                            
		this.lastBaseReached = lastBaseReached;                     
	}
	
	/*---------------------------------SETTERS AND GETTERS----------------------------------*/
	
	public void setSteeringDirection(int steeringDirection) {
		this.steeringDirection = steeringDirection;
	}
	
	/**
	 * changeHeading() is the method that is required to be 
	 * implemented by ISteerable.
	 */
	
	public void changeHeading(int heading) {
		this.setHeading(heading);
	}
	
	public void setMaximumSpeed(int maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	}
	
	public void setEnergyLevel(int energyLevel) {
		this.energyLevel = energyLevel;
	}
	
	public void setEnergyConsumptionRate(int energyConsumptionRate) {
		this.energyConsumptionRate = energyConsumptionRate;
	}
	
	public void setDamageLevel(int damageLevel) {                         //When damage level is set, it will 
		this.damageLevel = damageLevel;                                   //Call the color fader
		cyborgColorFader();
	}
	
	public void setMaxDamageLevel(int maxDamageLevel) {
		this.maxDamageLevel = maxDamageLevel;
	}
	
	public void setLastBaseReached(int lastBaseReached) {
		this.lastBaseReached = lastBaseReached;
	}
	
	public int getSteeringDirection() {
		return steeringDirection;
	}
	
	public int getMaximumSpeed() {
		return maximumSpeed;
	}
	
	public int getEnergyLevel() {
		return energyLevel;
	}
	
	public int getEnergyConsumptionRate() {
		return energyConsumptionRate;
	}
	
	public int getMaxDamageLevel() {
		return maxDamageLevel;
	}
	
	public int getDamageLevel() {
		return damageLevel;
	}
	
	public int getLastBaseReached() {
		return lastBaseReached;
	}
	
	/*----------------------------------END OF SETTERS AND GETTERS--------------------------------*/
	
	/**
	 * isAlive() is to determine whether or not the cyborg is still "alive."
	 * Being alive constitutes being below the max damage level, and having
	 * an energy level above 0. If either of these or both are false, then
	 * tell whichever class called this method that false, cyborg is not alive.
	 */
	
	public boolean isAlive() {                                                        
		return (damageLevel < maxDamageLevel && energyLevel > 0);                       
	}
	
	
	/**
	 * Basically whenever this method is called, it will consume the energy by itself
	 * instead of having to do it in the GameWorld class.
	 */
	
	public abstract void consumeEnergy();
	
	
	/**
	 * Whenever the cyborg takes any damage, a color fader method is incurred.
	 * I found that when the red and blue values are increased while green stays
	 * where it is at, the green color becomes lighter.
	 */
	
	public abstract void cyborgColorFader();
}
