package filesystem;

import be.kuleuven.cs.som.annotate.*;
import filesystem.exception.*;


/**
 * A class of actual disk items, involving writability rights.
 * These items can either be files or directories, not links.
 * 
 * @note 	no additional invariants, only writability is added at this level
 * 
 * @author 	Tommy Messelis
 * @version	1.1 - 2017
 */
public abstract class ActualItem extends DiskItem {

	/**********************************************************
     * Constructors
     **********************************************************/ 
	
	/**		
     * Initialize a new root actual disk item with given name and writability.
     *
     * @param  	name
     *         	The name of the new actual disk item.
     * @param  	writable
     *         	The writability of the new actual disk item.
     * 
     * @effect 	The new actual disk item is initialized as a disk item.
     *         	| super()
     * @post 	The actual disk item is a root item
	 * 			| new.isRoot()
	 * @effect  The name of the actual disk item is set to the given name.
	 * 			If the given name is not valid, a default name is set.
	 *          | setName(name) 
	 * @effect	The writability is set to the given flag
	 * 			| setWritable(writable)
	 * 
	 * @note	Please note that the name can be set here. For actual items it is independent of other 
	 * 			properties. (Only for links, the referenced item is needed.)
	 * @note	This constructor turns the raw item from the super constructor into a steady state.
	 */
	@Model
    protected ActualItem(String name, boolean writable) {
    	super();
    	setName(name);
        setWritable(writable);
    }    

    /**
     * Initialize a new actual disk item with given parent directory, name and writability.
     *
     * @param  	parent
     *         	The parent directory of the new actual disk item.       
     * @param  	name
     *         	The name of the new actual disk item.
     * @param  	writable
     *         	The writability of the new actual disk item.
     * 
     * @effect 	The new actual disk item is initialized as a disk item.
     *         	| super()
     * @effect  The name of the actual disk item is set to the given name.
	 * 			If the given name is not valid, a default name is set.
	 *          | setName(name) 
	 * @effect	The writability is set to the given flag
	 * 			| setWritable(writable)
	 * @effect 	The given directory is set as the parent 
	 *         	directory of this item.
	 *         	| setParentDirectory(parent)
	 * @effect 	This item is added to the items of the parent directory
	 *         	| parent.addAsItem(this)
	 * 
	 * @throws 	IllegalArgumentException
	 *         	The given parent directory is not effective or terminated
	 *         	| parent == null || parent.isTerminated()
	 * @throws 	DiskItemNotWritableException(parent)
	 *         	The given parent directory is effective, but not writable.
	 *         	| parent != null && !parent.isWritable()
	 * 
	 * @throws 	IllegalArgumentException
	 *         	The given valid name already exists in the effective and writable parent directory
	 *          | parent != null && parent.isWritable() && 
	 *         	|   canHaveAsName(name) && parent.containsDiskItemWithName(name)
	 * @throws 	IllegalArgumentException
	 *         	The given name is not valid and the default name already exists in 
	 *         	the effective parent directory
	 *          | parent != null && parent.isWritable() && 
	 *         	|   !canHaveAsName(name) && parent.containsDiskItemWithName(getDefaultName())
	 * 
	 * @note	The name can be set here, it is independent of other properties.
	 * @note	This constructor turns the raw item from the super constructor into a steady state
	 */
    @Model
    protected ActualItem(Directory parent, String name, boolean writable) 
			throws IllegalArgumentException, DiskItemNotWritableException {
		super();
		if (parent == null) 
			throw new IllegalArgumentException("null is not allowed as parent");
		if(parent.isTerminated())
			throw new IllegalArgumentException("A terminated parent is not allowed.");
		if (!parent.isWritable()) 
			throw new DiskItemNotWritableException(parent);
		if (parent.isWritable() && canHaveAsName(name) && parent.containsDiskItemWithName(name))
			throw new IllegalArgumentException("The valid name already exists in the parent.");
		if (parent.isWritable() && !canHaveAsName(name) && parent.containsDiskItemWithName(getDefaultName()))
			throw new IllegalArgumentException("The name is not valid and the default name already exists in the parent.");
		
		setName(name);
		setWritable(writable);
		setParentDirectory(parent);
		try {
			parent.addAsItem(this);
		} catch (DiskItemNotWritableException e) {
			//cannot occur
			assert false;
		} catch (IllegalArgumentException e) {
			//cannot occur
			assert false;
		}
	}
    
    
    
	
    /**********************************************************
	 * delete/termination
	 **********************************************************/

