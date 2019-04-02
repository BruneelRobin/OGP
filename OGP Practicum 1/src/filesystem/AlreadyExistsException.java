package filesystem;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal attempts to change an item.
 * 
 * @author 	Tommy Messelis
 * @version	2.0 - 2015
 */
public class AlreadyExistsException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable referencing the item to which change was denied.
	 */
	private final Item item;

	/**
	 * Initialize this new item not writable exception involving the
	 * given item.
	 * 
	 * @param	item
	 * 			The item for the new item not writable exception.
	 * @post	The item involved in the new item not writable exception
	 * 			is set to the given item.
	 * 			| new.getItem() == item
	 */
	@Raw
	public AlreadyExistsException(Item item) {
		this.item = item;
	}
	
	/**
	 * Return the item involved in this item not writable exception.
	 */
	@Raw @Basic
	public Item getItem() {
		return item;
	}
	
	
}
