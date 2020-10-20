package com.mycompany.interfaces;

public interface ICollection {
	
	/**
	 * Anything that implements this interface
	 * must implement add() and getIterator
	 */
	
	public void add(Object newObject);
	public IIterator getIterator();

}
