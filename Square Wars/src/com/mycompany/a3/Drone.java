package com.mycompany.a3;
import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Drone extends Moveable {
	
	private ArrayList<GameObject> collisionList;
	private GameWorld gw;
	
	/**
	 * Constructor for Drone class. Get a random size between 10 and 40,
	 * and make all drones red. Location will also be randomly selected.
	 * And the color cannot change once it is created, so the method will
	 * be overriden. It calls its parent constructor in order to set these.
	 * 
	 * For heading, I'm making it -359 for counterclock wise and 359 for
	 * clockwise.
	 */
	
	public Drone(GameWorld gw) {
		super(randomNumber(10, 40), ColorUtil.rgb(255,0,0),                    //random size, red color.
				randomNumber(0, 359), randomNumber(100, 100),                   //speed and heading
				randomNumber(0, Game.getMapWidth()),                  
				randomNumber(0, Game.getMapHeight()));                       //Width = max x value, height = max y value, between 0 and 1000
		this.gw = gw;
		collisionList = new ArrayList<GameObject>();
	}
	
	public void setColor(int color) {               //Overriding setting color for Drones. Color can't be changed.
	
	}
	

	/**
	 * Collision handling method for the Drone
	 */
	
	public void handleCollision(GameObject gameObject) {
		 if(!collisionList.contains(gameObject)) { //else if it does not contain the object
			gw.droneCollide(gameObject); //the collision method will handle it if the colliding obj does not do a base collide 
			collisionList.add(gameObject);
		}
	}
	
	public void removeFromList(GameObject object) {
		if(collisionList.contains(object)) 
			collisionList.remove(object);
	}
	
	/**
	 * Move method for drone, equation was from page 9 of the assignment.
	 * It adds either a negative or positive (hence between -10 and 10)
	 * to the heading to make sure it does not go into a straight line,
	 * only after the location has been updated.
	 * If the heading goes over 0 or 359, it will obtain a new random heading
	 * between those two values.
	 */
	
	public void move(int elapsedMilliSecs) {
		float dist = (float) (this.getSpeed() * (elapsedMilliSecs/1000.0));
		float newX = (float) (this.getX() + (Math.cos(Math.toRadians((90 - this.getHeading()))) * dist));  //From equation on slide 16 of CH11
		float newY = (float) (this.getY() + (Math.sin(Math.toRadians((90 - this.getHeading()))) * dist));
		if(newX + getSize()/2 > Game.getMapWidth()) {                        //Error checking starts, make sure X does not go out of bounds
			newX = Game.getMapWidth() - getSize()/2;
		} else if(newX - getSize()/2 < 0) {
			newX = getSize()/2;
		}
		if(newY + getSize()/2 > Game.getMapHeight()) {                       //Make sure that Y value does not go out of bounds
			newY = Game.getMapHeight() - getSize()/2;
		} else if(newY - getSize() < 0) {
			newY = getSize();
		}
		setLocation(newX, newY);                                 //Set the new location 
		int randomHeading = randomNumber(-10, 20);               //Making sure drone does not go in straight line
		this.setHeading(this.getHeading() + randomHeading);      //Add the random + or - value to the heading
		if(this.getHeading() > 359 || this.getHeading() < 0)  //Make sure that the heading does not go above 0 or 359
			this.setHeading(randomNumber(0, 359));
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int topX = (int) (this.getX() + pCmpRelPrnt.getX());
		int topY = (int) (this.getY() + pCmpRelPrnt.getY());
		int bottomLeftX = (int) ((this.getX() - this.getSize() / 2) + pCmpRelPrnt.getX()); //Length of unequal side of triangle is size
		int bottomLeftY = (int) ((this.getY() - this.getSize()) + pCmpRelPrnt.getY());     //Length of height is size as well
		int bottomRightX = (int) ((this.getX() + this.getSize() / 2)+ pCmpRelPrnt.getX()); //from slide 14-7 and abovbe comments on pg 1 bottom para
		g.setColor(this.getColor());
		g.fillTriangle(topX, topY, bottomLeftX, bottomLeftY, bottomRightX, bottomLeftY); //using bottom left for bottom right bc same thing
	}
	
	/**
	 * toString method overriden to make print format look as such.
	 */
	
	public String toString() {
		return "Drone:  loc="+ Math.round(this.getX() * 10.0) / 10.0 + ","   //To get single point decimal
				+ Math.round(this.getY() * 10.0) / 10.0 + "    color=[" 
				+ ColorUtil.red(this.getColor()) + ","
				+ ColorUtil.green(this.getColor()) + ","
				+ ColorUtil.blue(this.getColor()) + "]    heading=" 
				+ this.getHeading() + "    speed=" 
				+ this.getSpeed() + "    size=" + this.getSize();
	}
	
}
