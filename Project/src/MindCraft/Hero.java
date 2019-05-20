package MindCraft;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class of Heroes. 
 * 
 * @invar	Each hero must have a valid strength
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
	 * @param 	name
	 * 			The name of this hero.
	 * @param 	hitpoints
	 * 			The amount of hitpoints of this hero.
	 * @param 	strength
	 * 			The strength of this hero.
	 * @effect	The new hero is set as a character with a given name, amount of hitpoints
	 * 			and a default number of anchors.
	 * 			| super(name, hitpoints, AnchorTypes.values().length)
	 * @post	The strength of this hero is set to the given strength
	 * 			| new.getStrength() == strength
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public Hero(String name, int hitpoints, float strength) throws IllegalArgumentException {
		super(name, hitpoints, AnchorType.values().length); // length of anchortypes
		
		if(!isValidStrength(strength)) {
			setStrength(0);
		}
		else {
			setStrength(strength);
		}
		
		giveStarterGear();
	}
	
	/**
	 * Create a hero with a given name, amount of hitpoints, strength and gear.
	 * @param 	name
	 * 			The name of this hero.
	 * @param 	hitpoints
	 * 			The amount of hitpoints of this hero.
	 * @param 	strength
	 * 			The strength of this hero.
	 * @param	items
	 * 			The initial gear of this hero.
	 * @effect	The new hero is set as a character with a given name, amount of hitpoints
	 * 			and a default number of anchors.
	 * 			| super(name, hitpoints, AnchorTypes.values().length)
	 * @post	The strength of this hero is set to the given strength
	 * 			| new.getStrength() == strength
	 * @post	...
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public Hero(String name, int hitpoints, float strength, List<Item> items) throws IllegalArgumentException {
		super(name, hitpoints, AnchorType.values().length); // length of anchortypes
		
		if(!isValidStrength(strength)) {
			setStrength(0);
		}
		else {
			setStrength(strength);
		}
		
		Iterator<Item> it = items.iterator();
		int anchorId = 0;
	    while(it.hasNext()){
	        this.equip(anchorId, it.next());
	        anchorId ++;
	    }
	}
	
	/********************************
	 * Name - defensive programming
	 ********************************/
	
	/**
	 * Checks if a hero can have the given name as name
	 * @return	Return true when the given name starts with a capital letter, only contains letters,
	 * 			maximum two apostrophes, spaces and colons followed by spaces.
	 * 			Otherwise return false
	 * 
	 * @note	Matches the given characters starting with a capital letter and returns false when it 
	 * 			contains a : without a space or 3 apostrophes
	 */
	@Override
	public boolean canHaveAsName(String name) {
		return (name != null && name.matches("(?![^:]*:[^ ])(?![^']*'[^']*'[^']*')[A-Z][A-Za-z' :]*"));
	}
	
	
	/*******************************
	 * Strength - total programming
	 *******************************/
	
	private int strengthInteger;
	private static float strengthPrecision = 0.01f;
	
	/**
	 * Return the internal precision of the float strength
	 * @return	Return the internal precision of the float strength
	 */
	public static float getStrengthPrecision () {
		return strengthPrecision;
	}
	
	/**
	 * Return true when a hero can have the given strength
	 * @param	strength
	 * 			The strength to check
	 * @return 	true when the given strength is positive
	 * 			| strength >= 0
	 */
	public static boolean isValidStrength(float strength) {
		return strength >= 0;
	}
	
	/**
	 * Returns the character's strength
	 * @return Returns the character's strength
	 */
	public float getStrength() {
		return ((float)strengthInteger) * getStrengthPrecision();
	}
	
	/**
	 * Sets the strength to the given strength
	 * @param strength
	 * 		  the new value of the hero's strength
	 * @post  The strength is set to the given strength, the strength is rounded with the static precision
	 * 		  | new.getStrength() == strength
	 */
	private void setStrength(float strength) {
		this.strengthInteger = (int)(strength/getStrengthPrecision());
	}
	
	/**
	 * Increase the hero's strength, by multiplying with the given factor.
	 * @param factor
	 * 		  factor to multiply the strength with
	 * @post  If the given factor is positive, the strength of the hero is multiplied by the given factor.
	 * 		  | new.getStrength() == this.getStrength()*factor
	 * @post  If the given factor is smaller than zero, the strength remains the same value.
	 * 		  | new.getStrength() == this.getStrength()
	 */
	public void multiplyStrength(int factor) {
		if(factor < 0) {
			factor = 1;
		}
		this.setStrength(this.getStrength()*factor);
		}
	
	/**
	 * Decrease the hero's strength, by dividing by the given divisor.
	 * @param divisor
	 * 		  the divisor to divide the strength by
	 * @post  If the given divisor is larger than zero, the strength of the hero is divided by the given divisor.
	 * 		  | new.getStrength() == this.getStrength()/divisor
	 * @post  If the given divisor is negative, the strength remains the same value.
	 * 		  | new.getStrength() == this.getStrength()
	 */
	public void divideStrength(int divisor) {
		if(divisor <= 0) {
			divisor = 1;
		}
		this.setStrength(this.getStrength()/divisor);
		}
	
	
	/***********************
	 * Capacity
	 ***********************/
	
	private static final int MAX_ARMOR_COUNT = 2;
	private static final float CAPACITY_FACTOR = 20f;
	
	/**
	 * Return the hero's capacity
	 * @return Return the hero's capacity
	 */
	public float getCapacity() {
		return CAPACITY_FACTOR*this.getStrength();
	}
	
	/*******************************
	 * Protection
	 *******************************/
	
	private static final int DEFAULT_PROTECTION = 10;
	
	/**
	 * Return the protection of the hero
	 * @return	Return the protection of the hero based on default protection value and armor
	 * 			| result == DEFAULT_PROTECTION + ((Armor)(this.getItemAt(AnchorType.BODY.getAnchorId()))).getProtection()
	 */
	@Override
	public int getProtection() {
		int anchorIdOfBody = AnchorType.BODY.getAnchorId();
		Armor armorOfHero = (Armor)(this.getItemAt(anchorIdOfBody));
		return(DEFAULT_PROTECTION + armorOfHero.getProtection());
		
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
	public boolean wantsToTake(Item item) { 
		if (item.isArmor()) {
			Armor armor = (Armor) item;
			Set<Entry<Integer, Item>> set = this.getAnchorEntrySet();
			int bestFullProtection = 0;
			
			for (Entry<Integer, Item> entry : set) {
				Item heroItem = entry.getValue();
				if (heroItem.isArmor()) {
					Armor heroArmor = (Armor) heroItem;
					int heroArmorProtection = heroArmor.getFullProtection();
					if (heroArmorProtection > bestFullProtection) {
						bestFullProtection = heroArmorProtection;
					}
				}
			}
			
			if (armor.getFullProtection() > bestFullProtection) {
				return true;
			} else {
				return false;
			}
			
		}
		
		else if (item.isWeapon()) {
			Weapon weapon = (Weapon) item;
			Set<Entry<Integer, Item>> set = this.getAnchorEntrySet();
			int bestDamage = 0;
			
			for (Entry<Integer, Item> entry : set) {
				Item heroItem = entry.getValue();
				if (heroItem.isWeapon()) {
					Weapon heroWeapon = (Weapon) heroItem;
					int heroWeaponDamage = heroWeapon.getDamage();
					if (heroWeaponDamage > bestDamage) {
						bestDamage = heroWeaponDamage;
					}
				}
			}
			
			if (weapon.getDamage() > bestDamage) {
				return true;
			} else {
				return false;
			}
		}
		
		else if (item.isBackpack()) {
			return false;
			
		}
		
		else if (item.isPurse()) {
			return false;
			
		} else {
			return false;
		}
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
	 * Heals the hero by adding hitpoints
	 * @post	The hero's new hp is a random prime number between its current health and its max health.
	 * 			| MathHelper.isPrime(getHitpoints())
	 */
	public void heal() {
		int newHitpoints;
		do {
			newHitpoints = MathHelper.getRandomIntBetweenRange(getHitpoints(), getMaxHitpoints());
		} while (!MathHelper.isPrime(newHitpoints));
		
		setHitpoints(newHitpoints);
	}
	

	
	/**
	 * Return the damage of the hero
	 * @return	Return the damage of the hero as the sum of his weapons and strength minus ten and divided by two.
	 * 			When this result is negative, zero is returned.
	 * 			| result = (int)((float)(AnchorType.RIGHT_HAND.getAnchorId() + AnchorType.LEFT_HAND.getAnchorId() + this.getStrength() -10)/2)
	 * 			
	 */
	@Override
	public int getDamage() {
		int anchorIdOfRightHand = AnchorType.RIGHT_HAND.getAnchorId();
		int anchorIdOfLeftHand = AnchorType.LEFT_HAND.getAnchorId();
		float strengthOfHero = this.getStrength();
		float sum = (float)(anchorIdOfRightHand + anchorIdOfLeftHand + strengthOfHero -10);
		int result = (int)(sum/2);
		if(result < 0) {
		return 0;
		}
		return result;
		
		}
	
	/**
	 * 
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
	 * Return true when the given item can be equipped
	 * @param 	item
	 * 			the item to be checked
	 * @return	Return true when the given item can be equipped
	 * 			| ...
	 */
	@Override
	public boolean canEquip(int anchorId, Item item) {
		if (super.canEquip(anchorId, item)) {
			if (AnchorType.getTypeFromId(anchorId).holdsPurse() == true) {
				return item.isPurse(); // true als purse, false als geen purse
			} else {
				return !(item.isPurse()); // true als geen purse, false als purse
			}
		} else {
			return false;
		}
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
	public void equip(AnchorType anchorType, Item item) {
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
	public void unequip(AnchorType anchorType) {
		int anchorId = anchorType.getAnchorId();
		unequip(anchorId);
	}
	
	/**
	 * Return the amount of armor's owned by this character
	 * @return	Return the amount of armor's owned by this character, also looks inside owned backpacks
	 */
	public int getArmorCount () {
		int armorCount = 0;
		
		for (Entry<Integer, Item> entry : getAnchorEntrySet()) {
			Item item = entry.getValue();
			if (item.isArmor()) {
				armorCount += 1;
			} else if (item.isBackpack()) {
				armorCount += ((Backpack)item).getArmorCount();
			}
		}
		return armorCount;
	}
	
	private static final int DEFAULT_ARMOR_PROTECTION = 15;
	private static final int DEFAULT_ARMOR_WEIGHTPERCENTAGE = 10;
	private static final int DEFAULT_FULLVALUE = 100;
	
	private static final int DEFAULT_PURSE_CAPACITY = 100;
	private static final float DEFAULT_PURSE_WEIGHT = 0.5f;
	
	/**
	 * Generate and equip the starter gear for a hero.
	 * 
	 */
	private void giveStarterGear() {
		
		float armorWeight = this.getCapacity()/DEFAULT_ARMOR_WEIGHTPERCENTAGE;
		Armor starterArmor = new Armor(MathHelper.getRandomPrime(), DEFAULT_ARMOR_PROTECTION, armorWeight, DEFAULT_FULLVALUE);
		
		int maxContent = (int)(((float)this.getCapacity() - armorWeight - DEFAULT_PURSE_WEIGHT)/Purse.getDucateWeight());
		int randomContent = MathHelper.getRandomIntBetweenRange(0, Math.min(maxContent, DEFAULT_PURSE_CAPACITY)); 
		Purse starterPurse = new Purse(DEFAULT_PURSE_WEIGHT, DEFAULT_PURSE_CAPACITY , randomContent);
		
		this.equip(AnchorType.BODY, starterArmor);
		this.equip(AnchorType.BELT, starterPurse);
	}
	
	
	
	
	
}
