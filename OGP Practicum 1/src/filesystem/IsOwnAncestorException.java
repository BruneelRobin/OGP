package filesystem;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal attempts to add an item to a directory.
 * 
 * @author 	Tommy Messelis
 * @version	2.0 - 2015
 */
public class IsOwnAncestorException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;
	
	private final Item child;

	/**
	 * Initialize this new is own ancestor exception involving the
	 * given child.
	 * 
	 * @param	child
	 * 			The child for the new is own ancestor exception.
	 * @post	The child involved in the new is own ancestor exception
	 * 			is set to the given child.
	 * 			| new.getChild() == child
	 */
	@Raw
	public IsOwnAncestorException(Item child) {
		this.child = child;
	}
	
	/**
	 * Return the child involved in this is own ancestor exception.
	 */
	@Raw @Basic
	public Item getChild() {
		return child;
	}
	
	
}
