package filesystem;

import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * A class of items.
 *
 * @invar	Each item must have a properly spelled name.
 * 			| isValidName(getName())
 * @invar	Each item must have a valid size.
 * 			| isValidSize(getSize())
 * @invar   Each item must have a valid creation time.
 *          | isValidCreationTime(getCreationTime())
 * @invar   Each item must have a valid modification time.
 *          | canHaveAsModificationTime(getModificationTime())
 *          
 * @author  Tommy Messelis
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 4.1
 * 
 */

public class Item {
	/**********************************************************
     * Constructors
     **********************************************************/
	
    /**
     * Initialize a new item with given directory, name and writability.
     *
     * @param	dir
     * 			The directory of the new item.
     * @param  	name
     *         	The name of the new item.
     * @param  	writable
     *         	The writability of the new item.
     * @effect  The directory of the item is set to the given directory.
     * 			
     *          | setDirectory(name)
     * @effect  The name of the item is set to the given name.
     * 			If the given name is not valid, a default name is set.
     *          | setName(name)
     * @effect	The writability is set to the given flag
     * 			| setWritable(writable)
     * @post    The new creation time of this item is initialized to some time during
     *          constructor execution.
     *          | (new.getCreationTime().getTime() >= System.currentTimeMillis()) &&
     *          | (new.getCreationTime().getTime() <= (new System).currentTimeMillis())
     * @post    The new item has no time of last modification.
     *          | new.getModificationTime() == null
     * @throws  AlreadyExistsException(this)
     * 			There is already a file with this name in the given directory.
     * 			| getItem(name) == null
     * @throws  IsOwnParentException(this)
     * 			An item can't be its own parent, this would create an invalid loop.
     * 			|  isDirectOrIndirectSubdirectoryOf()
     * 			
     * 			
     */
	public Item(Directory dir, String name, boolean writable) {
        setWritable(writable);
        setName(name);
        setDirectory(dir);
    }
    
    
    
    /**********************************************************
     * name - total programming
     **********************************************************/

    /**
     * Variable referencing the name of this item.
     * @note		See Coding Rule 32, for information on the initialization of fields.
     */
    private String name = null;

    /**
     * Return the name of this item.
     * @note		See Coding Rule 19 for the Basic annotation.
     */
    @Raw @Basic 
    public String getName() {
        return name;
    }

    /**
     * Check whether the given name is a legal name for an item.
     * 
     * @param  	name
     *			The name to be checked
     * @return	True if the given string is effective, not
     * 			empty and consisting only of letters, digits, dots,
     * 			hyphens and underscores; false otherwise.
     * 			| result ==
     * 			|	(name != null) && name.matches("[a-zA-Z_0-9.-]+")
     */
    public static boolean isValidName(String name) {
        return (name != null && name.matches("[a-zA-Z_0-9.-]+"));
    }
    
    /**
     * Set the name of this item to the given name.
     *
     * @param   name
     * 			The new name for this item.
     * @post    If the given name is valid, the name of
     *          this item is set to the given name,
     *          otherwise the name of the item is set to a valid name (the default).
     *          | if (isValidName(name))
     *          |      then new.getName().equals(name)
     *          |      else new.getName().equals(getDefaultName())
     */
    @Raw @Model 
    private void setName(String name) {
        if (isValidName(name)) {
        		this.name = name;
        } else {
        		this.name = getDefaultName();
        }
    }
    
    /**
     * Return the name for a new item which is to be used when the
     * given name is not valid.
     *
     * @return   A valid item name.
     *         | isValidName(result)
     */
    @Model
    private static String getDefaultName() {
    	return "new_item";
    }

    /**
     * Change the name of this item to the given name.
     *
     * @param	name
     * 			The new name for this item.
     * @effect  The name of this item is set to the given name, 
     * 			if this is a valid name and the item is writable, 
     * 			otherwise there is no change.
     * 			| if (isValidName(name) && isWritable())
     *          | then setName(name)
     * @effect  If the name is valid and the item is writable, the modification time 
     * 			of this item is updated.
     *          | if (isValidName(name) && isWritable())
     *          | then setModificationTime()
     * @throws  NotWritableException(this)
     *          This item is not writable
     *          | ! isWritable() 
     */
    public void changeName(String name) throws NotWritableException {
        if (isWritable()) {
            if (isValidName(name)){
            	setName(name);
                setModificationTime();
            }
        } else {
            throw new NotWritableException(this);
        }
    }
    
    /**********************************************************
     * creationTime
     **********************************************************/

    /**
     * Variable referencing the time of creation.
     */
    private final Date creationTime = new Date();
   
    /**
     * Return the time at which this item was created.
     */
    @Raw @Basic @Immutable
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * Check whether the given date is a valid creation time.
     *
     * @param  	date
     *         	The date to check.
     * @return 	True if and only if the given date is effective and not
     * 			in the future.
     *         	| result == 
     *         	| 	(date != null) &&
     *         	| 	(date.getTime() <= System.currentTimeMillis())
     */
    public static boolean isValidCreationTime(Date date) {
    	return 	(date!=null) &&
    			(date.getTime()<=System.currentTimeMillis());
    }

    

