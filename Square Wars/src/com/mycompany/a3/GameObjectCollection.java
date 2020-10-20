package com.mycompany.a3;
import java.util.ArrayList;

import com.mycompany.interfaces.ICollection;
import com.mycompany.interfaces.IIterator;

public class GameObjectCollection implements ICollection{
	
	private ArrayList<GameObject> gameObjects;         //Holds all the gameobjects in the game
	
	/**
	 * The contructor for creating the list of objects, should only be
	 * one collection in the game class.
	 */
	
	public GameObjectCollection() {
		gameObjects = new ArrayList<GameObject>();
	}
	
	/**
	 * Add objects of type GameObject, else don't
	 * add anything to the list.
	 */
	
	public void add(Object newObject) {
		if(newObject instanceof GameObject) {
			gameObjects.add((GameObject) newObject);
		}
	}
	
	/**
	 * Return the iterator so that a method
	 * may run through the list of objects
	 */
	
	public IIterator getIterator() {
		return new GameObjectIterator();
	}
	
	/**
	 * Private inner class that implements
	 * the IIterator, in order to have access
	 * to the objects in the list.
	 */
	
	private class GameObjectIterator implements IIterator {
		
		private int currElementIndex;
		
		/**
		 * Constructor sets the index to -1 so it
		 * can start from the beginning for every
		 * iterator.
		 */
		
		public GameObjectIterator() {
			currElementIndex = -1;
		}
		
		/**
		 * Returns true or false if there is
		 * a next item.
		 */
		
		public boolean hasNext() {
			if(gameObjects.size() <= 0)
				return false;
			if(currElementIndex == gameObjects.size() - 1) 
				return false;
			return true;
		}
		
		/**
		 * Returns the next item in the list
		 * and increments the index up by one.
		 */
		
		public Object getNext() {
			currElementIndex ++;
			return(gameObjects.get(currElementIndex));
		}
	}

}
