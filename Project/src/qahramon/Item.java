package qahramon;

import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.TerminatedException;

/**
 * An abstract class of items involving an identification, a class type, a termination state,
 * an identification, a weight, a value, a character and a parent backpack.
 * 
 * @invar 	Each item must have a unique and valid identification number.
 * 			| canHaveAsIdentification(getIdentification())
 * @invar	Each item must have a valid weight.
 * 			| isValidWeight(getWeight())
 * @invar	Each item must have a valid value.
 * 			| canHaveAsValue(getValue())
 * @invar	Each item must have a proper character when bound.
 * 			| getCharacter() == null || hasProperCharacter()
 * @invar	Each item must have a proper parent backpack when bound.
 * 			| getParentBackpack() == null || hasProperParentBackpack()
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
	 * 
	 * @param 	weight
	 * 			the weight of this item
	 * @param 	value
	 * 			the value of this item
	 * @post	The identification is set to a generated identification.
	 * 			| new.getIdentification() == generateIdentification()
	 * @post	The weight is set to the given weight.
	 * 			If the given weight is not valid, a default weight is set.
	 * 			| new.getWeight() == weight
	 * @post	The value of this item is set to the given value if the value lies
	 * 			between the boundaries.
	 * 			| new.getValue() == clamp(value, getMinValue(), getMaxValue())
	 */
	@Model
	protected Item(float weight, int value) {
		this.identification = generateIdentification();
		if (isValidWeight(weight)) {
			this.weight = weight;
		} else {
			this.weight = getDefaultWeight();
		}
		
		setValue(MathHelper.clamp(value, getMinValue(), getMaxValue()));
	}
	
	/**
	 * Create an item with given identification, weight and value.
	 * 
	 * @param 	identification
	 * 			the identification of this item
	 * @param 	weight
	 * 			the weight of this item
	 * @param 	value
	 * 			the value of this item
	 * @post	The identification is set to the given identification.
	 * 			| new.getIdentification() == identification
	 * @post	The weight is set to the given weight.
	 * 			If the given weight is not valid, a default weight is set.
	 * 			| new.getWeight() == weight
	 * @post	The value of this item is set to the given value.
	 * 			| new.getValue() == clamp(value, getMinValue(), getMaxValue())
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
	 * Check whether this item is a weapon.
	 * 
	 * @return	Return true when this item is a weapon.
	 * 			Return false otherwise.
	 */
	@Basic@Immutable@Raw
	public boolean isWeapon () {
		return false;
	}
	
	/**
	 * Check whether this item is an armor.
	 * 
	 * @return	Return true when this item is an armor.
	 * 			Return false otherwise.
	 */
	@Basic@Immutable@Raw
	public boolean isArmor () {
		return false;
	}
	
	/**
	 * Check whether this item is a backpack.
	 * 
	 * @return	Return true when this item is a backpack.
	 * 			Return false otherwise.
	 */
	@Basic@Immutable@Raw
	public boolean isBackpack () {
		return false;
	}
	
	/**
	 * Check whether this item is a purse.
	 * 
	 * @return	Return true when this item is a purse.
	 * 			Return false otherwise.
	 */
	@Basic@Immutable@Raw
	public boolean isPurse () {
		return false;
	}
	
	/**
	 * Check whether this item is a purse or backpack.
	 * 
	 * @return 	Return true when this item is a purse or backpack.
	 * 			Return false otherwise.
	 */
	@Immutable@Raw
	public boolean isContainer () {
		return isBackpack() || isPurse();
	}
	
	/***********************
	 * Destructor
	 ***********************/
	
	/**
	 * Variable referencing the terminated state of this item.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Check whether this item is terminated.
	 * 
	 * @return	Return true when this item is terminated.
	 * 			Return false otherwise.
	 */
	@Basic@Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this item.
	 * 
	 * @post	Set terminated state to true.
	 * 			| isTerminated() == true
	 * @effect	Drop this item to break all associations.
	 * 			| drop()
	 */
	public void terminate () {
		this.drop();
		this.isTerminated = true;
	}
	
	/*************************************
	 * Identification - total programming
	 *************************************/
	
	/**
	 * Variable referencing the identification of this item.
	 */
	private final long identification;
	
	/**
	 * Return the item's identification
	 * @return Return the item's identification
	 */
	@Immutable@Basic@Raw
	public long getIdentification() {
		return this.identification;
	}
	
	/**
	 * Check whether this class can have this id as identification where id doesn't have to be unique.
	 * 
	 * @param	identification
	 * 			the identification to be checked
	 * @return	Return true when this class can have this id as identification where id doesn't have to be 
	 * 			unique.
	 * 			Return false otherwise.
	 */
	@Raw
	public abstract boolean canHaveAsIdentification(long identification);
	
	/**
	 * Check whether this class can have this id as identification where the given id has to be unique.
	 * 
	 * @param	identification
	 * 			the identification to be checked
	 * @return	Return true when this class can have this id as identification where the given id has to 
	 * 			be unique.
	 * 			Return false otherwise.
	 */
	@Raw
	public abstract boolean canHaveAsNewIdentification(long identification);
	
	/**
	 * Return a valid id for this item.
	 * 
	 * @return	Return a valid id for this item.
	 */
	@Raw
	protected abstract long generateIdentification();

	
	
	/*****************************
	 * Weight - total programming
	 *****************************/
	
	/**
	 * Variable referencing the weight of this item.
	 */
	private final float weight;
	
	/**
	 * Return the item's weight.
	 * 
	 * @return Return the item's weight.
	 */
	@Basic@Immutable@Raw
	public float getWeight() {
		return this.weight;
	}
	
	/**
	 * Check whether the given weight is valid.
	 * 
	 * @param 	weight
	 * 			the weight to check
	 * @return	Return true when the given weight is valid, thus positive.
	 * 			Return false otherwise.
	 * 			| weight >= 0
	 */
	public static boolean isValidWeight(float weight) {
		
		return (weight >= 0);
	}
	
	/**
	 * Return the default weight.
	 * 
	 * @return	Return the default weight.
	 */
	@Immutable@Basic
	public float getDefaultWeight () {
		return 10f;
	}
	
	/***********************
	 * Value
	 ***********************/
	
	/**
	 * Variable referencing the value of this item.
	 */
	private int value;
	
	/**
	 * Return the maximum value for this item.
	 * 
	 * @return	Return the maximum value for this item.
	 */
	@Immutable@Basic
	public abstract int getMaxValue ();
	
	/**
	 * Return the minimum value for this item.
	 * 
	 * @return	Return the minimum value for this item.
	 */
	@Immutable@Basic
	public abstract int getMinValue ();
	
	/**
	 * Return the item's value.
	 * 
	 * @return Return the item's value.
	 */
	@Basic@Raw
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Set the item's value to the given value.
	 * 
	 * @param value
	 * 		  the new value
	 * @post  The value is set to the given value.
	 * 		  | new.getValue() == value
	 */
	protected void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Check whether the given value is valid.
	 * 
	 * @param 	value
	 * 		  	the integer checked for its validity
	 * @return	Return true if the given value lies between the minimum and maximum value.
	 * 			Return false otherwise.
	 * 		   	| result == value >= getMinValue() && value <= getMaxValue()
	 */
	@Raw
	public boolean canHaveAsValue(int value) {
		return (value >= getMinValue() && value <= getMaxValue());
	}
	
	
	/***********************
	 * Character
	 ***********************/
	
	/**
	 * Variable referencing the character of this item.
	 */
	private Character character;
	
	/**
	 * Return the item's character.
	 * 
	 * @return Return the item's character.
	 */
	@Basic
	public Character getCharacter() {
		return this.character;
	}
	
	/**
	 * Set the item's character to the given character.
	 * 
	 * @param character
	 * 		  the new character
	 * @post  The character is set to the given character.
	 * 		  | new.getCharacter() == character
	 */
	@Raw
	private void setCharacter(Character character) {
		this.character = character;
	}
	
	/**
	 * Bind a character to this anchored item.
	 * 
	 * @param 	character
	 * 			the character to set
	 * @pre		This item cannot be terminated.
	 * 			| !this.isTerminated()
	 * @post	Bind a character to this anchored item.
	 * 			| new.getParentBackpack() == null && new.getCharacter() == character
	 * @post	When this item is in a backpack it will be removed from that backpack.
	 * 			| getParentBackpack().removeItem(this);
	 *			| setParentBackpack(null);
	 * @post	When this item is already anchored to the character, 
	 * 			it will be removed from the character
	 * 			| getCharacter().removeItemFromHolder(this);
	 */
	@Raw
	protected void bindCharacter (Character character) {
			if (getParentBackpack() != null) {
				getParentBackpack().remove(this);
				setParentBackpack(null);
			} else if (getCharacter() != null) {
				getCharacter().removeItemFromHolder(this);
			}
			setCharacter(character);
	}
	
	/**
	 * Check whether this item has a proper character.
	 * 
	 * @return	Return true when this item can have the current character and the character has
	 * 			this item anchored.
	 * 			Return false otherwise.
	 * 			| result == canHaveAsCharacter(getCharacter()) && getCharacter().hasAnchored(this)
	 */
	@Raw
	public boolean hasProperCharacter () {
		return canHaveAsCharacter(getCharacter()) && getCharacter().hasAnchored(this);
	}
	
	/**
	 * Check whether this item can have the given character.
	 * 
	 * @param 	character
	 * 			the character to check
	 * @return	Return true when the given character is not null.
	 * 			Return false otherwise.
	 * 			| result == character != null
	 */
	@Raw
	public boolean canHaveAsCharacter (Character character) {
		return character != null;
	}
	
	/***********************
	 * ParentBackpack
	 ***********************/
	
	/**
	 * Variable referencing the parent backpack of this item.
	 */
	private Backpack parentBackpack;
	
	/**
	 * Return the item's parent backpack.
	 * 
	 * @return Return the item's parent backpack.
	 */
	@Basic
	public Backpack getParentBackpack() {
		return this.parentBackpack;
	}
	
	/**
	 * Set the item's parentBackpack to the given backpack.
	 * 
	 * @param backpack
	 * 		  the new parent backpack of the item
	 * @post  The parent backpack is set to the given parentBackpack.
	 * 		  | new.getParentBackpack() == backpack
	 */
	@Raw
	private void setParentBackpack(Backpack backpack) {
		this.parentBackpack = backpack;
	}
	
	/**
	 * Check whether this item has a proper backpack.
	 * 
	 * @return	Return true when this item can have the given backpack and the backpack contains this item.
	 * 			Return false otherwise.
	 * 			| result == canHaveAsParentBackpack(getParentBackpack()) && getParentBackpack().contains(this)
	 */
	@Raw
	public boolean hasProperParentBackpack () {
		return canHaveAsParentBackpack(getParentBackpack()) && getParentBackpack().contains(this);
	}
	
	/**
	 * Check whether this item can have the given backpack.
	 * 
	 * @param 	backpack
	 * 			the backpack to check
	 * @return	Return true when the given backpack is not null, not terminated and the given backpack
	 * 			can have this item.
	 * 			Return false otherwise.
	 * 			| result == backpack != null && !backpack.isTerminated() && backpack.canHaveAsItem(this)
	 */
	@Raw
	public boolean canHaveAsParentBackpack (Backpack backpack) {
		return backpack != null && !backpack.isTerminated() && backpack.canHaveAsItem(this);
	}
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * Drop this item to the ground.
	 * 
	 * @effect 	When this item is anchored, the holder of the item is set to null(= on the ground).
	 * 		   	Since this is a bidirectional relation, the item is also removed from the holder.
	 * 			| this.getCharacter().removeItemFromHolder(this)
	 * 		   	| this.setCharacter(null)
	 * @effect 	When this item is in a backpack, the parent backpack of this item is set to null
	 * 			and since this is a bidirectional, the item is also removed from the backpack.
	 * 			| this.getParentBackpack().removeItem(this)
	 * 		   	| this.setParentBackpack(null)
	 */
	public void drop() {
		if (this.getCharacter() != null) {
			this.getCharacter().removeItemFromHolder(this);
			this.setCharacter(null);
		} else if (this.getParentBackpack() != null) {
			this.getParentBackpack().remove(this);
			this.setParentBackpack(null);
		}
	}
	
	/**
	 * Return the holder of this item.
	 * 
	 * @return	Return the character associated when equipped on an anchor 
	 * 			or get the holder recursively when this item is in a backpack.
	 * 			When a backpack does not have a holder this method returns null.
	 * 			| result == if (getCharacter() != null) then getCharacter()
	 * 			|			else if (getParentBackpack() != null) then getParentBackpack().getHolder()
	 * 			|			else null
	 * 
	 * @note	If the holder is null, this item lies on the ground.
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
	
	/**
	 * Move this item to the given backpack.
	 * 
	 * @param	backpack
	 * 			the backpack to move this item to
	 * @post	Moves this item to the given backpack
	 * 			| backpack.containsItem(this)
	 * 			| new.getParentBackpack() == backpack
	 * 			| new.getCharacter() == null
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given backpack can not have this item.
	 * 			| !backpack.canHaveAsItem(this)
	 * @throws	TerminatedException
	 * 			Throws this error when this item is terminated.
	 * 			| this.isTerminated()
	 */
	public void moveTo (Backpack backpack) throws IllegalArgumentException, TerminatedException {
		if (this.isTerminated()) {
			throw new TerminatedException(this);
		}
		else if (!canHaveAsParentBackpack(backpack)) {
			throw new IllegalArgumentException ("The given backpack can not have this item");
		}
		
		drop(); // break all previous associations
		
		backpack.add(this);
		setParentBackpack(backpack);
	}
	
	/**
	 * Return a string containing general data over this string.
	 * 
	 * @return	Return a string containing data over its identification, value and weight.
	 */
	protected String getString() {
		return "Identification: " + getIdentification()
			+ "\nValue: " + getValue() + " ducates\nWeight: " + getWeight() + " kg";
	}
	
}
