package MindCraft;

import java.util.Map;
import java.util.HashMap;

/**
 * A class of characters.
 * @invar 	Each character must have proper items anchored.
 *        	| hasProperItems()
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */

public abstract class Character {
	
	/**
	 * Creates a character
	 * @post	Creates a character with the given name
	 * 			| new.getName() == name
	 * @post	Creates a character with the given hitpoints
	 * 			| new.getHitpoints() == hitpoints
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public Character(String name, int hitpoints) throws IllegalArgumentException {
		
	}
	
	/***********************
	 * Name
	 ***********************/
	
	private String name;
	
	/**
	 * Return true when the name is valid
	 * @return	Return true when the name starts with a capital
	 * 			and only contains letters, spaces and apostrophes
	 * 			| 
	 */
	public static boolean isValidName(String name) {
		return false;
	}
	
	/**
	 * Sets the name to the given name
	 * @param 	name
	 * 			the new name
	 * @post	The name is set to the given name
	 * 			| new.getName() = name
	 */
	@Raw
	private void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Changes the name to the given name when valid
	 * @param 	name
	 * 			the new name
	 * @post	The name is set to the given name
	 * 			| new.getName() = name
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public void changeName(String name) {
		
	}
	
	/**
	 * Return the character's name
	 * @return	Return the character's name
	 */
	public String getName() {
		return this.name;
	}
	
	/***********************
	 * Hitpoints
	 ***********************/
	private int hitpoints;
	private boolean isFighting = false;
	
	/**
	 * 
	 * @param hitpoints
	 * @return
	 */
	public boolean canHaveAsHitpoints(int hitpoints) {
		return hitpoints >= 0 && (isFighting() || isprime(hitpoints));
	}
	
	/**
	 * Sets the character's hitpoints to the given hitpoints
	 * @param 	hitpoints
	 * 			the new amount of hitpoints
	 * @post	The character's hitpoints are set to the given hitpoints
	 * 			| new.getHitpoints() = hitpoints
	 */
	private void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}
	
	/**
	 * Return the current amount of hitpoints
	 * @return	Return the current amount of hitpoints
	 */
	public int getHitpoints() {
		return this.hitpoints;
	}
	
	/**
	 * removes hitpoints from the character
	 * @param	hitpoints
	 * 			The amount of hitpoints to be taken
	 * @post	Removes the given amount of hitpoints
	 * 			| new.getHitpoints() = this.getHitpoints() - hitpoints
	 * @post	when the character takes damage, isFighting is set to true
	 * 			| new.isFighting() = true
	 */
	public void takeDamage(int hitpoints) {
		
	}
	
	/**
	 * Sets the current fighting state to the given state
	 * @param 	isFighting
	 * 			The new state of isFighting
	 * @post	Sets the current fighting state to the given state
	 * 			| new.isFighting() = isFighting
	 */
	private void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}
	
	/**
	 * Return the current fighting state of this character
	 * @return	Return the current fighting state of this character
	 */
	public boolean isFighting() {
		return this.isFighting;
	}
	
	/***********************
	 * Protection
	 ***********************/
	
	/**
	 * Return the protection of the character
	 * @return	Return the protection of the current character based
	 * 			on armor and default protection
	 */
	public abstract int getProtection();
	
	
	/***********************
	 * Anchors
	 ***********************/
	
	HashMap<Long, Item> anchors = new HashMap<Long, Item>();
	
	
	/**
	 * 
	 */
	public void Equip(int anchor, Item item) {
		
	}
	
	/**
	 * 
	 */
	public void PickUp(Item item) {
		
	}
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * 
	 */
	public void Hit(Character character) {
		
	}
	
	/**
	 * 
	 */
	public void CollectTreasures(Character character) {
		
	}
	
	
}
