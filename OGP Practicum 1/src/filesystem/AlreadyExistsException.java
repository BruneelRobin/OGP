package filesystem;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal attempts to add an item to a directory.
 * 
 * @author 	Tommy Messelis
 * @version	2.0 - 2015
 */
public class AlreadyExistsException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;
	
	private final Directory dir;
	private final Item child;

	/**
	 * Initialize this already exists exception involving the
	 * given child and directory.
	 * 
	 * @param 	dir
	 * 			The directory for where an item with the same name already exists.
	 * @param	child
	 * 			The child for the new already exists exception.
	 * @post	The child involved in the new already exists exception
	 * 			is set to the given child.
	 * 			| new.getChild() == child
	 * @post	The directory involved in the new already exists exception
	 * 			is set to the given directory.
	 * 			| new.getDirectory() == dir
	 */
	@Raw
	public AlreadyExistsException(Directory dir, Item child) {
		this.dir = dir;
		this.child = child;
	}
	
	/**
	 * Return the directory involved in this item already exists exception.
	 */
	@Raw @Basic
	public Item getDirectory() {
		return dir;
	}
	
	/**
	 * Return the child involved in this item already exists exception.
	 */
	@Raw @Basic
	public Item getChild() {
		return child;
	}
	
	
}
