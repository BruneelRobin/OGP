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
	return  ;

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
