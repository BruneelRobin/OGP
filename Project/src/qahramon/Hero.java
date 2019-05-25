package qahramon;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.DeadException;

import java.util.Map.Entry;
import java.util.HashMap;

/**
 * A class of heroes as special kinds of characters involving strength.
 * 
 * @invar	Each hero must have a valid strength.
 * 			| isValidStrength(getStrength())
 * 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */

public class Hero extends Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a hero with a given name, amount of hitpoints, strength and gear.
	 * 
	 * @param 	name
	 * 			the name of this hero
	 * @param 	hitpoints
	 * 			the amount of hitpoints of this hero
	 * @param 	strength
	 * 			the strength of this hero
	 * @param	items
	 * 			the initial gear of this hero
	 * @effect	The new hero is set as a character with a given name, amount of hitpoints
	 * 			and a default number of anchors.
	 * 			| super(name, hitpoints, AnchorTypes.values().length)
	 * @post	When the given strength is higher than the default strength, the hero's
	 * 			strength is set to the given strength,
	 * 			otherwise the default strength is set.
	 * 			| new.getStrength() == strength
	 * @post	Equip the given items on the given slots of this hero.
	 * 			Only what this hero can wear is equipped, all other items are dropped.
	 * 			| for (Entry<AnchorType, Item> entry : items.entrySet()) {
	 *			|	this.equip(entry.getKey(), entry.getValue());
	 *			| }
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid.
	 * 			| !isValidName(name)
	 */
	public Hero(String name, int hitpoints, float strength, HashMap<AnchorType, Item> items) throws IllegalArgumentException {
		super(name, hitpoints, AnchorType.values().length);
		
		if(!isValidStrength(strength)) {
			setStrength(getDefaultStrength());
		}
		else {
			setStrength(strength);
		}
		
		for (Entry<AnchorType, Item> entry : items.entrySet()) {
			this.equip(entry.getKey(), entry.getValue());
		}
	       
	}
	
	/**
	 * Create a hero with a given name, amount of hitpoints and strength.
	 * 
	 * @param 	name
	 * 			the name of this hero
	 * @param 	hitpoints
	 * 			the amount of hitpoints of this hero
	 * @param 	strength
	 * 			the strength of this hero
	 * @effect	The new hero is set as a character with a given name, amount of hitpoints,
	 * 			a default number of anchors and starter gear.
	 * 			| this(name, hitpoints, strength, getStarterGear())
	 */
	public Hero(String name, int hitpoints, float strength) throws IllegalArgumentException {
		this(name, hitpoints, strength, getStarterGear());
	}
	
	
	
	/********************************
	 * Name - defensive programming
	 ********************************/
	
	/**
	 * Check if a hero can have the given name as name.
	 * 
	 * @return	Return true when the given name starts with a capital letter, only contains letters,
	 * 			maximum two apostrophes, spaces and colons followed by spaces.
	 * 			Otherwise return false.
	 * 			| result == (name != null 
	 * 			|			&& name.matches("(?![^:]*:([^ ]|$))(?![^']*'[^']*'[^']*')[A-Z][A-Za-z' :]*"))
	 * 
	 * @note	Match the given characters starting with a capital letter and returns false when it 
	 * 			contains a : without a space or 3 apostrophes.
	 */
	@Override@Raw
	public boolean canHaveAsName(String name) {
		return (name != null && name.matches("(?![^:]*:([^ ]|$))(?![^']*'[^']*'[^']*')[A-Z][A-Za-z' :]*"));
	}
	
	
	/*******************************
	 * Strength - total programming
	 *******************************/
	
	/**
	 * Variable referencing the strength of this hero.
	 */
	private int strengthInteger;
	
	/**
	 * Variable referencing the strength precision for a hero.
	 */
	private final static float STRENGTH_PRECISION = 0.01f;
	
	/**
	 * Variable referencing the default strength for a hero.
	 */
	private static final float DEFAULT_STRENGTH = 0.5f;
	
	/**
	 * Return the character's strength.
	 * 
	 * @return Return the character's strength.
	 */
	@Basic
	public float getStrength() {
		return ((float)strengthInteger) * getStrengthPrecision();
	}
	
	/**
	 * Return the internal precision of the float strength.
	 * 
	 * @return	Return the internal precision of the float strength.
	 */
	@Basic@Immutable
	public static float getStrengthPrecision () {
		return STRENGTH_PRECISION;
	}
	
	/**
	 * Return the default strength of a hero.
	 * 
	 * @return	Return the default strength of a hero.
	 */
	@Basic@Immutable@Raw
	public static float getDefaultStrength () {
		return DEFAULT_STRENGTH;
	}
	
	/**
	 * Set the strength to the given strength.
	 * 
	 * @param 	strength
	 * 		  	the new value of the hero's strength
	 * @post  	The strength is set to the given strength, the strength is rounded
	 * 			with the static precision.
	 * 		  	| new.getStrength() == strength
	 */
	@Raw
	private void setStrength(float strength) {
		this.strengthInteger = (int)(strength/getStrengthPrecision());
	}
	
	/**
	 * Return true when a hero can have the given strength.
	 * 
	 * @param	strength
	 * 			The strength to check
	 * @return 	Return true when the given strength is positive.
	 * 			| strength >= getDefaultStrength()
	 */
	public static boolean isValidStrength(float strength) {
		return strength >= getDefaultStrength();
	}
	
	/**
	 * Increase the hero's strength, by multiplying with the given factor.
	 * 
	 * @param 	factor
	 * 		  	factor to multiply the strength with
	 * @post  	If the given factor is positive and the new strength is valid, the strength of the hero
	 * 			is multiplied by the given factor.
	 * 		  	| new.getStrength() == this.getStrength()*factor
	 * @post  	If the given factor is negative or the new strength is invalid,
	 * 			the strength remains the same value.
	 * 		  	| new.getStrength() == this.getStrength()
	 */
	public void multiplyStrength(int factor) {
		if(factor < 0) {
			factor = 1;
		}
		float newStrength = this.getStrength()*factor;
		if (isValidStrength(newStrength)) {
			this.setStrength(newStrength);
		}
	}
	
	/**
	 * Decrease the hero's strength, by dividing by the given divisor.
	 * 
	 * @param 	divisor
	 * 		  	the divisor to divide the strength by
	 * @post  	If the given divisor is positive and the new strength is valid, the strength of the hero
	 * 			is multiplied by the given divisor.
	 * 		  	| new.getStrength() == this.getStrength()/divisor
	 * @post  	If the given divisor is negative or the new strength is invalid,
	 * 			the strength remains the same value.
	 * 		  	| new.getStrength() == this.getStrength()
	 */
	public void divideStrength(int divisor) {
		if(divisor < 0) {
			divisor = 1;
		}
		float newStrength = this.getStrength()/divisor;
		if (isValidStrength(newStrength)) {
			this.setStrength(newStrength);
		}
	}
	
	/***********************
	 * Capacity
	 ***********************/
	
	/**
	 * Variable referencing the maximum armor count of a hero.
	 */
	private static final int MAX_ARMOR_COUNT = 2;
	
	/**
	 * Variable referencing the capacity factor of a hero.
	 */
	private static final float CAPACITY_FACTOR = 20f;
	
	/**
	 * Return the maximum armor count of a hero.
	 * 
	 * @return	Return the maximum armor count of a hero.
	 */
	@Immutable@Basic
	public static int getMaxArmorCount() {
		return MAX_ARMOR_COUNT;
	}
	
	/**
	 * Return the capacity factor of a hero.
	 * 
	 * @return	Return the capacity factor of a hero.
	 */
	@Immutable@Basic
	public static float getCapacityFactor() {
		return CAPACITY_FACTOR;
	}
	
	/**
	 * Return the hero's capacity.
	 * 
	 * @return	Return the hero's capacity.
	 * 			| result == getCapacityFactor()*this.getStrength();
	 */
	@Override
	public float getCapacity() {
		return getCapacityFactor()*this.getStrength();
	}
	
	/*******************************
	 * Protection
	 *******************************/
	
	/**
	 * Variable referencing the default protection of a hero.
	 */
	private static final int DEFAULT_PROTECTION = 10;
	
	/**
	 * Return the default protection of a hero.
	 * 
	 * @return	Return the default protection of a hero.
	 */
	@Immutable@Basic
	public static int getDefaultProtection() {
		return DEFAULT_PROTECTION;
	}
	
	/**
	 * Return the protection of the hero.
	 * 
	 * @return	Return the protection of the hero based on default protection value and armor.
	 * 			| result == DEFAULT_PROTECTION + ((Armor)(this.getItemAt(AnchorType.BODY.getAnchorId()))).getProtection()
	 */
	@Override
	public int getProtection() {
		int anchorIdOfBody = AnchorType.BODY.getAnchorId();
		Armor armorOfHero = (Armor)(this.getItemAt(anchorIdOfBody));
		return(getDefaultProtection() + armorOfHero.getProtection());
		
	}
	
	/***********************
	 * Starter gear
	 ***********************/
	
	/**
	 * Variable referencing the capacity of a default purse from the starter gear.
	 */
	private static final int DEFAULT_PURSE_CAPACITY = 100;
	
	/**
	 * Variable referencing the weight of a default purse from the starter gear. 
	 */
	private static final float DEFAULT_PURSE_WEIGHT = 0.5f;
	
	/**
	 * Variable referencing the protection of a default armor from the starter gear.
	 */
	private static final int DEFAULT_ARMOR_PROTECTION = 15;
	
	/**
	 * Variable referencing the weight of a default armor from the starter gear.
	 */
	private static final float DEFAULT_ARMOR_WEIGHT = 4f;
	
	/**
	 * Variable referencing the maximum value of a default armor from the starter gear.
	 */
	private static final int DEFAULT_FULLVALUE = 100;
	
	
	/**
	 * Return the starter gear for a hero.
	 * 
	 * @return	Return a starter armor and starter purse for this hero, as a hashMap.
	 * 			The purse will contain a random amount of ducates.
	 */
	private static HashMap<AnchorType, Item> getStarterGear() {
		
		Armor starterArmor = new Armor(MathHelper.getRandomPrime(), DEFAULT_ARMOR_PROTECTION, DEFAULT_ARMOR_WEIGHT, DEFAULT_FULLVALUE);
		
		//int maxContent = (int)(((float)this.getCapacity() - armorWeight - DEFAULT_PURSE_WEIGHT)/Purse.getDucateWeight());
		int randomContent = MathHelper.getRandomIntBetweenRange(0, DEFAULT_PURSE_CAPACITY); 
		Purse starterPurse = new Purse(DEFAULT_PURSE_WEIGHT, DEFAULT_PURSE_CAPACITY , randomContent);
		
		HashMap<AnchorType, Item> items = new HashMap<AnchorType, Item>();
		items.put(AnchorType.BODY, starterArmor);
		items.put(AnchorType.BELT, starterPurse);
		
		return items;
	}
	
	/***********************
	 * Other methods
	 ***********************/
	
	/**
	 * Check whether or not the hero wants to take an item.
	 * 
	 * @return 	Return true when the hero wants to take the item.
	 * 			Return false otherwise.	
	 */
	@Override
	public boolean wantsToTake(Item item) { 

		if (item.isArmor()) {
			Armor armor = (Armor)(item);
			Armor bestArmor = getBestArmor();
			if (bestArmor == null || armor.getFullProtection() > bestArmor.getFullProtection()) {
				return true;
			} 
		else {
			return false;
				}
			}
		
		else if (item.isWeapon()) {
			Weapon weapon = (Weapon) item;
			Weapon bestWeapon = getBestWeapon();
			if (bestWeapon == null || weapon.getDamage() > bestWeapon.getDamage()) {
				return true;
			} else {
				return false;
			}
		}
		
		else if (item.isBackpack()) {
			Backpack backpack = (Backpack) item;
			Backpack bestBackpack = getBestBackpack();
			if (bestBackpack == null || backpack.getCapacity() > bestBackpack.getCapacity()) {
				return true;
			} else {
				return false;
			}
		}
		
		else if (item.isPurse()) {
			Purse purse = (Purse) item;
			Purse thisPurse = (Purse) this.getItemAt(AnchorType.BELT.getAnchorId());
			if (purse.getCapacity() > thisPurse.getCapacity()) {
				return true;
			} else {
			return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Pick up the item when this character wants to take it.
	 * 
	 * @param	item
	 * 			the treasure to collect
	 * @effect	When this item is a backpack, all treasures inside this backpack will be collected.
	 * 			| if (item.isBackpack()) then 
	 * 			|		collectTreasures((Backpack) item)
	 * @effect	When this item is a purse, it will be equipped when better than the current purse.
	 * 			| if (item.isPurse()) then 
	 * 			|		equip(AnchorType.BELT, (Purse)item)
	 * @effect	When this item is a purse, the contents of the old purse will be added to the new purse 
	 * 			when the new purse won't tear, otherwise no contents are added.
	 * 			| newPurse.add(oldPurse)
	 * @effect	Otherwise this hero will pick up the item when he wants
	 * 			to take it
	 * 			| if (!item.isBackpack() && !item.isPurse() && wantsToTake(item)) then 
	 * 			|		pickUp(item)
	 */
	@Override
	public void collectTreasure (Item item) {
		if (item.isBackpack()) {
			Backpack backpack2 = (Backpack) item;
			collectTreasures(backpack2);
		}
		if (item.isPurse()) {
			Purse purse = (Purse) item;
			Purse oldPurse = (Purse) this.getItemAt(AnchorType.BELT.getAnchorId());
			if (wantsToTake(purse)) {
	        	equip(AnchorType.BELT,purse);
	        }
			
			Purse thisPurse = (Purse) this.getItemAt(AnchorType.BELT.getAnchorId());
			
			if (oldPurse != null && purse != null) { // We can fill a purse
				if (thisPurse == oldPurse) { // nothing equipped, old purse will be the purse we tried to add
					oldPurse = purse;
				}
				
				if (thisPurse.canHaveAsContent(thisPurse.getContent() + oldPurse.getContent())) {
		    		thisPurse.add(oldPurse);
		    	}
			}
			
		} else {
			if (wantsToTake(item)) { 
			pickUp(item);
			}
		}
	}
	
	/**
	 * Make the character hit the given character.
	 * @param	character
	 * 			the character to hit
	 * @effect	A random number between 0 and 100 is generated, when this number is higher than the 
	 * 			character's protection, the character takes the damage of this hero. When this number is
	 * 			lower than the character's protection, nothing happens.
	 * 			| character.takeDamage(getDamage())
	 * @post	When the given character dies, this hero's fighting state will be set to false
	 * 			| new.isFighting() == false
	 * @effect	When the given character dies, this hero will be healed
	 * 			| heal()
	 * @effect	When the given character dies, it's treasures will be collected
	 * 			| collectTreasures(character)
	 * @throws	DeadException
	 * 			Throws this exception when the current hero is dead.
	 * 			| isDead()
	 */
	@Override
	public void hit(Character character) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		int randomNumber = MathHelper.getRandomIntBetweenRange(0, 100);
		
		if (randomNumber >= character.getProtection()) {
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
	 * Heal the hero by adding hitpoints.
	 * 
	 * @post	The hero's new hitpoints is a random prime number between its current health and its max health.
	 * 			| MathHelper.isPrime(new.getHitpoints())
	 * @throws	DeadException
	 * 			Throws this exception when the current hero is dead.
	 * 			| isDead()
	 */
	public void heal() throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		int newHitpoints;
		do {
			newHitpoints = MathHelper.getRandomIntBetweenRange(getHitpoints(), getMaxHitpoints());
		} while (!MathHelper.isPrime(newHitpoints));
		
		setHitpoints(newHitpoints);
	}
	

	
	/**
	 * Return the damage of the hero.
	 * 
	 * @return	Return the damage of the hero as the sum of his weapons in his hands and strength minus ten and divided by two.
	 * 			When this result is negative, zero is returned.
	 * 			| result = (int)((float)(AnchorType.RIGHT_HAND.getAnchorId() + AnchorType.LEFT_HAND.getAnchorId() + this.getStrength() -10)/2)
	 * 			
	 */
	@Override
	public int getDamage() {
		int leftHandDamage;
		int rightHandDamage;
		
		Item left = getItemAt(AnchorType.LEFT_HAND.getAnchorId());
		if (left != null && left.isWeapon()) {
			leftHandDamage = ((Weapon)left).getDamage();
		} else {
			leftHandDamage = 0;
		}
		
		Item right = getItemAt(AnchorType.RIGHT_HAND.getAnchorId());
		if (right != null && right.isWeapon()) {
			rightHandDamage = ((Weapon)right).getDamage();
		} else {
			rightHandDamage = 0;
		}
		
		float strengthOfHero = this.getStrength();
		float sum = (float)(leftHandDamage + rightHandDamage + strengthOfHero -10);
		
		int result = (int)(sum/2);
		
		if(result < 0) {
			return 0;
		}
		return result;
	}
	
	/**
	 * Check whether an item can be picked up.
	 * 
	 * @param 	item
	 * 			The item to be picked up
	 * @return	Return false when the item can not be picked up by a character.
	 * 			Return false when the item is an armor
	 * 			and the new armor count will exceed the maximum armor count.
	 * 			Return true otherwise.
	 * 			| result == super.canPickUp(item) && !(item.isArmor() && this.getArmorCount() >= MAX_ARMOR_COUNT)					
	 */
	@Override
	public boolean canPickUp(Item item) {
		if(!super.canPickUp(item)){
			return false;
		}
		else if(item.isArmor() && this.getArmorCount() >= MAX_ARMOR_COUNT){
			return false;
			
		}
		return true;
	}
	
	/**
	 * Check whether the given item can be equipped.
	 * 
	 * @param 	item
	 * 			the item to be checked
	 * @return	Return true when the given item can be equipped.
	 * 			| ...
	 */
	@Override@Raw
	public boolean canHaveAsItemAt(int anchorId, Item item) {
		if (super.canHaveAsItemAt(anchorId, item)) {
			if (AnchorType.getTypeFromId(anchorId).holdsPurse() == true) {
				return item.isPurse();
			} else {
				return !(item.isPurse());
			}
		} else {
			return false;
		}
	}
		
	/**
	 * Equip an item in the given anchor.
	 * 
	 * @param 	anchorType
	 * 			the anchor to equip an item in
	 * @param 	item
	 * 			the item to equip
	 * @effect	Equip an item in the given anchor.
	 * 			| equip(AnchorType.getAnchorId())
	 * 
	 * @note	This method has been overloaded in order to use the enumerator 'AnchorType'.
	 */
	public void equip(AnchorType anchorType, Item item) {
		int anchorId = anchorType.getAnchorId();
		equip(anchorId, item);
	}
	
	/**
	 * Unequip an item in the given anchor.
	 * 
	 * @param 	anchorType
	 * 			the anchor to unequip an item from
	 * @effect	Unequip an item in the given anchor.
	 * 			| unequip(anchorType.getAnchorId())
	 * 
	 * @note	This method has been overloaded in order to use the enumerator 'AnchorType'.
	 */
	public void unequip(AnchorType anchorType) {
		int anchorId = anchorType.getAnchorId();
		unequip(anchorId);
	}
	
	/**
	 * Return the amount of armor's equipped by this character.
	 * 
	 * @return	Return the amount of armor's equipped by this character also
	 * 			looks inside anchored backpacks.
	 */
	@Raw
	public int getArmorCount () {
		int armorCount = 0;
		
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			Item item = entry.getValue();
			if (item.isArmor()) { //iterate over all items on dead body and pickup all items you want
				armorCount += 1;
			} else if (item.isBackpack()) {
				armorCount += ((Backpack)item).getArmorCount();
			}
		}
		return armorCount;
	}
	
	/**
	 * Return a string containing all public data of this hero.
	 * 
	 * @return Return a string containing all public data of this hero.
	 */
	@Override
	public String toString() {
		return "Hero\n" + super.getString()
				+ "\nStrength: " + getStrength() + "\n";
	}
}
