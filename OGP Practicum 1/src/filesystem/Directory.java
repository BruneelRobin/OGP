package filesystem;

import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of directories.
 *
 * @invar	Each item must have a properly spelled name.
 * 			| isValidName(getName())
 * @invar   Each item must have a valid creation time.
 *          | isValidCreationTime(getCreationTime())
 * @invar   Each item must have a valid modification time.
 *          | canHaveAsModificationTime(getModificationTime())
 *          
 *          
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */

public class Directory extends Item {
	
	/**********************************************************
     * Constructors
     **********************************************************/
	
	/**
	 * Initialize a new directory with a given directory, name and writability.
	 * 
	 * @param 	dir
	 * 			the directory of the new directory.
	 * @param 	name
	 * 			the name of the new directory.
	 * @param 	writable
	 * 			the writability of the new directory.
	 * @effect  The parent directory of the directory is set to the given directory.
     * 			If the given directory is not valid, an error is thrown.
     * @effect  The name of the directory is set to the given name.
     * 			If the given name is not valid, a default name is set.
     *          | setName(name)
     * @effect	The writability is set to the given flag.
     * 			| setWritable(writable)
     * @post    The new creation time of this directory is initialized to some time during
     *          constructor execution.
     *          | (new.getCreationTime().getTime() >= System.currentTimeMillis()) &&
     *          | (new.getCreationTime().getTime() <= (new System).currentTimeMillis())
     * @post    The new file has no time of last modification.
     *          | new.getModificationTime() == null
	 */
	public Directory(Directory dir, String name, boolean writable) {
		super(dir,name,writable);
	}
	
	/**
	 * Initialize a new directory with a given directory and name.
	 * 
	 * @param 	dir
	 * 			the directory of the new directory.
	 * @param 	name
	 * 			the name of the new directory.
	 * @effect  This new directory is initialized with the given name 
     * 			and writability.
     *         	| this(dir,name,writable)
	 */
	public Directory(Directory dir, String name) {
		this(dir,name,true);
	}
	
	/**
	 * Initialize a new directory with a given name and writability
	 * 
	 * @param 	name
	 * 			the name of the new directory.
	 * @param 	writable
	 * 			the writability of the new directory.
     * @effect  This new directory is initialized with the given name and writability.
     * 			The directory is set as a root directory.
     *         	| this(null,name,writable)
	 */
	public Directory(String name, boolean writable) {
		this(null,name,writable);
	}
	
	/**
	 * Initialize a new directory with a given name.
	 * 
	 * @param 	name
	 * 			the name of the new directory.
	 * @effect  This new directory is initialized with the given name and true writability. 
	 * 			The directory is set as a root directory.
     *         	| this(null,name,true)
	 */
	public Directory(String name) {
		this(null,name, true);
	}
	

	
    /**
     * Deletes this object and its associations
     * @effect	This item is deleted from its directory and the item's directory is set to null
     * 			| ((Item)this).delete()
     * @throws	DirectoryNotEmptyException
     * 			Throws this exception when the current directory is not empty.
     * @throws 	NotWritableException
     * 			Throws this exception when the current file is not writable
     */
	@Override
	public void delete () throws DirectoryNotEmptyException, NotWritableException {
		if (this.getNbItems() > 0)
			throw new DirectoryNotEmptyException(this);
		
		super.delete();
	}
	
	
	
	
	

    /**********************************************************
     * Children
     **********************************************************/
	
	ArrayList<Item> children = new ArrayList<Item>();
	
	/**
	 * Returns the number of items in this directory.
	 * @return Returns the number of items in this directory.
	 */
	public int getNbItems() {
		return children.size();
		
	}
		
	/**
	 * Returns the index of an item in this directory.
	 * @param 	item
	 * 		  	The item for which the index is searched for in this directory.
	 * @return 	Returns the index of an item in this directory.
	 * @return 	Returns 0 when the given item is not in this directory.
	 */
	public int getIndexOf(Item item) {
		return this.children.indexOf(item)+1;
	}
	
	
	
	/**
	 * Returns the item at the given index
	 * @param 	index
	 * 			The index of the item you want to request
	 * @return	Returns the item at the given index
	 * @throws 	IndexOutOfBoundsException
	 * 			Throws an exception when the item at the given index does not exist.
	 */
	public Item getItemAt(int index) throws IndexOutOfBoundsException {
		return children.get(index-1);
	}
	
	
	/**
	 * Returns the index of the item with the given name
	 * @param 	from	
	 * 			the index from where to look
	 * @param 	to	
	 * 			the index to where to look
	 * @param 	name
	 * 			the name to look for
	 * @param 	matchCase
	 * 			whether we need to take care of cases or not
	 * @return	Returns the index of the item with the given name.
	 * 			Returns -1 when the given name is not found.
	 */
	private int binarySearch (int from, int to, String name) {
		if (to >= from) {
	        int mid = from + (to - from) / 2;
	        Item midItem = children.get(mid);
	        int comp = midItem.compareName(name);
	  
	        // name == midItem.getName()
	        if (comp == 0) {
	            return mid; 
	        }
	        else if (comp == 1) { // midItem is hoger geranked dan name
	            return binarySearch(from, mid - 1, name); 
	        } else { // midItem is lager geranked dan name
	        	return binarySearch(mid + 1, to, name); 
	        }
	        
	    } 
	  
	    // We reach here when element is not 
	    // present in array 
	    return -1; 
	}
	
