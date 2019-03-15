import java.util.Date;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

class UnauthorizedException extends RuntimeException
{
	public UnauthorizedException() {
		
	}
	
	public File file;
	public int size;
	public String name;
	
    
    public UnauthorizedException(File file, int size, String message)
    {
    	super(message);
    	this.file = file;
    	this.size = size;
    }
    
    public UnauthorizedException(File file, String name, String message)
    {
    	super(message);
    	this.file = file;
    	this.name = name;
    }
    
    
    
    /**
     * 
     * @return	Returns the file where the error occurred
     */
    public File getFile() {
    	return file;
    }
    
    /**
     * 
     * @return	Returns the size that caused the error to happen
     */
    public int getSize() {
    	return size;
    }
    
    /**
     * 
     * @return	Returns the name that caused the error to happen
     */
    public String getName() {
    	return name;
    }
    
}

/**
 * A class of files involving a name, size, writable state, maximum size limit, minimum size limit, 
 * creation time and modification time.
 * 
 * @invar 	the size of a file must always be a valid size
 * 			| isValidSize(size)
 * 
 * @author robin, jean-louis, edward
 * @version 1.0
 */
public class File {
	private String name;
	private int size;
	private boolean writable;
	private static int numOfDefaultFiles = 1;
	private static int sizeMaxLimit = Integer.MAX_VALUE;
	private static int sizeMinLimit = 0;
	private final Date creationTime;
	private Date modificationTime;
	
	/**
	 * @return	Returns current date
	 */
	@Raw
	private static Date getCurrentTime () {
		return new Date();
	}
	
	/**
	 * @param	name
	 * 			The name of the new file
	 * @param	size
	 * 			The size of the new file
	 * @param	writable
	 * 			The rights on the new file; writable or not.
	 * 
	 * @pre		The given size must be a valid size
	 * 			| isValidSize(size)
	 * 
	 * @post	Creates a file with the given name, size and writable state. 
	 * @post	If the name is not valid the default name "File_x" is used, 
	 * 			where x is a unique integer starting from 1.
	 * 			Creation time is instantiated as the local time.
	 * 
	 */
	public File (String name, int size, boolean writable) {
		if (isValidName(name)) {
			this.name = name;
		} else {
			this.name = "File_" + String.valueOf(numOfDefaultFiles);
			numOfDefaultFiles ++;
		}
		
		this.size = size;
		this.writable = writable;
		this.creationTime = getCurrentTime();
	}
	
	/**
	 * @param	name
	 * 			The name of the new file
	 * 
	 * @effect	Creates a file with the given name.
	 * 			Default value 0 for size is used.	
	 * @effect	If the name is not valid the default name "File_x" is used, 
	 * 			where x is a unique integer starting from 1.
	 * 
	 */
	public File (String name) {
		this(name, 0, true);
	}
	
	/**
	 * @param	name
	 * 			The name to be checked
	 * @return	Returns true if name is valid, false if not
	 */
	@Raw
	public static boolean isValidName(String name) {
		return name != "" && name.matches("[a-zA-Z0-9._-]*");
	}
	
	/**
	 * @return	Returns the name of the file
	 */
	@Basic@Raw
	public String getName() {
		return this.name;
	}
	
	/** Changes the file name
	 * @param	name
	 * @post	changes the name to the new defined name when the file is writable
	 * 			| isWritable()
	 * @throws	UnauthorizedException
	 * 			You are not authorized to change the name of this file
	 * 			| isWritable() == false
	 */
	@Raw
	public void setName(String name) throws UnauthorizedException {
		if (isWritable()) {
			if (isValidName(name)) {
				this.name = name;
			} else {
				this.name = "File_" + String.valueOf(numOfDefaultFiles);
				numOfDefaultFiles ++;
			}
			
			this.modificationTime = getCurrentTime();
		} else {
			throw new UnauthorizedException(this, name, "You are not authorized to change the name of this file!");
		}
	}
	
	/**
	 * @return	Returns the size of the file
	 */
	@Basic@Raw
	public int getSize() {
		return this.size;
	}
	
