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

	public Link(Directory parent, String name, RealItem reference)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(parent, name, true);
		if (!canHaveAsReference(reference))
			throw new IllegalArgumentException();
		this.referencedItem = reference;
		if (!canHaveAsName(name)) {
			throw new IllegalArgumentException();
		}
	}
	
	private final RealItem referencedItem;
	
	@Override
	public boolean canHaveAsName(String name) {
		if (getReference() == null) // we postponen deze check want reference bestaat nog niet in diskitem constructor
			return true;
		return getReference().canHaveAsName(name);
	}
	
	@Raw
	public RealItem getReference () throws IllegalStateException {
		if (this.referencedItem != null && this.referencedItem.isTerminated()) {
			throw new IllegalStateException("The current reference is not valid");
		}
		return this.referencedItem;
	}
	
	@Raw
	public boolean canHaveAsReference (RealItem reference) {
		return reference != null && !reference.isTerminated();
	}
}
