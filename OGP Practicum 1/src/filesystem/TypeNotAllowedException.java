package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class for signaling illegal types for an item.
 * 
 * @author 	Tommy Messelis
 * @version	2.0 - 2015
 */
public class TypeNotAllowedException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable referencing the item to which change was denied.
	 */
	private final String type;

	/**
	 * Initialize this new type not allowed exception involving the
	 * given item.
	 * 
	 * @param	type
	 * 			The type for the new type not allowed exception.
	 * @post	The type involved in the new type not allowed exception
	 * 			is set to the given type.
	 * 			| new.getType() == type
	 */
	@Raw
	public TypeNotAllowedException(String type) {
		this.type = type;
	}
	
	/**
	 * Return the type involved in this type not allowed exception.
	 */
	@Raw @Basic
	public String getType() {
		return type;
	}
	
	
}
