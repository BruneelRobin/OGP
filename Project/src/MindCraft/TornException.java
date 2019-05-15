package MindCraft;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal attempts to change a purse
 * due to it being torn.
 */
public class TornException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5764443045080909434L;

	/**
	 * Variable referencing the purse item to which change was denied.
	 */
	private final Purse purse;

	/**
	 * Initialize this new torn exception involving the
	 * given purse.
	 * 
	 * @param	purse
	 * 			The purse for the new torn exception.
	 * @post	The purse involved in the new torn exception
	 * 			is set to the given purse.
	 * 			| new.getPurse() == purse
	 */
	@Raw
	public TornException(Purse purse) {
		this.purse = purse;
	}
	
	/**
	 * Return the purse involved in this torn exception.
	 */
	@Raw @Basic
	public Purse getPurse() {
		return this.purse;
	}
	
}
