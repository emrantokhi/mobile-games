package com.mycompany.a3;
import com.codename1.ui.Display;
import com.mycompany.interfaces.IIterator;
import com.mycompany.interfaces.IStrategy;
import com.mycompany.strategies.BaseAttack;
import com.mycompany.strategies.PlayerAttack;

import java.util.Observable;
import java.util.Random;

public class GameWorld extends Observable {
	
	/**
	 * I added width, height and indexOfCyborg into the GameWorld.
	 * I added width and height so that any error checking, specifically
	 * the out of bounds checking, can be easier, and can adjust to the 
	 * GameWorlds size if changed here, without having to change it in
	 * the other classes. 
	 * 
	 */
	
	private GameObjectCollection gameObjects;          //Collection that implements Iterator Design Pattern
	private boolean sound;                             //Sound on or off
	private boolean first;
	private Sound cyborgDamage;
	private Sound energyGain;
	private Sound lifeLost;
	private BGSound backgroundSound;
	private int currClockTime;                         //Current clock ticks
	private int livesRemaining;                        //Amount of lives remaining
	
	/**
	 * Constructor for GameWorld, always have it set the clock time 
	 * at 0, and have 3 lives, and set the width and height of the 
	 * GameWorld here.
	 */
	
	public GameWorld() {
		currClockTime = 0;                            //Clock ticks at 0       
		livesRemaining = 3;                           //Lives left at 3
		sound = true;                                 //Sound on
		first = true;
	}
	
	/**
	 * Getters
	 */
	
	public GameObjectCollection getGameObjectCollection() {
		return gameObjects;
	}
	
	public int getCurrClockTime() {
		return currClockTime;
	}
	
	public int getLivesRemaining() {
		return livesRemaining;
	}
	
	public boolean getSound() {
		return sound;
	}
	
