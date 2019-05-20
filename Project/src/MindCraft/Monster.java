package MindCraft;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;
import sun.security.jca.GetInstance.Instance;

import java.util.HashMap;

import java.util.Iterator;

/**
 * A class of monsters.
 * 
 * @invar	Each monster must have a valid damage
 * 			| isValidDamage(getDamage())
 * @invar	Each monster must have a valid protection
 * 			| isValidProtection(getProtection())
 * @invar	Each monster must have a valid capacity
 * 			| isValidCapacity(getCapacity())
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */

// TODO invariants (done)
//		content en moveto check op capacity hero  (done)
//		getTotalWeight en getTotalValue -> instanceof verwijderen (done)
//		constructor bij Hero -> nog te doen, maar giveStarterGear hiervoor aanpassen
//		constructors samenvoegen bij monster sws ... (done)

public class Monster extends Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a monster with a given name, amount of hitpoints, damage, protection,
	 * number of anchors and capacity.
	 * @param 	name
	 *			The name of this monster.
	 * @param 	hitpoints
	 * 			The maximum amount of hitpoints of this monster.
	 * @param 	damage
	 * 			The damage of this monster.
	 * @param 	protection
	 * 			The protection of this monster.
	 * @param 	numberOfAnchors
	 * 			The number of anchors of this monster.
	 * @param 	capacity
	 * 			The carry capacity of this monster.
	 * @effect	The new monster is set as a character with a given name, amount of hitpoints
	 * 			and a number of anchors.
	 * 			| super(name, hitpoints, numberOfAnchors)
	 * @effect	The new monster is initialized with the given damage and protection
	 * 			| initialize(damage, protection)
	 * @post	The capacity of this monster is set to the given capacity or 0 when the given capacity is negative.
	 */
	public Monster(String name, int hitpoints, int damage, int protection, int numberOfAnchors, float capacity) throws IllegalArgumentException {
		super(name, hitpoints, numberOfAnchors);
		this.initialize(damage, protection);
		if (capacity < 0) {
			capacity = 0;
		}
		this.capacity = capacity;
	}
	
	/**
	 * Create a monster with a given name, amount of hitpoints, damage, protection,
	 * number of anchors, capacity and given items, which are randomly distributed on the anchors.
	 * @param 	name
	 *			The name of this monster.
	 * @param 	hitpoints
	 * 			The maximum amount of hitpoints of this monster.
	 * @param 	damage
	 * 			The damage of this monster.
	 * @param 	protection
	 * 			The protection of this monster.
	 * @param 	numberOfAnchors
	 * 			The number of anchors of this monster.
	 * @param 	capacity
	 * 			The carry capacity of this monster.
	 * @param	items
	 * 			The given set of items for this monster to carry.
	 * @pre		The given number of anchors is equal to or higher than the amount of items to equip
	 * 			| numberOfAnchors >= items.size()
	 * @effect	The new monster is set as a character with a given name, amount of hitpoints
	 * 			and a number of anchors.
	 * 			| super(name, hitpoints, numberOfAnchors)
	 * @effect	The new monster is initialized with the given damage and protection
	 * 			| initialize(damage, protection)
	 * @post	The capacity of this monster is set to the given capacity, if the capacity 
	 * 			can handle the total weight of the given items. If not the capacity is set
	 * 			to the total weight of the given items.
	 * @post	Each item given in this constructor will be equipped, since all items are given in a set no order can be guaranteed
	 */
	public Monster(String name, int hitpoints, int damage, int protection, int numberOfAnchors, float capacity, HashSet<Item> items) throws IllegalArgumentException {
		super(name, hitpoints, numberOfAnchors);
		this.initialize(damage, protection);
		
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
	 * Initialize this monster with the given damage and protection
	 * @param 	damage
	 * 			The damage of this monster
	 * @param 	protection
	 * 			The protection of this monster
	 * @pre		The given damage is valid
	 * 			| isValidDamage(damage)
	 * @pre		The given protection is valid
	 * 			| isValidProtection(protection)
	 * @post	The damage of this monster is set to the given damage.
	 * 			| new.getDamage() == damage
	 * @post	The protection of this monster is set to the given protection.
	 * 			| new.getProtection() == protection
	 */
	private void initialize (int damage, int protection) {
		setDamage(damage);
		setProtection(protection);
	}

	
	/********************************
	 * Damage - nominal programming
	 ********************************/
	
	/**
	 * Variable referencing the damage of a monster.
	 */
	private int damage = 0;
	
	/**
	 * Set the damage of a monster to a given amount of damage.
	 * 
	 * @param	damage
	 * 			The new damage.
	 * @post	The damage is set to the given damage.
	 */
	private void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Return true when the given damage is valid
	 * @param	damage
	 * 			The damage to check
	 * @return	Return true when the given damage is positive
	 * 			| damage >= 0
	 */
	public static boolean isValidDamage(int damage) {
		return damage >= 0;
	}
	
	/**
	 * Return the damage of this monster.
	 * @return	Return the damage of this monster.
	 */
	public int getDamage() {
		return this.damage;
	}

	
	/********************************
	 * Protection
	 ********************************/
	
	/**
	 * Variable referencing the protection of a monster.
	 */
	private int protection = 0;
	private static final int MAX_PROTECTION = 100;
	
	/**
	 * Set the protection of a monster to a given amount of protection.
	 * 
	 * @param	protection
	 * 			The new protection.
	 * @post	The protection is set to the given protection.
	 */
	private void setProtection(int protection) {
		this.protection = protection;
	}
	
	
	/**
	 * Return true when the given protection is valid
	 * @param	protection
	 * 			The protection to check
	 * @return	Return true when the given protection is positive
	 * 			| protection >= 0
	 */
	public static boolean isValidProtection(int protection) {
		return protection >= 0 && protection <= MAX_PROTECTION;
	}
	
	/**
	 * Return the protection of the monster
	 * @return	Return the protection of the monster
	 */
	@Override
	public int getProtection() {
		return this.protection;
	}
	
	/***********************
	 * Capacity
	 ***********************/
	
	private final float capacity;
	
	/**
	 * Return the monster's capacity
	 * @return Return the monster's capacity
	 */
	public float getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Return true when the given capacity is valid
	 * @param	capacity
	 * 			The capacity to check
	 * @return	Return true when the given capacity is positive
	 * 			| capacity >= 0
	 */
	public static boolean isValidCapacity(float capacity) {
		return capacity >= 0;
	}
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * Return a boolean whether the monster wants to take this item
	 * @return	Returns true if the monster wants to take this item
	 * 			Returns false when the monster doesn't want to take this item
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
	 * This monster hits the given character.
	 * @post	The character that was hit by this monster will take damage.
	 */
	@Override
	public void hit(Character character) {
		int randomNumber = MathHelper.getRandomIntBetweenRange(0, 100);
		
		if (randomNumber < getHitpoints()) {
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
}
