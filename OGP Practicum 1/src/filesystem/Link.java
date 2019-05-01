package filesystem;

import filesystem.exception.DiskItemNotWritableException;

public class Link extends DiskItem {

	public Link(Directory parent, String name, boolean writable)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(parent, name, writable);
		// TODO Auto-generated constructor stub
	}
	
	RealItem referencedItem = null;
	
	@Override
	public boolean canHaveAsName(String name) {
		return this.referencedItem.canHaveAsName(name);
	}

}
