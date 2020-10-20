package com.mycompany.a3;
import com.codename1.charts.models.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;

import java.util.Random;

public abstract class GameObject implements IDrawable, ICollider{
	private int color;
	private int size;
	private Point location;
	
	/**
	 * 4 constructors, each allowing the children of this class to pass
	 * on values in order to change the values for color, size and location.
	 * There are different constructors so that it makes it harder for 
	 * outside entities to try and change certain values once they are created.
	 */
	
	public GameObject() {
	}
	
	public GameObject(int size) {
		this.size = size;
	}
	
	public GameObject(int size, int color) {                                             
		this.size = size;
		this.color = color;
	}
	
	public GameObject(int size, int color, float positionX, float positionY) {
		this.size = size;
		this.color = color;
		location = new Point(positionX, positionY);
	}
	
	/**
	 * Getters and setters
	 */
	
	/*--------------------------------GETTERS--------------------------------*/
	
	public int getColor() {                                                   //Return the color of the object
		return color;
	}
	
	public int getSize() {                                                    //Return the size of the object                               
		return size;
	}
	
	public Point getLocation() {                                              //Return the location as a Point
		return location;
	}
	
	public float getX() {                                                     //Return the X value of the location
		return location.getX();
	}
	
	public float getY() {                                                     //Return the Y value of the location
		return location.getY();
	}

	/*--------------------------------SETTERS--------------------------------*/
	
	public void setColor(int color) {                                         //Set the color of the object
		this.color = color;
	}
	
	/**
	 * This one sets a location based on a passed parameter x and y.
	 * So it sets location to a new point, rather than setting the 
	 * current location to a new x and new y. This was because it was
	 * giving me a weird null pointer exception error.
	 * Error checking occurs before the x and y values are passed onto
	 * this method. 
	 * 
	 * @param x is the x value in the world.
	 * @param y is the y value in the world.
	 */
	
	public void setLocation(float x, float y) {                               
		location = new Point(x, y);
	}
	
	/*--------------------------------END OF SETTERS/GETTERS--------------------------------*/
	
	public boolean collidesWith(GameObject gameObject) {
		boolean result = false;
		int thisCenterX = (int) (this.getX() + (this.getSize()/2)); //Find the centers of both objects
		int thisCenterY = (int) (this.getY() + (this.getSize()/2));  //Wrapping both objects around a square
		int otherCenterX = (int) (gameObject.getX() + (gameObject.getSize()/2));
		int otherCenterY = (int) (gameObject.getY() + (gameObject.getSize()/2));
		//Find the distance between the centers 
		int dx = thisCenterX - otherCenterX; 
		int dy = thisCenterY - otherCenterY;
		int distBetweenCenterSqr = (dx*dx + dy*dy);
		//finding the square of sum of radii
		int thisRadius = this.getSize()/2;
		int otherRadius = gameObject.getSize()/2;
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		if(distBetweenCenterSqr <= radiiSqr) 
			result = true;
		return result;
	}
	
	/**
	 * This method returns an random integer between x and x+y 
	 * which is why you may see weird numbers in place of the
	 * actual values. E.g. you may see -359, 718, because that 
	 * returns a value between x = -359 and y = -359 + 718.
	 * y = 359, so a value between -359 and 359.
	 * 
	 * @param start is the x value described above
	 * @param end is the y value
	 * @return a random int between these two x and y values.
	 */
	
	public static int randomNumber(int start, int end) {                   
		Random random = new Random();
		return start + random.nextInt(end);       //This equation was from page 13 of the assignment
	}
	
	public abstract void removeFromList(GameObject object);
	
}
