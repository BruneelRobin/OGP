package MindCraft;

import java.util.Map;

import be.kuleuven.cs.som.annotate.*;

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
	 * @return	Return the protection of the current character
	 */
	public abstract int getProtection();
	
	
	/***********************
	 * Anchors
	 ***********************/
	
	/**
	 * Variable referencing a dictionary of all anchored items of this character. 
	 * This class has a bidirectional relation with the class Item. An item can be anchored using the function equip/unequip
	 * or the protected function removeItemFromHolder.
	 * 
	 * @invar Each non null element in the hashmap references an effective item. 
	 *        | for (HashMap.Entry<Integer,Item> entry : anchors.entrySet())
	 *        | 	entry.getValue() != null
	 * @invar Each element in the hashmap references an item that references
	 *        back to this character.
	 *        | for (HashMap.Entry<Integer,Item> entry : anchors.entrySet())
	 *        | 	entry.getValue().getHolder() == this
	 */	
	HashMap<Integer, Item> anchors = new HashMap<Integer, Item>();
	
	/**
	 * Set the item at the given anchorId
	 * @param 	anchorId
	 * 			The anchorId to set the item at
	 * @param 	item
	 * 			The item to set at the given anchorId
	 */
	private void setAnchorAt(int anchorId, Item item) {
		
	}
	
	/**
	 * Return the item at the given anchorId
	 * @param 	anchorId
	 * 			The anchorId of the item
	 * @return	Return the item at the given anchorId
	 */
	public Item getAnchorAt (int anchorId) {
		return null;
	}
	
	/**
	 * Return true when the given item can be equipped
	 * @param 	item
	 * 			the item to be checked
	 * @return	Return true when the given item can be equipped
	 * 			| ...
	 */
	public boolean canEquipItem(Item item) {
		return true;
	}
	
	/**
	 * Equip item on the given slot
	 * @param	anchorId
	 * 			The id of the anchor to be set
	 * @param	item
	 * 			the item to be equipped
	 * @effect	Unequip the item in that slot if not null
	 * 			| unequip(anchorId)
	 * @post	Equip the given item in the given slot when possible, unequips the item in that slot when not null
	 * 			| canEquipItem(item)
	 */
	public void equip(int anchorId, Item item) {
		
	}
	
	/**
	 * Unequip item on the given slot
	 * @post	Unequip the item on the given slot and tries to put it in a backpack when possible otherwise drops it on the ground
	 */
	public void unequip(int anchorId) {
		
	}
	
	/**
	 * Removes the given item from its anchor when equipped
	 * @param 	item
	 * 			The item to be removed from its anchor
	 * @post	Searches and removes the given item from its anchor
	 */
	protected void removeItemFromHolder(Item item) {
		
	}
	
	/**
	 * Checks whether an item can be picked up
	 * @param 	item
	 * 			The item to be picked up
	 * @return	Returns true when the holder of the item is null or dead
	 * 			| ...
	 */
	public boolean canPickItem(Item item) {
		return true;
	}
	
	/**
	 * Picks an item up from a dead body or from the ground
	 * @param	item
	 * 			The item to be picked up
	 * @post	Picks an item up from a dead body or from the ground when it can be picked up
	 * 			| canPickItem(item)
	 */
	public void pickUp(Item item) {
		
	}
	
	
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * This character hits the given character
	 * @post	This character hits the given character
	 */
	public abstract void hit(Character character);
	
	/**
	 * This character collects the treasures found on a dead body
	 * @post	Collects all anchored items of the other character 
	 * 			when the current character wants to take it
	 * 			| wantsToTakeItem(item)
	 */
	public void collectTreasures(Character character) {
		
	}
	
	/**
	 * Return a boolean whether the character will take this item
	 * @return	Returns true if the character will take this item
	 * 			Returns false when the character won't take this item
	 */
	public abstract boolean wantsToTakeItem();
	
	
}
