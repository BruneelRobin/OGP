package qahramon;


import java.util.Map.Entry;
import java.util.Set;
import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.DeadException;

import java.util.Iterator;

/**
 * A class of monsters as special kinds of characters involving capacity, 
 * protection and damage.
 * 
 * @invar	Each monster must have a valid damage.
 * 			| isValidDamage(getDamage())
 * @invar	Each monster must have a valid protection.
 * 			| isValidProtection(getProtection())
 * @invar	Each monster must have a valid capacity.
 * 			| isValidCapacity(getCapacity())
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */

public class Monster extends Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a monster with a given name, amount of hitpoints, damage, protection,
	 * number of anchors, capacity and given items, which are randomly distributed on the anchors.
	 * 
	 * @param 	name
	 *			the name of this monster
	 * @param 	hitpoints
	 * 			the maximum amount of hitpoints of this monster
	 * @param 	damage
	 * 			the damage of this monster
	 * @param 	protection
	 * 			the protection of this monster
	 * @param 	numberOfAnchors
	 * 			the number of anchors of this monster
	 * @param 	capacity
	 * 			the carry capacity of this monster
	 * @param	items
	 * 			the given set of items of this monster
	 * @pre		The given damage must be valid.
	 * 			| isValidDamage(damage)
	 * @pre		The given protection must be valid.
	 * 			| isValidProtection(protection)
	 * 			The given set of items for this monster to carry.
	 * @pre		The given number of anchors is equal to or higher than the amount of items to equip.
	 * 			| numberOfAnchors >= items.size()
	 * @effect	The new monster is set as a character with a given name, amount of hitpoints
	 * 			and a number of anchors.
	 * 			| super(name, hitpoints, numberOfAnchors)
	 * @post	The capacity of this monster is set to the given capacity, if the capacity 
	 * 			can handle the total weight of the given items. If not the capacity is set
	 * 			to the total weight of the given items.
	 * @post	Each item given in this constructor will be equipped, since all items are 
	 * 			given in a set no order can be guaranteed.
	 * @post	The damage of this monster is set to the given damage.
	 * 			| new.getDamage() == damage
	 * @post	The protection of this monster is set to the given protection.
	 * 			| new.getProtection() == protection
	 */
	public Monster(String name, int hitpoints, int damage, int protection, int numberOfAnchors, float capacity, HashSet<Item> items) throws IllegalArgumentException {
		super(name, hitpoints, numberOfAnchors);
		
		this.damage = damage;
		this.protection = protection;
		
		float totalWeight = 0;
	    for (Item item : items) {
	    	if (item.isWeapon() || item.isArmor()) {
	    		totalWeight = totalWeight + item.getWeight();
	    	} else if (item.isContainer()) {
	    		totalWeight = totalWeight + ((Container) item).getTotalWeight();
	    	}
	    }
		
		if (capacity >= totalWeight) {
			this.capacity = capacity;
		} else {
			this.capacity = totalWeight;
	    }
		
		Iterator<Item> it = items.iterator();
		int anchorId = 0;
	    while(it.hasNext()){
	        this.equip(anchorId, it.next());
	        anchorId ++;
	    }
	}
	
	/**
	 * Create a monster with a given name, amount of hitpoints, damage, protection,
	 * number of anchors and capacity.
	 * 
	 * @param 	name
	 *			the name of this monster
	 * @param 	hitpoints
	 * 			the maximum amount of hitpoints of this monster
	 * @param 	damage
	 * 			the damage of this monster
	 * @param 	protection
	 * 			the protection of this monster
	 * @param 	numberOfAnchors
	 * 			the number of anchors of this monster
	 * @param 	capacity
	 * 			the carry capacity of this monster
	 * @effect	The new monster is set as a character with a given name, amount of hitpoints,
	 * 			a number of anchors and no items attached.
	 * 			| this(name, hitpoints, damage, protection, numberOfAnchors, capacity, new HashSet<Item>())
	 */
	public Monster(String name, int hitpoints, int damage, int protection, int numberOfAnchors, float capacity) throws IllegalArgumentException {
		this(name, hitpoints, damage, protection, numberOfAnchors, capacity, new HashSet<Item>());
	}
	


	
	/********************************
	 * Damage - nominal programming
	 ********************************/
	
	/**
	 * Variable referencing the damage of a monster.
	 */
	private final int damage;
	
	/**
	 * Return the damage of this monster.
	 * 
	 * @return	Return the damage of this monster.
	 */
	@Basic@Override
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * Return true when the given damage is valid.
	 * 
	 * @param	damage
	 * 			the damage to check
	 * @return	Return true when the given damage is positive.
	 * 			Return false otherwise.
	 * 			| damage >= 0
	 */
	public static boolean isValidDamage(int damage) {
		return damage >= 0;
	}
	
	/********************************
	 * Protection
	 ********************************/
	
	/**
	 * Variable referencing the protection of a monster.
	 */
	private final int protection;
	
	/**
	 * Variable referencing the maximum protection of all monsters.
	 */
	private static final int MAX_PROTECTION = 100;
	
	/**
	 * Return the protection of the monster.
	 * 
	 * @return	Return the protection of the monster.
	 */
	@Override@Basic
	public int getProtection() {
		return this.protection;
	}
	
	/**
	 * Return the maximum protection of all monsters.
	 * 
	 * @return	Return the maximum protection of all monsters.
	 */
	@Basic@Immutable
	public static int getMaxProtection() {
		return MAX_PROTECTION;
	}
	
	/**
	 * Check whether the given protection is valid.
	 * 
	 * @param	protection
	 * 			the protection to check
	 * @return	Return true when the given protection is positive.
	 * 			Return false otherwise.
	 * 			| protection >= 0
	 */
	public static boolean isValidProtection(int protection) {
		return protection >= 0 && protection <= getMaxProtection();
	}
	
	/***********************
	 * Capacity
	 ***********************/
	
	/**
	 * Variable referencing the capacity of a monster.
	 */
	private final float capacity;
	
	/**
	 * Return the monster's capacity.
	 * 
	 * @return Return the monster's capacity.
	 */
	@Basic@Override@Immutable
	public float getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Check whether the given capacity is valid.
	 * 
	 * @param	capacity
	 * 			the capacity to check
	 * @return	Return true when the given capacity is positive.
	 * 			Return false otherwise.
	 * 			| capacity >= 0
	 */
	public static boolean isValidCapacity(float capacity) {
		return capacity >= 0;
	}
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * Check whether the monster wants to take this item.
	 * 
	 * @return	Return true if the monster wants to take this item.
	 * 			Return false when the monster doesn't want to take this item.
	 * 
	 * @note	The more shiny an item is, the more likely the monster wants to take it.
	 */
	@Override
	public boolean wantsToTake(Item item) {
		int randomInt = MathHelper.getRandomIntBetweenRange(0,100);
		if (item.isArmor()) {
			Armor armor = (Armor) item;
			int shinyness = (armor.getProtection()/armor.getFullProtection())*100;
			if (randomInt <= shinyness) {
				return true;
			} else {
				return false;
			}
		}
		else if (item.isWeapon()) {
			if (randomInt <= 80) {
				return true;
			} else {
				return false;
			}
		}
		
		else if (item.isBackpack()) {
			if (randomInt <= 5) {
				return true;
			} else {
				return false;
			}
		}
		
		else if (item.isPurse()) {
			if (randomInt <= 25) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * This monster collects the treasures it wants to take found on a dead body.
	 * 
	 * @post	Collect all anchored items of the other character 
	 * 			when the current monster wants to take it.
	 * 			| wantsToTake(item)
	 * @throws	DeadException
	 * 			throws this exception when the current monster is dead.
	 */
	@Override
	public void collectTreasures(Character character) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		if (character.isDead()) {
			
			Set<Entry<Integer, Item>> set = character.getAnchorEntrySet();
			Set<Item> terminateSet = new HashSet<Item>();
			
			for (Entry<Integer, Item> entry : set) {
				Item item = entry.getValue();
				if (wantsToTake(item)) { //iterate over all items on dead body and pickup all items you want
					pickUp(item);
				} else if (item.isArmor() || item.isWeapon()) {
					terminateSet.add(item);
				} else if (item.isBackpack()) {
					((Backpack)item).terminateWeaponsAndArmor();
				}
			}
			
			for (Item item : terminateSet) {
				item.terminate();
			}
		}
	}
	
	/**
	 * This monster hits the given character.
	 * 
	 * @post	The character that was hit by this monster will take damage.
	 * @throws	DeadException
	 * 			Throws this exception when the current monster is dead.
	 */
	@Override
	public void hit(Character character) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		int randomNumber = MathHelper.getRandomIntBetweenRange(0, 100);
		
		if (randomNumber > getHitpoints()) {
			randomNumber = getHitpoints();
		}
		
		if (randomNumber >= character.getProtection()) {
			int damage = getDamage();
			
			character.takeDamage(damage);
			
			if (character.isDead()) {
				setFighting(false);
				int newHealth = MathHelper.getLowerPrime(getHitpoints(), 0);
				setHitpoints(newHealth);
				
				collectTreasures(character);
			}
		}
	}
	
	/**
	 * Return a string containing all public data of this monster.
	 * 
	 * @return Return a string containing all public data of this monster.
	 */
	@Override
	public String toString() {
		return "Monster\n" + super.getString() + "\n";
	}
}
