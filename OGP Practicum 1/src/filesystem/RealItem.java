package filesystem;

import filesystem.exception.DiskItemNotWritableException;

public class RealItem extends DiskItem {

	protected RealItem(Directory parent, String name, boolean writable)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(parent, name, writable);
		// TODO Auto-generated constructor stub
	}
	
	protected RealItem(String name, boolean writable)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(name, writable);
		// TODO Auto-generated constructor stub
	}

}
