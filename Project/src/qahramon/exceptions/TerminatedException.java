
package qahramon.exceptions;

import be.kuleuven.cs.som.annotate.*;
import qahramon.*;

/**
 * A class signaling illegal attempts to perform actions on terminated objects.
 * 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
public class TerminatedException extends RuntimeException {

	private static final long serialVersionUID = 5764443045080909435L;
	
	/**
	 * Variable referencing the item to which change was denied.
	 */
	private final Item item;
	
	/**
	 * Initialize this new terminated exception involving the
	 * given item.
	 * 
	 * @param	item
	 * 			The item for the new terminated exception.
	 * @post	The item involved in the new torn exception
	 * 			is set to the given item.
	 * 			| new.getItem() == item
	 */
	@Raw
	public TerminatedException(Item item) {
		this.item = item;
	}
	
	/**
	 * Return the item involved in this terminated exception.
	 */
	@Raw @Basic
	public Item getItem() {
		return this.item;
	}
	
}
