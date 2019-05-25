package qahramon;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.DeadException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * An abstract class of characters involving a name, an amount of hitpoints, a maximum
 * amount of hitpoints and anchors.
 * 
 * @invar 	Each character must have proper items anchored.
 *        	| hasProperItems()
 * @invar	Each character must have a valid name.
 * 			| canHaveAsName(getName())
 * @invar	Each character must have a valid amount of hitpoints.
 * 			| canHaveAsHitpoints(getHitpoints())
 * @invar	Each character must have a valid maximum amount of hitpoints.
 * 			| isValidMaxHitpoints(getMaxHitpoints())
 * @invar	Each character must have a valid number of anchors.
 * 			| isValidNumberOfAnchors(getNumberOfAnchors())
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */

// TODO annotations?, monster collectTreasures in testen
//		UML, alle coding rules

public abstract class Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a character with a given name, amount of hitpoints
	 * and a number of anchors.
	 * 
	 * @param	name
	 * 			the name of the new character
	 * @param	hitpoints
	 * 			the max amount of hitpoints of the new character
	 * @param	numberOfAnchors
	 * 			the number of anchors of the new character
	 * @pre		The given amount of maximum hitpoints must be valid.
	 * 			| isValidMaxHitpoints(hitpoints)
	 * @pre		The given number of anchors must be valid.
	 * 			| isValidNumberOfAnchors(numberOfAnchors)
	 * @post	The name of this character is set to the given name.
	 * 			| new.getName() == name
	 * @post	The amount of hitpoints of this character is set to the given hitpoints.
	 * 			| new.getHitpoints() == hitpoints
	 * @post	The maximum amount of hitpoints of this character is set to the given hitpoints.
	 * 			| new.getMaxHitpoints() == hitpoints
	 * @post	The number of anchors of this character is set to the given number of anchors.
	 * 			| new.getNumberOfAnchors() == numberOfAnchors
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid.
	 * 			| !isValidName(name)
	 */
	@Model
	protected Character(String name, int hitpoints, int numberOfAnchors) throws IllegalArgumentException {
		
		if (!canHaveAsName(name)) {
			throw new IllegalArgumentException("Invalid name!");
		}
		setName(name);
		setHitpoints(hitpoints);
		setMaxHitpoints(hitpoints);
		this.numberOfAnchors = numberOfAnchors;
	}
	
	/********************************
	 * Name - defensive programming
	 ********************************/
	
	/**
	 * Variable referencing the name of the character.
	 */
	private String name;
	
	/**
	 * Return the character's name.
	 * 
	 * @return	Return the character's name.
	 */
	@Basic
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name to the given name.
	 * 
	 * @param 	name
	 * 			the new name
	 * @post	The name is set to the given name.
	 * 			| new.getName() == name	
	 */
	@Raw
	protected void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Check whether the name is valid.
	 * 
	 * @return	Return true when the name starts with a capital
	 * 			and only contains letters, spaces and apostrophes.
	 * 			| name != null && name.matches("[A-Z][a-z' ]+")
	 */
	@Raw
	public boolean canHaveAsName(String name) {
		return (name != null && name.matches("[A-Z][A-Za-z' ]*"));
	}
	
	/**
	 * Change the name to the given name when valid.
	 * 
	 * @param 	name
	 * 			the new name
	 * @post	The name is set to the given name.
	 * 			| new.getName() == name
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given name is not valid.
	 * 			| !isValidName(name)
	 */
	public void changeName(String name) throws IllegalArgumentException {
		if (!canHaveAsName(name)) {
			throw new IllegalArgumentException("Invalid name!");
		}
		this.name = name;
	}
	
	/*************************************
	 * Hitpoints - nominal programming
	 *************************************/
	
	/**
	 * Variable referencing the amount of hitpoints of the character.
	 */
	private int hitpoints;
	
	/**
	 * Variable referencing the fighting state of the character.
	 */
	private boolean isFighting = false;
	
	/**
	 * Variable referencing the maximum amount of hitpoints of the character.
	 */
	private int maxHitpoints;
	
	/**
	 * Return the current amount of hitpoints.
	 * 
	 * @return	Return the current amount of hitpoints.
	 */
	@Basic
	public int getHitpoints() {
		return this.hitpoints;
	}
	
	/**
	 * Set the character's hitpoints to the given hitpoints.
	 * 
	 * @param 	hitpoints
	 * 			the new amount of hitpoints
	 * @pre		The given amount of hitpoints is a valid amount.
	 * 			| canHaveAsHitpoints(hitpoints)
	 * @post	The character's hitpoints are set to the given hitpoints.
	 * 			| new.getHitpoints() == hitpoints
	 */
	@Raw
	protected void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}
	
	/**
	 * Check whether the player can have the given amount of hitpoints.
	 * 
	 * @param 	hitpoints
	 * 			the amount of hitpoints to check
	 * @return	Return true when the character can have the amount of hitpoints.
	 * @return	Return false when the character can't have the amount of hitpoints.
	 */
	@Raw
	public boolean canHaveAsHitpoints(int hitpoints) {
		return hitpoints >= 0 && (isFighting() || MathHelper.isPrime(hitpoints));
	}
	
	/**
	 * Remove hitpoints from the character.
	 * 
	 * @param	hitpoints
	 * 			the amount of hitpoints to be taken
	 * @pre		The given amount of hitpoints must be a positive amount.
	 * 			| hitpoints >= 0
	 * @post	Remove the given amount of hitpoints or sets it to zero when
	 * 			the new amount of hitpoints would be negative.
	 * 			| new.getHitpoints() == Math.max(0, getHitpoints()-hitpoints)
	 * @post	when the character takes damage, isFighting is set to true.
	 * 			| new.isFighting()
	 */
	public void takeDamage(int hitpoints) {
		int newValue = Math.max(0, getHitpoints()-hitpoints);
		
		setFighting (true);
		setHitpoints(newValue);
	}
	
	/**
	 * Return the maximum amount of hitpoints.
	 * 
	 * @return	Return the maximum amount of hitpoints.
	 */
	@Basic
	public int getMaxHitpoints () {
		return this.maxHitpoints;
	}
	
	/**
	 * Set the maximum amount of hitpoints.
	 * 
	 * @param 	maxHitpoints
	 * 			the maximum amount of hitpoints
	 * @pre		The given maximum amount of hitpoints must be a valid amount.
	 * 			| canHaveAsMaxHitpoints(hitpoints)
	 * @post	Sets the maximum amount of hitpoints to the given value.
	 * 			| new.getMaxHitpoints() == maxHitpoints
	 */
	@Raw
	protected void setMaxHitpoints (int maxHitpoints) {
		this.maxHitpoints = maxHitpoints;
	}
	
	/**
	 * Check whether the player can have the maximum amount of hitpoints.
	 * 
	 * @param 	hitpoints
	 * 			the maximum amount of hitpoints to check
	 * @return	Return true when the character can have the maximum amount of hitpoints.
	 * 			Return false otherwise.
	 * 			| hitpoints > 0 && MathHelper.isPrime(hitpoints)
	 */
	public static boolean isValidMaxHitpoints(int hitpoints) {
		return hitpoints > 0 && MathHelper.isPrime(hitpoints);
	}
	
	/**
	 * Increase the maximum amount of hitpoints.
	 * 
	 * @param 	hitpoints
	 * 			the new amount of maximum hitpoints
	 * @pre		The given amount of hitpoints must be a positive amount.
	 * 			| hitpoints >= 0
	 * @pre		The new amount of maximum hitpoints must be valid.
	 * 			| canHaveAsMaxHitpoints(this.getMaxHitpoints() + hitpoints)
	 * @post	Increase the amount of maximum hitpoints with the given amount.
	 * 			| new.getMaxHitpoints() == this.getMaxHitpoints() + hitpoints
	 */
	public void increaseMaxHitpoints (int hitpoints) {
		setMaxHitpoints(getMaxHitpoints() + hitpoints);
	}
	
	/**
	 * Lower the maximum amount of hitpoints.
	 * 
	 * @param 	hitpoints
	 * 			the new amount of maximum hitpoints
	 * @pre		The given amount of hitpoints must be a positive amount.
	 * 			| hitpoints >= 0
	 * @pre		the new amount of maximum hitpoints must be valid
	 * 			| canHaveAsMaxHitpoints(this.getMaxHitpoints() - hitpoints)
	 * @post	Lower the amount of maximum hitpoints with the given amount.
	 * 			| new.getMaxHitpoints() == this.getMaxHitpoints() - hitpoints
	 * @post	When the new amount of maximum hitpoints is lower than the current value
	 * 			of hitpoints, the current value of hitpoints is set to the maximum amount 
	 * 			of hitpoints.
	 */
	public void lowerMaxHitpoints (int hitpoints) {
		setMaxHitpoints(getMaxHitpoints() - hitpoints);
		if (getHitpoints() > getMaxHitpoints()) {
			setHitpoints(getMaxHitpoints());
		}
	}
	
	
	/**
	 * Return the current fighting state of this character.
	 * 
	 * @return	Return the current fighting state of this character.
	 */
	@Basic
	public boolean isFighting() {
		return this.isFighting;
	}
	
	/**
	 * Set the current fighting state to the given state.
	 * 
	 * @param 	isFighting
	 * 			The new state of isFighting
	 * @post	Set the current fighting state to the given state.
	 * 			| new.isFighting() == isFighting
	 */
	@Raw
	protected void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}
	
	
	/**
	 * Check whether this character is dead.
	 * 
	 * @return	Return true when this character is dead.
	 * 			| getHitpoints() == 0
	 */
	@Raw
	public boolean isDead() {
		return getHitpoints() == 0;
	}
	
	/***********************
	 * Protection
	 ***********************/
	
	/**
	 * Return the protection of the character.
	 * 
	 * @return	Return the protection of the current character.
	 */
	public abstract int getProtection();
	
	/***********************
	 * Damage
	 ***********************/
	
	/**
	 * Return the damage of the character.
	 * 
	 * @return	Return the damage of the current character.
	 */
	public abstract int getDamage();
	
	
	/***********************
	 * Anchors
	 ***********************/
	
	/**
	 * Variable referencing the maximum number of anchors of this character.
	 */
	private final int numberOfAnchors;
	
	/**
	 * Variable referencing a dictionary of all anchored items of this character. 
	 * This class has a bidirectional relation with the class Item. An item can be 
	 * anchored using the function equip/unequip or the protected function
	 * removeItemFromHolder.
	 * 
	 * @invar	The dictionary must be effective.
	 * 			| anchors != null
	 * @invar 	Each non null element in the hashmap references an effective item. 
	 *        	| for (HashMap.Entry<Integer,Item> entry : anchors.entrySet())
	 *        	| 	entry.getValue() != null
	 * @invar 	Each element in the hashmap references an item that references
	 *        	back to this character.
	 *        	| for (HashMap.Entry<Integer,Item> entry : anchors.entrySet())
	 *        	| 	entry.getValue().getCharacter() == this
	 */	
	private final HashMap<Integer, Item> anchors = new HashMap<Integer, Item>();
	
	/**
	 * Return the item at the given anchorId.
	 * 
	 * @param 	anchorId
	 * 			the anchorId of the item
	 * @return	Return the item at the given anchorId.
	 * 			| result == this.anchors.get(anchorId)
	 */
	@Basic
	public Item getItemAt (int anchorId) {
		return this.anchors.get(anchorId);
	}
	
	/**
	 * Return the number of anchored items.
	 * 
	 * @return 	Return the number of anchored items.
	 * 			| result == getAnchorEntrySet().size()
	 */
	@Basic
	public int getNbItems () {
		return getAnchorEntrySet().size();
	}
	
	/**
	 * Set the item at the given anchorId.
	 * 
	 * @param 	anchorId
	 * 			the anchorId to set the item at
	 * @param 	item
	 * 			the item to set at the given anchorId
	 * @post	Remove the entry when item is null.
	 * 			| this.anchors.remove(anchorId)	
	 * @post	Set the item at the given anchor of this character when the item is not null.
	 * 			| this.anchors.put(anchorId, item)
	 */
	@Raw
	protected void setItemAt(int anchorId, Item item) {
		if (item == null) {
			this.anchors.remove(anchorId);
		} else {
			this.anchors.put(anchorId, item);
		}
	}
	
	/**
	 * Check whether the given item can be equipped in the given slot.
	 * 
	 * @param 	item
	 * 			the item to be checked
	 * @return	Return false when this item is terminated.
	 * 			Return false when the anchor id is lower than 0 and higher 
	 * 			than the maximum anchors allowed.
	 * 			Return false when the given item can't have this character
	 * 			Return true when the item is owned by this player or the 
	 * 			item is on the ground and able to be picked up.
	 * 			Return false otherwise.
	 * 			| result == (anchorId >= 0 && anchorId < getNumberOfAnchors()) && (item.getHolder == this)
	 * 			|			&& (item.canHaveAsCharacter(this))
	 * 			|			|| (item.getHolder() == null && canPickUp(item)))
	 */
	@Raw
	public boolean canHaveAsItemAt(int anchorId, Item item) {
		if (item.isTerminated()) {
			return false;
		}
		else if (anchorId < 0 || anchorId >= getNumberOfAnchors()) {
			return false;
		} else if (!item.canHaveAsCharacter(this)) {
			return false;
		}
		
		if ((item.getHolder() == this || ((item.getHolder() == null || item.getHolder().isDead()) && canPickUp(item)))){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check whether this character has the given item anchored.
	 * 
	 * @return	Return true when this character has the given item anchored.
	 * 			Return false otherwise.
	 */
	public boolean hasItem (Item item) {
		for (Item itemAt : this.anchors.values()) {
			if (itemAt == item) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Equip item from own inventory or from ground, on the given slot.
	 * 
	 * @param	anchorId
	 * 			the id of the anchor to be set
	 * @param	item
	 * 			the item to be equipped
	 * @post	When this item cannot be equipped, nothing happens (total programming).
	 * 			| !(canHaveAsItemAt())
	 * @effect	Unequip the item in that slot if not null.
	 * 			| unequip(anchorId)
	 * @post	When equipped the item at the given id will be set to the given item.
	 * 			| getItemAt(anchorId) == item
	 * @effect	When equipped the anchor of the current item will bound to this character.
	 * 			(so bidirectional relations are recreated).
	 * 			| item.bindCharacter(this)
	 * @throws	DeadException
	 * 			throws this exception when the current character is dead.
	 */
	public void equip(int anchorId, Item item) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		if (this.canHaveAsItemAt(anchorId, item)) {
			if (this.getItemAt(anchorId) != null) {
				this.unequip(anchorId);
			}
			item.bindCharacter(this);
			this.setItemAt(anchorId, item);
		}
	}
	
	/**
	 * Unequip item on the given slot.
	 * 
	 * @param	anchorId
	 * 			the anchorId of the item to be unequipped
	 * @post	Unequip the item on the given slot and tries to put it in a backpack
	 * 			when possible otherwise drops it on the ground.
	 * 			| !(new.hasItem(this.getItemAt(anchorId)))
	 * @effect	When possible the item will be moved into that backpack.
	 * 			| item.moveTo(backpack)
	 * @effect	When not put in a backpack the item will be dropped on the ground.
	 * 			| item.drop();
	 * @throws	DeadException
	 * 			throws this exception when the current character is dead.
	 * 			| isDead()
	 */
	public void unequip(int anchorId) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		Item item = this.getItemAt(anchorId);
		if(item == null) {
			return;
		}
		
		for (Map.Entry<Integer, Item> entry : this.anchors.entrySet()) {
		    int key = entry.getKey();
		    Item value = entry.getValue();
		    		
		    if (value.isBackpack() && key != anchorId) {
		    	Backpack backpack = (Backpack) value;
		    	
				if (backpack.canHaveAsItem(item)) {
					item.moveTo(backpack);
					return;
				}
			}
		} 
		item.drop();
	}
	
	/**
	 * Remove the given item from its anchor when equipped.
	 * 
	 * @param 	item
	 * 			The item to be removed from its anchor
	 * @post	Search and remove the given item from its anchor.
	 * 
	 * @note	Break the association with item unidirectionally.
	 */
	@Raw
	protected void removeItemFromHolder(Item item) {
		for (Entry<Integer, Item> entry : this.anchors.entrySet()) {
		    int key = entry.getKey();
		    Item value = entry.getValue();
		    if (value == item) {
		    	this.setItemAt(key,null);
		    	return;
		    }
		}
	}
	
	/**
	 * Check whether an item can be picked up.
	 * 
	 * @param 	item
	 * 			the item to be picked up
	 * @return	Return false when the holder of the item is not null and not dead.
	 * 			Return false when the new weight of this character will exceed 
	 * 			the capacity of this character.
	 * 			Return false when the item is terminated.
	 * 			Return false when the item is already owned by this character.
	 * 			Return true otherwise.
	 * 			| result == !(item.isTerminated()) && !(item.getHolder() == this)
	 * 						&& !(item.getHolder() != null && !item.getHolder().isDead())
	 * 						&& !(this.getCapacity() < this.getTotalWeight() + totalWeightOfItem)
	 */
	@Raw
	public boolean canPickUp(Item item) {
		float totalWeightOfItem;
		if(item.isContainer()) {
			totalWeightOfItem = ((Container)(item)).getTotalWeight();
			
			}
		else {
			totalWeightOfItem = item.getWeight();
		}
		
		if (item.isTerminated()) {
			return false;
		}
		else if(item.getHolder() != null && !item.getHolder().isDead()) {
			return false;
		}
		else if(item.getHolder() == this) {
			return false;
		}
		else if(this.getCapacity() < this.getTotalWeight() + totalWeightOfItem) {
			return false;
			
		} else {
		return true;
		}
	}
	
	/**
	 * Pick up an item from a dead body or from the ground.
	 * 
	 * @param	item
	 * 			the item to be picked up
	 * @post	If this item can not be picked up, nothings happens.
	 * 			| !canPickUp(item)
	 * @effect	Otherwise all anchors are checked, if an empty anchor
	 * 			is found and the item can be equipped, the item is equipped there.
	 * 			| this.equip(anchorId, item)
	 * @effect	If there are no available anchors and there is an anchored backpack
	 * 			that can take this item, then the item will be put in that backpack.
	 * 			| item.moveTo(backpack)
	 * @throws	DeadException
	 * 			throws this exception when the current character is dead.			
	 */
	public void pickUp(Item item) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		if(canPickUp(item)) {
			Set <Backpack> backpacks = new HashSet <Backpack>();
			for(int anchorId = 0; anchorId < this.getNumberOfAnchors(); anchorId ++) {
				Item itemAt = this.getItemAt(anchorId);
				if(itemAt != null && itemAt.isBackpack()) {
					backpacks.add((Backpack)(itemAt));
				}
				else if(itemAt == null && this.canHaveAsItemAt(anchorId, item)) {
					this.equip(anchorId, item);
					return;
				}
			}
			for(Backpack backpack : backpacks) {
				if(backpack.canHaveAsItem(item)) {
					item.moveTo(backpack);
				}
			}
		}
		
	}
	
	/**
	 * Return an entry set with all attached anchors.
	 * 
	 * @return	Return an entry set with all attached anchors.
	 */
	@Raw
	public Set<Entry<Integer, Item>> getAnchorEntrySet () {
		return this.anchors.entrySet();
	}
	
	/**
	 * Return a set with all items anchors attached.
	 * 
	 * @return	Return a set with all items anchors attached.
	 */
	@Raw
	public Set<Item> getAnchoredItems () {
		Set<Item> set = new HashSet<Item>();
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			set.add(entry.getValue());
		}
		return set;
	}
	
	private final int numberOfAnchors;
	
	/**
	 * Return the number of anchors of this character.
	 * 
	 * @return	Return the number of anchors of this character.
	 */
	@Immutable@Basic
	public int getNumberOfAnchors() {
		return this.numberOfAnchors;
	}
	
	/**
	 * Check whether the given number of anchors is valid.
	 * 
	 * @return	Return true when the given number of anchors is positive.
	 * 			Return false otherwise.
	 * 			| numberOfAnchors >= 0
	 */
	public static boolean isValidNumberOfAnchors (int numberOfAnchors) {
		return numberOfAnchors >= 0;
	}
	
	/**
	 * Check whether this character has proper items equipped.
	 * 
	 * @return	Return true when this item has proper items equipped.
	 * 			Return false when this item doesn't have proper items equipped.
	 */
	@Raw
	public boolean hasProperItems () {
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			if (!canHaveAsItemAt(entry.getKey(), entry.getValue()) || entry.getValue().getCharacter() != this) { // false when not possible to reequip in same slot
				return false;
			}
		}
		return true;
	}
	
	/***********************
	 * Capacity
	 ***********************/
	
	/**
	 * Return the character's capacity.
	 * 
	 * @return Return the character's capacity.
	 */
	public abstract float getCapacity();
	
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * This character hits the given character.
	 * @param	character
	 * 			the character to hit
	 * @post	This character hits the given character. The new amount of hitpoints
	 * 			of the character is lower or equal than before.
	 * @throws	DeadException
	 * 			throws this exception when the current character is dead.
	 * 			| isDead()
	 */
	public abstract void hit(Character character);
	
	/**
	 * This character collects the treasures it wants to take found on a dead character.
	 * @param	character
	 * 			the backpack to look check
	 * @effect	All items anchored onto the given dead character will be collected when possible
	 * 			| for (Item item : character.getAnchoredItems()) do
	 *			|		collectTreasure(item);
	 * @post	Does nothing when the given character is not dead
	 * 			| !character.isDead()
	 * @throws	DeadException
	 * 			throws this exception when the current character is dead.
	 * 			| isDead()
	 */
	public void collectTreasures(Character character) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		if (character.isDead()) {
		
			Set<Item> set = character.getAnchoredItems();
			for (Item item : set) {
				collectTreasure(item);
			}
		}
	}
	
	/**
	 * This character collects the treasures it wants to take found in a backpack.
	 * @param	backpack
	 * 			the backpack to look into
	 * @effect	All items in this backpack will be collected when possible
	 * 			| for (Item item : backpack.getItems()) do
	 *			|		collectTreasure(item);
	 * @post	Does nothing when the holder of this backpack is not dead
	 * 			| !backpack.getHolder().isDead()
	 * @throws	DeadException
	 * 			throws this exception when the current character is dead.
	 * 			| isDead()
	 */
	public void collectTreasures(Backpack backpack) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		if (backpack.getHolder().isDead()) {
			Set<Item> set = backpack.getItems();
			for (Item item : set) {
				collectTreasure(item);
			}
		}
	}
	
	/**
	 * Pick up the item when this character wants to take it
	 * @param 	item
	 * 			the item to check and pick up
	 * @post	Pick up the item when this character wants to take it.
	 */
	public abstract void collectTreasure(Item item);
	
	/**
	 * Check whether the character wants to take this item.
	 * 
	 * @return	Return true if the character wants to take this item.
	 * 			Return false when the character doesn't want to take this item.
	 */
	public abstract boolean wantsToTake(Item item);
	
	/**
	 * Return the total value of this character.
	 * 
	 * @return	Return the total value of this character in ducates calculated as the sum of the 
	 * 			total values of each anchored item.
	 */
	@Raw
	public int getTotalValue () {
		int value = 0;
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			Item item = entry.getValue();
			
			if (item.isContainer()) {
				value += ((Container) item).getTotalValue();
			} else {
				value += item.getValue();
			}
		}
		return value;
	}
	
	/**
	 * Return the total weight of this character.
	 * 
	 * @return	Return the total weight of this character in kilograms calculated as the sum of the
	 * 			total weights of each anchored item.
	 */
	@Raw
	public float getTotalWeight () {
		float weight = 0;
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			Item item = entry.getValue();
			
			if (item.isContainer()) {
				weight += ((Container) item).getTotalWeight();
			} else {
				weight += item.getWeight();
			}
		}
		return weight;
	}
	
	/**
	 * Return the best armor of any owned armor.
	 * 
	 * @return	Return the best armor of any owned armor.
	 * 		   
	 * @note	The evaluation of the armor is based on its full protection.
	 */
	public Armor getBestArmor() {
		Set<Entry<Integer, Item>> set = this.getAnchorEntrySet();
		int bestFullProtection = 0;
		Armor bestArmor = null;
		for (Entry<Integer, Item> entry : set) {
			Item item = entry.getValue();
			if (item.isArmor()) {
				Armor armor = (Armor) item;
				int armorProtection = armor.getFullProtection();
				if (armorProtection > bestFullProtection) {
					bestFullProtection = armorProtection;
					bestArmor = armor;
				}
			} else if (item.isBackpack()) {
				Backpack backpack = (Backpack) item;
				Armor bestBackpackArmor = backpack.getBestArmor();
				if (bestBackpackArmor != null && bestBackpackArmor.getFullProtection() > bestFullProtection) {
					bestFullProtection = bestBackpackArmor.getFullProtection();
					bestArmor = bestBackpackArmor;
				}
			}
		}
		return bestArmor;
	}
	
	/**
	 * Return the best armor of any owned weapon.
	 * 
	 * @return	Return the best armor of any owned armor.
	 * 		   
	 * @note	The evaluation of the weapon is based on its damage.
	 */
	public Weapon getBestWeapon() {
		Set<Entry<Integer, Item>> set = this.getAnchorEntrySet();
		int bestDamage = 0;
		Weapon bestWeapon = null;
		for (Entry<Integer, Item> entry : set) {
			Item item = entry.getValue();
			if (item.isWeapon()) {
				Weapon weapon = (Weapon) item;
				int weaponDamage = weapon.getDamage();
				if (weaponDamage > bestDamage) {
					bestDamage = weaponDamage;
					bestWeapon = weapon;
				}
			} else if (item.isBackpack()) {
				Backpack backpack = (Backpack) item;
				Weapon bestBackpackWeapon = backpack.getBestWeapon();
				if (bestBackpackWeapon != null && bestBackpackWeapon.getDamage() > bestDamage) {
					bestDamage = bestBackpackWeapon.getDamage();
					bestWeapon = bestBackpackWeapon;
				}
			}
		}
		return bestWeapon;
	}
	
	/**
	 * Return the best backpack of any owned armor.
	 * 
	 * @return	Return the best backpack of any owned armor.
	 * 		   
	 * @note	The evaluation of the backpack is based on its capacity.
	 */
	public Backpack getBestBackpack() {
		Set<Entry<Integer, Item>> set = this.getAnchorEntrySet();
		float bestCapacity = 0;
		Backpack bestBackpack = null;
		for (Entry<Integer, Item> entry : set) {
			Item item = entry.getValue();
			if (item.isBackpack()) {
				Backpack backpack = (Backpack) item;
				float backpackCapacity = backpack.getCapacity();
				if (backpackCapacity > bestCapacity) {
					bestCapacity = backpackCapacity;
					bestBackpack = backpack;
				}
			} else if (item.isBackpack()) {
				Backpack backpack = (Backpack) item;
				Backpack bestBackpackBackpack = backpack.getBestBackpack();
				if (bestBackpackBackpack != null && bestBackpackBackpack.getCapacity() > bestCapacity) {
					bestCapacity = bestBackpackBackpack.getCapacity();
					bestBackpack = bestBackpackBackpack;
				}
			}
		}
		return bestBackpack;
	}
	
	/**
	 * Return a string containing general data over this string.
	 * 
	 * @return	Return a string containing data over its identification, value and weight.
	 */
	protected String getString() {
		return "Name: " + getName() 
			+ "\nHitpoints: " + getHitpoints() 
			+ "\nMaxHitpoints: " + getMaxHitpoints() 
			+ "\nNumber of anchors: " + getNumberOfAnchors()
			+ "\nEquipped items: " + getNbItems()
			+ "\nCapacity: " + getCapacity() 
			+ "\nDamage: " + getDamage() 
			+ "\nProtection: " + getProtection()
			+ "\nTotal value: " + getTotalValue()
			+ " ducates\nTotal weight: " + getTotalWeight() + " kg";
	}
}