	public void setSound(boolean sound) {
		this.sound = sound;
		playBGMusic(this.sound);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * I use these two methods in the pause and play command
	 * so that when you pause while having it as true
	 * the music can come back on.
	 */
	
	public void createSounds() {
		cyborgDamage = new Sound("Boom.mp3");
		energyGain = new Sound("Energy.mp3");
		lifeLost = new Sound("Death.mp3");
		backgroundSound = new BGSound("Above All (Royalty Free).mp3");
	}
	
	public void playBGMusic(boolean b) {
		try {
			backgroundSound.play(b);
		}catch(Exception e) {
			System.out.println("Play bg music method error");
		}
	}
	
	/**
	 * Initialize the arraylist with new gameobjects.
	 * The reason why gameObjects is initialized here instead 
	 * of the constructor is because when game is reinitialized
	 * after the player loses, it resets the arraylist back to 
	 * the initial starting point. Does not reset lives remaining
	 * or clock time when called.
	 */
	
	public void init() {                                              /*Initialize the GameWorld with GameObjects */
		gameObjects = new GameObjectCollection();
		gameObjects.add(new Base(1, false, Game.getMapWidth() - 200, Game.getMapHeight() - 250, this));                 //Add new bases 1-4, (sequenceNumber, x position, y position)
		gameObjects.add(new Base(2, false, Game.getMapWidth() - 800, Game.getMapHeight() - 900, this));
		gameObjects.add(new Base(3, false, Game.getMapWidth() - 1200, Game.getMapHeight() - 400, this));
		gameObjects.add(new Base(4, true, Game.getMapWidth() - 150, Game.getMapHeight() - 700, this));
		Base base1 = findBase1();                                     //To grab the location of base 1, so cyborg can start there
		gameObjects.add(PlayerCyborg.getPlayerCyborg(base1.getX(), base1.getY(), this));      //Create one Cyborg placed on top of the location of base 1
		gameObjects.add(new NonPlayerCyborg(base1.getX() - 150, base1.getY(), this));                 //3 NPC's at least 2 cyborgs away
		gameObjects.add(new NonPlayerCyborg(base1.getX() - 300, base1.getY(), this));
		gameObjects.add(new NonPlayerCyborg(base1.getX() - 450, base1.getY(), this));
		gameObjects.add(new Drone(this));                                 //Create two drones
		gameObjects.add(new Drone(this));
		gameObjects.add(new EnergyStation(this));                         //Create two energystations
		gameObjects.add(new EnergyStation(this));
		setNPCStrategies();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Randomly set the NPC strategies to either
	 * Base Attack or Player Attack
	 */
	
	private void setNPCStrategies() {
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {
			Object holder = gameElements.getNext();
			if(holder instanceof NonPlayerCyborg) {    //Find all of the NPC's in the collcetion
				NonPlayerCyborg npc = (NonPlayerCyborg) holder;
				Random random = new Random();
				int num = random.nextInt(10) + 1;            //Randomly assign them to be either base or player attack
				if(num % 2 == 0) {
					npc.setStrategy((IStrategy) new BaseAttack(npc, gameObjects));
					npc.setLastBaseReached(npc.getLastBaseReached() + 1);
				}else
					npc.setStrategy((IStrategy) new PlayerAttack(npc, findPlayer()));
			}
		}
	}
	
	/**
	 * findBase1 is only intended for use in this class.
	 * It is used in init, in order to get the location of 
	 * the first base to allow cyborg to be set to that
	 * location as well.
	 * 
	 * @return the object Base whose sequence number is 1
	 */
	
	private Base findBase1() {
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {        //Go through the ArrayList in order to find the base
			Object holder = gameElements.getNext();          //Grab the object into a variable
			if(holder instanceof Base && ((Base) holder).getSequenceNumber() == 1)  //Check if the object's actual type is base
				return (Base) holder;                                               //And if its sequence number is 1, then return
		}
		return null;
	}
	
	/**
	 * This method is intended for use only in this class.
	 * Returns the object Base whose flag of lastBase is true.
	 * This is for use in determining if the cyborg has won the game.
	 * 
	 * @return the object Base whose lastBase flag is true.
	 */
	
	private Base findLastBase() {
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {     //Go through the ArrayList in order to find the base
			Object holder = gameElements.getNext();      //Grab the object into a variable
			if(holder instanceof Base && ((Base) holder).isLastBase() == true) //First check if it's a base, if so, then check
				return (Base) holder;                                          //if its flag is true as well, if so return this base
		}
		return null;
	}
	
	/**
	 * This method is intended for use only in this class.
	 * It makes sure to find the value of the cyborg's index
	 * and sets indexOfCyborg to its value. This method is 
	 * for robustness, so hardcoding the value for cyborg is 
	 * unnecessary.
	 */
	
	private PlayerCyborg findPlayer() {
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {  //Go through the ArrayList in order to find the base
			Object holder = gameElements.getNext();
			if(holder instanceof PlayerCyborg) 
				return (PlayerCyborg) holder;                  //If you find the cyborg, set the index to whatever 
		}
		return null;
	}
	
	/**
	 * This method is intended for use only in this class.
	 * This method just makes sure that the cyborg's max speed
	 * is dependent on its damage level. If the cyborgs speed is
	 * above its max speed (equation created by me), then set
	 * its speed to the max speed allowed due to damage level.
	 * 
	 * In A2 now it also slows down the cyborg
	 * 
	 * The equation is: MaxSpeed -((damageLevel as a percentage) * MaxSpeed)
	 * I can do this because the max damage level is 100, making calculations easier.
	 */
	
	private void slowCyborgFromDamage() {  //MAY NEED TO CHANGE FOR ENENMY CYBORGS TO CHECK-------------------------------------------------------------------------------------
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {
			Object holder = gameElements.getNext();
			if(holder instanceof Cyborg) {    //Find all of the Cyborgs in the collcetion
				Cyborg cyborg = (Cyborg) holder;
				int currMaxSpeed = (int) Math.floor(cyborg.getMaximumSpeed() - ((cyborg.getDamageLevel() / 100.0) * cyborg.getMaximumSpeed()));
				if(cyborg.getSpeed() > currMaxSpeed) {   //If the cyborgs current speed when this method is called is > the max allowed speed
					cyborg.setSpeed(currMaxSpeed);       //When calculated, then set its speed to the max speed.
				}
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Command to accelerate the cyborg's speed by 5 units.
	 * It makes sure to call the slowCyborgFromDamage to make
	 * sure that it does not go above the allowed max speed 
	 * due to damage level.
	 */
	
	public void accelerate() {                   //'a' command
		PlayerCyborg cyborg = findPlayer();
		cyborg.setSpeed(cyborg.getSpeed() + 5);  //Increase speed by 5
		slowCyborgFromDamage();                  //Have this method do checking 
		System.out.println("Accelerating!");     //Print out action.
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Command to brake, reduces cyborg's speed by 5 units.
	 * Makes sure that speed cannot go below 0.
	 */
	
	public void brake() {                              //'b' command
		PlayerCyborg cyborg = findPlayer();
		if(cyborg.getSpeed() > 0) {                   //If cyborgs speed is above 0, then allow braking to occur
			cyborg.setSpeed(cyborg.getSpeed() - 5);
			System.out.println("Applying brakes.");
		}
		if(cyborg.getSpeed() <= 0) {                  //If cyborg's speed is at 0, do not allow it to go any further down.
			cyborg.setSpeed(0);
			System.out.println("0 speed. Cannot brake any further.");
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Command to turn the STEERINGDIRECTION (not the heading) left.
	 * Makes sure that steering direction cannot go beyond -40.
	 */
	
	public void left() {                                           //'l' (ell) command
		PlayerCyborg cyborg = findPlayer();
		int currentDirection = cyborg.getSteeringDirection();      //Get the cyborg's current steering direction
		if(currentDirection > -40) {                               //if it's greater than -40, then allow it to reach -40
			cyborg.setSteeringDirection(currentDirection - 5);     //By incrementing it down by 5
 			System.out.println("Steering left.");                  //Print what it's doing to console
		}else        //Otherwise, do not allow the steeringDirection to change less than -40.
			System.out.println("Cannot steer left anymore.");
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Command to turn the STEERINGDIRECTION (not the heading) right.
	 * Makes sure that steering direction cannot go beyond 40.
	 */
	
	public void right() {                                         //'r' command
		PlayerCyborg cyborg = findPlayer();
		int currentDirection = cyborg.getSteeringDirection();     //Get current cyborg's steering direction
		if(currentDirection < 40) {                               //If it's less than 40, then allow it to increase
			cyborg.setSteeringDirection(currentDirection + 5);    //By 5 units.
			System.out.println("Steering right.");
		} else                                                    //Otherwise do not let it keep changing steering direction.
			System.out.println("Cannot steer right anymore.");
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Command to PRETEND that cyborg has collided with another cyborg.
	 * Does 30 damage.
	 * When this command is activated, it slows the cyborg down based on its
	 * damage level.
	 */
	
	public void cyborgCollide(GameObject gameObject, NonPlayerCyborg npc) {                 //'c' command, going to do 30 dmg
		if(gameObject instanceof Cyborg) {
			Cyborg cyborg = (Cyborg) gameObject;
			int enemyCyborgDamage = 30;
			cyborg.setDamageLevel(cyborg.getDamageLevel() + enemyCyborgDamage); //Adds damage to cyborg's current damageLevel, Auto fades color 
			npc.setDamageLevel(npc.getDamageLevel() + enemyCyborgDamage);
			System.out.println("Collided with another cyborg! Ouch!");
			cyborgDamage.play(sound);
		}
		setChanged();
		notifyObservers();
		checkGameState();                                                   //Check if cyborg is still alive or not
		slowCyborgFromDamage();                                             //If it is, then make sure the cyborg is slowed from the dmg
	}
	
	
	/**
	 * Command to PRETEND that cyborg has collided with a base.
	 * Checks to make sure that the base number passed to this method
	 * exists. If it does, then it checks if the lastBaseReached by
	 * Cyborg is + 1 of the value passed onto this method.
	 * If so, then increment cyborg's lastBaseReached, if not
	 * tell the user that you need to reach lastBaseReached + 1 first.
	 * 
	 * @param x is the base number that is passed onto this method.
	 */
	
	public void baseCollide(int x, GameObject gameObject) {                                  //numbers 1-9 command
		if(gameObject instanceof Cyborg) {
			Cyborg cyborg = (Cyborg) gameObject;
			int lastBaseNumber = findLastBase().getSequenceNumber();      //Get the final base needed to win the game's sequenceNumber
			if(x <= lastBaseNumber && (cyborg.getLastBaseReached() + 1) == x) {     //Check if x is > than the last base's sequence number
				cyborg.setLastBaseReached(x);                            //If so then set the last base reached to x  
				System.out.println("\nBase " + x + " reached!");
			}else if(x > lastBaseNumber)                             //If x > last base reached, notify that it doesn't exist in this game
				System.out.println("\nThat base currently does not exist.");
			else if(x <= cyborg.getLastBaseReached() && x >= 1)       //If base has been reached, print this
				System.out.println("\nThat base has already been reached.");
			else                                                      //Otherwise, it's a base that it needs to sequentially get to
				System.out.println("\nThat base is currently not reachable. Go to " + (cyborg.getLastBaseReached() + 1) + " first.");
		}
		setChanged();
		notifyObservers();
		checkGameState();                                         //Check if cyborg has won the game or not
		
	}
	
	/**
	 * Command for when any cyborg has collided with an EnergyStation.
	 * Sets EnergyStation's capacity to 0, gives the energy to Cyborg, and
	 * creates a new EnergyStation with a random location and size in the GW.
	 */
	
	public void energyCollide(GameObject gameObject, EnergyStation es) {                                     //'e' command
		if(gameObject instanceof Cyborg) {
			Cyborg cyborg = (Cyborg) gameObject;
			if(es.getCapacity() != 0) { //If it has energy to give 
				cyborg.setEnergyLevel(cyborg.getEnergyLevel() + (es.giveEnergy()));              //Then give cyborg
				energyGain.play(sound);       
				gameObjects.add(new EnergyStation(this));                         //Add in a new EnergyStation
				System.out.println("Energy added and new Energy Station has been created!");
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Command to PRETEND that cyborg has collided with a Drone.
	 * Does 15 damage. Slows cyborg down when hit if it's going at
	 * max speed at the time.
	 */
	
	public void droneCollide(GameObject gameObject) {                                   //'g' command
		if(gameObject instanceof Cyborg) {
			Cyborg cyborg = (Cyborg) gameObject;
			int droneDamage = 15;                                      //Drone does 15 damage to cyborg
			cyborg.setDamageLevel(cyborg.getDamageLevel() + droneDamage); //Add the damage to cyborg's damageLevel, Auto fades color
			System.out.println("Ouch! Drone collision!");
		}
		checkGameState();                                          //Make sure to check if Cyborg has died or not
		slowCyborgFromDamage();                                    //Enforce Speed-limitation rule
		setChanged();
		notifyObservers();
	}
	
	/**
	 * This command is to tick the in game clock.
	 * Makes sure that all moveable objects in the arraylist 
	 * use their move command. This is allowed becauseall moveable
	 * objects MUST program the move() method.
	 * Also checks if the cyborg is alive (for later versions, where 
	 * auto-collisions are implemented.)
	 * 
	 * Also made it so that if cyborg reaches the last base just as energy
	 * runs out, it will win instead of loss.
	 */
	
	public void clockTick() {                                     //'t' command
		PlayerCyborg cyborg = findPlayer();    
		if(first) {
			first = false;
			createSounds();
			playBGMusic(sound);
		} 
		cyborg.consumeEnergy();                                   //Consume the energy no matter what
		if(cyborg.isAlive()) {                                    //Check if the cyborg is alive.
			currClockTime += Game.getMilliSecs();                                   //If it is, advance the time by 1 tick.
			IIterator gameElements = gameObjects.getIterator();
			while(gameElements.hasNext()) {         //Run through all objects in arraylist
				Object object = gameElements.getNext();
				if(object instanceof Moveable) {                  //And make sure all moveable objects use their Move() method
					Moveable object2 = (Moveable) object;
					object2.move(Game.getMilliSecs());
				}
				if(object instanceof NonPlayerCyborg) {
					NonPlayerCyborg npc = (NonPlayerCyborg) object;
					npc.invokeStrategy();
				}
			}
			gameElements = gameObjects.getIterator();
			while(gameElements.hasNext()) {  //Collision detection for every time everything moves
				GameObject curObj = (GameObject) gameElements.getNext();
				IIterator iterator2 = gameObjects.getIterator();
				while(iterator2.hasNext()) {
					GameObject otherObj = (GameObject) iterator2.getNext();
					if(otherObj != curObj) { //Make sure that the objects are the not the same ones
						if(curObj.collidesWith(otherObj))
							curObj.handleCollision(otherObj);
						else
							curObj.removeFromList(otherObj);
					}
				}
			}
		}
		slowCyborgFromDamage();                                   //Make sure to slow cyborg if a collision happens
		setChanged();
		notifyObservers();
		checkGameState();                                         //Check if cyborg has won or lost or neither 
	}
	
	
	/**
	 * This method is called to check if the cyborg has:
	 * 			1)Reached the last base and won
	 * 			2)Is not alive anymore due to excessive damage, and resets game
	 * 			3)Has no lives remaining and exits the game entirely.
	 */
	
	private void checkGameState() {
		PlayerCyborg cyborg = findPlayer(); 
		if(cyborg.getLastBaseReached() == findLastBase().getSequenceNumber()) {   //Check if it's reached the last base and won
			System.out.println("\n\nYou won! Total time: " + currClockTime/1000);
			Display.getInstance().exitApplication();
		} else if((!cyborg.isAlive() || npcWinner())  && livesRemaining > 1) {       //Check if it lost this round and has more than its last life left
			livesRemaining -= 1;
			System.out.println("Round lost! Try again!\nLives Remaining: " + livesRemaining + "\n\n");
			lifeLost.play(sound);
			init();                                                //Reset the game if so
		} else if((!cyborg.isAlive() || npcWinner()) && livesRemaining == 1) {      //If the Cyborg has run out of lives
			livesRemaining = 0;
			System.out.println("\n\nGame Over! You've failed miserably.");
			Display.getInstance().exitApplication();                //Exit the game entirely
		}	
	}
	
	private boolean npcWinner() {
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {
			Object holder = gameElements.getNext();
			if(holder instanceof NonPlayerCyborg) {    //Find all of the NPC's in the collcetion
				NonPlayerCyborg npc = (NonPlayerCyborg) holder;
				if(npc.getLastBaseReached() == findLastBase().getSequenceNumber())
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Changes all of the NPC's strategies to the other one
	 */
	
	public void changeStrategies() {
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {
			Object holder = gameElements.getNext();
			if(holder instanceof NonPlayerCyborg) {    //Find all of the NPC's in the collcetion
				NonPlayerCyborg npc = (NonPlayerCyborg) holder;
				if(npc.getStrategy() instanceof PlayerAttack) {
					npc.setStrategy((IStrategy) new BaseAttack(npc, gameObjects));  //If it's already playerattack, switch to base
				}else
					npc.setStrategy((IStrategy) new PlayerAttack(npc, findPlayer()));
			}
		}
		setChanged();
		notifyObservers();
		checkGameState();                                         //Check if cyborg has won or lost or neither (for later version)
	}
	
	/**
	 * This command is to display the "map" which
	 * shows all the locations of every object that is
	 * in the arraylist of gameobjects, using their 
	 * overridden toString() method
	 */
	public void printGameObjects() {                                                   //'m' command
		System.out.println("\n<--------------------------------------START OF MAP-------------------------------------->");
		IIterator gameElements = gameObjects.getIterator();
		while(gameElements.hasNext()) {             
			System.out.println(gameElements.getNext() + "\n");                //Print out all of the gameobjects using their toString()
		}
		System.out.println("<---------------------------------------END OF MAP--------------------------------------->\n");
	}
}
