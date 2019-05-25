package qahramon;

import be.kuleuven.cs.som.annotate.*;

/**
 * An interface for containers involving a total weight and total value.
 * 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public interface Container {
	
	/**
	 * Return the total weight of this container.
	 * 
	 * @return Return the total weight of this container.
	 */
	public float getTotalWeight();
	
	/**
	 * Return the total value of this container.
	 * 
	 * @return	Return the total value of this container in ducates.
	 */
	public int getTotalValue();
}
