package filesystem;

import filesystem.exception.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of files.
 *
 * @invar	Each file must have a valid size.
 * 			| isValidSize(getSize())
 * @invar   Each file must have a valid type.
 *          | isValidType(getType())
 * 
 * @note	Subclasses may only add/strengthen invariants (Liskov principle)
 * 
 * @note	Please note how this class does not provide constructors without a parent directory.
 * 			Calling such a constructor prohibits this parent to be null.
 * 
 * @author 	Tommy Messelis
 * @version	4.1 - 2017        
 */
public class File extends ActualItem{
	
	
    /**********************************************************
     * Constructors
     **********************************************************/
   
    /**
     * Initialize a new file with given parent directory, name,
     * type, size and writability.
     *
     * @param  	parent
     *         	The parent directory of the new file.       
     * @param  	name
     *         	The name of the new file.
     * @param  	type
     *         	The type of the new file. 
     * @param  	size
     *         	The size of the new file.
     * @param  	writable
     *         	The writability of the new file.
     * 
     * @pre		type is effective
     * 			|type != null
     * @effect 	The new file is an actual disk item with the given
     *         	parent, name and writability.
     *         	| super(parent,name,writable)
     * @effect 	The new file has the given size
     *         	| setSize(size)
     * @post   	The type of this new file is set to the given type.
     *         	| new.getType() == type        
     */
    public File(Directory parent, String name, Type type, int size, boolean writable)
    		throws IllegalArgumentException, DiskItemNotWritableException {
    	super(parent,name,writable);
    	setSize(size);
    	this.type=type;
    }

    /**
     * Initialize a new writable, empty file with given parent directory,
     * name and type.
     *
     * @param  	parent
     *         	The parent directory of the new file.
     * @param  	name
     *         	The name of the new file.
     * @param  	type
     *         	The type of the new file.        
     * 
     * @effect 	This new file is initialized with the given parent directory,
     * 			name, type, zero size and true writability.
     *         	| this(parent,name,type,0,true)
     */
    public File(Directory parent, String name, Type type)
    		throws IllegalArgumentException, DiskItemNotWritableException {
    	this(parent,name,type,0,true);
    }    
    
 
    
    
    /**********************************************************
	 * delete/termination
	 **********************************************************/

    /**
	 * Check whether this disk item can be terminated. 
	 * 
	 * @return	True if the file is not yet terminated, and it is either a root or
	 * 			its parent directory is writable, false otherwise
	 * 			| result == !isTerminated() isWritable() && (isRoot() || getParentDirectory().isWritable())
	 * 
	 * @note	We must close the specification from the superclass.
	 * 			(The superclass says nothing about the conditions under which ‘true' can be returned.)
	 * 			
	 */
	@Override
    public boolean canBeTerminated(){
		//implementation-wise, we can simply refer to the superclass, because we know it will
		//behave correctly. (Because we implemented it!)
		return super.canBeTerminated();
	}
	
	/**
     * Check whether this file can be recursively deleted
     * 
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
	 * writable
	 **********************************************************/

    /**
	 * Check whether the new writability is allowed
	 * 
	 * @param 	writability
	 * 			the writability to check
	 * @return	always true 
	 * 			| result == true
	 */
	public boolean canAcceptAsNewWritability(boolean writability){
		return true;
	}
	
	
	
	
	/**********************************************************
	 * Name
	 **********************************************************/
	
	/**
	 * Check whether the given name is a legal name for a file.
	 * 
	 * @param  	name
	 *			The name to be checked
	 * @return	True if the given string is effective, not
	 * 			empty and consisting only of letters, digits, dots,
	 * 			hyphens and underscores; false otherwise.
	 * 			| result ==
	 * 			|	(name != null) && name.matches("[a-zA-Z_0-9.-]+")
	 */
	@Override
	@Raw
	public boolean canHaveAsName(String name) {
		return (name != null && name.matches("[a-zA-Z_0-9.-]+"));
	}
    
	/**
	 * Returns the absolute path of the link
	 * 
	 * @return	the string representation of this File, preceded with the absolute path of the parent
	 * 			| result.equals(getParentDirectory().getAbsolutePath() + "/" + toString())
	 */
	public String getAbsolutePath(){
		return getParentDirectory().getAbsolutePath() + "/" + toString();	
	}
	
