package qahramon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import qahramon.exceptions.TerminatedException;

/**
 * A class of backpacks as special kinds of items involving a capacity and content.
 * This class implements the interface 'Container'.
 * 
 * @invar	Each backpack must have a valid capacity.
 * 			| isValidCapacity(getCapacity())
 * @invar	Each backpack must have proper items.
 * 			| hasProperItems()
 * 			
 * 
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
	 * @param 	capacity
	 * 			the capacity of this backpack
	 * @param 	weight
	 * 			the weight of this backpack
	 * @param 	value
	 * 			the value of this backpack
	 * @pre		The given capacity must be valid.
	 * 			| isValidCapacity(capacity)
	 * @effect	The backpack is set as an item with given weight and value.
	 * 			| super(weight, value)
	 * @post	The capacity is set to the given capacity.
	 * 			| new.getCapacity() == capacity
	 */
	public Backpack (float capacity, float weight, int value) {
		super(weight, value);
		
		this.capacity = capacity;
		backpackCount ++;
	}
	
	/***********************
	 * Class type
	 ***********************/
	
	/**
	 * Check whether this item is a backpack.
	 * 
	 * @return	Always return true since this item is a backpack.
	 * 			| result == true
	 */
	@Basic@Immutable@Override@Raw
	public boolean isBackpack () {
		return true;
	}
	
	/**************************************
	 * Identification - total programming
	 **************************************/
	
	/**
	 * Return a valid identification number for this class.
	 * 
	 * @return	Return a valid identification number for this class which equals the sum of all nth binomial
	 * 			coefficients with n the backpack count after creating this class.
	 * 			| result == Math.pow(2, (backpackCount+1))
	 * 
	 * @note	This is the shortest method to get the wanted coefficient.
	 * 			The more extensive calculations can be found in the commentary.
	 */
	@Override@Raw
	protected long generateIdentification() {
		long n = backpackCount+1;
		/*long prev = 1L;
		long result = prev;
		for (int i = 1; i<n; i++) {
			result += (long)((float)(i-n)/(float)(i+1)*(float)prev);
		}
		return result;*/
		return (long) Math.pow(2, n);
	}
	
	/**
	 * Check whether the given identification is valid.
	 * 
	 * @param 	identification
	 * 			the identification to check
	 * @return	Return true when the given identification is divisible by two and larger than zero.
	 * 			Return false otherwise.
	 * 			| result == (identification % 2 == 0 && identification > 0)
	 */
	@Override@Raw
	public boolean canHaveAsIdentification(long identification) {
		return (identification % 2 == 0 && identification > 0);
	}
	
	/**
	 * Check whether the given identification is valid and unique.
	 * 
	 * @param	identification
	 * 			the identification to check
	 * @return	Return true when this backpack can have the given identification number that has to be unique.
	 * 			Return false otherwise.
	 * 			| result == canHaveAsIdentification (identification) 
	 * 			|				&& identification == generateIdentification()
	 */
	@Override@Raw
	public boolean canHaveAsNewIdentification (long identification) {
		return canHaveAsIdentification (identification) && identification == generateIdentification();
	}
	
	/*******************************
	 * Capacity - total programming
	 *******************************/
	
	/**
	 * Variable referencing the capacity of this backpack.
	 */
	private final float capacity;
	
	/**
	 * Return the capacity of this backpack.
	 * 
	 * @return	Return the capacity of this backpack.
	 */
	@Basic@Immutable@Raw
	public float getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Check whether the given capacity is valid.
	 * 
	 * @param 	capacity
	 * 			the capacity to check
	 * @return	Return true when the given capacity is positive.
	 * 			Return false otherwise.
	 * 			| result == capacity >= 0
	 */
	public static boolean isValidCapacity (float capacity) {
		return capacity >= 0;
	}
	
	/**********************************
	 * Content - defensive programming
	 **********************************/
	
	/**
	 * Variable referencing a dictionary of lists of items in this backpack. 
	 * This class has a bidirectional relation with the class Item.
	 * An item can be added/removed using addItem()/removeItem()
	 * 
	 * @invar	The dictionary must be effective.
	 * 			| content != null
	 * @invar 	Each item in this structure must be an effective item. 
	 * 			| for( Item item : getItems())
	 *        	| 	item != null
	 * @invar 	Each item references back to this backpack (bidirectional relation)
	 * 			| for( Item item : getItems())
	 *        	| 	item.getParentBackpack() == this
	 */	
	private final HashMap<Long, HashSet<Item>> content = new HashMap<Long, HashSet<Item>>();
	
	/**
	 * Set the value at the given key to the given hashSet.
	 * 
	 * @param	id
	 * 			the key used in the dictionary
	 * @param 	set
	 * 			the value used in the dictionary
	 * @post	Set the value at the given key to the given hashSet.
	 * 			| getContentAt(id) == set
	 * @throws	IllegalArgumentException
	 * 			Throws this error when this backpack contains an item with this key.
	 * 			| containsKey(id)
	 */
	@Raw
	private void setContentAt (long id, HashSet<Item> set) throws IllegalArgumentException {
		if(containsKey(id)) {
			throw new IllegalArgumentException("An item with this identification already exists in this backpack.");
		}
		this.content.put(id, set);
	}
	
	/**
	 * Return the set at the given id.
	 * 
	 * @param 	id
	 * 			the key of the dictionary
	 * @return	Return the set at the given id.
	 */
	@Basic@Raw
	private HashSet<Item> getContentAt (long id) {
		return this.content.get(id);
	}
	
	/**
	 * Check whether the current backpack contains this item.
	 * 
	 * @param 	item
	 * 			the item to be checked
	 * @return	Return true when the current backpack contains this item.
	 * 			Return false otherwise.
	 * 			| result == containsKey(item.getIdentification()) 
	 * 			|			&& this.getContentAt(item.getIdentification()).contains(item)
	 */
	public boolean contains(Item item) {
		return (containsKey(item.getIdentification()) && this.getContentAt(item.getIdentification()).contains(item));
	}
	
	/**
	 * Return the number of items in this backpack.
	 * 
	 * @return	Return the number of items in this backpack.
	 * 			| result == getItems().size()
	 */
	@Basic
	public int getNbItems() {
		return getItems().size();
	}
	
	/**
	 * Check whether the current backpack contains an item with this identification.
	 * 
	 * @param 	identification
	 * 			the identification to be checked
	 * @return	Return true when the current backpack contains an item with this id.
	 * 			Return false otherwise.
	 */
	private boolean containsKey(long identification) {
		return this.content.containsKey(identification);
	}
	
	/**
	 * Remove an item from this backpack.
	 * 
	 * @param 	item
	 * 			the item to be removed
	 * @post	Remove the item from this backpack.
	 * 			| new.contains(item) == false
	 * @throws	IllegalArgumentException
	 * 			Throws this error when the given item is not found in this backpack.
	 * 			| !contains(item)
	 * @throws	TerminatedException
	 * 			Throws this error when this item is terminated.
	 * 			| isTerminated()
	 */
	@Raw
	protected void remove(Item item) throws IllegalArgumentException, TerminatedException {
		if (isTerminated()) {
			throw new TerminatedException(this);
		}
		
		if (!contains(item)) {
			throw new IllegalArgumentException ("The given item does not exist in this backpack");
		} else {
			HashSet<Item> set = getContentAt(item.getIdentification());
			set.remove(item);
		}
	}
	
	/**
	 * Check whether this backpack can have an item.
	 * 
	 * @param 	item
	 * 			the item to check
	 * @return	Return false when the given item is this backpack or the item is terminated or this backpack is terminated.
	 * 			Return false when the item is a purse.
	 * 			Return false when the item is held by another non dead character than the holder of this backpack.
	 * 			Return false when the item can't be picked up by the holder of this backpack.
	 * 			Return false when the given item is a direct or indirect parent backpack of this backpack.
	 * 			Return false when the weight of the backpack with this item is higher than the capacity 
	 * 			of this backpack.
	 * 			Return true otherwise.
	 * 			| result == !(item == this || this.isTerminated() || item.isTerminated())
	 * 			| 			&& !(item.isPurse())
	 * 			|			&& !(item.getHolder() != null && item.getHolder() != this.getHolder() && item.getHolder().isDead() == false)
	 * 			|       	&& !(item.getHolder() == null && this.getHolder() != null && !this.getHolder().canPickUp(item))
	 * 			|			&& !(item.isBackpack() && this.isDirectOrIndirectSubBackpackOf((Backpack)item))
	 * 			|     		&& !(this.getTotalWeight() + item.getWeight() - this.getWeight() > this.getCapacity())
	 */
	@Raw
	public boolean canHaveAsItem (Item item) {
		if (item == this || this.isTerminated() || item.isTerminated()) {
			return false;
		}
		else if (item.isPurse()) {
			return false;
		}
		else if (item.getHolder() != null && item.getHolder() != this.getHolder() && item.getHolder().isDead() == false) {
			return false;
		} else if (item.getHolder() == null && this.getHolder() != null && !this.getHolder().canPickUp(item)) { 
			return false;
		} else if (item.isBackpack() && this.isDirectOrIndirectSubBackpackOf((Backpack)item)) {
			return false;
		} else if (this.getTotalWeight() + item.getWeight() - this.getWeight() > this.getCapacity()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check whether the given backpack is a direct or indirect parent backpack of this backpack.
	 * 
	 * @param 	backpack
	 * 			the parent backpack to check
	 * @return	Return true when the given backpack is a direct or indirect parent backpack of this backpack.
	 * 			Return false otherwise.
	 * 			| result == (backpack == this.getParentBackpack() || 
	 * 			|		this.getParentBackpack().getParentBackpack().isDirectOrIndirectSubBackpackOf(backpack))
	 */
	@Raw
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
	 * Add an item to this backpack.
	 * 
	 * @param 	item
	 * 			the item to be added
	 * @post	Add the item to this backpack.
	 * 			| new.contains(item)
	 * @throws	IllegalArgumentException
	 * 			Throws this error when an item can't be added to this backpack.
	 * 			| !canHaveAsItem(item)
	 * @throws	TerminatedException
	 * 			Throws this error when this item is terminated.
	 * 			| this.isTerminated()
	 */
	@Raw
	protected void add(Item item) throws IllegalArgumentException, TerminatedException {
		if (this.isTerminated()) {
			throw new TerminatedException(this);
		}
		
		if (!canHaveAsItem(item)) {
			throw new IllegalArgumentException("This item is not valid");
		}
		
		if (!containsKey(item.getIdentification())) {
			HashSet<Item> newList = new HashSet<Item>();
			newList.add(item);
			setContentAt(item.getIdentification(), newList);
		} else {
			HashSet<Item> list = getContentAt(item.getIdentification());
			list.add(item);
		}
	}
	
	/**
	 * Return a set with all the items of this backpack.
	 * 
	 * @return Return a set with all the items of this backpack.
	 */
	@Raw
	public Set<Item> getItems () {
		HashSet<Item> itemSet = new HashSet<Item> ();
		
		for (HashSet<Item> set : this.content.values()) {
			for (Item item : set) {
				itemSet.add(item);
			}
		}
		
		return itemSet;
	}
	
	/**
	 * Check whether this backpack has proper items.
	 * 
	 * @Return	Return true when this backpack can have each item in this backpack
	 * 			and the parentBackpack of this item is this backpack.
	 * 			Return false otherwise.
	 * 			| for each (Item item : getItems())
	 * 			| 	(canHaveAsItem(item) && item.getParentBackpack() == this)
	 */
	@Raw
	public boolean hasProperItems () {
		for (Item item : getItems()) {
			if (!canHaveAsItem(item) || item.getParentBackpack() != this) {
				return false;
			}
		}
		return true;
	}
	
	/*************************
	 * Value
	 *************************/
	
	/**
	 * Return the maximum value for this item.
	 * 
	 * @return	Return the maximum value for this item.
	 */
	@Immutable@Override@Basic@Raw
	public int getMaxValue () {
		return 500;
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
	 * Change the own value of a backpack.
	 * 
	 * @param	amount
	 * 		  	the amount of change, positive if an increase is wished for, negative if the value should be decreased
	 * @post 	The value of the backpack is set to the sum of the old value and the given amount.
	 * 		 	The result is then clamped, to make sure it lies between the value boundaries.
	 *       	| new.getValue() == MathHelper.clamp(old.getValue() + amount, getMinValue(), getMaxValue())
	 */
	public void changeValue(int amount) {
		this.setValue(MathHelper.clamp(this.getValue() + amount, getMinValue(), getMaxValue()));
	}
	
	/*************************
	 * Other methods
	 *************************/
	
	/**
	 * Return the total weight of this backpack.
	 * 
	 * @return	Return the total weight of this backpack, this is calculated using the own weight
	 * 			of this backpack and the weight of all descendants in this backpack.
	 * 			| result == sum ({item in getItems() : 
	 * 			|		if (item.isContainer()) then ((Container)(item)).getTotalWeight()
	 * 			|		else item.getWeight()
	 * 			|	})
	 */
	@Raw
	public float getTotalWeight() {
		float totalWeight = getWeight();
		for (Item item : getItems()) {
			if (item.isContainer()) {
				totalWeight += ((Container)(item)).getTotalWeight();
			} else {
				totalWeight += item.getWeight();
			}
		}
		
		return totalWeight;
		
	}
	
	/**
	 * Return the total value of this purse.
	 * 
	 * @return	Return the total value of this purse in ducates.
	 * 			| result == sum ({item in getItems() : 
	 * 			|		if (item.isContainer()) then ((Container)(item)).getTotalValue()
	 * 			|		else item.getValue()
	 * 			|	})
	 */
	@Raw
	public int getTotalValue() {
		int totalValue = getValue();
		for (Item item : getItems()) {
			if (item.isContainer()) {
				totalValue += ((Container)(item)).getTotalValue();
			} else {
				totalValue += item.getValue();
			}
		}
		
		return totalValue;
	}
	
	/**
	 * Return the amount of armors in this backpack.
	 * 
	 * @return	Return the amount of armors in this backpack also looks inside anchored backpacks.
	 * 			| result == sum ({item in getItems() : 
	 * 			|		if (item.isArmor()) then 1
	 * 			|		else ((Backpack)item).getArmorCount()
	 * 			|	})
	 */
	public int getArmorCount () {
		int armorCount = 0;
		for (Item item : getItems()) {
			if (item.isArmor()) {
				armorCount += 1;
			} else if (item.isBackpack()) {
				armorCount += ((Backpack)item).getArmorCount();
			}
		}
		
		return armorCount;
	}
	
	/**
	 * Terminate all weapons and armor in this backpack recursively.
	 * 
	 * @post	Terminate all weapons and armor in this backpack recursively.
	 * 			| for each (Item item : getItems())
	 * 			|	if (item.isBackpack()) then ((Backpack)item).terminateWeaponsAndArmor()
	 * 			|   else if (item.isArmor() || item.isWeapon()) then item.terminate()
	 */
	protected void terminateWeaponsAndArmor () {
		for (Item item : getItems()) {
			if (item.isBackpack()) {
				((Backpack)item).terminateWeaponsAndArmor();
			} else if (item.isArmor() || item.isWeapon()) {
				item.terminate();
			}
		}
	}
	
	/**
	 * Return the best armor in a backpack.
	 * 
	 * @return	Return the best armor of a backpack.
	 * 			| result == max ({item in getItems() : 
	 * 			|		if (item.isArmor()) then ((Armor)item).getFullProtection()
	 * 			|		else ((Backpack)item).getBestArmor()
	 * 			|	})
	 */
	public Armor getBestArmor() {
		int bestFullProtection = 0;
		Armor bestArmor = null;
		for (Item item: getItems()) {
			if (item.isArmor()) {
				Armor armor = (Armor) item;
				int armorProtection = armor.getFullProtection();
				if (armorProtection > bestFullProtection) {
					bestFullProtection = armorProtection;
					bestArmor = armor;
				}
			} else if (item.isBackpack()) {
				Backpack backpack = (Backpack) item;
				Armor bestBackpackArmor = backpack.getBestArmor();
				if (bestBackpackArmor != null && bestBackpackArmor.getFullProtection() > bestFullProtection) {
					bestFullProtection = bestBackpackArmor.getFullProtection();
					bestArmor = bestBackpackArmor;
				}
			}
		}
		return bestArmor;
	}
	
	/**
	 * Return the best weapon in a backpack.
	 * 
	 * @return	Return the best weapon of a backpack.
	 * 			| result == max ({item in getItems() : 
	 * 			|		if (item.isWeapon()) then ((Weapon)item).getDamage()
	 * 			|		else ((Backpack)item).getBestWeapon()
	 * 			|	})
	 * 		   
	 * 
	 */
	public Weapon getBestWeapon() {
		int bestDamage = 0;
		Weapon bestWeapon = null;
		for (Item item: getItems()) {
			if (item.isWeapon()) {
				Weapon weapon = (Weapon) item;
				int weaponDamage = weapon.getDamage();
				if (weaponDamage > bestDamage) {
					bestDamage = weaponDamage;
					bestWeapon = weapon;
				}
			} else if (item.isBackpack()) {
				Backpack backpack = (Backpack) item;
				Weapon bestBackpackWeapon = backpack.getBestWeapon();
				if (bestBackpackWeapon != null && bestBackpackWeapon.getDamage() > bestDamage) {
					bestDamage = bestBackpackWeapon.getDamage();
					bestWeapon = bestBackpackWeapon;
				}
			}
		}
		return bestWeapon;
	}
	
	/**
	 * Return the best backpack in a backpack.
	 * 
	 * @return	Return the best backpack of a backpack.
	 * 			| result == max ({item in getItems() : 
	 * 			|		if (item.isBackpack()) then 
	 * 			|			max ({((Backpack)item).getCapacity(), ((Backpack)item).getBestBackpack()})
	 * 			|	})		   
	 * 
	 */
	public Backpack getBestBackpack() {
		float bestCapacity = 0;
		Backpack bestBackpack = null;
		for (Item item: getItems()) {
			if (item.isBackpack()) {
				Backpack backpack = (Backpack) item;
				float backpackCapacity = backpack.getCapacity();
				if (backpackCapacity > bestCapacity) {
					bestCapacity = backpackCapacity;
					bestBackpack = backpack;
				}
				
				Backpack bestBackpackBackpack = backpack.getBestBackpack();
				if (bestBackpackBackpack != null && bestBackpackBackpack.getCapacity() > bestCapacity) {
					bestCapacity = bestBackpackBackpack.getCapacity();
					bestBackpack = bestBackpackBackpack;
				}
			}
		}
		return bestBackpack;
	}
	
	/**
	 * Return a string containing all public data of this backpack.
	 * 
	 * @return Return a string containing all public data of this backpack.
	 */
	@Override
	public String toString() {
		return "Backpack\n" + super.getString()
				+ "\nNumber of items: " + getItems().size() + "\nCapacity: " + getCapacity()
				+ " kg\nTotal weight: " + 
				getTotalWeight() + " kg\nTotal value: " + getTotalValue() + " ducates\n";
	}
}
