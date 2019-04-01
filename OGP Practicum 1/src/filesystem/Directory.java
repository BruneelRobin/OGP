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
	 * Initialize a new directory with a given name and writability
	 * 
	 * @param 	name
	 * 			the name of the new directory.
	 * @param 	writable
	 * 			the writability of the new directory.
	 */
	public Directory(String name, boolean writable) {
		super(name,writable);
	}
	
	/**
	 * Initialize a new directory with a given name.
	 * 
	 * @param 	name
	 * 			the name of the new directory.
	 */
	public Directory(String name) {
		super(name, true);
	}
	
	/**
	 * Initialize a new directory with a given directory, name and writability.
	 * 
	 * @param 	dir
	 * 			the directory of the new directory.
	 * @param 	name
	 * 			the name of the new directory.
	 * @param 	writable
	 * 			the writability of the new directory.
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
	 */
	public Directory(Directory dir, String name) {
		super(dir,name,true);
	}
	
	/**********************************************************
     * Items
     **********************************************************/
	
	
}
