package filesystem;

import be.kuleuven.cs.som.annotate.*;

import java.util.Date;

/**
 * A class of files.
 *
 * @invar	Each file must have a properly spelled name.
 * 			| isValidName(getName())
 * @invar	Each file must have a valid size.
 * 			| isValidSize(getSize())
 * @invar   Each file must have a valid creation time.
 *          | isValidCreationTime(getCreationTime())
 * @invar   Each file must have a valid modification time.
 *          | canHaveAsModificationTime(getModificationTime())
 * @author  Mark Dreesen
 * @author  Tommy Messelis
 * @version 3.1
 * 
 * @note		See Coding Rule 48 for more info on the encapsulation of class invariants.
 */
public class File extends Item {

    /**********************************************************
     * Constructors
     **********************************************************/
	
	/**
     * Initialize a new file with given name, size and writability.
     * @param	dir
     * 			The directory of the current file.
     * @param  	name
     *         	The name of the new file.
     * @param  	size
     *         	The size of the new file.
     * @param  	writable
     *         	The writability of the new file.
     * @param	type
     * 			The type of the new file.
     * @effect  The directory of the file is set to the given directory.
     * 			If the given directory is not valid, an error is thrown.
     * @effect  The name of the file is set to the given name.
     * 			If the given name is not valid, a default name is set.
     *          | setName(name)
     * @effect	The size is set to the given size (must be valid)
     * 			| setSize(size)
     * @effect	The writability is set to the given flag
     * 			| setWritable(writable)
     * @effect	The type of the current file is set to the given string
     * 			| setType(type)
     * @post    The new creation time of this file is initialized to some time during
     *          constructor execution.
     *          | (new.getCreationTime().getTime() >= System.currentTimeMillis()) &&
     *          | (new.getCreationTime().getTime() <= (new System).currentTimeMillis())
     * @post    The new file has no time of last modification.
     *          | new.getModificationTime() == null
     */
	public File(Directory dir, String name, int size, boolean writable, String type) {
		super(dir, name, writable); //maakt item aan
		setSize(size);
		setType(type);
	}
	
	/**
     * Initialize a new file with given name.
     * @param	dir
     * 			The directory of the current file.
     * @param   name
     *          The name of the new file.
     * @param	type
     * 			The type of the new file.
     * @effect  This new file is initialized with the given directory,
     * 			given name, a zero size, true writability and given type.
     *         | this(dir, name, 0, true, type)
     */
	public File(Directory dir, String name, String type) {
		this(dir, name, 0, true, type);
	}
	
    /**
     * Initialize a new file with given name, size and writability.
     *
     * @param  	name
     *         	The name of the new file.
     * @param  	size
     *         	The size of the new file.
     * @param  	writable
     *         	The writability of the new file.
     * @effect  The name of the file is set to the given name.
     * 			If the given name is not valid, a default name is set.
     *          | setName(name)
     * @effect	The size is set to the given size (must be valid)
     * 			| setSize(size)
     * @effect	The writability is set to the given flag
     * 			| setWritable(writable)
     * @post    The new creation time of this file is initialized to some time during
     *          constructor execution.
     *          | (new.getCreationTime().getTime() >= System.currentTimeMillis()) &&
     *          | (new.getCreationTime().getTime() <= (new System).currentTimeMillis())
     * @post    The new file has no time of last modification.
     *          | new.getModificationTime() == null
     */
	public File(String name, int size, boolean writable, String type) {
		super(name, writable); //maakt item aan
        setSize(size);
        setType(type);
    }

	/**
     * Initialize a new file with given name.
     * @param   name
     *          The name of the new file.
     * @param	type
     * 			The type of the new file.
     * @effect  This new file is initialized with the given name, 
     * 			a zero size, true writability and given type.
     *         	| this(dir, name, 0, true, type)
     */
	public File(String name, String type) {
		this(name, 0, true, type);
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
    	return "new_file";
    }
    
    /**********************************************************
     * type - total programming
     **********************************************************/
    String type;
    private void setType (String type) {
    	this.type = type;
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
    public void enlarge(int delta) throws NotWritableException {
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
    public void shorten(int delta) throws NotWritableException {
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
     * @throws FileNotWritableException(this)
     *         This file is not writable.
     *         | ! isWritable()
     */
    @Model 
    private void changeSize(int delta) throws NotWritableException{
        if (isWritable()) {
            setSize(getSize()+delta);
            setModificationTime();   
        } else {
        	throw new NotWritableException(this);
        }
    }
    
}

