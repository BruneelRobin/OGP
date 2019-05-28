package qahramon;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.TerminatedException;
import qahramon.exceptions.TornException;

/**
 * A class of purses as special kinds of items
 * involving capacity, content and isTorn.
 * This class implements the interface 'Container'.
 * 
 * @invar	Each purse must have a valid capacity.
 * 			| isValidCapacity(getCapacity())
 * @invar	Each purse must have a valid content.
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
	 * 
	 * @param 	capacity
	 * 			the capacity of this purse
	 * @param 	weight
	 * 			the weight of this purse
	 * @param	content
	 * 			the default content
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
		purseIds.add(getIdentification());
	}
	
	/***********************
	 * Class type
	 ***********************/
	
	/**
	 * Check whether this item is a purse.
	 * 
	 * @return	Always return true since this item is a purse.
	 * 			| result == true
	 */
	@Basic@Immutable@Override@Raw
	public boolean isPurse () {
		return true;
	}
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	/**
	 * Check whether the given identification is valid.
	 * 
	 * @param 	identification
	 * 			the identification to check
	 * @return	Return true when the given identification is a long.
	 * 			Return false otherwise.
	 * 			| result == true
	 */
	@Override@Raw
	public boolean canHaveAsIdentification(long identification) {
		return true;
	}
	
	/**
	 * Check whether the given identification is valid and unique.
	 * 
	 * @param	identification
	 * 			the identification to check
	 * @return	Return true when this purse can have the given identification number that has to be unique
	 * 			Return false otherwise.
	 * 			| result == canHaveAsIdentification (identification) 
	 * 							&& purseIds.contains(identification) == false
	 * 
	 * @note 	The uniqueness of the generated long is always considered true.
	 * 			The chance of colliding is not zero, but neglectable (5.4*10^(-20)).			
	 */
	@Override@Raw
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && purseIds.contains(identification) == false;
	}
	
	/**
	 * Generate a valid identification for a purse.
	 * 
	 * @return 	Return a unique long.
	 * 			| MathHelper.getRandomLong()
	 */
	@Override@Raw
	protected long generateIdentification() {
		long candidate = MathHelper.getRandomLong();
		
		if (!canHaveAsNewIdentification (candidate)) {
			return generateIdentification();
		}
		
		return candidate;
	}
	
	/**
	 * Variable referencing a set with all purseIds of this class. 
	 * 
	 * @invar Each non null element in the hashSet references an effective identification. 
	 *        | for (Long id : purseIds)
	 *        | 	id != null
	 * @invar Each non null element in the hashSet references a valid identification. 
	 *        | for (Long id : purseIds)
	 *        | 	canHaveAsIdentification(id)
	 */
	private static final HashSet<Long> purseIds = new HashSet<Long>();
	
	/*******************************
	 * Capacity - total programming
	 *******************************/
	
	/**
	 * Variable referencing the amount of ducates a purse can hold.
	 */
	private final int capacity;
	
	/**
	 * Return the capacity of this container.
	 * 
	 * @return	Return the capacity of this container
	 */
	@Basic@Immutable@Raw
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Check whether the given capacity is valid.
	 * 
	 * @param 	capacity
	 * 			the capacity to check
	 * @return	Return true when the given capacity is valid.
	 * 			Return false otherwise.
	 * 			| result == (capacity >= 0)
	 */
	public static boolean isValidCapacity (int capacity) {
		return (capacity >= 0);
	}
	
	/***********************
	 * Torn
	 ***********************/
	
	/**
	 * Variable referencing whether or not the purse is torn.
	 */
	private boolean isTorn = false;
	
	/**
	 * Return the torn state of this container.
	 * 
	 * @return	Return the torn state of this container.
	 */
	@Basic@Raw
	public boolean isTorn() {
		return this.isTorn;
	}
	
	/**
	 * Set the torn state of this container to the given value.
	 * 
	 * @param	isTorn
	 * 			the new torn state
	 * @post	the new torn state is set to the given state
	 * 			| new.isTorn() == isTorn
	 */
	@Raw
	private void setTorn (boolean isTorn) {
		this.isTorn = isTorn;
	}
	
	/**
	 * Set the torn state of this container to true.
	 * 
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
	
	/**
	 * Variable referencing the amount of ducates currently in the purse.
	 */
	private int content;
	
	/**
	 * Return the content of this container.
	 * 
	 * @return	Return the content of this container
	 */
	@Basic@Raw
	public int getContent() {
		return this.content;
	}
	
	/**
	 * Set the content of this container.
	 * 
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
	 * Check whether this purse can have the given content.
	 * 
	 * @param 	content
	 * 			the content to check
	 * @return	Return true when the content of this non torn purse is within the allowed range 
	 * 			and the additional content doesn't exceed the maximum capacity of the holder of this purse.
	 * 			Return false otherwise.
	 * 			| result == !isTorn() && content >= 0 && content <= getCapacity() && (this.getHolder() == null || 
	 * 						this.getHolder().getCapacity() - this.getHolder().getTotalWeight() >= (content - getContent())*getDucateWeight())
	 */
	@Raw
	public boolean canHaveAsContent (int content) {
		float newWeight = (content - getContent())*getDucateWeight();
		boolean validWeight =  (this.getHolder() == null || this.getHolder().getCapacity() - this.getHolder().getTotalWeight() >= newWeight);
		
		return (!isTorn() && content >= 0 && content <= getCapacity() && validWeight);
	}
	
	/**
	 * Add the given amount of ducates to this purse.
	 * 
	 * @param 	amount
	 * 			the amount to add
	 * @post	The new content is increased with the given amount.
	 * 			| new.getContent() == this.getContent() + amount
	 * @effect	When the new content is higher than the allowed capacity this purse is torn.
	 * 			| makeTorn()
	 * @throws	TornException
	 * 			Throws this error when you try to add ducates to a torn purse.
	 * 			| this.isTorn()
	 * @throws	IllegalArgumentException
	 * 			Throws this error when an illegal amount (overflow or negative) is given or this purse can't have the new amount.
	 * 			| getContent() + amount < getContent() || !canHaveAsContent(this.getContent() + amount)
	 * @throws	TerminatedException
	 *			Throws this error when this purse is terminated.
	 *			| this.isTerminated()
	 */
	public void add (int amount) throws TornException, IllegalArgumentException, TerminatedException {
		if (isTorn()) {
			throw new TornException(this);
		} else if (this.isTerminated()) {
			throw new TerminatedException(this);
		}
		
		int newAmount = getContent() + amount;
		if (newAmount >= getContent() && canHaveAsContent(newAmount)) {
			setContent(getContent() + amount);
		} else if (newAmount > getCapacity()) {
			makeTorn();
		} else {
			// overflow or negative amount
			throw new IllegalArgumentException ("Illegal amount given.");
		}
	}
	
	/**
	 * Remove the given amount of ducates from this purse.
	 * 
	 * @param 	amount
	 * 			the amount to remove
	 * @post	The new content is decreased with the given amount.
	 * 			| new.getContent() == this.getContent() - amount
	 * @throws	TornException
	 * 			Throws this error when you try to remove ducates from a torn purse.
	 * 			| this.isTorn()
	 * @throws	IllegalArgumentException
	 * 			Throws this error when an illegal amount (overflow or negative) is given or this purse can't have the new amount.
	 * 			| getContent() - amount < getContent() || !canHaveAsContent(this.getContent() - amount)
	 * @throws	TerminatedException
	 *			Throws this error when this purse is terminated.
	 *			| this.isTerminated()
	 */
	public void remove (int amount) throws TornException, IllegalArgumentException, TerminatedException {
		if (isTorn()) {
			throw new TornException(this);
		} else if (this.isTerminated()) {
			throw new TerminatedException(this);
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
	 * Add the contents of the given purse to the current purse.
	 * 
	 * @param 	purse
	 * 			the purse to empty
	 * @post	The new content is increased with the content of the given purse.
	 * 			| this.getContent() == this.getContent() + purse.getContent()
	 * 			| purse.getContent() == 0
	 * @post	The emptied purse is dropped.
	 * 			| purse.drop()
	 * @effect	When the new content is higher than the allowed capacity this purse is torn.
	 * 			| makeTorn()
	 * @throws	TornException
	 * 			Throws this error when you try to take ducates from a torn purse.
	 * 			| purse.isTorn()
	 * @throws	TerminatedException
	 *			Throws this error when the given purse is terminated.
	 *			| purse.isTerminated()
	 */
	public void add(Purse purse) throws TornException, TerminatedException {
		if (purse.isTerminated()) {
			throw new TerminatedException(purse);
		} else if (purse.isTorn()) {
			throw new TornException(purse); 
		}
		
		int content = purse.getContent();
		
		purse.remove(content);
		this.add(content);
		
		purse.drop();
	}
	
	/***********************
	 * Other Methods
	 ***********************/
	
	/**
	 * Variable referencing the weight of a ducate.
	 */
	private final static float DUCATE_WEIGHT = 0.05f;
	
	/**
	 * Return the weight of one ducate.
	 * 
	 * @return	Return the weight of one ducate.
	 */
	@Basic@Immutable@Raw
	public static float getDucateWeight () {
		return DUCATE_WEIGHT;
	}
	
	/**
	 * Return the default weight.
	 * 
	 * @return	Return the default weight.
	 */
	@Immutable@Basic@Override
	public float getDefaultWeight () {
		return 0.1f;
	}
	
	/**
	 * Return the total weight of this purse.
	 * 
	 * @return	Return the total weight this purse: weight of the purse combined with weight of its content.
	 * 			| result == ((float)(this.getContent())*getDucateWeight() + this.getWeight())
	 */
	@Raw
	public float getTotalWeight() {
		return ((float)(this.getContent())*getDucateWeight() + this.getWeight());
	}
	
	/**
	 * Return the maximum value for this item.
	 * 
	 * @return	Return the maximum value for this item.
	 */
	@Immutable@Override@Basic@Raw
	public int getMaxValue () {
		return 0;
	}
	
	/**
	 * Return the minimum value for this item.
	 * 
	 * @return	Return the minimum value for this item.
	 */
	@Immutable@Override@Basic@Raw
	public int getMinValue () {
		return 0;
	}
	
	/**
	 * Return the total value of this purse.
	 * 
	 * @return	Return the total value of this purse in ducates.
	 * 			| result == this.getContent()
	 */
	@Raw
	public int getTotalValue() {
		return this.getContent();
	}
	
	/**
	 * Return a string containing all public data of this purse.
	 * 
	 * @return Return a string containing all public data of this purse.
	 */
	@Override
	public String toString() {
		return "Purse\n" + super.getString()
				+ "\nContent: " + getContent() + " ducates\nCapacity: " + getCapacity() + " ducates\n";
	}
}
