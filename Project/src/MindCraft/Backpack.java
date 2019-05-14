package MindCraft;

import java.util.ArrayList;
import java.util.HashMap;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class of backpacks
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public class Backpack {
	
	private static int backpackCount = 0;
	
	/***********************
	 * Constructors
	 ***********************/
	
	/**
	 * Creates a backpack
	 */
	public Backpack () {
		
		
		
		backpackCount ++;
	}
	
	
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	private final long identification = generateIdentification();
	
	
	/**
	 * 
	 */
	public boolean isValidIdentification(long identification) {
		return false;
		
	}
	
	/**
	 * 
	 */
	protected long generateIdentification() {
		//binomiaal met backpackCount
		return 0L;
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
	HashMap<Long, ArrayList> content = new HashMap<Long, ArrayList>();
	
	/**
	 * Set the value at the given key to the given arraylist
	 * @param	id
	 * 			the key used in the dictionary
	 * @param 	list
	 * 			the value used in the dictionary
	 */
	private void setContent (long id, ArrayList list) {
		
	}
	
	/**
	 * Return the list at the given id
	 * @param 	id
	 * 			The key of the dictionary
	 * @return	Return the list at the given id
	 */
	private ArrayList getListAt (long id) {
		return null;
	}
	
	/**
	 * Return true when the current backpack contains this item
	 * @param 	item
	 * 			The item to be checked
	 * @return	Return true when the current backpack contains this item
	 * 			Return false when the current backpack doesn't contain this item
	 */
	public boolean containsItem(Item item) {
		return false;
	}
	
	/**
	 * Removes an item from this backpack
	 * @param 	item
	 * 			The item to be removed
	 * @post	Removes the item from this backpack
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given item is not found in this backpack
	 */
	public void removeItem(Item item) throws IllegalArgumentException {
		
	}
	
	/**
	 * Checks whether this backpack can have an item
	 * @param 	item
	 * 			The item to check
	 * @return	Return true when this backpack can have this item
	 * 			Return false when this backpack can't have this item
	 */
	public boolean canHaveAsItem (Item item) {
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
	public void addItem(Item item) throws IllegalArgumentException {
		
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
}