   /**
	* Return a textual representation of this file.
	* 
	* @return  The name of this file followed by a dot
	*          followed by the extension representing the
	*          type of this file.
	*          | result.equals(getName()+"."+getType().getExtension())
	*/    
    public String toString(){
    	  return getName()+"."+getType().getExtension();
    }
	
	
    
    
    /**********************************************************
     * type
     **********************************************************/
    
    /**
	 * Variable referencing the type of this file.					
	 */
    private final Type type;
    
    /**
     * Return whether the given type is a valid type for a file.
     *
     * @param  type
     *         The type to check.
     * @return True if and only if the given type is effective.
     *         | result == (type != null)
     */
    public static boolean isValidType(Type type){
    	  return type != null;
    }
    
    /**
     * Return the type of this file.
     */ 
    @Raw @Basic @Immutable
    public Type getType(){
    	  return type;
    }

    
    
    
    /**********************************************************
     * size - nominal programming
     **********************************************************/
    
    /**
     * Variable registering the size of this file (in bytes).
     */
    private int size = 0;
    
    /**
     * Variable registering the maximum size of any file (in bytes).
     */
    private static final int maximumSize = Integer.MAX_VALUE;


    /**
     * Return the size of this file (in bytes).
     */
    @Raw @Basic 
    public int getSize() {
        return size;
    }
    
    /**
     * Set the size of this file to the given size.
     *
     * @param  size
     *         The new size for this file.
     * @pre    The given size must be legal.
     *         | isValidSize(size)
     * @post   The given size is registered as the size of this file.
     *         | new.getSize() == size
     */
    @Raw @Model 
    private void setSize(int size) {
        this.size = size;
    }
   
    /**
     * Return the maximum file size.
     */
    @Basic @Immutable
    public static int getMaximumSize() {
        return maximumSize;
    }

    /**
     * Check whether the given size is a valid size for a file.
     *
     * @param  size
     *         The size to check.
     * @return True if and only if the given size is positive and does not
     *         exceed the maximum size.
     *         | result == ((size >= 0) && (size <= getMaximumSize()))
     */
    public static boolean isValidSize(int size) {
        return ((size >= 0) && (size <= getMaximumSize()));
    }

    /**
     * Increases the size of this file with the given delta.
     *
     * @param   delta
     *          The amount of bytes by which the size of this file
     *          must be increased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The size of this file is increased with the given delta.
     *          | changeSize(delta)
     */
    public void enlarge(int delta) throws DiskItemNotWritableException {
        changeSize(delta);
    }

    /**
     * Decreases the size of this file with the given delta.
     *
     * @param   delta
     *          The amount of bytes by which the size of this file
     *          must be decreased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The size of this file is decreased with the given delta.
     *          | changeSize(-delta)
     */
    public void shorten(int delta) throws DiskItemNotWritableException {
        changeSize(-delta);
    }

    /**
     * Change the size of this file with the given delta.
     *
     * @param  delta
     *         The amount of bytes by which the size of this file
     *         must be increased or decreased.
     * @pre    The given delta must not be 0
     *         | delta != 0
     * @effect The size of this file is adapted with the given delta.
     *         | setSize(getSize()+delta)
     * @effect The modification time is updated.
     *         | setModificationTime()
     * @throws DiskItemNotWritableException(this)
     *         This file is not writable.
     *         | ! isWritable()
     */
    @Model 
    private void changeSize(int delta) throws DiskItemNotWritableException{
        if (isWritable()) {
            setSize(getSize()+delta);
            setModificationTime();            
        }else{
        	throw new DiskItemNotWritableException(this);
        }
    }  
    
    
    /**********************************************************
	 * parent directory
	 **********************************************************/

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
	 *	@note	Please note that null is not always forbidden: terminated Files don't have a parent!
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
	 * Returns the total disk usage of this File
	 * 
	 * @return	the size of this file
	 * 			| result == getSize()
	 */
	public int getTotalDiskUsage(){
		return getSize();
	}
    
}