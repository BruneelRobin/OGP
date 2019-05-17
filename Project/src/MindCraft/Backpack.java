package MindCraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of backpacks
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public class Backpack extends Item implements Container {
	
	private static int backpackCount = 0;
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Create a backpack with given capacity, weight and value.
	 * 
	 * @pre		The given capacity is valid
	 * 			| isValidCapacity(capacity)
	 */
	
	/**
	 * Create a backpack with given capacity, weight and value.
	 * @param 	capacity
	 * 			The capacity of this backpack.
	 * @param 	weight
	 * 			The weight of this backpack.
	 * @param 	value
	 * 			The value of this backpack.
	 * @pre		The given capacity is valid
	 * 			| isValidCapacity(capacity)
	 * @effect	The backpack is set as an item with given weight and value.
	 * 			| super(weight, value)
	 * @post	The capacity is set to the given capacity.
	 * 			| new.capacity == capacity
	 */
	public Backpack (float capacity, float weight, int value) {
		super(weight, value);
		
		setCapacity(capacity);
		backpackCount ++;
	}
	
	
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	/**
	 * Return a valid identification number for this class
	 * @return	Return a valid identification number for this class which equals the sum of all binomial
	 * 			coefficients in the nth row with n the backpack count after creating this class.
	 * 			| result == 2^(backpackCount+1)
	 */
	@Override
	protected long generateIdentification() {
		long n = backpackCount+1;
		/*long prev = 1L;
		long result = prev;
		for (int i = 1; i<n; i++) {
			result += (i-n)/(i+1)*prev;
		}
		return result;*/
		return 2^n;
	}
	
	/**
	 * 
	 * @param 	identification
	 * 			The identification to check
	 * @return	Return true when this item can have the given identification number
	 * 			Return false when this item can't have the given identification number
	 * 			| result == identification % 2 == 0
	 */
	@Override
	public boolean canHaveAsIdentification(long identification) {
		return identification % 2 == 0;
	}
	
	/**
	 * @param	identification
	 * 			The identification to check
	 * @return	Return true when this backpack can have the given identification number that has to be unique
	 * 			Return false when this backpack can't
	 * 			| result == canHaveAsIdentification (identification) 
	 * 							&& identification == generateIdentification()
	 */
	@Override
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && identification == generateIdentification();
	}
	

	
	
	
	
	/*******************************
	 * Capacity - total programming
	 *******************************/
	
	private float capacity;
	
	/**
	 * Return the capacity of this container
	 * @return	Return the capacity of this container
	 */
	@Basic
	public float getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Set the capacity of this container
	 * @param 	capacity
	 * 			the new capacity
	 * @post	the new capacity is set to the given capacity
	 * 			| new.getCapacity() == capacity
	 */
	private void setCapacity(float capacity) {
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
	public static boolean isValidCapacity (float capacity) {
		return false;
	}
	
	/**********************************
	 * Content - defensive programming
	 **********************************/
	
	/**
	 * Variable referencing a dictionary of lists of items in this backpack. 
	 * This class has a bidirectional relation with the class Item.
	 * An item can be added/removed using addItem()/removeItem()
	 * 
	 * @invar Each item in this structure must be an effective item. 
	 *        | item != null
	 * @invar Each item references back to this backpack (bidirection relation)
	 *        | item.getParentBackpack() == this
	 */	
	private final HashMap<Long, HashSet<Item>> content = new HashMap<Long, HashSet<Item>>();
	
	/**
	 * Set the value at the given key to the given arraylist
	 * @param	id
	 * 			the key used in the dictionary
	 * @param 	list
	 * 			the value used in the dictionary
	 */
	private void setContent (long id, HashSet<Item> list) {
		this.content.put(id, list);
	}
	
	/**
	 * Return the list at the given id
	 * @param 	id
	 * 			The key of the dictionary
	 * @return	Return the list at the given id
	 */
	private HashSet<Item> getListAt (long id) {
		return this.content.get(id);
	}
	
	/**
	 * Return true when the current backpack contains this item
	 * @param 	item
	 * 			The item to be checked
	 * @return	Return true when the current backpack contains this item
	 * 			Return false when the current backpack doesn't contain this item
	 */
	public boolean containsItem(Item item) {
		if (!containsId(item.getIdentification()) || !this.getListAt(item.getIdentification()).contains(item)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Return true when the current backpack contains an item with this identification
	 * @param 	identification
	 * 			The identification to be checked
	 * @return	Return true when the current backpack contains an item with this id
	 * 			Return false when the current backpack doesn't contain an item with this id
	 */
	private boolean containsId (long identification) {
		return this.content.containsKey(identification);
	}
	
	/**
	 * Removes an item from this backpack
	 * @param 	item
	 * 			The item to be removed
	 * @post	Removes the item from this backpack
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given item is not found in this backpack
	 */
	@Raw
	protected void removeItem(Item item) throws IllegalArgumentException {
		if (!containsItem(item)) {
			throw new IllegalArgumentException ("The given item does not exist in this backpack");
		} else {
			HashSet<Item> list = getListAt(item.getIdentification());
			list.remove(item);
		}
	}
	
	/**
	 * Checks whether this backpack can have an item
	 * @param 	item
	 * 			The item to check
	 * @return	Return false when the item is terminated
	 * 			Return false when the item is a purse
	 * 			Return false when the item is held by another non dead character than the holder of this backpack
	 * 			Return false when the given item is a direct or indirect parent backpack of this backpack
	 * 			Return false when the weight of the backpack with this item is higher than the capacity 
	 * 			of this backpack.
	 * 			Return true otherwise.
	 * 			
	 */
	public boolean canHaveAsItem (Item item) {
		if (item.isTerminated()) {
			return false;
		}
		else if (item instanceof Purse) {
			return false;
		}
		else if (item.getHolder() != null && item.getHolder() != this.getHolder() && item.getHolder().isDead() == false) {
			return false;
		} else if (item instanceof Backpack && this.isDirectOrIndirectSubBackpackOf((Backpack)item)) {
			return false;
		} else if (this.getTotalWeight() + item.getWeight() > this.getCapacity()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Return true when the given backpack is a direct or indirect parent backpack of this backpack
	 * @param 	backpack
	 * 			the parent backpack to check
	 * @return	Return true when the given backpack is a direct or indirect parent backpack of this backpack
	 * 			Return false when the given backpack is not a direct or indirect parent backpack of this backpack
	 */
	public boolean isDirectOrIndirectSubBackpackOf (Backpack backpack) {
		Backpack parent = this.getParentBackpack();
		while (parent != null) {
			if (backpack == parent) {
				return true;
			}
			parent = parent.getParentBackpack();
		}
		
		return false;
	}
	
	/**
	 * Adds an item in this backpack
	 * @param 	item
	 * 			The item to be added
	 * @post	Adds the item to this backpack
	 * @throws	IllegalArgumentException
	 * 			Throws this error when an item can't be added to this backpack
	 * 			| !canHaveAsItem(item)
	 */
	@Raw
	protected void addItem(Item item) throws IllegalArgumentException {
		if (!canHaveAsItem(item)) {
			throw new IllegalArgumentException("This item is not valid");
		}
		
		if (!containsId(item.getIdentification())) {
			HashSet<Item> newList = new HashSet<Item>();
			newList.add(item);
			setContent(item.getIdentification(), newList);
		} else {
			HashSet<Item> list = getListAt(item.getIdentification());
			list.add(item);
		}
	}
	
	/**
	 * Return a set with all the item of this backpack
	 * @return Return a set with all the item of this backpack
	 */
	public HashSet<Item> getItems () {
		HashSet<Item> itemSet = new HashSet<Item> ();
		
		for (HashSet<Item> set : this.content.values()) {
			for (Item item : set) {
				itemSet.add(item);
			}
		}
		
		return itemSet;
	}
	
	/*************************
	 * Value
	 *************************/
	/**
	 * Return the maximum value for this item
	 * @return	Return the maximum value for this item
	 */
	@Immutable@Override
	public int getMaxValue () {
		return 500;
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
	 * Change the own value of a backpack
	 * @param amount
	 * 		  The amount of change, positive if an increase is wished for, negative if the value should be decreased.
	 * @post the value of the backpack is set to the sum of the old value and the given amount.
	 * 		 The result is then clamped, to make sure it lies between the MIN_VALUE and MAX_VALUE.
	 *       | new.getValue() == MathHelper.clamp(old.getValue() + amount, MIN_VALUE, MAX_VALUE)
	 */
	public void changeValue(int amount) {
		this.setValue(MathHelper.clamp(this.getValue() + amount, getMinValue(), getMaxValue()));
	}
	
	
	
	
	
	
	/*************************
	 * Other methods
	 *************************/
	
	/**
	 * Return the total weight of this backpack
	 * @return	Return the total weight of this backpack, this is calculated using the own weight
	 * 			of this backpack and the weight of all descendants in this backpack
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
		return 0;
	}
	
	/**
	 * Return the amount of armor's in this backpack
	 * @return	Return the amount of armor's in this backpack also looks inside anchored backpacks
	 */
	public int getArmorCount () {
		int armorCount = 0;
		for (HashSet<Item> set : this.content.values()) {
			for (Item item : set) {
				if (item instanceof Armor) {
					armorCount += 1;
				} else if (item instanceof Backpack) {
					armorCount += ((Backpack)item).getArmorCount();
				}
			}
		}
		
		return armorCount;
	}
}
