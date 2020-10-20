package com.mycompany.strategies;

import com.codename1.util.MathUtil;
import com.mycompany.a3.Base;
import com.mycompany.a3.GameObjectCollection;
import com.mycompany.a3.NonPlayerCyborg;
import com.mycompany.interfaces.IIterator;
import com.mycompany.interfaces.IStrategy;

public class BaseAttack implements IStrategy{
	private NonPlayerCyborg npc;
	private GameObjectCollection gameObjects;
	
	/**
	 * Get access to the NPC and the bases from the iterator
	 * @param npc
	 * @param gameObjects
	 */
	public BaseAttack(NonPlayerCyborg npc, GameObjectCollection gameObjects) {
		this.npc = npc;
		this.gameObjects = gameObjects;
	}
	
	/**
	 * What to apply, equations obtained from A2 page 8
	 */
	
	public void apply() {
		float npcX = npc.getX();
		float npcY = npc.getY();
		float baseX = 0;
		float baseY = 0;
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {
			Object holder = gameElements.getNext();
			if(holder instanceof Base && ((Base) holder).getSequenceNumber() == (npc.getLastBaseReached() + 1)) {
				Base base = (Base) holder;
				baseX = base.getX();  //Get the X value for the base that is 1 higher than the last base reached
				baseY = base.getY(); //Get the Y value
			}
		}
		float xDiff = baseX - npcX;
		float yDiff = baseY - npcY;
		double angle = MathUtil.atan2(yDiff, xDiff); //MathUtil docs say "arctan2(y,x)"
		angle = Math.toDegrees(angle); //Angle found from equation on page 8
		npc.setHeading((int) angle);
	}
	
	public String toString() {
		return "Strategy: Base Attack";
	}

}
