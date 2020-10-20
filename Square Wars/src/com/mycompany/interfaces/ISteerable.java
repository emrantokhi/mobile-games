package com.mycompany.interfaces;

/**
 * Anything with this interface, means that they are steerable.
 * So far, only cyborg should be a steerable object.
 */

public interface ISteerable {
	
	/**
	 * include a method that allows the heading to be changed
	 */
	public abstract void changeHeading(int heading);
}
