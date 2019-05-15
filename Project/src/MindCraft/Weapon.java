package MindCraft;

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
	 * TODO: implementeren
	 * 
	 * @param	identification
	 * @param	weight
	 * @param	value
	 * @param	holder
	 * @param 	parentBackpack
	 */
	public Weapon(int damage, float weight, int value) {
		super(weight, value);
		//if (!isValidDamage(damage)) {
		//	assert false; // preconditie
		//}
		
		
		setDamage(damage); //nominaal dus condities OK
		this.hasGivenValue = true;
	}
	
	/**
	 * TODO: implementeren
	 * 
	 * @param	identification
	 * @param	weight
	 * @param	holder
	 * @param 	parentBackpack
	 */
	public Weapon(int damage, float weight) {
		super(weight, 0);
		//if (!isValidDamage(damage)) {
		//	assert false; // preconditie
		//}
		
		
		setDamage(damage); //nominaal dus condities OK
		this.hasGivenValue = false;
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
		
	}
	
	
	
	/***********************
	 * Value
	 ***********************/
	
	private final int MAX_VALUE = 200;
	private final int MIN_VALUE = 0;
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
		return MathHelper.clamp(this.getDamage()*VALUE_FACTOR, MIN_VALUE, MAX_VALUE); 
		}
		return super.getValue();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
