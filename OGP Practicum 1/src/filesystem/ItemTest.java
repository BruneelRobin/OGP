package filesystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */

public class ItemTest {
	
	private static Item normalItem;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		normalItem = new Item("normalItem", true);
	}
	
	/**
	 * Tests the effect of making an item a root.
	 */
	@Test
	public void makeRoot() {
		normalItem.makeRoot();
		assertEquals(null, normalItem.getRoot());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	




}
