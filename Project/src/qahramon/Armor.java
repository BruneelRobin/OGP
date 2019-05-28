package qahramon;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.TerminatedException;

/**
 * A class of armors as special kinds of items involving a protection 
 * and full protection.
 * 
 * @invar	Each armor must have a valid full protection.
 * 			| isValidFullProtection(getFullProtection())
 * @invar	Each armor must have a valid protection.
 * 			| canHaveAsProtection(getProtection())
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */
public class Armor extends Item {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create armor with given identification, protection, weight and fullValue.
	 * 
	 * @param	identification
	 * 			the identification of this armor
	 * @param 	protection
	 * 			the full protection and current protection of this armor
	 * @param 	weight
	 * 			the weight of this armor
	 * @param 	fullValue
	 * 			the maximum value of this armor
	 * @pre		The given protection must be valid.
	 * 			| isValidFullProtection(protection)
	 * @effect	The armor is set as an item with given the identification, weight and maximum value.
	 * 			| super(identification, weight, fullValue)
	 * @post	Set the current protection to the given value.
	 * 			| new.getProtection() = protection
	 * @post	Set the full protection to the given value.
	 * 			| new.getFullProtection() = protection
	 */
	public Armor(long identification, int protection, float weight, int fullValue) {
		super(identification, weight, fullValue);
		setProtection(protection);
		this.fullProtection = protection;
		armorIds.add(getIdentification());
	}
	
	/***********************
	 * Class type
	 ***********************/
	
	/**
	 * Check whether this item is an armor.
	 * 
	 * @return	Always return true since this item is an armor.
	 * 			| result == true
	 */
	@Basic@Immutable@Override@Raw
	public boolean isArmor () {
		return true;
	}
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	/**
	 * Return a valid identification number for this class.
	 * 
	 * @return	Return a random valid identification number for this class.
	 * 			| result == MathHelper.getRandomPrime() && canHaveAsNewIdentification(result)
	 */
	@Override@Raw
	protected long generateIdentification() {
		long prime = MathHelper.getRandomPrime();
		
		if (!canHaveAsNewIdentification (prime)) {
			return generateIdentification();
		} else {
			//System.out.println(prime);
			return prime;
		}
	}
	
	/**
	 * Check whether the given identification is valid.
	 * 
	 * @param 	identification
	 * 			the identification to check
	 * @return	Return true when this armor can have the given identification number.
	 * 			Return false otherwise.
	 * 			| result == (MathHelper.isPrime(identification) && identification > 0)
	 */
	@Override@Raw
	public boolean canHaveAsIdentification(long identification) {
		return (MathHelper.isPrime(identification) && identification>0);
	}
	
