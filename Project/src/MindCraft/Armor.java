package MindCraft;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of armors.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */
public class Armor extends Item {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create armor with given identification, pro
	 * @param	identification
	 * 			The identification of this armor.
	 * @param 	protection
	 * 			The full protection and current protection of this armor.
	 * @param 	weight
	 * 			The weight of this armor.
	 * @param 	fullValue
	 * 			The maximum value of this armor.
	 * @pre		The given protection must be valid.
	 * 			| isValidProtection(fullProtection)
	 * @effect	The armor is set as an item with given identification, weight and maximum value.
	 * 			| super(identification, weight, fullValue)
	 * @post	
	 */
	
	protected Armor(long identification, int protection, float weight, int fullValue) {
		super(identification, weight, fullValue);
		this.currentProtection = protection;
		this.fullProtection = protection;
		armorIds.add(getIdentification());
	}
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	/**
	 * Return a valid identification number for this class
	 * @return	Return a random valid identification number for this class
	 */
	@Override
	protected long generateIdentification() {
		long min = 3L;
		long max = Long.MAX_VALUE;
		long candidate = MathHelper.getRandomLongBetweenRange(min, max);
		
		if (!canHaveAsIdentification (candidate)) {
			return generateIdentification();
		} else {
			return candidate;
		}
	}
	
	/**
	 * 
	 * @param 	identification
	 * 			The identification to check
	 * @return	Return true when this armor can have the given identification number
	 * 			Return false when this armor can't have the given identification number
	 * 			| result == MathHelper.isPrime(identification)
	 */
	@Override
	public boolean canHaveAsIdentification(long identification) {
		return MathHelper.isPrime(identification);
	}
	
	/**
	 * @param	identification
	 * 			The identification to check
	 * @return	Return true when this armor can have the given identification number that has to be unique
	 * 			Return false when this armor can't
	 * 			| result == canHaveAsIdentification (identification) 
	 * 							&& ids.contains(identification) == false
	 */
	@Override
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && armorIds.contains(identification) == false;
	}

	/**
	 * Variable referencing a set with all ids of this class. 
	 * 
	 * @invar Each non null element in the hashset references an effective item. 
	 *        | for (Item item : ids)
	 *        | 	item != null
	 */
	private static final HashSet<Long> armorIds = new HashSet<Long>();


	/***********************
	 * Protection (nominal)
	 ***********************/	
	
	private static final int MAX_FULLPROTECTION = 100;
	private static final int MIN_FULLPROTECTION = 1;
	private static final int MIN_PROTECTION = 0;
	private final int fullProtection;
	private int currentProtection;
	
	/**
	 * Returns the fullProtection of an armor
	 * @return Returns the fullProtection of an armor
	 * 		   | result == this.fullProtection
	 */
	public int getFullProtection() {
		return this.fullProtection;
	}
	
	
	/**
	 * Returns the armor's current protection
	 * @return Returns the armor's current protection
	 */
	public int getProtection() {
		return this.currentProtection;

	}
	
	

	/**
	 * Sets the protection to the given protection
	 * @param protection
	 * 		  the new value of the armor's protection
	 * @post  The protection is set to the given protection
	 * 		  | new.getProtection == protection
	 */
	@Raw
	private void setProtection(int protection) {
		currentProtection = protection;
	
	}
	
	/**
	 * Returns whether this armor can have the given protection as protection
	 * @param	protection
	 * 			The protection to check
	 * @return	Return true when the given protection lies between MIN_PROTECTION and its fullProtection
	 * 			
	 */
	public boolean canHaveAsProtection (int protection) {
		return (protection >= MIN_PROTECTION && protection <= this.getFullProtection());
	}
	
	/**
	 * Returns whether this armor can have the given fullProtection as fullProtection
	 * @param	fullProtection
	 * 			The fullProtection to check
	 * @return	Return true when the given fullProtection lies between MIN_FULLPROTECTION and MAX_FULLPROTECTION
	 * 			
	 */
	public boolean isValidFullProtection(int fullProtection) {
		return(fullProtection >= MIN_FULLPROTECTION && fullProtection <= MAX_FULLPROTECTION);
	}
	
	/**
	 * Decrease the protection of an armor
	 * @param 	amount
	 * 		  	amount by which the protection should be decreased
	 * @pre   	the given amount has to be positive
	 * 			| amount > 0
	 * @pre	  	the protection still has to be valid after the decrease with the given amount
	 * 			| canHaveAsProtection(getProtection() - amount)
	 * @post  	the protection of the armor is decreased by the given amount
	 * 			| new.getProtection() = this.getProtection() - amount
	 */
	public void wearOut(int amount) {
		this.setProtection(this.getProtection() - amount );
		
	}
	
	/**
	 * Increase the protection of an armor
	 * @param 	amount
	 * 		  	amount by which the protection should be increased
	 * @pre   	the given amount has to be positive
	 * 			| amount > 0
	 * @pre   	the protection still has to be valid after the increase with the given amount
	 * 			| canHaveAsProtection(getProtection() + amount)
	 * @post  	the protection of the armor is increased by the given amount
	 * 			| new.getProtection() = this.getProtection() + amount
	 */
	public void repair(int amount) {
		this.setProtection(this.getProtection() + amount);
		
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
		return 1000;
	}
	
	/**
	 * Return the minimum value for this item
	 * @return	Return the minimum value for this item
	 */
	@Immutable@Override
	public int getMinValue () {
		return 0;
	}
	
	
	/**
	 * Returns the value of the armor.
	 * @return return the value as its function: highest possible value of the armor times
	 * 		   the current protection percentage of the armor. 
	 * 		   | result == this.getValue()*(this.getProtection()/this.getFullProtection())
	 */
	@Override
	public int getValue() { 
		return super.getValue()*(this.getProtection()/this.getFullProtection());
	}
	
	
	
	
	/***********************
	 * Other Methods
	 ***********************/
	
}
