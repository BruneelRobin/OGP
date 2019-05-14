package filesystem;

import be.kuleuven.cs.som.annotate.*;
import filesystem.exception.*;

/**
 * A class representing links to actual items
 * 
 * @invar	the linked item is valid
 * 			| isValidLinkedItem(getLinkedItem())
 * 
 * @author 	Tommy Messelis
 * @version	1.2 - 2017
 */
public class Link extends DiskItem {

	/**********************************************************
     * Constructors
     **********************************************************/ 
	
	/**
	 * Initialize a new link in a given parent directory, with the given name
	 * and referencing the given item
	 * 
	 * @param 	parent
	 * 			the parent directory of this link
	 * @param 	name
	 * 			the name of this link
	 * @param 	linkedItem
	 * 			the actual item which this link refers to
	 * 
	 * @effect 	The new link is initialized as a disk item.
     *         	| super()
     * @effect  The name of the link is set to the given name.
	 * 			If the given name is not valid, a default name is set.
	 *          | setName(name) 
	 * @post    The given item is registered as the linked item
	 * 			| new.getLinkedItem() == linkedItem
	 * @post	The new link is valid after construction
	 * 			| new.isValidLink()
	 * @effect 	The given directory is set as the parent 
	 *         	directory of this item.
	 *         	| setParentDirectory(parent)
	 * @effect 	This item is added to the items of the parent directory
	 *         	| parent.addAsItem(this)
	 * 
	 * @throws 	IllegalArgumentException
	 * 			The linkedItem is null or terminated
	 * 			| linkedItem == null || linkedItem.isTerminated()
	 * @throws 	IllegalArgumentException
	 *         	The given parent directory is not effective or terminated
	 *         	| parent == null || parent.isTerminated()
	 * @throws 	DiskItemNotWritableException(parent)
	 *         	The given parent directory is effective, but not writable.
	 *         	| parent != null && !parent.isWritable()
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
	 * @note	This constructor would not be allowed to call a super constructor that sets the name and
	 * 			the parent. Was this the case, then the link would already be added to the parent, before
	 * 			we can check here whether it even may be added. If that would not be allowed, this constructor
	 * 			would have to undo whatever the super constructor did, before throwing an exception.
	 */
	public Link(Directory parent, String name, ActualItem linkedItem)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(); // Java requires this to be the first call of a constructor!
		if(linkedItem == null)
			throw new IllegalArgumentException("null is not allowed as a linked item.");
		if(linkedItem.isTerminated())
			throw new IllegalArgumentException("A terminated item may not be linked.");
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
		
		this.linkedItem = linkedItem;
		setName(name);
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
	 * @return	True if the disk item is not yet terminated, and it is either a root or
	 * 			its parent directory is writable, false otherwise
	 * 			| result == !isTerminated() && (isRoot() || getParentDirectory().isWritable())
	 * 
	 * @note	We must override this method here, as we must complete the return clause
	 * 			of the superclass.
	 * 			
	 */
	@Override
	public boolean canBeTerminated() {
		return super.canBeTerminated();
	}
	
	/**
     * Check whether this link can be recursively deleted
     * 
     * @note	No additional specification needed.
     */
	@Override
    public boolean canBeRecursivelyDeleted(){
    	return canBeTerminated();
    }
	
	/**
     * Delete this link recursively.  
     *
	 * @note	This specification should not be repeated, the parent spec suffices.
     */    
    @Override
	public void deleteRecursive() throws IllegalStateException{
    	terminate();
    }
    
    
	
	
	/**********************************************************
	 * linkedItem
	 **********************************************************/
	
	/**
	 * variable referencing the linked item.
	 */
	private final ActualItem linkedItem;
	
	/**
	 * returns the linkedItem
	 */
	@Basic @Raw
	public final ActualItem getLinkedItem() {
		return linkedItem;
	}
	
	/**
	 * Check whether this linked item is valid
	 * 
	 * @param	item
	 * 			The item to check
	 * @return	true if this link can have the given item as a linked item
	 * 			that is, when it is not null
	 * 			| result == (item != null) 
	 */
	public static boolean isValidLinkedItem(ActualItem item) {
		return item != null;
	}
	
