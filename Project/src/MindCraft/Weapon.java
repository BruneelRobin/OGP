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
	 * Checks whether the given damage is a valid damage
	 * @returns true if the given damage is divisible by seven, greater than 1 and smaller than 100
	 * @returns false if the given damage is not valid
	 */
	public static boolean isValidDamage(int damage) {
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
