package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
//import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
//import com.codename1.ui.plaf.Border;
import com.mycompany.commands.PositionCmd;
import com.mycompany.interfaces.IIterator;

public class MapView extends Container implements Observer {
	
	private GameWorld gw;
	private Game game;
	
	public MapView(GameWorld gw, Game game) {
		//this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.rgb(0, 255, 0)));
		this.gw = gw;
		this.game = game;
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		Fixed selectedObj = null;
		IIterator gameElement = gw.getGameObjectCollection().getIterator();
		while(gameElement.hasNext()) {
			Object holder = gameElement.getNext();
			if(holder instanceof Fixed) {
				Fixed obj = (Fixed) holder;
				if(obj.isSelected()) {
					selectedObj = obj;
				}
			}
		}
		//If the position button is clicked and no obj selected
		if(game.isStopped() && PositionCmd.isClicked() && selectedObj == null) 
			PositionCmd.setClicked(false);   //Set clicked to false
		//If the game is stopped, the obj is selected, and the position button is clicked
		if(game.isStopped() && (selectedObj != null) && selectedObj.isSelected() && PositionCmd.isClicked()) { 
			//x and y are the screen's origin, so subtract it by mapviews origin to get accurate 
			//positioning
			int newX = x - this.getAbsoluteX(); 
			int newY = y - this.getAbsoluteY();
			selectedObj.setLocation(newX, newY);
			PositionCmd.setClicked(false); //do clicked back to false
			repaint();
		}
		//Just to select the object, had to put this below it all so that it considers the next click for
		//the new position for the shape to to placed
		if(game.isStopped()) {
			x = x - getParent().getAbsoluteX();
			y = y - getParent().getAbsoluteY();
			Point pPtrRelPrnt = new Point(x, y);
			Point pCmpRelPrnt = new Point(getX(), getY());
			IIterator gameElements = gw.getGameObjectCollection().getIterator();
			while(gameElements.hasNext()) {
				Object holder = gameElements.getNext();
				if(holder instanceof Fixed) {
					Fixed obj = (Fixed) holder;
					//if the Ptr(pointer) is within the Cmp (component)
					if(obj.contains(pPtrRelPrnt, pCmpRelPrnt)) {
						obj.setSelected(true);
					}
					else {
						obj.setSelected(false);
					}
				}
			}
			repaint();
		}
	}
	
	/**
	 * Used for outside of the mapview incase we need to unselect an item.
	 * Really used for just calling the repaint
	 */
	public void unselect() {
		IIterator gameElements = gw.getGameObjectCollection().getIterator();
		while(gameElements.hasNext()) {
			Object holder = gameElements.getNext();
			if(holder instanceof Fixed) {
				Fixed obj = (Fixed) holder;
				obj.setSelected(false);
			}
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		IIterator gameElements = gw.getGameObjectCollection().getIterator();
		while(gameElements.hasNext()) {
			Object holder = gameElements.getNext();
			Point mapCoordinate = new Point(this.getX(), this.getY());
			if(holder instanceof GameObject) {
				GameObject obj = (GameObject) holder;
				obj.draw(g, mapCoordinate); //Not sure if location part is right check when drawing
			}
		}
		
	}
	
	public void update(Observable o, Object arg) {
		repaint();
	}

}
