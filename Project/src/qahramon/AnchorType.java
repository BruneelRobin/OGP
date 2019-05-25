package qahramon;

import be.kuleuven.cs.som.annotate.Value;

/**
 * 	An enumeration of anchor types.
 *  In its current definition, the class only distinguishes between
 *  right hand, left hand, belt, back and body anchors. With each anchor , a name and id is associated.
 *    	
 *    
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version	1.0 - 2019
 */
@Value
public enum AnchorType {
	
	RIGHT_HAND("Right hand", 0, false),
	LEFT_HAND("Left hand", 1, false),
	BELT("Belt", 2, true),
	BACK("Back", 3, false),
	BODY("Body", 4, false);
	
	/**
	 * Variable referencing the name of the anchor.
	 */
	private final String name;
	
	/**
	 * Variable referencing the id of the anchor.
	 */
	private final int anchorId;
	
	/**
	 * Variable referencing whether this anchor holds a purse.
	 */
	private final boolean holdsPurse;
	
	/**
	 * Create an anchorType with the given name, annchorId and holdsPurse.
	 * 
	 * @param 	name
	 * 			the name of this anchorType			
	 * @param	anchorId
	 * 			the id of this anchorType
	 * @param 	holdsPurse
	 * 			boolean indicating whether this anchorType holds a purse
	 * @post	The name of this anchorType is set to the given name.
	 * 			| new.getName() == name
	 * @post	The anchorId of this anchorType is set to the given anchorId.
	 * 			| new.getAnchorId() == anchorId
	 * @post	The boolean indicating whether this anchorType holds a purse is set to the given boolean.
	 * 			| new.holdsPurse() == holdsPurse
	 */
	 private AnchorType(String name, int anchorId, boolean holdsPurse) {
		this.name = name;
		this.anchorId = anchorId;
		this.holdsPurse = holdsPurse;
	}
	

	/**
	 * Return the anchor's name.
	 * 
	 * @return Return the anchor's name.
	 */
 	public String toString() {
 		return this.name;
	}
	
	/**
	 * Return the anchorId of this anchorType.
	 * 
	 * @return Return the anchorId of this anchorType.
	 */
	 public int getAnchorId() {
		 return this.anchorId;
	 }

	/**
	 * Check whether this anchorType holds a purse.
	 * 
	 * @return	Check whether this anchorType holds a purse. 
	 * 
	 */
	 public boolean holdsPurse() {
		 return this.holdsPurse;
	 }
	 
	/**
	 * Return the anchorType associated with this id.
	 * 
	 * @param 	anchorId
	 * 		The id of the type
	 * @return	Return the AnchorType associated with the given id.
	 * @return	Return null when the given anchorId exists.
	 */
	 public static AnchorType getTypeFromId (int anchorId) {
		 for (AnchorType value : AnchorType.values()) {
			 if (value.getAnchorId() == anchorId) {
				 return value;
			 }
		 }
		 return null;
	 }
	 

}
