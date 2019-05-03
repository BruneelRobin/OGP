package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import filesystem.exception.DiskItemNotWritableException;

/**
 * A class of links.
 * @invar 	Each link can only reference one item in its lifetime.
 * 
 * @author 	Robin Bruneel, Edward Wiels, Jean-Louis Carron
 * @version 2.3 - 2018
 */

public class Link extends DiskItem {

	/**
	 * Initialize a new link with a given parent directory, name and reference.
	 * 
	 * @param 	parent
	 * 			The parent directory of the new link.
	 * @param 	name
	 * 			The name of the new link.	
	 * @param 	reference
	 * 			The reference item to which the link refers.
	 * 
	 * @effect	The new link is a disk item with a given parent directory, name and true writability.
	 * 			| super(parent, name, true)
	 * 
	 * @post	The reference of this new link is set to the given reference.
	 * 			| new.getReference = reference
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given reference is not effective, empty or is terminated.
	 * 			| !canHaveAsReference(reference)
	 * 			
	 * @throws	IllegalArgumentException
	 * 			The given name is not valid.
	 * 			| !canHaveAsName(name)
	 * 		
	 */
	public Link(Directory parent, String name, RealItem reference)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(parent, name, true);
		if (!canHaveAsReference(reference)) {
			this.terminate();
			throw new IllegalArgumentException();
		}
		this.referencedItem = reference;
		if (!canHaveAsName(name)) {
			this.terminate();
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Variable referencing the referenced item of this link.
	 */
	private final RealItem referencedItem;
	
	/**
	 * Check whether the given name is a legal name for a link.
	 * 
	 * @param	name
	 * 			The name to be checked.
	 * 
	 * @return	
	 * 			
	 */
	@Override
	public boolean canHaveAsName(String name) {
		if (getReference() == null) // we postponen deze check want reference bestaat nog niet in diskitem constructor
			return true;
		return getReference().canHaveAsName(name);
	}
	
	/**
	 * Return the referenced item of a link.
	 * 
	 * @return 	If the referenced item is not empty and unterminated,
	 * 			the referenced item of this link is returned.
	 * 
	 * @throws 	IllegalStateException
	 * 			The referenced item is not empty, but terminated.
	 * 			| this.referencedItem != null && this.referencedItem.isTerminated()
	 */
	@Raw
	public RealItem getReference () throws IllegalStateException {
		if (this.referencedItem != null && this.referencedItem.isTerminated()) {
			throw new IllegalStateException("The current reference is not valid");
		}
		return this.referencedItem;
	}
	
	/**
	 * Check whether the given reference is a legal reference.
	 * 
	 * @param 	reference
	 * 			The reference to be checked.
	 * @return	True if the given reference is effective, not empty and unterminated.
	 * 			| result ==
	 * 			| 	reference != null && !reference.isTerminated()
	 */
	@Raw
	public boolean canHaveAsReference (RealItem reference) {
		return reference != null && !reference.isTerminated();
	}
}
