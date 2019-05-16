package MindCraft;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of items
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public abstract class Item {
	
	/***********************
	 * Constructors
	 ***********************/
	
	// TODO:	* holden en parentBackpack weg uit constructor want zorgt voor problemen met validatie
	//			* nieuwe naam voor holder, want niet altijd gedefiniëerd (ofwel in bp ofwel in anchor)
	//			* subklasses aanpassen
	
	/**
	 * Creates a new item
	 */
	@Model
	protected Item(float weight, int value) {
		setIdentification(generateIdentification());
		if (isValidWeight(weight)) {
			this.weight = weight;
		} else {
			this.weight = getDefaultWeight();
		}
		
		setValue(value);
	}
	
	protected Item(long identification, float weight, int value) {
		setIdentification(identification);
		if (isValidWeight(weight)) {
			this.weight = weight;
		} else {
			this.weight = getDefaultWeight();
		}
		
		setValue(value);
	}
	
	/*protected void initialize (int value, Character holder, Backpack parentBackpack) {
	}*/
	
	/*************************************
	 * Identification - total programming
	 *************************************/
	
	private long identification;
	
	/**
	 * Return the item's identification
	 * @return Return the item's identification
	 */
	@Immutable
	public long getIdentification() {
		return this.identification;
	}
	
	protected void setIdentification (long identification) {
		this.identification = identification;
	}
	
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
	private abstract final int MAX_VALUE;
	private abstract final int MIN_VALUE;
	
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
		return (value >= MIN_VALUE && value <= MAX_VALUE);
	}
	
	
	
	/***********************
	 * Anchor
	 ***********************/
	private Character anchor;
	
	/**
	 * Returns the item's anchor
	 * @return Returns the item's anchor
	 */
	public Character getAnchor() {
		return this.anchor;
	}
	
	/**
	 * Sets the item's anchor to the given anchor
	 * @param anchor
	 * 		  The new anchor
	 * @post  The anchor is set to the given anchor
	 * 		  | new.getAnchor() == anchor
	 */
	private void setAnchor(Character anchor) {
		this.anchor = anchor;
	}
	
	/**
	 * Binds a character to this anchor
	 * @param 	anchor
	 * 			The character to set as anchor
	 * @post	Binds a character to this anchor, when this item is in a backpack it will be removed
	 * 			| getParentBackpack() == null && getAnchor() == anchor
	 */
	@Raw
	protected void bindAnchor (Character anchor) {
		//if (anchor == getAnchor() || getAnchor() == null) {
			if (getParentBackpack() != null) {
				getParentBackpack().removeItem(this);
				setParentBackpack(null);
			}
			setAnchor(anchor);
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
	 * 
	 */
	public void moveTo (Backpack backpack) throws IllegalArgumentException {
		if (!backpack.canHaveAsItem(this)) {
			throw new IllegalArgumentException ("Invalid backpack to move to");
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
		if (this.getAnchor() != null) {
			this.getAnchor().removeItemFromHolder(this);
			this.setAnchor(null);
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
		if (getAnchor() != null) {
			return getAnchor();
		} else if (getParentBackpack() != null) {
			return getParentBackpack().getHolder();
		} else {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
