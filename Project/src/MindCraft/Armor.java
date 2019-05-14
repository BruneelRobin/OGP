package MindCraft;

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
	
	protected Armor(int protection, int fullProtection, float weight, int value, Character holder, Backpack parentBackpack) {
		super(weight, value, holder, parentBackpack);
		
		this.currentProtection = protection;
		this.fullProtection = fullProtection;
	}
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	@Override
	protected long generateIdentification() {
		return 0;
	}
	
	/**
	 * 
	 * @param 	identification
	 * 			The identification to check
	 * @return	Return true when this item can have the given identification number
	 * 			Return false when this item can't have the given identification number
	 * 			| result = ...
	 */
	@Override
	public boolean canHaveAsIdentification(long identification) {
		return false;
	}




	/***********************
	 * Protection
	 ***********************/	
	
	private static int MAX_PROTECTION = 100;
	private static int MIN_PROTECTION = 0;
	private final int fullProtection;
	private int currentProtection;
	
	
	/**
	 * Returns the armor's protection
	 * @return Returns the armor's protection
	 */
	public float getProtection() {
		return 0f;

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
	 * Other Methods
	 ***********************/
	


	/**
	 * Class variable referencing the maximum value of any armor.
	 */
	private static int MAX_VALUE = 1000;
	
}
