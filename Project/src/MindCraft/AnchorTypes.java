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
 public enum AnchorTypes {
	
	RIGHT_HAND("Right hand", 0, false),
	LEFT_HAND("Left hand", 1, false),
	BELT("Belt", 2, true),
	BACK("Back", 3, false),
	BODY("Body", 4, false);
	
	private final String name;
	private final int slotId;
	private final boolean holdsPurse;
	
	
	 private AnchorTypes(String name, int slotId, boolean holdsPurse) {
		this.name = name;
		this.slotId = slotId;
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
	 public int getSlotId() {
		 return this.slotId;
	 	}

		
	/**
	 * Returns the character's strength
	 * @return Returns the character's strength
	 */
	 public boolean holdsPurse() {
		 return this.holdsPurse;
	 	}
	 
	 
	
	
	
	
	
	

}