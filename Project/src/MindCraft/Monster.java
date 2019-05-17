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
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */

// TODO invariants
//		content en moveto check op capacity hero
//		getTotalWeight en getTotalValue -> instanceof verwijderen
//		constructor bij Hero

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
	 * @post	The damage of this monster is set to the given damage.
	 * @post	The number of anchors of this monster is set to the given number of anchors.
	 * @post	The capacity of this monster is set to the given capacity.
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid.
	 */
	public Monster(String name, int hitpoints, int damage, int protection, int numberOfAnchors, float capacity) throws IllegalArgumentException {
		super(name, hitpoints, numberOfAnchors);
		setDamage(damage);
		setProtection(protection);
		this.capacity = capacity;
	}
	
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
	 * @param	itemset	
	 * 			The given set of items for this monster to carry.
	 * @effect	The new monster is set as a character with a given name, amount of hitpoints
	 * 			and a number of anchors.
	 * @post	The damage of this monster is set to the given damage.
	 * @post	The number of anchors of this monster is set to the given number of anchors.
	 * @post	The capacity of this monster is set to the given capacity, if the capacity 
	 * 			can handle the total weight of the given itemset. If not the capacity is set
	 * 			to the total weight of the given itemset.
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid.
	 */
	public Monster(String name, int hitpoints, int damage, int protection, int numberOfAnchors, float capacity, HashSet<Item> itemset) throws IllegalArgumentException {
		super(name, hitpoints, numberOfAnchors);
		setDamage(damage);
		setProtection(protection);
		
		Iterator<Item> i = itemset.iterator();
		float totalWeight = 0;
	     while(i.hasNext()){
	    	Item item = i.next();
	    	if (item instanceof Weapon || item instanceof Armor) {
	    		totalWeight = totalWeight + item.getWeight();
	    	} else if (item instanceof Backpack) {
	    		Backpack backpack = (Backpack) item;
	    		totalWeight = totalWeight + backpack.getTotalWeight();
	    	} else if (item instanceof Purse) {
	    		Purse purse = (Purse) item;
	    		totalWeight = totalWeight +  purse.getTotalWeight();
	    	}
	     }
		
		if (capacity >= totalWeight) {
			this.capacity = capacity;
		} else {
			this.capacity = totalWeight;
	     }
		
		Iterator<Item> it = itemset.iterator();
		int anchorId = 0;
	    while(it.hasNext()){
	        this.equip(anchorId, it.next());
	        anchorId ++;
	    }
	
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
	public boolean wantsToTakeItem(Item item) {
		int randomInt = MathHelper.getRandomIntBetweenRange(0,100);
		if (item instanceof Armor) {
			Armor armor = (Armor) item;
			int shinyness = (armor.getProtection()/armor.getFullProtection())*100;
			if (randomInt <= shinyness) {
				return true;
			} else {
				return false;
			}
		}
		else if (item instanceof Weapon) {
			Weapon weapon = (Weapon) item;
			if (randomInt <= 80) {
				return true;
			} else {
				return false;
			}
		}
		
		else if (item instanceof Backpack) {
			Backpack backpack = (Backpack) item;
			if (randomInt <= 5) {
				return true;
			} else {
				return false;
			}
		}
		
		else if (item instanceof Purse) {
			Purse purse = (Purse) item;
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