	/**
	 * @return	Returns true if the file is writable and false when read-only
	 */
	@Basic@Raw
	public boolean isWritable() {
		return this.writable;
	}
	
	/**
	 * @param	writable
	 * 			the new writable value
	 * @post	the rights are updated to the new writable state
	 */
	@Raw
	public void setWritable(boolean writable) {
		this.writable = writable;
	}
	
	@Raw
	public boolean isValidSize (int size) {
		if (size >= getMinSizeLimit() && size <= getMaxSizeLimit()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return	Returns the last modification time of this file
	 */
	@Basic@Raw
	public Date getModificationTime() {
		return this.modificationTime;
	}
	
	/**
	 * @return	Returns the creation time of this file
	 */
	@Basic@Immutable@Raw
	public Date getCreationTime() {
		return this.creationTime;
	}
	
	/**
	 * @return	Returns the maximum size limit
	 */
	@Basic@Raw
	public static int getMaxSizeLimit() {
		return sizeMaxLimit;
	}
	
	/**
	 * @param	limit
	 * 			The new size limit
	 * @post	The maximum size limit changes for all files to the new limit
	 */
	@Raw
	public static void setMaxSizeLimit(int limit) {
		sizeMaxLimit = limit;
	}
	
	/**
	 * @return	Returns the minimum size limit
	 */
	@Basic@Raw
	public static int getMinSizeLimit() {
		return sizeMinLimit;
	}
	
	/**
	 * @param	limit
	 * 			The new size limit
	 * @post	The minimum size limit changes for all files to the new limit
	 */
	@Raw
	public static void setMinSizeLimit(int limit) {
		sizeMinLimit = limit;
	}
	
	/**
	 * @param	size
	 * 			The size to increase (in bytes)
	 * @pre		The size must be a valid size
	 * 			| size >= 0 && getSize() < getMaxSizeLimit() - size
	 * @post	The new size of this file is enlarged with the parameter size
	 * 			| this.size = getSize() + size
	 * @throws	UnauthorizedException(this,size,message)
	 * 			You are not authorized to change the size of this file
	 * 			| isWritable() == false
	 */
	public void enlarge(int size) {
		if (isWritable()) {
			this.size += size;
			this.modificationTime = getCurrentTime();
		} else {
			throw new UnauthorizedException(this, size,"You are not authorized to change the size of this file!");
		}
		
	}
	
	/**
	 * @param	size
	 * 			The size to increase (in bytes)
	 * @pre		The size must be a valid size
	 * 			| getSize() >= getMinSizeLimit() + size && size >= 0
	 * @post	The new size of this file is shortened with the parameter size
	 * 			| this.size = getSize() - size
	 * @throws	UnauthorizedException(this,size,message)
	 * 			You are not authorized to change the size of this file
	 * 			| isWritable() == false
	 */
	
	public void shorten(int size) {
		if (isWritable()) {
			this.size -= size;
			this.modificationTime = getCurrentTime();
		} else {
			throw new UnauthorizedException(this, size,"You are not authorized to change the size of this file!");
		}
	}
	
	/**
	 * @return	Returns difference in seconds between last modification time and creation time
	 * 			Returns 0 when file hasn't been modified
	 */
	@Raw
	public double getUsePeriod () {
		double usePeriod = 0;
		if (modificationTime != null) {
			usePeriod = modificationTime.getTime() - creationTime.getTime();
		}
		return usePeriod/1000;
	}
	
	/**
	 * @return	Returns true when use periods overlap
	 * @return	When this File is null, other file is null or modification times 
	 * 			don't exist the standard value false is returned
	 */
	@Raw
	public boolean hasOverlappingUsePeriod (@Raw File other) {
		if (this == null || this.modificationTime == null || other == null || other.modificationTime == null) {
			return false;
		}
		
		if (this.getCreationTime().getTime() > other.getModificationTime().getTime() 
				|| this.getModificationTime().getTime() < other.getCreationTime().getTime()) {
			return false;
		} else {
			return true;
		}
	}
}
