package qahramon;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.DeadException;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A class of characters.
 * @invar 	Each character must have proper items anchored.
 *        	| hasProperItems()
 * @invar	Each character must have a valid name.
 * 			| canHaveAsName(getName())
 * @invar	Each character must have a valid amount of hitpoints
 * 			| canHaveAsHitpoints(getHitpoints())
 * @invar	Each character must have a valid maximum amount of hitpoints
 * 			| isValidMaxHitpoints(getMaxHitpoints())
 * @invar	Each character must have a valid number of anchors
 * 			| isValidNumberOfAnchors(getNumberOfAnchors())
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */

// TODO Purse test case
//		annotations, monster collectTreasures in testen
//		hero.collectTreasures
//		UML

public abstract class Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a character with a given name, amount of hitpoints
	 * and a number of anchors.
	 * @param	name
	 * 			The name of the new character
	 * @param	hitpoints
	 * 			The max amount of hitpoints
	 * @pre		The given amount of maximum hitpoints must be valid.
	 * 			| canHaveAsMaxHitpoints(hitpoints)
	 * @pre		The given number of anchors must be valid.
	 * 			| isValidNumberOfAnchors(numberOfAnchors)
	 * @post	The name of this character is set to the given name.
	 * 			| new.getName() == name
	 * @post	The amount of hitpoints of this character is set to the given hitpoints.
	 * 			| new.getHitpoints() == hitpoints
	 * @post	The maximum amount of hitpoints of this character is set to the given hitpoints.
	 * 			| new.getMaxHitpoints() == hitpoints
	 * @post	The number of anchors of this character is set to the given number of anchors.
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
	
	private String name;
	
	/**
	 * Return true when the name is valid
	 * @return	Return true when the name starts with a capital
	 * 			and only contains letters, spaces and apostrophes
	 * 			| name != null && name.matches("[A-Z][a-z' ]+")
	 */
	@Raw
	public boolean canHaveAsName(String name) {
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
		if (!canHaveAsName(name)) {
			throw new IllegalArgumentException("Invalid name!");
		}
		this.name = name;
	}
	
	/**
	 * Return the character's name
	 * @return	Return the character's name
	 */
	@Basic
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
	@Raw
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
	@Raw
	protected void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}
	
	/**
	 * Return the current amount of hitpoints
	 * @return	Return the current amount of hitpoints
	 */
	@Basic
	public int getHitpoints() {
		return this.hitpoints;
	}
	
	/**
	 * removes hitpoints from the character
	 * @param	hitpoints
	 * 			The amount of hitpoints to be taken
	 * @pre		The given amount of hitpoints must be a positive amount.
	 * 			| hitpoints >= 0
	 * @post	Removes the given amount of hitpoints or sets it to zero when the new amount of hitpoints would be negative
	 * 			| new.getHitpoints() == Math.max(0, getHitpoints()-hitpoints)
	 * @post	when the character takes damage, isFighting is set to true
	 * 			| new.isFighting() == true
	 */
	public void takeDamage(int hitpoints) {
		int newValue = Math.max(0, getHitpoints()-hitpoints);
		
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
	@Raw
	protected void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}
	
	/**
	 * Return the current fighting state of this character
	 * @return	Return the current fighting state of this character
	 */
	@Basic
	public boolean isFighting() {
		return this.isFighting;
	}
	
	
	/**
	 * Return the current life state of this character.
	 * @return	Return the current life state of this character.
	 * 			| getHitpoints == 0
	 */
	@Raw
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
		return hitpoints > 0 && MathHelper.isPrime(hitpoints);
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
	 * @pre		The given amount of hitpoints must be a positive amount.
	 * 			| hitpoints >= 0
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
	 * @pre		The given amount of hitpoints must be a positive amount.
	 * 			| hitpoints >= 0
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
	 * Damage
	 ***********************/
	
	/**
	 * Return the damage of the character
	 * @return	Return the damage of the current character
	 */
	public abstract int getDamage();
	
	
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
	 * @post	Sets the item at the given anchor of this character
	 */
	@Raw
	private void setItemAt(int anchorId, Item item) {
		if (item == null) {
			this.anchors.remove(anchorId);
		} else {
			this.anchors.put(anchorId, item);
		}
	}
	
	/**
	 * Return the item at the given anchorId
	 * @param 	anchorId
	 * 			The anchorId of the item
	 * @return	Return the item at the given anchorId
	 */
	@Basic
	public Item getItemAt (int anchorId) {
		return this.anchors.get(anchorId);
	}
	
	/**
	 * Return true when the given item can be equipped in the given slot
	 * @param 	item
	 * 			the item to be checked
	 * @return	Return false when this item is terminated
	 * 			Return false when the anchor id is lower than 0 and higher than the maximum anchors allowed
	 * 			Return false when the given item can't have this character
	 * 			Return true when the item is owned by this player or the item is on the ground and able to
	 * 			be picked up.
	 * 			Return false otherwise
	 * 			| result == (anchorId >= 0 && anchorId < getNumberOfAnchors()) && (item.getHolder == this
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
	 * Return true when this character has the given item anchored
	 * @return	Return true when this character has the given item anchored
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
	 * Equip item from own inventory or from ground, on the given slot
	 * @param	anchorId
	 * 			The id of the anchor to be set
	 * @param	item
	 * 			the item to be equipped
	 * @effect	Unequip the item in that slot if not null
	 * 			| unequip(anchorId)
	 * @post	Equip the given item in the given slot when possible, unequips the item in that slot when not null
	 * 			| canHaveAsItemAt(item)
	 * @post	When equipped the item at the given id will be set to the given item
	 * 			| getItemAt(anchorId) == item
	 * @effect	When equipped the anchor of the current item will bound to this character 
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
			this.setItemAt(anchorId, item);
			item.bindCharacter(this);
		}
	}
	
	/**
	 * Unequip item on the given slot.
	 * @post	Unequip the item on the given slot and tries to put it in a backpack
	 * 			when possible otherwise drops it on the ground.
	 * @effect	When put in a backpack the item will be moved into that backpack
	 * 			| item.moveTo(backpack)
	 * @effect	When not put in a backpack the item will be dropped on the ground
	 * 			| item.drop();
	 * @throws	DeadException
	 * 			throws this exception when the current character is dead.
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
	 * Removes the given item from its anchor when equipped
	 * @param 	item
	 * 			The item to be removed from its anchor
	 * @post	Searches and removes the given item from its anchor
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
	 * Checks whether an item can be picked up
	 * @param 	item
	 * 			The item to be picked up
	 * @return	Return false when the holder of the item is not null and not dead
	 * 			Return false when the new weight of this character will exceed the capacity of this character
	 * 			Return true otherwise
	 * 			| result == !(item.getHolder() != null && !item.getHolder().isDead()) && 
	 * 						!(this.getCapacity() < this.getTotalWeight() + totalWeightOfItem)
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
	 * Picks an item up from a dead body or from the ground
	 * @param	item
	 * 			The item to be picked up
	 * @post	If this item can not be picked up, nothings happens
	 * 			| !canPickUp(item)
	 * @effect	Otherwise all anchors are checked, if an empty anchor is found and the item can be equipped
	 * 			the item equipped there.
	 * 			| this.equip(anchorId, item)
	 * @effect	If there are no available anchors and there is an anchored backpack that can take this item,
	 * 			then the item will be put in that backpack.
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
	 * Return an entry set with all anchors attached
	 * @return	Return an entry set with all anchors attached
	 */
	@Raw
	public Set<Entry<Integer, Item>> getAnchorEntrySet () {
		return this.anchors.entrySet();
	}
	
	private final int numberOfAnchors;
	
	/**
	 * Return true when the given number of anchors is valid
	 * @return	Return true when the given number of anchors is valid
	 */
	public static boolean isValidNumberOfAnchors (int numberOfAnchors) {
		return numberOfAnchors > 0;
	}
	
	/**
	 * Return the number of anchors of this character
	 * @return	Return the number of anchors of this character
	 */
	@Immutable@Basic
	public int getNumberOfAnchors() {
		return this.numberOfAnchors;
	}
	
	/**
	 * Return true when this character has proper items
	 * @return	Return true when this item has proper items
	 * 			Return false when this item doesn't have proper items
	 */
	@Raw
	public boolean hasProperItems () {
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			if (!canHaveAsItemAt(entry.getKey(), entry.getValue())) { // false when not possible to reequip in same slot
				return false;
			}
		}
		return true;
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
	 * @throws	DeadException
	 * 			throws this exception when the current monster is dead.
	 */
	public abstract void hit(Character character);
	
	/**
	 * This character collects the treasures it wants to take found on a dead body.
	 * @post	Collects all anchored items of the other character 
	 * 			when the current character wants to take it
	 * 			| wantsToTake(item)
	 * @throws	DeadException
	 * 			Throws this error when this character is dead
	 */
	public abstract void collectTreasures(Character character) throws DeadException;
	
	/**
	 * Return a boolean whether the character wants to take this item
	 * @return	Returns true if the character wants to take this item
	 * 			Returns false when the character doesn't want to take this item
	 */
	public abstract boolean wantsToTake(Item item);
	
	/**
	 * Return the total value of this character
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
	 * Return the total weight of this character
	 * @return	Return the total weight of this character in kg calculated as the sum of the
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
	 * Return the best armor of any owned armor
	 * @return	Return the best armor of any owned armor
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
	 * Return the best armor of any owned weapon
	 * @return	Return the best armor of any owned armor
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
	 * Return the best backpack of any owned armor
	 * @return	Return the best backpack of any owned armor
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
	 * Return a string containing general data over this string
	 * @return	Return a string containing data over its identification, value and weight
	 */
	protected String getString() {
		return "Name: " + getName() 
			+ "\nHitpoints: " + getHitpoints() 
			+ "\nMaxHitpoints: " + getMaxHitpoints() 
			+ "\nNumber of anchors: " + getNumberOfAnchors()
			+ "\nEquipped items: " + getAnchorEntrySet().size()
			+ "\nCapacity: " + getCapacity() 
			+ "\nDamage: " + getDamage() 
			+ "\nProtection: " + getProtection()
			+ "\nTotal value: " + getTotalValue()
			+ " ducates\nTotal weight: " + getTotalWeight() + " kg";
	}
}
