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
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid.
	 * 			| !isValidName(name)
	 */
	public Hero(String name, int hitpoints, float strength, HashMap<AnchorType, Item> items) throws IllegalArgumentException {
		super(name, hitpoints, AnchorType.values().length); // length of anchortypes
		
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
	
	/********************************
	 * Name - defensive programming
	 ********************************/
	
	/**
	 * Check if a hero can have the given name as name.
	 * 
	 * @return	Return true when the given name starts with a capital letter, only contains letters,
	 * 			maximum two apostrophes, spaces and colons followed by spaces.
	 * 			Otherwise return false.
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
	
	private int strengthInteger;
	private final static float STRENGTH_PRECISION = 0.01f;
	
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
	 * Return true when a hero can have the given strength.
	 * 
	 * @param	strength
	 * 			The strength to check
	 * @return 	Return true when the given strength is positive.
	 * 			| strength >= 0
	 */
	public static boolean isValidStrength(float strength) {
		return strength >= getDefaultStrength();
	}
	
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
	 * Set the strength to the given strength.
	 * 
	 * @param 	strength
	 * 		  	the new value of the hero's strength
	 * @post  	The strength is set to the given strength, the strength is rounded
	 * 			with the static precision.
	 * 		  	| new.getStrength() == strength
	 */
	private void setStrength(float strength) {
		this.strengthInteger = (int)(strength/getStrengthPrecision());
	}
	
	/**
	 * Increase the hero's strength, by multiplying with the given factor.
	 * 
	 * @param 	factor
	 * 		  	factor to multiply the strength with
	 * @post  	If the given factor is positive, the strength of the hero
	 * 			is multiplied by the given factor.
	 * 		  	| new.getStrength() == this.getStrength()*factor
	 * @post  	If the given factor is smaller than zero, the strength remains the same value.
	 * 		  	| new.getStrength() == this.getStrength()
	 */
	public void multiplyStrength(int factor) {
		if(factor < 0) {
			factor = 1;
		}
		this.setStrength(this.getStrength()*factor);
	}
	
	/**
	 * Decrease the hero's strength, by dividing by the given divisor.
	 * 
	 * @param 	divisor
	 * 		  	the divisor to divide the strength by
	 * @post  	If the given divisor is larger than zero, the strength of the hero
	 * 			is divided by the given divisor.
	 * 		  	| new.getStrength() == this.getStrength()/divisor
	 * @post  	If the given divisor is negative, the strength remains the same value.
	 * 		  	| new.getStrength() == this.getStrength()
	 */
	public void divideStrength(int divisor) {
		if(divisor <= 0) {
			divisor = 1;
		}
		this.setStrength(this.getStrength()/divisor);
	}
	
	private static final float DEFAULT_STRENGTH = 0.5f;
	
	/**
	 * Return the default strength of this character.
	 * 
	 * @return	Return the default strength of this character.
	 */
	public static float getDefaultStrength () {
		return DEFAULT_STRENGTH;
	}
	
	/***********************
	 * Capacity
	 ***********************/
	
	private static final int MAX_ARMOR_COUNT = 2;
	private static final float CAPACITY_FACTOR = 20f;
	
	/**
	 * Return the hero's capacity.
	 * 
	 * @return Return the hero's capacity.
	 */
	@Override
	public float getCapacity() {
		return CAPACITY_FACTOR*this.getStrength();
	}
	
	/*******************************
	 * Protection
	 *******************************/
	
	private static final int DEFAULT_PROTECTION = 10;
	
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
		return(DEFAULT_PROTECTION + armorOfHero.getProtection());
		
	}
	
	/***********************
	 * Starter gear
	 ***********************/
	
	private static final int DEFAULT_PURSE_CAPACITY = 100;
	private static final float DEFAULT_PURSE_WEIGHT = 0.5f;
	
	private static final int DEFAULT_ARMOR_PROTECTION = 15;
	private static final float DEFAULT_ARMOR_WEIGHT = 4f;
	private static final int DEFAULT_FULLVALUE = 100;
	
	
	/**
	 * Return the starter gear for a hero.
	 * 
	 * @return	Return a starter armor and starter purse for this hero, the weights of these items will
	 * 			be scaled so this hero can always equip the gear.
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
	 * @return 	Return false when the hero does not want to take the item.
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
	 * This hero collects the treasures it wants to take found on a dead body.
	 * 
	 * @post	Collect all anchored items of the other character
	 * 			when the current hero wants to take it.
	 * 			| wantsToTake(item)
	 * @throws	DeadException
	 * 			Throws this error when this hero is dead.
	 */
	@Override
	public void collectTreasures(Character character) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		if (character.isDead()) {
		
			Set<Entry<Integer, Item>> set = character.getAnchorEntrySet();
			Set<Purse> purses = new HashSet<Purse>();
			Purse thisPurse = (Purse) this.getItemAt(AnchorType.BELT.getAnchorId());
			if (thisPurse != null) {
			purses.add(thisPurse);
			}
			
			for (Entry<Integer, Item> entry : set) {
				Item item = entry.getValue();
				if (item.isBackpack()) {
					Backpack backpack = (Backpack) item;
					collectTreasures(backpack);
				}
				if (item.isPurse()) {
					Purse purse = (Purse) item;
					purses.add(purse);
				} else {
					if (wantsToTake(item)) { 
					pickUp(item);
					}
				}
			}
			
		    for (Purse purse : purses){
		        if (wantsToTake(purse)) {
		        	equip(AnchorType.BELT,purse);
		        }
		    }
		    thisPurse = (Purse) this.getItemAt(AnchorType.BELT.getAnchorId());
		    for(Purse purse : purses) {
		    	if (thisPurse.canHaveAsContent(thisPurse.getContent() + purse.getContent()) && thisPurse != purse) {
		    		thisPurse.add(purse);
		    	}
		    }
		}
	}
	
	/**
	 * This hero collects the treasures it wants to take found in a backpack.
	 * 
	 * @post	Collect all items of a backpack
	 * 			when the current hero wants to take it.
	 * 			| wantsToTake(item)
	 */
	public void collectTreasures(Backpack backpack) throws DeadException {
		if (isDead()) {
			throw new DeadException(this);
		}
		
		Set<Item> set = backpack.getItems();
		Set<Purse> purses = new HashSet<Purse>();
		Purse thisPurse = (Purse) this.getItemAt(AnchorType.BELT.getAnchorId());
		if (thisPurse != null) {
		purses.add(thisPurse);
		}
		
		for (Item item : set) {
			if (item.isBackpack()) {
				Backpack backpack2 = (Backpack) item;
				collectTreasures(backpack2);
			}
			if (item.isPurse()) {
				Purse purse = (Purse) item;
				purses.add(purse);
			} else {
				if (wantsToTake(item)) { 
				pickUp(item);
				}
			}
		}
		
		for (Purse purse : purses){
	        if (wantsToTake(purse)) {
	        	equip(AnchorType.BELT,purse);
	        }
	    }
	    thisPurse = (Purse) this.getItemAt(AnchorType.BELT.getAnchorId());
	    for(Purse purse : purses) {
	    	if (thisPurse.canHaveAsContent(thisPurse.getContent() + purse.getContent()) && thisPurse != purse) {
	    		thisPurse.add(purse);
	    	}
	    }
	}
	
	/**
	 * Make the character hit the given character.
	 * 
	 * @post	A random number between 0 and 100 is generated, when this number is higher than the 
	 * 			character's protection, the character takes the damage of this hero. When this number is
	 * 			lower than the character's protection, nothing happens.
	 * 			| character.takeDamage(this.getDamage())
	 * @throws	DeadException
	 * 			throws this exception when the current hero is dead.
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
	 * @post	The hero's new hp is a random prime number between its current health and its max health.
	 * 			| MathHelper.isPrime(getHitpoints())
	 * @throws	DeadException
	 * 			throws this exception when the current hero is dead.
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
	 * @return	Return the damage of the hero as the sum of his weapons and strength minus ten and divided by two.
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
	 * @return	Return false when the holder of the item is not null and not dead.
	 * 			Return false when the new weight of this hero will exceed 
	 * 			the capacity of this hero.
	 * 			Return true otherwise.
	 * 			| result == !(item.getHolder() != null && !item.getHolder().isDead()) && 
	 * 						!(this.getCapacity() < this.getTotalWeight() + totalWeightOfItem)
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
	 * 			The anchor to equip an item in
	 * @param 	item
	 * 			The item to equip
	 * @effect	Equip an item in the given anchor.
	 * 			| equip(anchorType.getAnchorId())
	 * @note	This method has been overloaded in order to use our enumerator.
	 */
	public void equip(AnchorType anchorType, Item item) {
		int anchorId = anchorType.getAnchorId();
		equip(anchorId, item);
	}
	
	/**
	 * Unequip an item in the given anchor.
	 * 
	 * @param 	anchorType
	 * 			The anchor to unequip an item from
	 * @effect	Unequip an item in the given anchor.
	 * 			| unequip(anchorType.getAnchorId())
	 * @note	This method has been overloaded in order to use our enumerator.
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
