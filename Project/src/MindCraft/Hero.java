package MindCraft;

/**
 * A class of Heroes. 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */

public class Hero extends Character {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * 
	 * @param name
	 * @param hitpoints
	 * @param strength
	 * 
	 * @post	Creates a hero with the given name
	 * 			| new.getName() == name
	 * @post	Creates a hero with the given hitpoints
	 * 			| new.getHitpoints() == hitpoints
	 * @post	Creates a hero with the given strength
	 * 			| new.getStrength() == strength
	 * @throws	IllegalArgumentException
	 * 			Throws this exception when the given name is not valid
	 * 			| !isValidName(name)
	 */
	public Hero(String name, int hitpoints, float strength) throws IllegalArgumentException {
		super(name, hitpoints);
		
		setStrength(strength);
		
	}
	
	/********************************
	 * Name - defensive programming
	 ********************************/
	
	/**
	 * Checks if a hero can have the given name as name
	 */
	@Override
	public boolean canHaveAsName(String name) {
		return false;
	}
	
	
	/*******************************
	 * Strength - total programming
	 *******************************/
	
	private float strength;
	
		/**
		 * Returns the character's strength
		 * @return Returns the character's strength
		 */
	public float getStrength() {
		return this.strength;
	
	}
	
	/**
	 * Sets the strength to the given strength
	 * @param strength
	 * 		  the new value of the hero's strength
	 * @post  The strength is set to the given strength
	 * 		  | new.getStrength == strength
	 */
	private void setStrength(float strength) {
		this.strength = strength;
	}
	
	
	/***********************
	 * Capacity
	 ***********************/
	
	private static final int MAX_ARMOR_COUNT = 2;
	
	/**
	 * Returns the hero's capacity
	 * @return Returns the hero's capacity
	 */
	public float getCapacity() {
		return 20*this.getStrength();
	}
	
	
	
	/***********************
	 * Other methods
	 ***********************/
	
	
	/**
	 * Returns whether or not the hero wants to take an item
	 * 
	 * @return Returns true when the hero wants to take the item
	 * @return Returns false when the hero does not want to take the item
	 */
	@Override
	public boolean wantsToTakeItem(Item item) {
		return false;
	}
	
	/**
	 * 
	 */
	public int getDamage() {
		return 0;
	}
	
	/**
	 * Makes the character hit the given character
	 * @post	A random number between 0 and 100 is generated, when this number is higher than the 
	 * 			character's protection, the character takes the damage of this hero. When this number is
	 * 			lower than the character's protection, nothing happens.
	 * 			| character.takeDamage(this.getDamage())
	 * 
	 */
	public void hit(Character character) {
		int rnd = (int)MathHelper.getRandomLongBetweenRange(0, 100);
		
		if (rnd >= character.getProtection()) {
			int damage = getDamage();
			
			character.takeDamage(damage);
			
			if (character.isDead()) {
				heal();
				setFighting(false);
				collectTreasures(character);
			}
		}
		
	}
	
	/**
	 * Heals the hero by adding hitpoints
	 * @post	The hero's new hp is a random prime number between its current health and its max health.
	 * 			| MathHelper.isPrime(getHitpoints())
	 */
	public void heal() {
		int newHitpoints;
		do {
			newHitpoints = (int)MathHelper.getRandomLongBetweenRange(getHitpoints(), getMaxHitpoints());
		} while (!MathHelper.isPrime(newHitpoints));
		
		setHitpoints(newHitpoints);
	}
	
	private static final int DEFAULT_PROTECTION = 10;
	/**
	 * Return the protection of the hero
	 * @return	Return the protection of the hero based on default protection value and armor
	 */
	@Override
	public int getProtection() {
		return 0;
	}
	
	
	@Override
	public boolean canPickUpItem(Item item) {
		if(!super.canPickUpItem(item)){
			return false;
		}
		else if(item instanceof Armor && this.getArmorCount() >= MAX_ARMOR_COUNT){
			return false;
			
		}
		return true;

	}
	

}
