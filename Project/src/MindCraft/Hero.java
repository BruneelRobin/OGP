package MindCraft;

import java.util.Set;
import java.util.Map.Entry;

/**
 * A class of Heroes. 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */

public class Hero extends Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a hero with a given name, amount of hitpoints and strength.
	 * @param 	name
	 * 			The name of this hero.
	 * @param 	hitpoints
	 * 			The amount of hitpoints of this hero.
	 * @param 	strength
	 * 			The strength of this hero.
	 * @effect	The new hero is set as a character with a given name, amount of hitpoints
	 * 			and a default number of anchors.
	 * 			|super(name, hitpoints, AnchorTypes.values().length)
	 * @post	The strength of this hero is set to the given strength
	 * 			| new.getStrength() == strength
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public Hero(String name, int hitpoints, float strength) throws IllegalArgumentException {
		super(name, hitpoints, AnchorTypes.values().length); // length of anchortypes
		
		setStrength(strength);
		
	}
	
	/********************************
	 * Name - defensive programming
	 ********************************/
	
	/**
	 * Checks if a hero can have the given name as name
	 */
	@Override
	public boolean canHaveAsName(String name) {
		return false;
	}
	
	
	/*******************************
	 * Strength - total programming
	 *******************************/
	
	private float strength;
	
		/**
		 * Returns the character's strength
		 * @return Returns the character's strength
		 */
	public float getStrength() {
		return this.strength;
	
	}
	
	/**
	 * Sets the strength to the given strength
	 * @param strength
	 * 		  the new value of the hero's strength
	 * @post  The strength is set to the given strength
	 * 		  | new.getStrength == strength
	 */
	private void setStrength(float strength) {
		this.strength = strength;
	}
	
	
	/***********************
	 * Capacity
	 ***********************/
	
	private static final int MAX_ARMOR_COUNT = 2;
	
	/**
	 * Return the hero's capacity
	 * @return Return the hero's capacity
	 */
	public float getCapacity() {
		return 20*this.getStrength();
	}
	
	
	
	/***********************
	 * Other methods
	 ***********************/
	
	
	/**
	 * Returns whether or not the hero wants to take an item
	 * 
	 * @return Returns true when the hero wants to take the item
	 * @return Returns false when the hero does not want to take the item
	 */
	@Override
	public boolean wantsToTakeItem(Item item) {
		return false;
	}
	
	/**
	 * 
	 */
	public int getDamage() {
		return 0;
	}
	
	/**
	 * Makes the character hit the given character
	 * @post	A random number between 0 and 100 is generated, when this number is higher than the 
	 * 			character's protection, the character takes the damage of this hero. When this number is
	 * 			lower than the character's protection, nothing happens.
	 * 			| character.takeDamage(this.getDamage())
	 * 
	 */
	public void hit(Character character) {
		int rnd = (int)MathHelper.getRandomLongBetweenRange(0, 100);
		
		if (rnd >= character.getProtection()) {
			int damage = getDamage();
			
			character.takeDamage(damage);
			
			if (character.isDead()) {
				heal();
				setFighting(false);
				collectTreasures(character);
			}
		}
		
	}
	
	/**
	 * Heals the hero by adding hitpoints
	 * @post	The hero's new hp is a random prime number between its current health and its max health.
	 * 			| MathHelper.isPrime(getHitpoints())
	 */
	public void heal() {
		int newHitpoints;
		do {
			newHitpoints = (int)MathHelper.getRandomLongBetweenRange(getHitpoints(), getMaxHitpoints());
		} while (!MathHelper.isPrime(newHitpoints));
		
		setHitpoints(newHitpoints);
	}
	
	private static final int DEFAULT_PROTECTION = 10;
	/**
	 * Return the protection of the hero
	 * @return	Return the protection of the hero based on default protection value and armor
	 */
	@Override
	public int getProtection() {
		return 0;
	}
	
	
	@Override
	public boolean canPickUpItem(Item item) {
		if(!super.canPickUpItem(item)){
			return false;
		}
		else if(item instanceof Armor && this.getArmorCount() >= MAX_ARMOR_COUNT){
			return false;
			
		}
		return true;
	}
		
	/**
	 * Equips an item in the given anchor
	 * @param 	anchorType
	 * 			The anchor to equip an item in
	 * @param 	item
	 * 			The item to equip
	 * @effect	Equips an item in the given anchor
	 * 			| equip(anchorType.getAnchorId())
	 * @note	This method has been overloaded in order to use our enumerator
	 */
	public void equip(AnchorTypes anchorType, Item item) {
		int anchorId = anchorType.getAnchorId();
		equip(anchorId, item);
	}
	
	/**
	 * Unequips an item in the given anchor
	 * @param 	anchorType
	 * 			The anchor to unequip an item from
	 * @effect	Unequips an item in the given anchor
	 * 			| unequip(anchorType.getAnchorId())
	 * @note	This method has been overloaded in order to use our enumerator
	 */
	public void unequip(AnchorTypes anchorType) {
		int anchorId = anchorType.getAnchorId();
		unequip(anchorId);
	}
	
	/**
	 * Return the amount of armor's equipped by this character
	 * @return	Return the amount of armor's equipped by this character also looks inside anchored backpacks
	 */
	public int getArmorCount () {
		int armorCount = 0;
		
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			Item item = entry.getValue();
			if (item instanceof Armor) { //iterate over all items on dead body and pickup all items you want
				armorCount += 1;
			} else if (item instanceof Backpack) {
				armorCount += ((Backpack)item).getArmorCount();
			}
		}
		return armorCount;
	}
}
