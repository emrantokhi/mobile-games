package com.mycompany.a3;
import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class Base extends Fixed {
	
	/**
	 * I added a boolean lastBase here in order to have a flag
	 * for use in the GameWorld, in order to know if this is the
	 * last base that the cyborg has to reach in order to win.
	 */
	
	private int sequenceNumber;
	private boolean lastBase;
	private ArrayList<GameObject> collisionList;
	private GameWorld gw;
	private boolean selected;
	
	/**
	 * Constructor for the Base class, the size will always
	 * be 10 for all bases, and the color will always be yellow.
	 * Location will be manually set in the GameWorld.
	 * Size and color will be passed onto parent constructor to set the values
	 * 
	 * @param lastBase = a flag which will tell if this is the last base in the GameWorld or not
	 */
	
	public Base(int sequenceNumber, boolean lastBase, float positionX, float positionY, GameWorld gw) {
		super(90, ColorUtil.YELLOW, positionX, positionY);                       
		this.sequenceNumber = sequenceNumber;                               
		this.lastBase = lastBase;
		this.gw = gw;
		collisionList = new ArrayList<GameObject>();
	}
	
	/**
	 * These overrides are to make sure the base and color cannot be changed once it is created
	 * */
	
	public void setColor(int color) {                                     
	
	}
	
	/**
	 * These bottom two methods are getters for returning the sequenceNumber and if it is the lastBase or not 
	 */
	
	public int getSequenceNumber() {                                
		return sequenceNumber;
	}
	
	/**
	 * If the base is the last base (aka the winning base) return true
	 * @return whether it's the last base or not.
	 */
	public boolean isLastBase() {
		return lastBase;
	}
	
	/**
	 * Collision handling method for the Base
	 */
	
	public void handleCollision(GameObject gameObject) {
		 if(!collisionList.contains(gameObject)) { //else if it does not contain the object
			gw.baseCollide(sequenceNumber, gameObject); //the collision method will handle it if the colliding obj does not do a base collide 
			collisionList.add(gameObject);
		}
	}
	
	public void removeFromList(GameObject object) {
		if(collisionList.contains(object)) 
			collisionList.remove(object);
	}
	
	/**
	 * Selectable methods, this one changes
	 * the selected boolean variable
	 */
	public void setSelected(boolean b) {
		selected = b;
	}
	
	/**
	 * Returns if it is selected or not
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Draw the base method. An isosceles triangle, along with its number.
	 */
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int topX = (int) (this.getX() + pCmpRelPrnt.getX());
		int topY = (int) (this.getY() + pCmpRelPrnt.getY());
		int bottomLeftX = (int) ((this.getX() - this.getSize() / 2) + pCmpRelPrnt.getX()); //Length of unequal side of triangle is size
		int bottomLeftY = (int) ((this.getY() - this.getSize()) + pCmpRelPrnt.getY());     //Length of height is size as well
		int bottomRightX = (int) ((this.getX() + this.getSize() / 2)+ pCmpRelPrnt.getX()); //from slide 14-7 and abovbe comments on pg 1 bottom para
		int[] xPoints = {topX, bottomLeftX, bottomRightX};
		int[] yPoints = {topY, bottomLeftY, bottomLeftY};
		int numPoints = 3;
		g.setColor(this.getColor());
		if(!isSelected())
			g.fillPolygon(xPoints, yPoints, numPoints); //using bottom left for bottom right bc same thing
		else
			g.drawPolygon(xPoints, yPoints, numPoints);
		g.setColor(ColorUtil.BLACK);
		int sizePixels = Display.getInstance().convertToPixels(4);
		Font style = Font.createTrueTypeFont("Anasthesia", "Anasthesia.ttf"
				).derive(sizePixels, Font.STYLE_PLAIN);
		g.setFont(style);
		g.drawString(this.getSequenceNumber() + "", topX , topY);
	}
	
	/**
	 * This toString method is overridden to print out the information as such
	 */
	
	public String toString() {                                              
		return "Base #" + getSequenceNumber() + ":  loc=" 
				+ Math.round(this.getX() * 10.0) / 10.0 + ","              //These are to round the numbers to one decimal
				+ Math.round(this.getY() * 10.0) / 10.0 
				+ "    color=[" + ColorUtil.red(this.getColor()) + ","
				+ ColorUtil.green(this.getColor()) + ","
				+ ColorUtil.blue(this.getColor()) + "]    size=" + this.getSize();
	}

}