	/**
	 * Searches for an item with the given name
	 * @param 	name
	 * 			The name to look for.
	 * @return	Returns the item with the given name.
	 * 			Returns null when the item is not found.
	 */
	@Basic
	public Item getItem (String name) {
		int index = binarySearch(0, this.getNbItems()-1, name);
		return index == -1 ? null : children.get(index);
	}
	
	/**
	 * Returns the children of the current directory
	 * @return	Returns the children of the current directory as an ArrayList object
	 */
	@Basic
	public ArrayList<Item> getChildren() {
		return this.children;
	}
	
	/*public void test () {
		for (int i = 0; i<100;i++) {
			Item item = new Item(this, String.valueOf(i), true);
			this.addChild(item);
		}
		
		System.out.println(this.getItem("34") == null);
		System.out.println(this.exists("34"));
		System.out.println(this.getItem("103") == null);
		System.out.println(this.exists("103"));
		
		
	}*/
	
	/*public void test () {
		for (int i = 0; i<100;i++) {
			try {
				Item item = new Item(this, String.valueOf((int)(Math.random() * 100 + 1)), true);
				this.addChild(item);
			} catch (AlreadyExistsException e) {
				
			}
			
		}
		
		for (int i = 0; i<getNbItems();i++) {
			System.out.println(this.getItemAt(i+1).getName());
		}
		
		
		
	}*/
	
	
	/**
	 * Checks whether an item already exists within this directory, only check one level below. 
	 * @param item
	 * 		  The searched for item
	 * @return Checks whether an item already exists within the current directory, only checks one level below.
	 * 		   Returns false when the item does not exist within the current directory
	 * 		   Returns true when the item does exist within the current directory
	 */
	
	public boolean hasAsItem (Item item) {
		return (this.getItem(item.getName()) == item);
	}
	
	/**
	 * Checks whether a file or subdirectory with the given name exists within this directory, 
	 * doesn't match cases and only checks one level below.
	 * @param 	name
	 * 			The name of the file to check
	 * @return	Checks whether an item exists in the current directory. This function ignores cases
	 * 			Returns false when the file exists.
	 * 			Returns true when the file doesn't exist.
	 */
	public boolean exists (String name) {
		for (Item child : children) {
			if (child.getName().toLowerCase().equals(name.toLowerCase()))
				return true;
		}
		return false;
	}
	
	/**
	 * Finds the correct index to insert a new item
	 * @param 	from
	 * 			the index from where to start looking
	 * @param 	to
	 * 			the index until where to look
	 * @param 	name
	 * 			the name that needs to be inserted
	 * @return	Returns the index where the item needs to be inserted
	 * 			Returns -1 when the name already exists in the current directory
	 */
	
	private int getInsertIndex (int from, int to, String name) {
		if (to<from) {
			return 0;
		}
		
        int mid = from + (to - from) / 2; 
        Item midItem = children.get(mid);
        Item firstItem = children.get(from);
        Item endItem = children.get(to);
        int compFirst = firstItem.compareName(name);
        int compMid = midItem.compareName(name);
        int compEnd = endItem.compareName(name);
        
        if (compFirst == 0 || compMid == 0 || compEnd == 0) {
        	// Equals
        	//throw AlreadyExistsException(this, name);
        	return -1;
        }
  
        // name == midItem.getName()
        if (compFirst == 1) { //dan moet voor deze geinsert worden (door from+1, mid+1)
        	return from;
        }
        else if (compEnd == -1) { //dan moet na deze geinsert worden (door mid-1, from-1)
        	return to+1;
        }
        else if (compFirst == -1 && compMid == 1) { // gezochte plek hiertussen
        	if (mid-from <= 1) { // moet hiertussen
        		return mid;
        	}
            return getInsertIndex(from+1, mid-1, name); 
        }
        else {
        	if (to-mid <= 1) {
        		return to;
        	}
        	return getInsertIndex(mid+1, to-1, name);
        }
	}
	
	/**
	 * Adds a child to the directory
	 * @param 	child
	 * 			the child to be added
	 * @throws 	IsOwnAncestorException
	 * 			Throws this error when you try to add a child directory that is an ancestor 
	 * 			of the current directory
	 * @throws 	AlreadyExistsException
	 * 			Throws this error when there already exists a child with the same name.
	 */
	protected void addChild(Item child) throws IsOwnAncestorException, AlreadyExistsException {
		int insertIndex = getInsertIndex(0, getNbItems()-1, child.getName());
		if (insertIndex == -1) {
			throw new AlreadyExistsException(this, child);
		} else if (child.getClass() == Directory.class && this.isDirectOrIndirectSubdirectoryOf((Directory)child)) {
			throw new IsOwnAncestorException(child);
		}
		children.add(insertIndex, child);
		this.setModificationTime();
	}
	
	/**
	 * Removes the child from this directory
	 * @param 	child
	 * 			the child to be removed
	 */
	protected void removeChild(Item child) {
		children.remove(child);
		this.setModificationTime();
	}
	
	/**
	 * Checks whether the given directory is an ancestor of the current directory
	 * @param 	directory
	 * 			the directory to check
	 * @return	Returns true when the given directory is an ancestor of the current directory
	 * 			Returns false when the given directory is not an ancestor of the current directory
	 */
	@Raw
	public boolean isDirectOrIndirectSubdirectoryOf(Directory directory) {
		Directory folder = this.getDirectory();
		while (folder != null) {
			if (folder == directory) {
				return true;
			} else folder = folder.getDirectory();
		}
		return false;
	}
}
