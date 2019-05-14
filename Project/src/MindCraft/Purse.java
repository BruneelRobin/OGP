package MindCraft;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of purses
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public class Purse extends Item implements Container {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Creates a purse
	 * @pre		The given capacity is valid
	 * 			| isValidCapacity (capacity)
	 */
	public Purse (int capacity, float weight, int value, Character holder, Backpack parentBackpack) {
		super(weight, value, holder, parentBackpack);
		setCapacity(capacity);
	}
	
	
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	@Override
	protected long generateIdentification() {
		return 0;
	}
	
	/**
	 * 
	 * @param 	identification
	 * 			The identification to check
	 * @return	Return true when this item can have the given identification number
	 * 			Return false when this item can't have the given identification number
	 * 			| result = ...
	 */
	@Override
	public boolean canHaveAsIdentification(long identification) {
		return false;
	}
	

	
	/*******************************
	 * Capacity - total programming
	 *******************************/
	
	private int capacity;
	
	/**
	 * Return the capacity of this container
	 * @return	Return the capacity of this container
	 */
	@Basic
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Set the capacity of this container
	 * @param 	capacity
	 * 			the new capacity
	 * @post	the new capacity is set to the given capacity
	 * 			| new.getCapacity() == capacity
	 */
	@Raw
	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Return true when the given capacity is valid
	 * @param 	capacity
	 * 			The capacity to check
	 * @return	Return true when the given capacity is valid
	 * 			Return false when the given capacity is invalid
	 * 			| result == ...
	 */
	public static boolean isValidCapacity (int capacity) {
		return false;
	}
	
	/***********************
	 * Torn
	 ***********************/
	
	private boolean isTorn = false;
	
	/**
	 * Return the torn state of this container
	 * @return	Return the torn state of this container
	 */
	@Basic
	public boolean isTorn() {
		return this.isTorn;
	}
	
	/**
	 * Set the torn state of this container to the given value
	 * @param	isTorn
	 * 			the new torn state
	 * @post	the new torn state is set to the given state
	 * 			| new.isTorn() == isTorn
	 */
	private void setTorn (boolean isTorn) {
		this.isTorn = isTorn;
	}
	
	/**
	 * Set the torn state of this container to true
	 * @post	the new torn state is set to true
	 * 			| new.isTorn() == true
	 * @post	the content is set to 0
	 * 			| new.getContent() == 0
	 */
	@Raw
	public void makeTorn() {
		setTorn(true);
	}
	
	/**********************************
	 * Content - defensive programming
	 **********************************/
	
	private int content;
	
	/**
	 * Return the content of this container
	 * @return	Return the content of this container
	 */
	@Basic
	public int getContent() {
		return this.capacity;
	}
	
	/**
	 * Set the content of this container
	 * @param 	content
	 * 			the new content
	 * @post	the new content is set to the given content
	 * 			| new.getContent() == content
	 */
	@Raw
	private void setContent(int content) {
		this.content = content;
	}
	
	/**
	 * Adds the given amount
	 * @param 	amount
	 * 			The amount to add
	 * @post	The new content is increased with the given amount
	 * 			| this.getContent() == this.getContent() + amount
	 * @effect	When the new content is higher than the allowed capacity this purse is torn
	 * 			| makeTorn()
	 * @throws	TornException
	 * 			throws this error when you try to add ducates to a torn purse
	 */
	public void add (int amount) throws TornException {
		
	}
	
	/**
	 * Adds the contents of the given purse to the current purse
	 * @param 	purse
	 * 			The purse to empty
	 * @post	The new content is increased with the content of the current purse
	 * 			| this.getContent() == this.getContent() + purse.getContent()
	 * @post	The emptied purse is dropped
	 * 			| purse.drop()
	 * @effect	When the new content is higher than the allowed capacity this purse is torn
	 * 			| makeTorn()
	 * @throws	TornException
	 * 			throws this error when you try to add ducates to a torn purse
	 */
	public void add (Purse purse) throws TornException {
		
	}
	
	/***********************
	 * Other Methods
	 ***********************/
	
	private final static float DUCATE_WEIGHT = 0.05f;
	
	/**
	 * Return the weight of one ducate
	 * @return	Return the weight of one ducate
	 */
	public static float getDucateWeight () {
		return DUCATE_WEIGHT;
	}
	
	/**
	 * Return the total weight of this purse
	 * @return	Return the total weight of this purse
	 * 			| result == this.getContent() * DUCATE_WEIGHT
	 */
	public float getTotalWeight() {
		return 0;
	}
	
	/**
	 * Return the total value of this purse
	 * @return	Return the total value of this purse in ducates
	 * 			| result == this.getContent()
	 */
	public int getTotalValue() {
		return this.getContent();
	}
}
