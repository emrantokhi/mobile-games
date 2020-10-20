package com.mycompany.a3;

public abstract class Moveable extends GameObject {
	private int heading;                     //Moveable objects have both heading and speed
	private int speed;
	
	/**
	 * Constructor for moveable, in case any class were to call it.
	 * Later implementation possible.
	 */
	public Moveable() {
		
	}
	
	/**
	 * The constructor for moveable which passes on values to its parent,
	 * GameObject, in order to set these paramters.
	 * @param size is the size of the object.
	 * @param color is the color of the object (provided by ColorUtil).
	 * @param positionX is the X position in the world.
	 * @param positionY is the Y position in the world.
	 */
	
	public Moveable (int size, int color, int heading, int speed, float positionX, float positionY) {
		super(size, color, positionX, positionY);
		this.heading = heading;
		this.speed = speed;
	}
	
	/**
	 * Setters and getters
	 */
	
	public void setHeading(int heading) {
		this.heading = heading;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getHeading() {
		return heading;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * This abstract move method tells any child of this class
	 * to make sure to implement a move method because they are
	 * moveable.
	 */
	
	public abstract void move(int elapsedMilliSecs);
}
