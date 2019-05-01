package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import filesystem.exception.DiskItemNotWritableException;

/**
 * A class of links.
 * @invar 	Each link can only reference one item in its lifetime.
 * 
 * @author 	Robin Bruneel
 * @version 2.3 - 2018
 */

public class Link extends DiskItem {

	public Link(Directory parent, String name, RealItem reference)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(parent, name, true);
		if (!canHaveAsReference(reference))
			throw new IllegalArgumentException();
		this.referencedItem = reference;
	}
	
	private final RealItem referencedItem;
	
	@Override
	public boolean canHaveAsName(String name) {
		return this.referencedItem.canHaveAsName(name);
	}
	
	@Raw@Basic
	public RealItem getReference () {
		return this.referencedItem;
	}
	
	@Raw
	public boolean canHaveAsReference (RealItem reference) {
		return reference != null && !reference.isTerminated();
	}
}
