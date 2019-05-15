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
	public Purse (int capacity, float weight) {
		super(weight, 0);
		setCapacity(capacity);
	}
	
	
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	
	
	/**
	 * Generates a valid identification for a purse.
	 * @return returns a unique long
	 * 		   The uniqueness of the generated long is always considered true
	 * 		   The chance of colliding is not zero, but neglectable (5.4*10^(-20))
	 */
	@Override
	protected long generateIdentification() {
		long candidate = MathHelper.getRandomLongBetweenRange(Long.MIN_VALUE, Long.MAX_VALUE);
		return candidate;
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
	private void makeTorn() {
		setTorn(true);
		this.setContent(0);
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
	 * @post	The new content is increased with the given amount (when amount is > 0)
	 * 			| this.getContent() == this.getContent() + amount
	 * @effect	When the new content is higher than the allowed capacity this purse is torn
	 * 			| makeTorn()
	 * @throws	TornException
	 * 			throws this error when you try to add ducates to a torn purse
	 */
	public void add (int amount) throws TornException, IllegalArgumentException {
		if (isTorn()) {
			throw new TornException(this);
		}
		
		int newAmount = getContent() + amount;
		if (newAmount > getContent() && newAmount <= getCapacity()) {
			setContent(getContent() + amount);
		} else if (newAmount > getCapacity()) {
			makeTorn();
		} else {
			// overflow of negative amount
			throw new IllegalArgumentException ("Illegal amount given");
		}
	}
	
	/**
	 * Adds the given amount
	 * @param 	amount
	 * 			The amount to remove
	 * @post	The new content is decreased with the given amount (when amount is > 0 and the new amount > 0)
	 * 			| this.getContent() == this.getContent() - amount
	 * @throws	TornException
	 * 			throws this error when you try to remove ducates from a torn purse
	 */
	public void remove (int amount) throws TornException, IllegalArgumentException {
		if (isTorn()) {
			throw new TornException(this);
		}
		
		int newAmount = getContent() - amount;
		if (newAmount < getContent() && newAmount > 0) {
			setContent(newAmount);
		} else {
			// overflow of negative amount
			throw new IllegalArgumentException ("Illegal amount given");
		}
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
	 * 			throws this error when you try to add or take ducates from a torn purse
	 */
	public void add (Purse purse) throws TornException {
		if (!purse.isTorn() && !this.isTorn()) {
			int c = purse.getContent();
			
			purse.remove(c);
			this.add(c);
		} else if (purse.isTorn()) {
			throw new TornException(purse);
		} else {
			throw new TornException(this);
		}
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
	 * @return	Return the total weight this purse: weight of the purse combined with weiht of its content.
	 * 			| result == (this.getContent() * DUCATE_WEIGHT) + this.getWeight()
	 */
	public float getTotalWeight() {
		return ((float)(this.getContent())*DUCATE_WEIGHT + this.getWeight());
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