	/**
	 * Check whether the given identification is valid and unique.
	 * 
	 * @param	identification
	 * 			the identification to check
	 * @return	Return true when the given identification is valid and unique.
	 * 			Return false otherwise.
	 * 			| result == canHaveAsIdentification (identification) 
	 * 							&& !armorIds.contains(identification)
	 */
	@Override@Raw
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && !armorIds.contains(identification);
	}

	/**
	 * Variable referencing a set with all armoridentifications of this class. 
	 * 
	 * @invar Each non null element in the hashSet references an effective identification. 
	 *        | for (Long id : armorIds)
	 *        | 	id != null
	 * @invar Each non null element in the hashSet references a valid identification. 
	 *        | for (Long id : armorIds)
	 *        | 	canHaveAsIdentification(id)
	 */
	private static final HashSet<Long> armorIds = new HashSet<Long>();


	/***********************
	 * Protection (nominal)
	 ***********************/	
	
	/**
	 * Variable registering the maximum fullProtection for all armors.
	 */
	private static final int MAX_FULLPROTECTION = 100;
	
	/**
	 * Variable registering the minimum fullProtection for all armors.
	 */
	private static final int MIN_FULLPROTECTION = 1;
	
	/**
	 * Variable registering the minimum protection an armor can have.
	 */
	private static final int MIN_PROTECTION = 0;
	
	/**
	 * Variable registering the maximum protection of an armor.
	 */
	private final int fullProtection;
	
	/**
	 * Variable registering the current protection of an armor.
	 */
	private int protection;
	
	
	/**
	 * Return the maximum fullProtection allowed for all armors.
	 * 
	 * @return	Return the maximum fullProtection allowed for all armors.
	 */
	@Basic @Immutable
	public static int getMaxFullProtection() {
		return MAX_FULLPROTECTION;
	}
	
	/**
	 * Return the minimum fullProtection allowed for all armors.
	 * 
	 * @return	Return the minimum fullProtection allowed for all armors.
	 */
	@Basic @Immutable
	public static int getMinFullProtection() {
		return MIN_FULLPROTECTION;
	}
	
	/**
	 * Return the minimum protection allowed for an armors.
	 * 
	 * @return	Return the minimum protection allowed for an armors.
	 */
	@Basic @Immutable
	public static int getMinProtection() {
		return MIN_PROTECTION;
	}
	
	/**
	 * Return the fullProtection of an armor.
	 * 
	 * @return Return the fullProtection of an armor.
	 */
	@Basic@Immutable
	public int getFullProtection() {
		return this.fullProtection;
	}
	
	/**
	 * Return the armor's current protection.
	 * 
	 * @return Return the armor's current protection.
	 */
	@Basic
	public int getProtection() {
		return this.protection;

	}
	
	/**
	 * Set the protection to the given protection.
	 * 
	 * @param protection
	 * 		  the new value of the armor's protection
	 * @post  The protection is set to the given protection.
	 * 		  | new.getProtection() == protection
	 */
	@Raw
	private void setProtection(int protection) {
		this.protection = protection;
	
	}
	
	/**
	 * Check whether this armor can have the given protection as protection.
	 * 
	 * @param	protection
	 * 			the protection to check
	 * @return	Return true when the given protection lies between MIN_PROTECTION and its fullProtection.
	 * 			Return false otherwise.
	 * 			| (protection >= MIN_PROTECTION && protection <= this.getFullProtection())
	 */
	@Raw
	public boolean canHaveAsProtection (int protection) {
		return (protection >= MIN_PROTECTION && protection <= this.getFullProtection());
	}
	
	/**
	 * Check whether this armor can have the given fullProtection as fullProtection.
	 * 
	 * @param	fullProtection
	 * 			the fullProtection to check
	 * @return	Return true when the given fullProtection lies between MIN_FULLPROTECTION and MAX_FULLPROTECTION.
	 * 			Return false otherwise.
	 * 			| result == (fullProtection >= MIN_FULLPROTECTION && fullProtection <= MAX_FULLPROTECTION)			
	 */
	public static boolean isValidFullProtection(int fullProtection) {
		return (fullProtection >= MIN_FULLPROTECTION && fullProtection <= MAX_FULLPROTECTION);
	}
	
	/**
	 * Decrease the protection of this armor.
	 * 
	 * @param 	amount
	 * 		  	amount by which the protection should be decreased
	 * @pre   	The given amount has to be positive.
	 * 			| amount >= 0
	 * @pre	  	The protection still has to be valid after the decrease with the given amount.
	 * 			| canHaveAsProtection(getProtection() - amount)
	 * @post  	The protection of the armor is decreased by the given amount.
	 * 			| new.getProtection() = this.getProtection() - amount
	 * @throws	TerminatedException
	 * 			Throws this exception when this armor is terminated.
	 * 			| this.isTerminated()
	 */
	public void wearOut(int amount) throws TerminatedException {
		if(this.isTerminated()) {
			throw new TerminatedException(this);
		}
		this.setProtection(this.getProtection() - amount );
		
	}
	
	/**
	 * Increase the protection of this armor.
	 * 
	 * @param 	amount
	 * 		  	amount by which the protection should be increased
	 * @pre   	The given amount has to be positive.
	 * 			| amount >= 0
	 * @pre   	The protection still has to be valid after the increase with the given amount.
	 * 			| canHaveAsProtection(getProtection() + amount)
	 * @post  	The protection of the armor is increased by the given amount.
	 * 			| new.getProtection() = this.getProtection() + amount
	 * @throws	TerminatedException
	 * 			Throws this exception when this armor is terminated.
	 * 			| this.isTerminated()
	 */
	public void repair(int amount) throws TerminatedException {
		if(this.isTerminated()) {
			throw new TerminatedException(this);
		}
		this.setProtection(this.getProtection() + amount);
		
	}
	
	/***********************
	 * Value
	 ***********************/
	
	/**
	 * Return the maximum value for this item.
	 * 
	 * @return	Return the maximum value for this item.
	 */
	@Immutable@Override@Basic@Raw
	public int getMaxValue () {
		return 1000;
	}
	
	/**
	 * Return the minimum value for this item.
	 * 
	 * @return	Return the minimum value for this item.
	 */
	@Immutable@Override@Basic@Raw
	public int getMinValue () {
		return 0;
	}
	
	/**
	 * Return the value of the armor.
	 * 
	 * @return Return the value as its function: highest possible value of the armor times
	 * 		   the current protection percentage of the armor. 
	 * 		   | result == (int)((float)getFullValue()*((float)getProtection()/(float)getFullProtection()))
	 */
	@Override@Raw
	public int getValue() { 
		return (int)((float)getFullValue()*((float)getProtection()/(float)getFullProtection()));
	}
	
	/**
	 * Return the fullValue of the armor.
	 * 
	 * @return Return the fullValue of the armor.
	 * 		   | result == super.getValue()
	 */
	@Basic@Immutable
	public int getFullValue() {
		return super.getValue();
	}
	
	/**
	 * Return a string containing all public data of this armor.
	 * 
	 * @return Return a string containing all public data of this armor.
	 */
	@Override
	public String toString() {
		return "Armor\n" + super.getString()
				+ "\nProtection: " + getProtection() + "\n";
	}
}
