package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.mycompany.interfaces.ISelectable;

public abstract class Fixed extends GameObject implements ISelectable {
	
	/**
	 * The constructors for fixed. The first one is for later implementation
	 * if necessary.
	 */
	
	public Fixed() {
		
	}

	/**
	 * This constructor is used to pass on data to the GameObject
	 * class in order to set the data to said values.
	 * 
	 * @param size is the size of the object
	 * @param color is the color of the object (obtained from ColorUtil)
	 * @param positionX is the X position of the object in the world
	 * @param positionY is the Y position of the object in the world
	 */
	public Fixed (int size, int color, float positionX, float positionY) {
		super(size, color, positionX, positionY);
	}
	
	/**
	 * Determine if the pointer has contained in the bounds
	 * of the shape.
	 */
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int pointerX = (int) pPtrRelPrnt.getX();
		int pointerY = (int) pPtrRelPrnt.getY();
		int fixedX = (int) (pCmpRelPrnt.getX() + this.getX());
		int fixedY = (int) (pCmpRelPrnt.getY() + this.getY());
		if((pointerX >= fixedX) 
				&& (pointerX <= fixedX + this.getSize())
				&& (pointerY >= fixedY) 
				&& (pointerY <= fixedY + this.getSize())) 
			return true;
		else
			return false;
	}
}
