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
	public Hero(String name, Int hitpoints, Float strength) throws IllegalArgumentException {
		super(name, hitpoints);
		
		
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
	
	
	

}
