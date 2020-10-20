package com.mycompany.a3;
import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class EnergyStation extends Fixed {
	
	private int capacity;                                           //How much energy the station can hold
	private ArrayList<GameObject> collisionList;
	private GameWorld gw;
	private boolean selected;
	
	/**
	 * Constructor for EnergyStation.
	 * The size will be random, and all energy stations will be
	 * the color blue. The location will be randomly selected
	 * by a method implemented by the GameObject that chooses a random 
	 * number. The locations cannot be changed.
	 */
	
	public EnergyStation(GameWorld gw) {
		super(randomNumber(10, 40), ColorUtil.BLUE,                            
				/*randomNumber(50, Game.getMapWidth() - 100),              //Minus 40 so that it's always w/in the border           
				randomNumber(50, Game.getMapHeight() - 100)*/
				Game.getMapWidth() - 200, Game.getMapHeight() - 250);           //Also starts with 40 for same reason
		capacity = this.getSize();                                 //Capacity is proportional to the size of the energy station but made it /2 bc size was really small drawn
		this.gw = gw;
		collisionList = new ArrayList<GameObject>();
		selected = false;
	}
	
	/**
	 * When giveEnergy is called, the energy is given to the Cyborg
	 * and the capacity will be set to 0, since all the energy was 
	 * given away. 
	 * 
	 * @return the capacity essentially, so that the cyborg can absorb
	 * said energy.
	 */
	
	public int giveEnergy() {       
		int battery = capacity;
		capacity = 0;                                             //Set to 0 since cyborg absorbs all the energy when stepped on it
		this.setColor(ColorUtil.rgb(200, 200, 255));                //Fades the color of the EnergyStation
		return battery;
	}
	
	public int getCapacity() {                                    //Return the value of capacity
		return capacity;
	}
	

	/**
	 * Collision handling method for the Energy Station
	 */
	
	public void handleCollision(GameObject gameObject) {
		 if(!collisionList.contains(gameObject)) { //else if it does not contain the object
			gw.energyCollide(gameObject, this); //the collision method will handle it if the colliding obj does not do a base collide 
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
	 * Draw method of the Energy Station, is a circle
	 */
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int x = (int) (this.getX() + pCmpRelPrnt.getX());
		int y = (int) (this.getY() + pCmpRelPrnt.getY());
		if(!isSelected())
			g.fillArc(x, y, this.getSize(), this.getSize(), 0, 360);
		else
			g.drawArc(x, y, this.getSize(), this.getSize(), 0, 360);
		g.setColor(ColorUtil.BLACK);
		int sizePixels = Display.getInstance().convertToPixels(4);
		Font style = Font.createTrueTypeFont("Anasthesia", "Anasthesia.ttf"
				).derive(sizePixels, Font.STYLE_PLAIN);
		g.setFont(style);
		g.drawString(this.getCapacity() + "", x , y + 45);
	}
	
	/**
	 * toString method overriden to format printing like this.
	 */
	
	public String toString() {                                          
		return "EnergyStation:  loc=" + Math.round(this.getX() * 10.0) / 10.0 + ","  //To show one decimal place for location
				+ Math.round(this.getY() * 10.0) / 10.0 + "    color=[" 
				+ ColorUtil.red(this.getColor()) + ","
				+ ColorUtil.green(this.getColor()) + ","
				+ ColorUtil.blue(this.getColor()) + "]    size=" + this.getSize() 
				+ "    capacity=" + getCapacity();
	}
	
	

}
