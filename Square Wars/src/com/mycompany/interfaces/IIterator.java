package com.mycompany.interfaces;

public interface IIterator {
	
	/**
	 * Anything that implements this interface
	 * must implement hasNext and getNext
	 */
	
	public boolean hasNext();
	public Object getNext();

}
