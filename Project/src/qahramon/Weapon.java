package qahramon;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;

import qahramon.exceptions.*;

/**
 * A class of weapons as special kinds of items
 * involving damage.
 * 
 * @invar	Each weapon must have a valid damage.
 * 			| isValidDamage(getDamage())
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
	 * 			the identification of this weapon
	 * @param	weight
	 * 			the weight of this weapon
	 * @param	value
	 * 			the value of this weapon
	 * @pre		The given damage must be valid.
	 * 			| isValidDamage(damage)
	 * @effect	The new weapon is set as an item with the given weight and value.
	 * 			| super(weight, value)
	 * @post	The damage is set to the given damage.
	 * 			| new.getDamage() == damage
	 */
	public Weapon(int damage, float weight, int value) {
		super(weight, value);
		setDamage(damage);
		this.hasGivenValue = true;
		weaponIds.add(getIdentification());
	}
	
	/**
	 * Create a weapon with given damage and value.
	 * 
	 * @param	identification
	 * 			the identification of this weapon
	 * @param	weight
	 * 			the weight of this weapon
	 * @pre		The given damage must be valid.
	 * 			| isValidDamage(damage)
	 * @effect	The new weapon is set as an item with given weight and zero as value.
	 * 			| super(weight, 0)
	 * @post	The damage is set to the given damage.
	 * 			| new.getDamage() == damage
	 */
	public Weapon(int damage, float weight) {
		super(weight, 0);
		setDamage(damage);
		this.hasGivenValue = false;
		weaponIds.add(getIdentification());
	}
	
	/***********************
	 * Class type
	 ***********************/
	
	/**
	 * Check whether this item is a weapon.
	 * 
	 * @return	Always return true since this item is a weapon.
	 * 			| result == true
	 */
	@Basic@Immutable@Override@Raw
	public boolean isWeapon () {
		return true;
	}
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	/**
	 * Generate a valid identification.
	 * 
	 * @return 	Return a unique, positive long, divisible by 6.
	 * 			| result == MathHelper.getRandomLongBetweenRange(0, Long.MAX_VALUE/6)*6 
	 * 			|			&& canHaveAsNewIdentification(result)
	 */
	@Override@Raw
	protected long generateIdentification() {
		long flooredMax = Long.MAX_VALUE/6;
		long generatedNumber = MathHelper.getRandomLongBetweenRange(0, flooredMax);
		long candidate = generatedNumber*6;
		if(!canHaveAsNewIdentification(candidate)) { // this number already exists
			return generateIdentification();
		}
		
		return candidate;
	}
	
	/**
	 * Check whether the given identification is a valid one.
	 * 
	 * @param 	identification
	 * 			the identification to check
	 * @return	Return true when the given identification number is positive, divisible by 6.
	 * 			Return false otherwise.
	 * 			| result == ((identification >= 0) && (identification%6 == 0))			
	 */
	@Override@Raw
	public boolean canHaveAsIdentification(long identification) {
		return ((identification >= 0) && (identification%6 == 0));

	}
	
	/**
	 * Check whether the given identification is valid and unique.
	 * 
	 * @param	identification
	 * 			the identification to check
	 * @return	Return true when this weapon can have the given identification number that has to be unique.
	 * 			Return false otherwise.
	 * 			| result == canHaveAsIdentification (identification) 
	 * 							&& !weaponIds.contains(identification)
	 *
	 * @note	Uniqueness is always considered true, the chance of colliding is not zero, but neglectable (6.5*10^(-19)).
	 */
	@Override@Raw
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && !weaponIds.contains(identification);
	}
	
	/**
	 * Variable referencing a set with all weaponIds of this class. 
	 * 
	 * @invar Each non null element in the hashSet references an effective identification. 
	 *        | for (Long id : weaponIds)
	 *        | 	id != null
	 * @invar Each non null element in the hashSet references a valid identification. 
	 *        | for (Long id : weaponIds)
	 *        | 	canHaveAsIdentification(id)
	 */
	private static final HashSet<Long> weaponIds = new HashSet<Long>();
	
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
	 * Check whether the given damage is valid.
	 * 
	 * @param	damage
	 * 			the damage to check
	 * @return 	Return true if the given damage is divisible by seven, greater than 1 and smaller than 100.
	 * 		 	Return false otherwise.
	 * 			| (damage >= MIN_DAMAGE && damage <= MAX_DAMAGE && damage % 7 == 0)
	 */
	public static boolean isValidDamage(int damage) {
		return (damage >= MIN_DAMAGE && damage <= MAX_DAMAGE && damage % 7 == 0);
	}
	
	/**
	 * Variable referencing the damage of a weapon.
	 */
	private int damage;
	
	/**
	 * Set the damage of a weapon to a given amount of damage.
	 * 
	 * @param	damage
	 * 			the new damage
	 * @post	The damage is set to the given damage.
	 * 			| new.getDamage() == damage
	 */
	@Raw
	private void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Return the damage of this weapon.
	 * 
	 * @return	Return the damage of this weapon.
	 */
	@Basic
	public int getDamage() {
		return this.damage;
	}
	
	
	/**
	 * Upgrade the weapon to a higher damage.
	 * 
	 * @param	amount
	 * 			the amount to upgrade the weapon damage with
	 * @pre		The given amount must be positive.
	 * 			| amount >= 0
	 * @pre		The result has to be valid.
	 * 			| isValidDamage(this.getDamage() + amount)
	 * @post 	The damage of the weapon is incremented with the given amount
	 * 			| new.getDamage() == this.getDamage() + amount
	 * @throws	TerminatedException
	 * 		  	Throws this exception when this weapon is terminated.
	 * 		 	| isTerminated()
	 */
	public void upgrade(int amount) throws TerminatedException{
		if (this.isTerminated()) {
			throw new TerminatedException(this);
		} else {
		this.setDamage(this.getDamage() + amount);
		}
	}
	
	
	/**
	 * Downgrade the weapon to a lower damage.
	 * 
	 * @param	amount
	 * 			the amount to decrease the damage with
	 * @pre 	the given amount must be positive
	 * 			| amount >= 0
	 * @pre 	the result has to be valid
	 * 			| isValidDamage(this.getDamage() - amount)
	 * @post 	The damage of the weapon is decremented with the given amount
	 * 			| new.getDamage() == this.getDamage() - amount
	 * @throws	TerminatedException
	 * 		  	Throws this exception when this weapon is terminated.
	 * 			| isTerminated()
	 */
	public void downgrade(int amount) throws TerminatedException {
		if (this.isTerminated()) {
			throw new TerminatedException(this);
		} else {
		this.setDamage(this.getDamage() - amount);
		}
	}
	
	/***********************
	 * Value
	 ***********************/
	
	/**
	 * Return the maximum value for this weapon.
	 * 
	 * @return	Return the maximum value for this weapon.
	 */
	@Immutable@Override@Basic@Raw
	public int getMaxValue () {
		return 200;
	}
	
	/**
	 * Return the minimum value for this weapon.
	 * 
	 * @return	Return the minimum value for this weapon.
	 */
	@Immutable@Override@Basic@Raw
	public int getMinValue () {
		return 0;
	}
	
	/**
	 * Variable referencing the factor to multiply the damage of this weapon with to get the value.
	 */
	private static final int VALUE_FACTOR = 2;
	
	/**
	 * Variable referencing whether a value was given upon creation.
	 */
	private final boolean hasGivenValue;
	
	/**
	 * Return the value of the weapon.
	 * 
	 * @return 	When a value was given in the constructor, return that value.
	 * 		   	| result == super.getValue()
	 * @return 	When no value was given in the constructor, return the value as its function: damage of the weapon times
	 * 		   	the defined value factor. The result will be clamped between the value boundaries of this class.
	 * 		  	| result == this.getDamage()*VALUE_FACTOR
	 */
	@Override@Raw
	public int getValue() {
		if(hasGivenValue == false) {
			return MathHelper.clamp(this.getDamage()*VALUE_FACTOR, getMinValue(), getMaxValue()); 
		}
		return super.getValue();
	}
	
	/**
	 * Return a string containing all public data of this purse.
	 * 
	 * @return	Return a string containing all public data of this purse.
	 */
	@Override
	public String toString() {
		return "Weapon\n" + super.getString()
				+ "\nDamage: " + getDamage() + "\n";
	}
	
}
