package MindCraft;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of purses
 * 
 * @invar	Each purse must have a valid capacity
 * 			| isValidCapacity(getCapacity())
 * @invar	Each purse must have a valid content
 * 			| canHaveAsContent(getContent())
 * 
 * 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public class Purse extends Item implements Container {
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a purse with given capacity and weight.
	 * @param 	capacity
	 * 			The capacity of this purse.
	 * @param 	weight
	 * 			The weight of this purse.
	 * @param	content
	 * 			The default content
	 * @pre		The given capacity is valid
	 * 			| isValidCapacity (capacity)
	 * @pre		The given content is valid
	 * 			| canHaveAsContent(content)
	 * @effect	The new purse is set as an item with given weight and 0 value.
	 * 			| super(weight, 0)
	 * @post	The capacity is set to the given capacity.
	 * 			| new.getCapacity() == capacity
	 * @post	The content is set to the given content.
	 * 			| new.getContent() == content
	 */
	public Purse (float weight, int capacity, int content) {
		super(weight, 0);
		this.capacity = capacity;
		setContent(content);
		ids.add(getIdentification());
	}
	
	/***********************
	 * Class type
	 ***********************/
	
	/**
	 * Return true when this item is a purse
	 * @return	Always return true since this item is a purse
	 * 			| result == true
	 */
	public boolean isPurse () {
		return true;
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
		
		if (!canHaveAsNewIdentification (candidate)) {
			return generateIdentification();
		}
		
		return candidate;
	}
	
	/**
	 * 
	 * @param 	identification
	 * 			The identification to check
	 * @return	Return true when this item can have the given identification number
	 * 			Return false when this item can't have the given identification number
	 * 			| result == true
	 */
	@Override
	public boolean canHaveAsIdentification(long identification) {
		return true;
	}
	
	/**
	 * @param	identification
	 * 			The identification to check
	 * @return	Return true when this purse can have the given identification number that has to be unique
	 * 			Return false when this purse can't
	 * 			| result == canHaveAsIdentification (identification) 
	 * 							&& ids.contains(identification) == false
	 */
	@Override
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && ids.contains(identification) == false;
	}
	
	/**
	 * Variable referencing a set with all ids of this class. 
	 * 
	 * @invar Each non null element in the hashset references an effective item. 
	 *        | for (Item item : ids)
	 *        | 	item != null
	 */
	private static final HashSet<Long> ids = new HashSet<Long>();
	
	/*******************************
	 * Capacity - total programming
	 *******************************/
	
	private final int capacity;
	
	/**
	 * Return the capacity of this container
	 * @return	Return the capacity of this container
	 */
	@Basic
	public int getCapacity() {
		return this.capacity;
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
		return (capacity >= 0);
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
	 * Return true when this purse can have the given content
	 * @param 	content
	 * 			The content to check
	 * @return	Return true when the content of this purse is within the allowed range and the additional content doesn't exceed 
	 * 			the maximum capacity of the holder of this purse
	 * 			| result == content >= 0 && content <= getCapacity() && (this.getHolder() == null || 
	 * 						this.getHolder().getCapacity() - this.getHolder().getTotalWeight() >= (content - getContent())*getDucateWeight())
	 */
	public boolean canHaveAsContent (int content) {
		float newWeight = (content - getContent())*getDucateWeight();
		boolean validWeight =  (this.getHolder() == null || this.getHolder().getCapacity() - this.getHolder().getTotalWeight() >= newWeight);
		
		return content >= 0 && content <= getCapacity() && validWeight;
	}
	
	/**
	 * Adds the given amount
	 * @param 	amount
	 * 			The amount to add
	 * @post	The new content is increased with the given amount
	 * 			| new.getContent() == this.getContent() + amount
	 * @effect	When the new content is higher than the allowed capacity this purse is torn
	 * 			| makeTorn()
	 * @throws	TornException
	 * 			Throws this error when you try to add ducates to a torn purse
	 * 			| this.isTorn()
	 * @throws	IllegalArgumentException
	 * 			Throws this error when an illegal amount (overflow or negative) is given or this purse can't have the new amount.
	 * 			| getContent() + amount < getContent() || !canHaveAsContent(this.getContent() + amount)
	 * @throws	IllegalStateException
	 *			Throws this error when this purse is terminated
	 *			| this.isTerminated()
	 */
	public void add (int amount) throws TornException, IllegalArgumentException, IllegalStateException {
		if (isTorn()) {
			throw new TornException(this);
		} else if (this.isTerminated()) {
			throw new IllegalStateException ("This item is terminated");
		}
		
		int newAmount = getContent() + amount;
		if (newAmount >= getContent() && canHaveAsContent(newAmount)) {
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
	 * @post	The new content is decreased with the given amount
	 * 			| new.getContent() == this.getContent() - amount
	 * @throws	TornException
	 * 			throws this error when you try to remove ducates from a torn purse
	 * 			| this.isTorn()
	 * @throws	IllegalArgumentException
	 * 			Throws this error when an illegal amount (overflow or negative) is given or this purse can't have the new amount.
	 * 			| getContent() - amount < getContent() || !canHaveAsContent(this.getContent() - amount)
	 * @throws	IllegalStateException
	 *			Throws this error when this purse is terminated
	 *			| this.isTerminated()
	 */
	public void remove (int amount) throws TornException, IllegalArgumentException, IllegalStateException {
		if (isTorn()) {
			throw new TornException(this);
		} else if (this.isTerminated()) {
			throw new IllegalStateException ("This item is terminated");
		}
		
		int newAmount = getContent() - amount;
		if (newAmount <= getContent() && canHaveAsContent(newAmount)) {
			setContent(newAmount);
		} else {
			// overflow or negative amount
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
	 * 			throws this error when you try to take ducates from a torn purse
	 * 			| purse.isTorn()
	 * @throws	IllegalArgumentException
	 *			Throws this error when the given purse is terminated
	 *			| purse.isTerminated()
	 */
	public void add (Purse purse) throws TornException, IllegalArgumentException {
		if (purse.isTerminated()) {
			throw new IllegalArgumentException ("The given purse is terminated");
		} else if (purse.isTorn()) {
			throw new TornException(purse); 
		}
		
		int c = purse.getContent();
		
		purse.remove(c);
		this.add(c);
		
		purse.drop();
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
	 * Return the maximum value for this item
	 * @return	Return the maximum value for this item
	 */
	@Immutable@Override
	public int getMaxValue () {
		return 0;
	}
	
	/**
	 * Return the minimum value for this item
	 * @return	Return the minimum value for this item
	 */
	@Immutable@Override
	public int getMinValue () {
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
