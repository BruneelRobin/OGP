package MindCraft;


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
	
	/**
	 * Creates an item
	 */
	public Item(long identification, float weight, int value, Character holder, Backpack parentBackpack) {
		
		
	}
	
	
	/*************************************
	 * Identification - total programming
	 *************************************/
	private long identification;
	
	/**
	 * Returns the item's identification
	 * @Return Returns the item's identification
	 */
	public long getIdentification() {
		return this.identification;
	}
	

	/**
	 * Sets the item's identification to the given identification
	 * @param identification
	 * 		  the new identification
	 * @post  The item's new identification is the given identification
	 * 		  | new.getIdentification() == identification
	 */
	private void setIdentification(long identification) {
		this.identification = identification;
	}
	
	/**
	 * Returns whether or not the given identification is valid
	 * @return true when the identification is valid
	 * @return false when the identification is not valid
	 * 
	 */
	public static boolean isValidIdentification(long identification) {
		return false;
	}
	
	
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
	 * Sets the item's weight to the given weight
	 * @param weight
	 * 		  The new value of the weight
	 * @post  The weight is set to the given weight
	 * 		  | new.getWeight() == weight
	 */
	private void setWeight(float weight) {
		this.weight = weight;
	}
	
	
	
	
	/***********************
	 * Value
	 ***********************/
	private int value = -1;
	
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
	private void setValue(int value) {
		this.value = value;
	}
	
	
	
	/***********************
	 * Holder
	 ***********************/
	private Character holder;
	
	/**
	 * Returns the item's holder
	 * @return Returns the item's holder
	 */
	public Character getHolder() {
		return this.holder;
	}
	
	/**
	 * Sets the item's holder to the given holder
	 * @param holder
	 * 		  The new holder
	 * @post  The holder is set to the given holder
	 * 		  | new.getHolder() == holder
	 */
	private void setHolder(Character holder) {
		this.holder = holder;
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
	
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * Drops the item to the ground
	 * @effect The holder of the item is set to null(= on the ground).
	 * 		   Since this is a bidirectional relation, the item is also removed from the holder.
	 * 		   |this.setHolder(null)
	 * 		   |this.getHolder.removeItemFromHolder(this)
	 * 	
	 */
	public void drop() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