	/**
	 * Check whether this link is still valid
	 * 
	 * @return	true if this link is not terminated and the 
	 * 			linkedItem is not yet terminated
	 * 			| result == !isTerminated() && !getLinkedItem().isTerminated()
	 */
	public boolean isValidLink() {
		return !isTerminated() && !getLinkedItem().isTerminated();
	}

	
	
	
	/**********************************************************
	 * Name
	 **********************************************************/
	
	/**
	 * Check whether the given name is a legal name for a directory.
	 * 
	 * @param  	name
	 *			The name to be checked
	 * @return	True if the given string is a valid name for the linked item
	 * 			| result == getLinkedItem() != null &&
	 * 			|	getLinkedItem().canHaveAsName(name)
	 */
	@Override
	@Raw
	public boolean canHaveAsName(String name) {
		return getLinkedItem() != null && getLinkedItem().canHaveAsName(name);
	}
	
	/**	
	 * Check whether the name of this actual disk item can be changed into the
	 * given name.
	 * 
	 * @return  True if this link is not terminated, the given 
	 *          name is a valid name for a disk item,
	 *			the given name is different from the current name of this link
	 *          and either this item is a root item or the parent directory does not 
	 *          already contain an other item with the given name;
	 *          false otherwise.
	 *          | result == !isTerminated() && canHaveAsName(name) &&
	 *          |			!getName().equals(name) && ( isRoot() || !getParentDirectory().exists(name) )
	 *          
	 * @note	This is a fully closed specification of this method.
	 * 			We must complete the specification here, otherwise we don't know when this
	 * 			method will return a true!
	 */
	@Override		
	public boolean canAcceptAsNewName(String name) {
		return super.canAcceptAsNewName(name);
	}
	
	/**
	 * Returns the absolute path of the link
	 * 
	 * @return	the name of this link, preceded with the absolute path of the parent
	 * 			| result.equals(getParentDirectory().getAbsolutePath() + "/" + getName())
	 */
	public String getAbsolutePath(){
		return getParentDirectory().getAbsolutePath() + "/" + getName();		
	}
	
	
	
	
	/**********************************************************
	 * parent directory
	 **********************************************************/
	
	/**
	 * Check whether this link can be moved to another parent
	 * 
	 * @return	True, links can always be moved
	 * 			| result == true
	 */
	public boolean canBeMoved(){
		return true;
	}
	
	/** 
	 * Check whether this disk item can have the given directory as
	 * its parent directory.
	 * 
	 * @param  	directory
	 *          The directory to check.
	 * @return  If this disk item is terminated, 
	 * 			true if the given directory is not effective, 
	 * 			false otherwise.
	 *          | if (this.isTerminated())
	 *          | then result == (directory == null)
	 * @return	If this disk item is not terminated,
	 * 				if the given directory is not effective,
	 * 				then true if this disk item is a root item or the parent of this item is writable, 
	 * 					 false otherwise
	 * 				else if the given directory is terminated, then false
	 * 					 if this disk item is the same as the given directory, then false
	 * 					 if this disk item is a direct or indirect parent of the given directory, then false
	 * 					 else true if the given directory is writable and it can have this item as an item
	 * 							and this item is a root or the parent directory of this item is writable,
	 * 						  false otherwise.
	 *			| if (!this.isTerminated())
	 *			| then if (directory != null)
	 *			|	   then if (directory.isTerminated()) then result == false
	 *			|		 	if (directory == this) then result == false
	 *			|			if (this.isDirectOrIndirectParentOf(directory)) then result == false
	 *			|			else result == (directory.isWritable() && directory.canHaveAsItem(this) &&
	 *			|							(this.isRoot() || this.getParentDirectory().isWritable()) )
	 *			|	   else result == false
	 *	
	 *	@note 	The specification must now be closed.
	 */
	@Raw @Override
	public boolean canHaveAsParentDirectory(Directory directory) {
		// the implementation of the superclass is sufficient
		return super.canHaveAsParentDirectory(directory);
	}
	
	
	/**********************************************************
	 * disk usage
	 **********************************************************/

	/**
	 * Returns the total disk usage of this link
	 * 
	 * @return	the disk usage of a link: 0 bytes
	 * 			| result == 0
	 */
	public int getTotalDiskUsage(){
		return 0;
	}
	
	
}
