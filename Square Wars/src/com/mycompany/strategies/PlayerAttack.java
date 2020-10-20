package com.mycompany.strategies;
import com.codename1.util.MathUtil;
import com.mycompany.a3.NonPlayerCyborg;
import com.mycompany.a3.PlayerCyborg;
import com.mycompany.interfaces.IStrategy;

public class PlayerAttack implements IStrategy{
	private NonPlayerCyborg npc;
	private PlayerCyborg player;
	
	/**
	 * Get access to the NPC and the bases from the iterator
	 * @param npc
	 * @param gameObjects
	 */
	
	public PlayerAttack(NonPlayerCyborg npc, PlayerCyborg player) {
		this.npc = npc;
		this.player = player;
	}
	
	
	/**
	 * How to apply the strategy to the NPC, equation
	 * from page 8 of A2
	 */
	public void apply() {
		float npcX = npc.getX();   //Get NPC's x and y
		float npcY = npc.getY();
		float playerX = player.getX();  //Get Player's current X and Y
		float playerY = player.getY();
		float xDiff = playerX - npcX;
		float yDiff = playerY - npcY;
		double angle = MathUtil.atan2(yDiff, xDiff); //MathUtil docs say "arctan2(y,x)"
		angle = Math.toDegrees(angle);
		npc.setHeading((int) angle); //Set steeringdirection to the angle calculated
	}
	
	/**
	 * To String method
	 */
	
	public String toString() {
		return "Strategy: Player Attack";
	}
}
