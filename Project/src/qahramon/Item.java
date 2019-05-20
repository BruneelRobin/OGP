package qahramon;

import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.TerminatedException;

/**
 * A class of items
 * 
 * @invar 	Each item must have a unique and valid identification number
 * 			| canHaveAsIdentification(getIdentification())
 * @invar	Each item must have a valid weight
 * 			| isValidWeight(getWeight())
 * @invar	Each item must have a valid value
 * 			| canHaveAsValue(getValue())
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public abstract class Item {
	
	/***********************
	 * Constructors
	 ***********************/
	
	
	/**
	 * Create an item with given weight and value.
	 * @param 	weight
	 * 			The weight of this item.
	 * @param 	value
	 * 			The value of this item.
	 * 
	 * @post	The identification is set to a generated identification.
	 * 			| new.getIdentification() == generateIdentification()
	 * @post	The weight is set to the given weight.
	 * 			If the given weight is not valid, a default weight is set.
	 * 			| new.getWeight() == weight
	 * @post	The weight is valid after construction.
	 * 			| new.isValidWeight
	 * @post	The value of this item is set to the given value.
	 * 			| setValue(value)
	 */
	@Model
	protected Item(float weight, int value) {
		this.identification = generateIdentification();
		if (isValidWeight(weight)) {
			this.weight = weight;
		} else {
			this.weight = getDefaultWeight();
		}
		
		setValue(MathHelper.clamp(value, getMinValue(), getMaxValue())); //total programming 
	}
	
	/**
	 * Create an item with given identification, weight and value.
	 * @param 	identification
	 * 			The identification of this item.
	 * @param 	weight
	 * 			The weight of this item.
	 * @param 	value
	 * 			The value of this item.
	 * @post	The identification is set to the given identification
	 * 			| new.getIdentification() == identification
	 * @post	The weight is set to the given weight.
	 * 			If the given weight is not valid, a default weight is set.
	 * 			| new.getWeight() == weight
	 * @post	The weight is valid after construction.
	 * 			| new.isValidWeight
	 * @post	The value of this item is set to the given value.
	 * 			| setValue(value)
	 * 
	 * @note	For the class Armor, the identification needs to be given. For now this
	 * 			is the only class that uses this constructor.
	 */
	@Model
	protected Item(long identification, float weight, int value) {
		if (!canHaveAsNewIdentification(identification)) {
			this.identification = generateIdentification();
		} else {
			this.identification = identification;
		}
		
		if (isValidWeight(weight)) {
			this.weight = weight;
		} else {
			this.weight = getDefaultWeight();
		}
		
		setValue(MathHelper.clamp(value, getMinValue(), getMaxValue()));
	}
	
	/***********************
	 * Class type
	 ***********************/
	
	/**
	 * Return true when this item is a weapon
	 * @return	Return true when this item is a weapon
	 * 			Return false otherwise
	 */
	public boolean isWeapon () {
		return false;
	}
	
	/**
	 * Return true when this item is an armor
	 * @return	Return true when this item is an armor
	 * 			Return false otherwise
	 */
	public boolean isArmor () {
		return false;
	}
	
	/**
	 * Return true when this item is a backpack
	 * @return	Return true when this item is a backpack
	 * 			Return false otherwise
	 */
	public boolean isBackpack () {
		return false;
	}
	
	/**
	 * Return true when this item is a purse
	 * @return	Return true when this item is a purse
	 * 			Return false otherwise
	 */
	public boolean isPurse () {
		return false;
	}
	
	/**
	 * Return true when this item is a purse or backpack
	 * @return 	Return true when this item is a purse or backpack
	 */
	public boolean isContainer () {
		return isBackpack() || isPurse();
	}
	
	/***********************
	 * Destructor
	 ***********************/
	
	private boolean isTerminated = false;
	
	/**
	 * Return true when this item is terminated
	 * @return	Return true when this item is terminated
	 * 			Return false otherwise
	 */
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminates this item
	 * @post	Terminates this item by breaking all associations (dropping it on the ground)
	 * 			and sets terminated state on true
	 * 			| isTerminated() == true
	 */
	public void terminate () {
		this.drop();
		this.isTerminated = true;
	}
	
	/*************************************
	 * Identification - total programming
	 *************************************/
	
	private final long identification;
	
	/**
	 * Return the item's identification
	 * @return Return the item's identification
	 */
	@Immutable
	public long getIdentification() {
		return this.identification;
	}
	
	//protected void setIdentification (long identification) {
	//	this.identification = identification;
	//}
	
	/**
	 * Return true when this class can have this id as identification where id doesn't have to be unique
	 * @return	Return true when this class can have this id as identification where id doesn't have to be 
	 * 			unique
	 */
	public abstract boolean canHaveAsIdentification(long identification);
	
	/**
	 * Return true when this class can have this id as identification where the given id has to be unique
	 * @return	Return true when this class can have this id as identification where the given id has to 
	 * 			be unique
	 */
	public abstract boolean canHaveAsNewIdentification(long identification);
	
	/**
	 * Return a valid id for this item
	 * @return	Return a valid id for this item
	 */
	protected abstract long generateIdentification();

	
	
	/*****************************
	 * Weight - total programming
	 *****************************/
	private final float weight;
	
	/**
	 * Returns the item's weight
	 * @return Returns the item's weight
	 */
	public float getWeight() {
		return this.weight;
	}
	
	/**
	 * Return true when the given weight is valid
	 * @param 	weight
	 * 			the weight to check
	 * @return	Return true when the given weight is valid, thus positive.
	 * 			Return false when the given weight is invalid
	 */
	public boolean isValidWeight(float weight) {
		
		return (weight >= 0);
	}
	
	/**
	 * Return the default weight
	 * @return	Return the default weight
	 */
	public float getDefaultWeight () {
		return 100;
	}
	
	
	
	
	/***********************
	 * Value
	 ***********************/
	private int value;
	
	/**
	 * Return the maximum value for this item
	 * @return	Return the maximum value for this item
	 */
	@Immutable
	public abstract int getMaxValue ();
	
	/**
	 * Return the minimum value for this item
	 * @return	Return the minimum value for this item
	 */
	@Immutable
	public abstract int getMinValue ();
	
	/**
	 * Returns the item's value 
	 * @return Returns the item's value
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Sets the item's value to the given value
	 * @param value
	 * 		  The new value
	 * @post  The value is set to the given value
	 * 		  | new.getValue() == value
	 */
	protected void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Returns whether or not the given value is valid
	 * @param value
	 * 		  the integer checked for its validity
	 * @return returns true if the given value lies between the minimum and maximum value.
	 * 		   | (value >= MIN_VALUE && value <= MAX_VALUE)
	 * @return returns false if the given value does not lie between the minimum and maximum.
	 * 
	 */
	public boolean canHaveAsValue(int value) {
		return (value >= getMinValue() && value <= getMaxValue());
	}
	
	
	/***********************
	 * Anchor
	 ***********************/
	private Character character;
	
	/**
	 * Returns the item's character
	 * @return Returns the item's character
	 */
	public Character getCharacter() {
		return this.character;
	}
	
	/**
	 * Sets the item's character to the given character
	 * @param character
	 * 		  The new character
	 * @post  The character is set to the given character
	 * 		  | new.getCharacter() == anchor
	 */
	private void setCharacter(Character character) {
		this.character = character;
	}
	
	/**
	 * Binds a character to this anchor
	 * @param 	anchor
	 * 			The character to set as anchor
	 * @pre		This item is not terminated
	 * 			| !this.isTerminated()
	 * @post	Binds a character to this anchor, when this item is in a backpack it will be removed
	 * 			| getParentBackpack() == null && getAnchor() == anchor
	 */
	@Raw
	protected void bindCharacter (Character character) {
		//if (character == getCharacter() || getCharacter() == null) {
			if (getParentBackpack() != null) {
				getParentBackpack().removeItem(this);
				setParentBackpack(null);
			}
			setCharacter(character);
		//}
		
	}
	
	/***********************
	 * ParentBackpack
	 ***********************/
	private Backpack parentBackpack;
	
	/**
	 * Returns the item's parentBackpack
	 * @return Returns the item's parentBackpack
	 */
	public Backpack getParentBackpack() {
		return this.parentBackpack;
	}
	
	/**
	 * Sets the item's parentBackpack to the given backpack
	 * @param backpack
	 * 		  The new parentBackpack of the item
	 * @post  The parentBackpack is set to the given parentBackpack
	 * 		  | new.getParentBackpack() == backpack
	 */
	private void setParentBackpack(Backpack backpack) {
		this.parentBackpack = backpack;
	}
	
	/**
	 * Moves this item to the given backpack
	 * @param	backpack
	 * 			The backpack to move this item to
	 * @post	Moves this item to the given backpack
	 * 			| backpack.containsItem(this)
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given backpack can't have this item
	 * 			| !backpack.canHaveAsItem(this)
	 * @throws	TerminatedException
	 * 			Throws this error when this item is terminated
	 * 			| this.isTerminated()
	 */
	public void moveTo (Backpack backpack) throws IllegalArgumentException, TerminatedException {
		if (this.isTerminated()) {
			throw new TerminatedException(this);
		}
		else if (!backpack.canHaveAsItem(this)) {
			throw new IllegalArgumentException ("The given backpack can't have this item");
		}
		
		drop(); // break all previous associations
		
		backpack.addItem(this);
		setParentBackpack(backpack);
	}
	
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * Drops the item to the ground
	 * @effect 	When this item is anchored, the holder of the item is set to null(= on the ground).
	 * 		   	Since this is a bidirectional relation, the item is also removed from the holder.
	 * 			|this.getAnchor().removeItemFromHolder(this)
	 * 		   	|this.setAnchor(null)
	 * @effect 	When this item is in a backpack, the parent backpack of this item is set to null
	 * 			and since this is a bidirectional, the item is also removed from the backpack.
	 * 			|this.getParentBackpack().removeItem(this)
	 * 		   	|this.setParentBackpack(null)
	 * 	
	 */
	public void drop() {
		if (this.getCharacter() != null) {
			this.getCharacter().removeItemFromHolder(this);
			this.setCharacter(null);
		} else if (this.getParentBackpack() != null) {
			this.getParentBackpack().removeItem(this);
			this.setParentBackpack(null);
		}
	}
	
	/**
	 * Return the holder of this item
	 * @return	Return the character associated when equipped on an anchor, does this method recursively
	 * 			when this item is in a backpack. If a backpack doesn't have a holder this method returns null.
	 */
	public Character getHolder() {
		if (getCharacter() != null) {
			return getCharacter();
		} else if (getParentBackpack() != null) {
			return getParentBackpack().getHolder();
		} else {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
