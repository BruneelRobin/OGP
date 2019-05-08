package MindCraft;

/**
 * A class of Heroes. 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 *
 *
 */

public class Hero extends Character{
	
	
	/**
	 * 
	 * @param name
	 * @param hitpoints
	 * @param strength
	 */
	public Hero(String name, int hitpoints, float strength) throws IllegalArgumentException {
		super(name, hitpoints);
		
		this.setStrength(strength);
		
	}
	
	
	
	
	
	
	
	/***********************
	 * Strength
	 ***********************/
	
	private float strength;
	
		/**
		 * Returns the strength
		 * @return
		 */
	public float getStrength() {
		return this.strength;
	
	}
	
	/**
	 * Sets the strength to the given strength
	 * @param strength
	 */
	private void setStrength(float strength) {
		this.strength = strength;
		
		
	}
	
	
	/***********************
	 * Capacity
	 ***********************/
	
	/**
	 * Returns the capacity
	 * @return
	 */
	public float getCapacity() {
		return 20*this.getStrength();
		
	}
	
	
	
	/***********************
	 * Other methods
	 ***********************/
	
	
	/**
	 * Returns whether or not the Hero wants to take an item
	 * 
	 * 
	 */
	public boolean wantsToTakeItem(Item item) {
		
	}
	
	/**
	 * Makes the character hit the given character
	 * 
	 */
	public void hit(Character character) {
		
		
	}
	
	/**
	 * Heals the hero by adding hitpoints
	 * 
	 */
	
	public void heal() {
		
	}
	

}
