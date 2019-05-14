package MindCraft;

/**
 * A class of monsters.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */

public class Monster extends Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Creates a monster.
	 * @post	Creates a monster with the given name.
	 * @post	Creates a monster with the given hitpoints.
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid.
	 */
	
	public Monster(String name, int hitpoints, int damage, int protection) throws IllegalArgumentException {
		super(name, hitpoints);
		setDamage(damage);
		setProtection(protection);
	}
	
	/********************************
	 * Damage - nominal programming
	 ********************************/
	
	/**
	 * Variable referencing the damage of a monster.
	 */
	private int damage = 0;
	
	/**
	 * Set the damage of a monster to a given amount of damage.
	 * 
	 * @param	damage
	 * 			The new damage.
	 * @post	The damage is set to the given damage.
	 */
	private void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Return the damage of this monster.
	 * @return	Return the damage of this monster.
	 */
	public int getDamage() {
		return this.damage;
	}

	
	/********************************
	 * Protection
	 ********************************/
	
	/**
	 * Variable referencing the protection of a monster.
	 */
	private int protection = 0;
	
	/**
	 * Set the protection of a monster to a given amount of protection.
	 * 
	 * @param	protection
	 * 			The new protection.
	 * @post	The protection is set to the given protection.
	 */
	private void setProtection(int protection) {
		this.protection = protection;
	}
	
	
	/**
	 * Return the protection of the monster
	 * @return	Return the protection of the monster
	 */
	@Override
	public int getProtection() {
		return this.protection;
	}
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * Return a boolean whether the monster wants to take this item
	 * @return	Returns true if the monster wants to take this item
	 * 			Returns false when the monster doesn't want to take this item
	 */
	@Override
	public boolean wantsToTakeItem(Item item) {
		return false;
	}
	
	/**
	 * This monster hits the given character.
	 * @post	The character that was hit by this monster will take damage.
	 */
	@Override
	public void hit(Character character) {
		
	}
}
