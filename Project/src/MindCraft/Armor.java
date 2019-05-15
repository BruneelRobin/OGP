package MindCraft;

import java.util.HashSet;

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
		
		if (!isValidIdentification (candidate)) {
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
	public static boolean isValidIdentification(long identification) {
		return MathHelper.isPrime(identification) && ids.contains(identification) == false;
	}

	private static final HashSet<Long> ids = new HashSet<Long>();


	/***********************
	 * Protection
	 ***********************/	
	
	private static int MAX_PROTECTION = 100;
	private static int MIN_PROTECTION = 0;
	private final int fullProtection;
	private int currentProtection;
	
	/**
	 * Returns the fullprotection of an armor
	 * @return Returns the fullprotection of an armor
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
	private void setProtection(int protection) {
		currentProtection = protection;
	
	}
	
	/**
	 * Return true when the given protection is valid
	 * @param	protection
	 * 			The protection to check
	 * @return	Return true when the given protection is valid
	 * 			| result == ... 
	 */
	public static boolean isValidProtection (int protection) {
		return false;
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void wearOut(int amount) {
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void repair(int amount) {
		
	}
	
	
	
	/***********************
	 * Value
	 ***********************/
	
	private final int MAX_VALUE = 1000;
	private final int MIN_VALUE = 0;
	
	
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
	


	/**
	 * Class variable referencing the maximum value of any armor.
	 */
	private static int MAX_VALUE = 1000;
	
}
