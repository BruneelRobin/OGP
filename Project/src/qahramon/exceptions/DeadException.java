package qahramon.exceptions;



/**
 * A class for signaling illegal attempts of a character to invoke a method, due to it being dead.
 * 
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 *
 */
public class DeadException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5764443045080909436L;
	
	/**
	 * Variable referencing the character to which a method was denied.
	 */
	private final Character character;
	
	
	/**
	 * Initialize this new DeadException involving the given character
	 * @param character
	 * 		  The character for the new DeadException.
	 * @post The character involved in this DeadException is set to the given character
	 * 		 | new.getCharacter() == character
	 */
	@Raw
	public DeadException(Character character) {
		this.character = character;
		}
	
	
	/**
	 * Return the character involved in this DeadException.
	 * @return Return the character involved in this DeadException
	 */
	@Raw @Basic
	public Character getCharacter() {
		return this.character;
	}
	
	
	
	

}
