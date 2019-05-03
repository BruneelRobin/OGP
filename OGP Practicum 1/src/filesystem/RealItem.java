package filesystem;

import filesystem.exception.DiskItemNotWritableException;

/**
 * A class of realItems.
 * 
 * @author 	Robin Bruneel, Edward Wiels, Jean-Louis Carron
 * @version 2.3 - 2019
 */

public class RealItem extends DiskItem {
	
	/**
	 * Creates a RealItem with the given parameters
	 * @param	parent
	 * 			The parent directory for the item to be in
	 * @param 	name
	 * 			The name of the item
	 * @param 	writable
	 * 			The writability of the item
	 * @effect	Calls the constructor of DiskItem
	 * 			| super(parent, name, writable)
	 * @throws 	IllegalArgumentException
	 * @throws 	DiskItemNotWritableException
	 */
	protected RealItem(Directory parent, String name, boolean writable)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(parent, name, writable);
	}
	
	/**
	 * Creates a RealItem with the given parameters
	 * @param 	name
	 * 			| the name of the item
	 * @param 	writable
	 * 			| the writability of the item
	 * @effect	Calls the constructor of DiskItem
	 * 			| super(name, writable)
	 * @throws 	IllegalArgumentException
	 * @throws 	DiskItemNotWritableException
	 */
	protected RealItem(String name, boolean writable)
			throws IllegalArgumentException, DiskItemNotWritableException {
		super(name, writable);
	}

}