    /**********************************************************
     * modificationTime
     **********************************************************/

    /**
     * Variable referencing the time of the last modification,
     * possibly null.
     */
    private Date modificationTime = null;
   
    /**
     * Return the time at which this item was last modified, that is
     * at which the name or size was last changed. If this item has
     * not yet been modified after construction, null is returned.
     */
    @Raw @Basic
    public Date getModificationTime() {
        return modificationTime;
    }

    /**
     * Check whether this item can have the given date as modification time.
     *
     * @param	date
     * 			The date to check.
     * @return 	True if and only if the given date is either not effective
     * 			or if the given date lies between the creation time and the
     * 			current time.
     *         | result == (date == null) ||
     *         | ( (date.getTime() >= getCreationTime().getTime()) &&
     *         |   (date.getTime() <= System.currentTimeMillis())     )
     */
    public boolean canHaveAsModificationTime(Date date) {
        return (date == null) ||
               ( (date.getTime() >= getCreationTime().getTime()) &&
                 (date.getTime() <= System.currentTimeMillis()) );
    }

    /**
     * Set the modification time of this item to the current time.
     *
     * @post   The new modification time is effective.
     *         | new.getModificationTime() != null
     * @post   The new modification time lies between the system
     *         time at the beginning of this method execution and
     *         the system time at the end of method execution.
     *         | (new.getModificationTime().getTime() >=
     *         |                    System.currentTimeMillis()) &&
     *         | (new.getModificationTime().getTime() <=
     *         |                    (new System).currentTimeMillis())
     */
    @Model 
    protected void setModificationTime() {
        modificationTime = new Date();
    }

    /**
     * Return whether this item and the given other item have an
     * overlapping use period.
     *
     * @param 	other
     *        	The other item to compare with.
     * @return 	False if the other item is not effective
     * 			False if the prime object does not have a modification time
     * 			False if the other item is effective, but does not have a modification time
     * 			otherwise, true if and only if the open time intervals of this item and
     * 			the other item overlap
     *        	| if (other == null) then result == false else
     *        	| if ((getModificationTime() == null)||
     *        	|       other.getModificationTime() == null)
     *        	|    then result == false
     *        	|    else 
     *        	| result ==
     *        	| ! (getCreationTime().before(other.getCreationTime()) && 
     *        	|	 getModificationTime().before(other.getCreationTime()) ) &&
     *        	| ! (other.getCreationTime().before(getCreationTime()) && 
     *        	|	 other.getModificationTime().before(getCreationTime()) )
     */
    public boolean hasOverlappingUsePeriod(Item other) {
        if (other == null) return false;
        if(getModificationTime() == null || other.getModificationTime() == null) return false;
        return ! (getCreationTime().before(other.getCreationTime()) && 
        	      getModificationTime().before(other.getCreationTime()) ) &&
        	   ! (other.getCreationTime().before(getCreationTime()) && 
        	      other.getModificationTime().before(getCreationTime()) );
    }

    
    
    /**********************************************************
     * writable
     **********************************************************/
   
    /**
     * Variable registering whether or not this item is writable.
     */
    private boolean isWritable = true;
    
    /**
     * Check whether this item is writable.
     */
    @Raw @Basic
    public boolean isWritable() {
        return isWritable;
    }

    /**
     * Set the writability of this item to the given writability.
     *
     * @param isWritable
     *        The new writability
     * @post  The given writability is registered as the new writability
     *        for this item.
     *        | new.isWritable() == isWritable
     */
    @Raw 
    public void setWritable(boolean isWritable) {
        this.isWritable = isWritable;
    }
    
    /**********************************************************
     * directory
     **********************************************************/
	Directory dir = null;
    
    /**
     * Returns the current directory of this item
     * @return	Returns the current directory
     * @return 	Returns null when this item is in the root directory
     */
    public Directory getDirectory () {
    	return this.dir;
    }
    
    /**
     * Set the directory of this item to the given directory.
     * 
     * @param 	dir
     * 			The new directory
     * @post	The given directory is registered as the new directory for this item.
     * 			| new.getDirectory() == dir
     */
    private void setDirectory(Directory dir) {
    	this.dir = dir;
    	
    }
    
    /**
     * Sets the directory of this item to the root directory
     * @post	The new directory is the root directory.
     * 			| new.dir == null
     */
    public void makeRoot () {
    	this.dir = null;
    }
    
    /**
     * Moves an item to the new directory
     * @param 	dir
     * 			The new directory, when dir is null the new item will be in the root directory.
     * @post	The given directory is registered as the new directory for this item.
     * 			The item will be removed from his old directory and added into the new directory.
     */
    public void move(Directory dir) {
    	setDirectory(dir);
    }
    
    /**
     * Returns the root item from the item's tree
     * @return	Returns the root of the current item tree.
     */
    public Item getRoot() {
    	Item parent = this.dir;
    	while (parent != null) {
    		parent = parent.dir;
    	}
    	
    	return parent;
    }
    
    /**********************************************************
     * Children
     **********************************************************/
	ArrayList<Item> children = new ArrayList<Item>();
	
	public void addChild(Item child) throws  {
		
	}
}
