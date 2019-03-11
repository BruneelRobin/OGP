import java.util.Date;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

class UnauthorizedException extends RuntimeException
{
	public UnauthorizedException() {
		
	}
      // Constructor that accepts a message
      public UnauthorizedException(String message)
      {
         super(message);
      }
}

/*
 * 
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
	@Raw@Basic
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
	 * @effect	Creates a file with the given name, size and writable. 
	 * 			If the name is not valid the default name "File_x" is used, 
	 * 			where x is a unique integer starting from 1.
	 * 			Creationtime is instantiated as the local time.
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
	 * 			
	 * 			If the name is not valid the default name "File_x" is used, 
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
		return name.matches("[a-zA-Z0-9._-]*");
	}
	
	/**
	 * @return	Returns the name of the file
	 */
	@Basic
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
	public void setName(String name) throws UnauthorizedException {
		if (isWritable()) {
			this.name = name;
			this.modificationTime = getCurrentTime();
		} else {
			throw new UnauthorizedException("You are not authorized to change the name of this file!");
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
	@Basic
	public boolean isWritable() {
		return this.writable;
	}
	
	/**
	 * @param	writable
	 * 			the new writable value
	 * @post	the rights are updated to the new writable state
	 */
	public void setWritable(boolean writable) {
		this.writable = writable;
	}
	
	/**
	 * @return	Returns the last modification time of this file
	 */
	@Basic
	public Date getModificationTime() {
		return this.modificationTime;
	}
	
	/**
	 * @return	Returns the creation time of this file
	 */
	@Basic
	public Date getCreationTime() {
		return this.creationTime;
	}
	
	/**
	 * @return	Returns the maximum size limit
	 */
	@Basic
	public static int getMaxSizeLimit() {
		return sizeMaxLimit;
	}
	
	/**
	 * @param	limit
	 * 			The new size limit
	 * @post	The maximum size limit changes for all files to the new limit
	 */
	public static void setMaxSizeLimit(int limit) {
		sizeMaxLimit = limit;
	}
	
	/**
	 * @return	Returns the minimum size limit
	 */
	@Basic
	public static int getMinSizeLimit() {
		return sizeMinLimit;
	}
	
	/**
	 * @param	limit
	 * 			The new size limit
	 * @post	The minimum size limit changes for all files to the new limit
	 */
	public static void setMinSizeLimit(int limit) {
		sizeMinLimit = limit;
	}
	
	/**
	 * @param	size
	 * 			The size to increase (in bytes)
	 * @pre		The size must be a valid size
	 * 			| size >= 0 && getSize() < getMaxSizeLimit() - size
	 * @throws	UnauthorizedException
	 * 			You are not authorized to change the size of this file
	 * 			| isWritable() == false
	 */
	public void enlarge(int size) {
		if (isWritable()) {
			this.size += size;
			this.modificationTime = getCurrentTime();
		} else {
			throw new UnauthorizedException("You are not authorized to change the size of this file!");
		}
		
	}
	
	/**
	 * @param	size
	 * 			The size to increase (in bytes)
	 * @pre		The size must be a valid size
	 * 			| getSize() >= getMinSizeLimit() + size && size >= 0
	 * @throws	UnauthorizedException
	 * 			You are not authorized to change the size of this file
	 * 			| isWritable() == false
	 */
	
	public void shorten(int size) {
		if (isWritable()) {
			this.size -= size;
			this.modificationTime = getCurrentTime();
		} else {
			throw new UnauthorizedException("You are not authorized to change the size of this file!");
		}
	}
	
	/**
	 * @return	Returns difference in seconds between last modification time and creation time
	 * 			Returns 0 when file hasn't been modified
	 */
	@Basic
	public double getUsePeriod () {
		double usePeriod = 0;
		if (modificationTime != null) {
			usePeriod = modificationTime.getTime() - creationTime.getTime();
		}
		return usePeriod/1000;
	}
	
	/**
	 * @return	Returns true when use periods overlap
	 * 			When this File is null, other file is null or modification times 
	 * 			don't exist the standard value false is returned
	 */
	public boolean hasOverlappingUsePeriod (File other) {
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