    /**
	 * Check whether this disk item can be terminated.
	 * 
	 * @return	False if the disk item is not writable
	 * 			| if(!isWritable())
	 * 			| then result == false
	 * 
	 * @note	We add a new condition under which the result is false. The clause is still
	 * 			not conclusive in other cases, it will be in the subclasses.
	 * 			
	 */
	@Override
    public boolean canBeTerminated(){
		return super.canBeTerminated() && isWritable();
	}

	
	
	
	/**********************************************************
	 * writable
	 **********************************************************/

	/**
	 * Variable registering whether or not this disk item is writable.
	 */
	private boolean isWritable = true;

	/**
	 * Check whether this disk item is writable.
	 */
	@Raw @Basic
	public boolean isWritable() {
		return isWritable;
	}

	/**
	 * Set the writability of this disk item to the given writability.
	 *
	 * @param isWritable
	 *        The new writability
	 * @post  The given writability is registered as the new writability
	 *        for this disk item.
	 *        | new.isWritable() == isWritable
	 */
	@Model @Raw 
	protected void setWritable(boolean isWritable) {
		this.isWritable = isWritable;
	}
	
	/**
	 * Changes the writability of an actual item, if allowed.
	 * 
	 * @param 	writability
	 * 			the new writability
	 * 
	 * @effect	if allowed, the new writability is set,
	 * 			otherwise, no change is made
	 * 			| if(canAcceptAsNewWritability(writability))
	 * 			|	then setWritable(writability)
	 */
	public void changeWritabilityStatus(boolean writability){
		if(canAcceptAsNewWritability(writability))
			setWritable(writability);
	}
	
	/**
	 * Check whether the new writability is allowed
	 * 
	 * @param 	writability
	 * 			the writability to check
	 * 
	 * @return	true if the given writability status is allowed
	 * 
	 * @note	Please note that the parameter is not strictly necessary
	 * 			But this may change in the future...
	 */
	public abstract boolean canAcceptAsNewWritability(boolean writability);
	
	

		
	/**********************************************************
	 * name
	 **********************************************************/

	/**	
	 * Check whether the name of this actual disk item can be changed into the
	 * given name.
	 * 
	 * @return  True if this disk item is not terminated, the given 
	 *          name is a valid name for a disk item, the disk item is writable,
	 *			the given name is different from the current name of this disk item
	 *          and either this item is a root item or the parent directory does not 
	 *          already contain an other item with the given name;
	 *          false otherwise.
	 *          | result == !isTerminated() && canHaveAsName(name) && isWritable() &&
	 *          |			!getName().equals(name) && ( isRoot() || !getParentDirectory().exists(name) )
	 *          
	 * @note	This is a fully closed specification of this method.
	 */
	@Override		
	public boolean canAcceptAsNewName(String name) {
		return isWritable() && super.canAcceptAsNewName(name);
	}
	
	/**
	 * Set the name of this disk item to the given name.
	 * 
	 * @throws 	DiskItemNotWritableException(this)   
	 * 			The actual disk item is not writable
	 * 			| !isWritable()
	 *
	 * @note	The conditions under which this method can throw a DiskItemNotWritableExcpetions
	 * 			are now specifiable. This exception turned into a must throw exception!
	 * 			Please note that the [can] and the '?' have been removed from the clause.
	 */
	public void changeName(String name) throws DiskItemNotWritableException, IllegalStateException {
		if(!isWritable())
			throw new DiskItemNotWritableException(this);
		super.changeName(name);
	}
	
	
	
	
	/**********************************************************
	 * parent directory
	 **********************************************************/
	
	/**
	 * Check whether this actual item can be moved to another parent
	 * 
	 * @return	True if it is writable
	 * 			| result == isWritable()
	 */
	public boolean canBeMoved(){
		return isWritable();
	}
	
}
