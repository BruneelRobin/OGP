package MindCraft;

import java.util.Map;

import be.kuleuven.cs.som.annotate.*;
import sun.security.jca.GetInstance.Instance;

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
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Creates a character
	 * @param	name
	 * 			The name of the new character
	 * @param	hitpoints
	 * 			The max amount of hitpoints
	 * @pre		The given amount of maximum hitpoints must be valid
	 * 			| canHaveAsMaxHitpoints(hitpoints)
	 * @post	Creates a character with the given name
	 * 			| new.getName() == name
	 * @post	Creates a character with the given hitpoints
	 * 			| new.getHitpoints() == hitpoints
	 * @post	Creates a character with the maximum amount of hitpoints
	 * 			| new.getMaximumHitpoints() == hitpoints
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid
	 * 			| !isValidName(name)
	 */
	@Model
	protected Character(String name, int hitpoints) throws IllegalArgumentException {
		
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Invalid name!");
		}
		setName(name);
		setHitpoints(hitpoints);
		
	}
	
	/********************************
	 * Name - defensive programming
	 ********************************/
	
	private String name;
	
	/**
	 * Return true when the name is valid
	 * @return	Return true when the name starts with a capital
	 * 			and only contains letters, spaces and apostrophes
	 * 			| name != null && name.matches("[A-Z][a-z' ]+")
	 */
	public static boolean isValidName(String name) {
		return (name != null && name.matches("[A-Z][A-Za-z' ]*"));
	}
	
	/**
	 * Sets the name to the given name
	 * 
	 * @param 	name
	 * 			the new name
	 * @post	The name is set to the given name
	 * 			| new.getName() == name	
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
	 * 			| new.getName() == name
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public void changeName(String name) {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Invalid name!");
		}
		this.name = name;
	}
	
	/**
	 * Return the character's name
	 * @return	Return the character's name
	 */
	public String getName() {
		return this.name;
	}
	
	/*************************************
	 * Hitpoints - nominal programming
	 *************************************/
	private int hitpoints;
	private boolean isFighting = false;
	private int maxHitpoints;
	
	/**
	 * Returns whether the player can have the given amount of hitpoints
	 * @param 	hitpoints
	 * 			the amount of hitpoints to check
	 * @return	Return true when the character can have the amount of hitpoints
	 * @return	Return false when the character can't have the amount of hitpoints
	 */
	public boolean canHaveAsHitpoints(int hitpoints) {
		return hitpoints >= 0 && (isFighting() || MathHelper.isPrime(hitpoints));
	}
	
	/**
	 * Sets the character's hitpoints to the given hitpoints
	 * @param 	hitpoints
	 * 			the new amount of hitpoints
	 * @pre		The given amount of hitpoints is a valid amount.
	 * 			| canHaveAsHitpoints(hitpoints)
	 * @post	The character's hitpoints are set to the given hitpoints.
	 * 			| new.getHitpoints() == hitpoints
	 */
	protected void setHitpoints(int hitpoints) {
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
	 * 			| new.getHitpoints() == this.getHitpoints() - hitpoints
	 * @post	when the character takes damage, isFighting is set to true
	 * 			| new.isFighting() == true
	 * @pre		The given amount of hitpoints is valid
	 * 			| hitpoints > 0
	 */
	public void takeDamage(int hitpoints) {
		int newValue = getHitpoints()-hitpoints;
		if (newValue <= 0) {
			newValue = 0;
		}
		
		setFighting (true);
		setHitpoints(newValue);
	}
	
	/**
	 * Sets the current fighting state to the given state
	 * @param 	isFighting
	 * 			The new state of isFighting
	 * @post	Sets the current fighting state to the given state
	 * 			| new.isFighting() == isFighting
	 */
	protected void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}
	
	/**
	 * Return the current fighting state of this character
	 * @return	Return the current fighting state of this character
	 */
	public boolean isFighting() {
		return this.isFighting;
	}
	
	
	/**
	 * Return the current life state of this character.
	 * @return	Return the current life state of this character.
	 * 			| getHitpoints == 0
	 */
	public boolean isDead() {
		return getHitpoints() == 0;
	}
	
	/**
	 * Returns whether the player can have the maximum amount of hitpoints
	 * @param 	hitpoints
	 * 			the maximum amount of hitpoints to check
	 * @return	Return true when the character can have the maximum amount of hitpoints
	 * @return	Return false when the character can't have the maximum amount of hitpoints
	 */
	public static boolean isValidMaxHitpoints(int hitpoints) {
		return hitpoints >= 0 && MathHelper.isPrime(hitpoints);
	}
	
	/**
	 * Return the maximum amount of hitpoints
	 * @return	Return the maximum amount of hitpoints
	 */
	@Basic
	public int getMaxHitpoints () {
		return this.maxHitpoints;
	}
	
	/**
	 * Set the maximum amount of hitpoints
	 * @param 	maxHitpoints
	 * 			the maximum amount of hitpoints
	 * @pre		The given maximum amount of hitpoints must be a valid amount.
	 * 			| canHaveAsMaxHitpoints(hitpoints)
	 * @post	Sets the maximum amount of hitpoints to the given value
	 * 			| new.getMaxHitpoints() == maxHitpoints
	 */
	@Raw
	private void setMaxHitpoints (int maxHitpoints) {
		this.maxHitpoints = maxHitpoints;
	}
	
	/**
	 * Increase the maximum amount of hitpoints
	 * @param 	hitpoints
	 * 			the new amount of maximum hitpoints
	 * @pre		the new amount of maximum hitpoints must be valid
	 * 			| canHaveAsMaxHitpoints(this.getMaxHitpoints() + hitpoints)
	 * @post	Increase the amount of maximum hitpoints with the given amount
	 * 			| new.getMaxHitpoints() == this.getMaxHitpoints() + hitpoints
	 */
	public void increaseMaxHitpoints (int hitpoints) {
		this.maxHitpoints = this.maxHitpoints + hitpoints;
	}
	
	/**
	 * Lowers the maximum amount of hitpoints
	 * @param 	hitpoints
	 * 			the new amount of maximum hitpoints
	 * @pre		the new amount of maximum hitpoints must be valid
	 * 			| canHaveAsMaxHitpoints(this.getMaxHitpoints() - hitpoints)
	 * @post	Lower the amount of maximum hitpoints with the given amount
	 * 			| new.getMaxHitpoints() == this.getMaxHitpoints() - hitpoints
	 * @post	When the new amount of maximum hitpoints is lower than the current value of hitpoints.
	 * 			Then the current value of hitpoints is set to the maximum amount of hitpoints.
	 */
	public void lowerMaxHitpoints (int hitpoints) {
		this.maxHitpoints = this.maxHitpoints - hitpoints;
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
	private final HashMap<Integer, Item> anchors = new HashMap<Integer, Item>();
	
	/**
	 * Set the item at the given anchorId
	 * @param 	anchorId
	 * 			The anchorId to set the item at
	 * @param 	item
	 * 			The item to set at the given anchorId
	 */
	private void setAnchorAt(int anchorId, Item item) {
		this.anchors.put(anchorId, item);
	}
	
	/**
	 * Return the item at the given anchorId
	 * @param 	anchorId
	 * 			The anchorId of the item
	 * @return	Return the item at the given anchorId
	 */
	public Item getAnchorAt (int anchorId) {
		return this.anchors.get(anchorId);
	}
	
	/**
	 * Return true when the given item can be equipped
	 * @param 	item
	 * 			the item to be checked
	 * @return	Return true when the given item can be equipped
	 * 			| ...
	 */
	public boolean canEquipItem(int anchorId, Item item) {
		if ((item.getHolder() == this || item.getHolder() == null) && this.getAnchorAt(anchorId) == null){
			return true;
		} else {
			return false;
		}
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
		if (this.canEquipItem(anchorId, item)) {
			if (this.getAnchorAt(anchorId) != null) {
				this.unequip(anchorId);
			}
			this.setAnchorAt(anchorId, item);
		}
	}
	
	/**
	 * Unequip item on the given slot.
	 * @post	Unequip the item on the given slot and tries to put it in a backpack
	 * 			when possible otherwise drops it on the ground.
	 */
	public void unequip(int anchorId) {
		
		Item item = this.getAnchorAt(anchorId);
		
		for (Map.Entry<Integer, Item> entry : this.anchors.entrySet()) {
		    int key = entry.getKey();
		    Item value = entry.getValue();
		    		
		    if (value instanceof Backpack && key != anchorId) {
		    	Backpack backpack = (Backpack) value;
		    	
				if (backpack.getTotalWeight() + item.getWeight() <= backpack.getCapacity()) {
					item.moveTo(backpack);
					return;
				}
			}
		} 
		item.drop();
	}
	
	/**
	 * Removes the given item from its anchor when equipped
	 * @param 	item
	 * 			The item to be removed from its anchor
	 * @post	Searches and removes the given item from its anchor
	 */
	protected void removeItemFromHolder(Item item) {
		for (Map.Entry<Integer, Item> entry : this.anchors.entrySet()) {
		    int key = entry.getKey();
		    Item value = entry.getValue();
		    if (value == item) {
		    	this.setAnchorAt(key,null);
		    	return;
		    }
		}
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
	 * @pre		The character must be able te pick up the item.
	 * 			| canPickItem(item)
	 * @post	Picks an item up from a dead body or from the ground.
	 */
	public void pickUp(Item item) {
		
	}
	
	/**
	 * Return the total value of this character
	 * @return	Return the total value of this character in ducates
	 */
	public int getTotalValue () {
		return 0;
	}
	
	/***********************
	 * Capacity
	 ***********************/
	
	/**
	 * Return the character's capacity
	 * @return Return the character's capacity
	 */
	public abstract float getCapacity();
	
	
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
	 * Return a boolean whether the character wants to take this item
	 * @return	Returns true if the character wants to take this item
	 * 			Returns false when the character doesn't want to take this item
	 */
	public abstract boolean wantsToTakeItem(Item item);
}
