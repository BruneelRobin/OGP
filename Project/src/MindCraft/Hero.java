package MindCraft;

/**
 * A class of Heroes. 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */

public class Hero extends Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * 
	 * @param name
	 * @param hitpoints
	 * @param strength
	 * 
	 * @post	Creates a hero with the given name
	 * 			| new.getName() == name
	 * @post	Creates a hero with the given hitpoints
	 * 			| new.getHitpoints() == hitpoints
	 * @post	Creates a hero with the given strength
	 * 			| new.getStrength() == strength
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public Hero(String name, int hitpoints, float strength) throws IllegalArgumentException {
		super(name, hitpoints);
		
		this.setStrength(strength);
		
	}
	
	
	
	
	/*******************************
	 * Strength - total programming
	 *******************************/
	
	private float strength;
	
		/**
		 * Returns the character's strength
		 * @return Returns the character's strength
		 */
	public float getStrength() {
		return this.strength;
	
	}
	
	/**
	 * Sets the strength to the given strength
	 * @param strength
	 * 		  the new value of the hero's strength
	 * @post  The strength is set to the given strength
	 * 		  | new.getStrength == strength
	 */
	private void setStrength(float strength) {
		this.strength = strength;
		
		}
	
	
	/***********************
	 * Capacity
	 ***********************/
	
	/**
	 * Returns the hero's capacity
	 * @return Returns the hero's capacity
	 */
	public float getCapacity() {
		return 20*this.getStrength();
		
	}
	
	
	
	/***********************
	 * Other methods
	 ***********************/
	
	
	/**
	 * Returns whether or not the hero wants to take an item
	 * 
	 * @return Returns true when the hero wants to take the item
	 * @return Returns false when the hero does not want to take the item
	 */
	@Override
	public boolean wantsToTakeItem(Item item) {
		return false;
	}
	
	/**
	 * Makes the character hit the given character
	 * @post	The given character loses hitpoints.
	 * 			|
	 * 
	 */
	public void hit(Character character) {
		
		
	}
	
	/**
	 * Heals the hero by adding hitpoints
	 * @post The hitpoints 
	 * 
	 */
	//(maxHP-currentHP)*(random0-1) 
	//om priem te zijn, checken waar dichtste priem is via +2 en -2, dan nog eens checken
	public void heal() {
		
	}
	
	private int defaultProtection = 10;
	/**
	 * Return the protection of the hero
	 * @return	Return the protection of the hero based on default protection value and armor
	 */
	@Override
	public int getProtection() {
		return 0;
	}
	
	/**
	 * Checks if a hero can have the given name as name
	 */
	public static boolean isValidName(String name) {
		return false;
	}
	

}
