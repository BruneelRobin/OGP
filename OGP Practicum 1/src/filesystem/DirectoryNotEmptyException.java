package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class for signaling illegal attempts to remove a non empty directory.
 * 
 * @author 	Tommy Messelis
 * @version	2.0 - 2015
 */
public class DirectoryNotEmptyException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;
	
	private final Directory dir;

	/**
	 * Initialize this already exists exception involving the
	 * given child and directory.
	 * 
	 * @param 	dir
	 * 			The directory that needs to be removed.
	 * @post	The directory involved in the directory not empty exception
	 * 			is set to the given directory.
	 * 			| new.getDirectory() == dir
	 */
	@Raw
	public DirectoryNotEmptyException(Directory dir) {
		this.dir = dir;
	}
	
	/**
	 * Return the directory involved in this exception.
	 */
	@Raw @Basic
	public Item getDirectory() {
		return dir;
	}
	
	
}

