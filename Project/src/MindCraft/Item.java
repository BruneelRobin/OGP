package MindCraft;


/**
 * A class of items
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public abstract class Item {
	
	/**
	 * Creates an item
	 */
	public Item(long identification, float weight, int value, Character holder, Backpack parentBackpack) {
		
		
	}
	
	
	/***********************
	 * Identification
	 ***********************/
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
	 * 		  | new.getIdentification = identification
	 */
	private void setIdentification(long identification) {
		this.identification = identification;
	}
	
	
	/***********************
	 * Weight
	 ***********************/
	private float weight;
	
	
	
	/***********************
	 * Value
	 ***********************/
	private int value;
	
	
	/***********************
	 * Holder
	 ***********************/
	private Character holder;
	
	
	
	/***********************
	 * ParentBackpack
	 ***********************/
	private Backpack parentBackpack;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
