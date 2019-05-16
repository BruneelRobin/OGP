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
	 * 
	 * @param protection
	 * @param fullProtection
	 * @param weight
	 * @param value
	 * @param holder
	 * @param parentBackpack
	 * @pre		The given full protection is valid
	 * 			| isValidProtection (fullProtection)
	 * @pre		The given protection is valid
	 * 			| isValidProtection(fullProtection) && protection <= fullProtection
	 */
	
	protected Armor(long identification, int protection, int fullProtection, float weight, int value) {
		super(identification, weight, value);
		
		if (!isValidIdentification(identification)) {
			setIdentification(generateIdentification());
		}
		
		this.currentProtection = protection;
		this.fullProtection = fullProtection;
		ids.add(getIdentification());
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
	 * @return	Return true when this item can have the given identification number
	 * 			Return false when this item can't have the given identification number
	 * 			| result == MathHelper.isPrime(identification) && ids.contains(identification) == false
	 */
	@Override
	public boolean canHaveAsIdentification(long identification) {
		return MathHelper.isPrime(identification) && ids.contains(identification) == false;
	}

	private static final HashSet<Long> ids = new HashSet<Long>();


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
	 * @param amount
	 * 		  amount by which the protection should be decreased
	 * @pre   the given amount has to be positive
	 * @pre	  the protection still has to be valid after the decrease with the given amount
	 * @post  the protection of the armor is decreased by the given amount
	 */
	public void wearOut(int amount) {
		this.setProtection(this.getProtection() - amount );
		
	}
	
	/**
	 * Increase the protection of an armor
	 * @param amount
	 * 		  amount by which the protection should be increased
	 * @pre   the given amount has to be positive
	 * @pre   the protection still has to be valid after the increase with the given amount
	 * @post  the protection of the armor is increased by the given amount
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
		return this.getValue()*(this.getProtection()/this.getFullProtection());
	
	}
	
	
	
	/**
	 * Returns whether or not the given value is valid
	 * @param value
	 * 		  the integer checked for its validity
	 * @return returns true if the given value is an even number that lies between the minimum and maximum value
	 * 		   | (value >= MIN_VALUE && value <= MAX_VALUE && value%2 == 0)
	 * @return returns false if the given value is not even and/or does not lie between the minimum and maximum.
	 * 
	 */
	@Override
	public boolean canHaveAsValue(int value) {
		return ((value >= MIN_VALUE) && (value <= MAX_VALUE) && (value%2 == 0));
		
		}
	
	
	
	
	/***********************
	 * Other Methods
	 ***********************/
	
}
