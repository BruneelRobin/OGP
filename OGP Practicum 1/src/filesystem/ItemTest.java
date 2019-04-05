package filesystem;

import static org.junit.jupiter.api.Assertions.*;


/**
 * A class collecting tests for the class of items.
 * 
 * @author Robin Bruneel, Jean-Louis Carron en Edward Wiels
 * @version 1.0
 *
 */

import org.junit.jupiter.api.Test;

public class ItemTest {
	
	private static Item normalItem;
	
	/**
	 * 
	 */
	
	@Before 
	public void setUpMutableFixture() {
		
		normalItem = new Item(dir, "normalItem", true);
		

	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	
	@BeforeClass
	public static void setUpImmutableFixture() throws InterruptedException {
		
	}
	
	/**
	 * Tests the effect of making an item a root.
	 */
	@test
	public void makeRoot() {
		normalItem.makeRoot();
		assertequals(null, normalItem.getRoot());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	




}
