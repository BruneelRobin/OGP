package MindCraft;

import be.kuleuven.cs.som.annotate.Value;

/**
 * 	An enumeration of anchor types.
 *  In its current definition, the class only distinguishes between
 *  right hand, left hand, belt, back and body anchors. With each anchor , a name and id is associated.
 *    
 * @invar	
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
	
	private final String name;
	private final int anchorId;
	private final boolean holdsPurse;
	
	
	 private AnchorType(String name, int anchorId, boolean holdsPurse) {
		this.name = name;
		this.anchorId = anchorId;
		this.holdsPurse = holdsPurse;
	}
	

	/**
	 * Returns the anchor's name
	 * @return Returns the anchor's name
	 */
 	public String toString() {
 		return this.name;
	
	}
	
	/**
	 * Returns the character's strength
	 * @return Returns the character's strength
	 */
	 public int getAnchorId() {
		 return this.anchorId;
	 	}

		
	/**
	 * Returns the character's strength
	 * @return Returns the character's strength
	 */
	 public boolean holdsPurse() {
		 return this.holdsPurse;
	 }
	 
	 /**
	  * Return the anchorType associated with this id
	  * @param 	anchorId
	  * 		The id of the type
	  * @return	Return the AnchorType associated with the given id
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
