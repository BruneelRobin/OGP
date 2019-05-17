package MindCraft;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of weapons.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */
public class Weapon extends Item {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a weapon with given damage, weight and value.
	 * 
	 * @param	identification
	 * 			The identification of this weapon.
	 * @param	weight
	 * 			The weight of this weapon.
	 * @param	value
	 * 			The value of this weapon.
	 * @pre		The given damage must be valid.
	 * 			| isValidDamage(damage)
	 * @effect	The new weapon is set as an item with given weight and value.
	 * 			| super(weight, value)
	 * @post	The damage is set to the given damage.
	 * 			| setDamage(damage)
	 * @post	The value has a given value.
	 * 			| new.hasGivenValue
	 */
	public Weapon(int damage, float weight, int value) {
		super(weight, value);
		setDamage(damage);
		this.hasGivenValue = true;
		ids.add(getIdentification());
	}
	
	/**
	 * Create a weapon with given damage and value.
	 * @param	identification
	 * 			The identification of this weapon.
	 * @param	weight
	 * 			The weight of this weapon.
	 * @pre		The given damage must be valid.
	 * 			| isValidDamage(damage)
	 * @effect	The new weapon is set as an item with given weight and 0 value.
	 * 			| super(weight, 0)
	 * @post	The damage is set to the given damage.
	 * 			| setDamage(damage)
	 * @post	The value has no given value.
	 * 			| !new.hasGivenValue
	 */
	public Weapon(int damage, float weight) {
		super(weight, 0);
		setDamage(damage);
		this.hasGivenValue = false;
		ids.add(getIdentification());
	}
	
	
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	/**
	 * Generates a valid identification.
	 * 
	 * @return Returns a unique, positive long, divisible by 6.
	 * 		   
	 */
	@Override
	protected long generateIdentification() {
		long flooredMax = Long.MAX_VALUE/6;
		long generatedNumber = MathHelper.getRandomLongBetweenRange(0, flooredMax);
		long candidate = generatedNumber*6;
		if(!canHaveAsIdentification(candidate)) {
			assert false;
		}
		
		return candidate;
	}
	
	/**
	 * Returns whether or not the given identification is a valid one.
	 * @param 	identification
	 * 			The identification to check
	 * @return	Return true when the given identification number is positive, divisible by 6 and unique.
	 * 			| result == ((identification > -1) && (identification%6 == 0))
	 * 			
	 * @return	Return false when the given identification is not positive and/or not divisible by 6
	 * @note Uniqueness is always considered true, the chance of colliding is not zero, but neglectable (6.5*10^(-19)).
	 * 			
	 */
	@Override
	public boolean canHaveAsIdentification(long identification) {
		return ((identification > -1) && (identification%6 == 0));

	}
	
	/**
	 * @param	identification
	 * 			The identification to check
	 * @return	Return true when this weapon can have the given identification number that has to be unique
	 * 			Return false when this weapon can't
	 * 			| result == canHaveAsIdentification (identification) 
	 * 							&& ids.contains(identification) == false
	 */
	@Override
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && ids.contains(identification) == false;
	}
	
	private static final HashSet<Long> ids = new HashSet<Long>();
	
	
	
	
	
	/********************************
	 * Damage - nominal programming
	 ********************************/

	/**
	 * Class variable referencing the maximum damage of any weapon.
	 */
	private static int MAX_DAMAGE = 100;
	
	/**
	 * Class variable referencing the minimum damage of any weapon.
	 */
	private static int MIN_DAMAGE = 1;
	
	/**
	 * Checks whether the given damage is valid
	 * @param	damage
	 * 			The damage to check
	 * @return 	True if the given damage is divisible by seven, greater than 1 and smaller than 100
	 * @return 	False if the given damage is not valid
	 */
	public static boolean isValidDamage(int damage) {
		return false;
	}
	
	/**
	 * Variable referencing the damage of a weapon.
	 */
	private int damage = 0;
	
	/**
	 * Set the damage of a weapon to a given amount of damage.
	 * 
	 * @param	damage
	 * 			The new damage.
	 * @post	The damage is set to the given damage.
	 * 			| new.getDamage() == damage
	 */
	private void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Return the damage of this weapon.
	 * @return	Return the damage of this weapon.
	 */
	public int getDamage() {
		return this.damage;
	}
	
	
	/**
	 * Upgrades the weapon to a higher damage
	 * @pre the given amount must be positive
	 * @pre the result has to be valid
	 * 		| isValidDamage(this.getDamage() + amount) == true
	 * @post The damage of the weapon is incremented with the given amount
	 * 		| new.getDamage() == this.getDamage() + amount
	 * 
	 */
	public void upgrade(int amount) {
		this.setDamage(this.getDamage() + amount);
		}
	
	
	/**
	 * Downgrades the weapon to a lower damage
	 * @pre the given amount must be positive
	 * @pre the result has to be valid
	 * 		| isValidDamage(this.getDamage() - amount) == true
	 * @post The damage of the weapon is decremented with the given amount
	 * 		| new.getDamage() == this.getDamage() - amount
	 * 
	 */
	public void downgrade(int amount) {
		this.setDamage(this.getDamage() - amount);
		
	}
	
	
	
	/***********************
	 * Value
	 ***********************/
	
	/**
	 * Return the maximum value for this item
	 * @return	Return the maximum value for this item
	 */
	@Immutable@Override
	public int getMaxValue () {
		return 200;
	}
	
	/**
	 * Return the minimum value for this item
	 * @return	Return the minimum value for this item
	 */
	@Immutable@Override
	public int getMinValue () {
		return 0;
	}
	
	private static final int VALUE_FACTOR = 2;
	private final boolean hasGivenValue;
	
	/**
	 * Returns the value of the weapon.
	 * @return if a value was given in the constructor, return that value
	 * 		   | result == super.getValue()
	 * @return if no value was given in the constructor, return the value as its function: damage of the weapon times
	 * 		   the defined value factor. Clamp the result to make sure it lies between the MIN_VALUE and MAX_VALUE.
	 * 		   | result == this.getDamage()*VALUE_FACTOR
	 */
	@Override
	public int getValue() {
		if(hasGivenValue == false) {
		return MathHelper.clamp(this.getDamage()*VALUE_FACTOR, getMinValue(), getMaxValue()); 
		}
		return super.getValue();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
