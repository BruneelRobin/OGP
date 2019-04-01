package filesystem;

import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of directories.
 * 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */

public class Directory extends Item {
	
	/**********************************************************
     * Constructors
     **********************************************************/
	
	/**
	 * Initialize a new directory with a given directory, name and writability.
	 * 
	 * @param 	dir
	 * 			the directory of the new directory.
	 * @param 	name
	 * 			the name of the new directory.
	 * @param 	writable
	 * 			the writability of the new directory.
	 * @effect  The parent directory of the directory is set to the given directory.
     * 			If the given directory is not valid, an error is thrown.
     * @effect  The name of the directory is set to the given name.
     * 			If the given name is not valid, a default name is set.
     *          | setName(name)
     * @effect	The writability is set to the given flag.
     * 			| setWritable(writable)
     * @post    The new creation time of this directory is initialized to some time during
     *          constructor execution.
     *          | (new.getCreationTime().getTime() >= System.currentTimeMillis()) &&
     *          | (new.getCreationTime().getTime() <= (new System).currentTimeMillis())
     * @post    The new file has no time of last modification.
     *          | new.getModificationTime() == null
	 */
	public Directory(Directory dir, String name, boolean writable) {
		super(dir,name,writable);
	}
	
	/**
	 * Initialize a new directory with a given directory and name.
	 * 
	 * @param 	dir
	 * 			the directory of the new directory.
	 * @param 	name
	 * 			the name of the new directory.
	 * @effect  This new directory is initialized with the given name 
     * 			and writability.
     *         	| this(name, writable)
	 */
	public Directory(Directory dir, String name) {
		this(dir,name,true);
	}
	
	/**
	 * Initialize a new directory with a given name and writability
	 * 
	 * @param 	name
	 * 			the name of the new directory.
	 * @param 	writable
	 * 			the writability of the new directory.
     * @effect  This new directory is initialized with the given name and writability.
     * 			The directory is set as a root directory.
     *         	| this(null,name,writable)
	 */
	public Directory(String name, boolean writable) {
		this(null,name,writable);
	}
	
	/**
	 * Initialize a new directory with a given name.
	 * 
	 * @param 	name
	 * 			the name of the new directory.
	 * @effect  This new directory is initialized with the given name and true writability. 
	 * 			The directory is set as a root directory.
     *         	| this(null,name,true)
	 */
	public Directory(String name) {
		this(null,name, true);
	}
	
<<<<<<< Updated upstream
	/**********************************************************
     * Items
     **********************************************************/
=======

>>>>>>> Stashed changes
	
	
}
