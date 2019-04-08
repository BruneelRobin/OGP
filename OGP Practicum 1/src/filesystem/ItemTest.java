package filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */

public class ItemTest {
	
	private Item normalItem;
	private Item normalItemRoot;
	private Item nonWritableItem;
	private Directory parentDir;
	private Directory childDir;
	private Item otherItem;
	
	Date timeBeforeConstruction, timeAfterConstruction;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		timeBeforeConstruction = new Date();
		parentDir = new Directory("parentDir");
		childDir = new Directory(parentDir, "childDir");
		normalItem = new Item(parentDir, "normalItem", true);
		normalItemRoot = new Item("normalItem", true);
		nonWritableItem = new Item(parentDir, "nonWritableItem", false);
		otherItem = new Item("otherItem", true);
		timeAfterConstruction = new Date();
	}
	
	@Test
	void testItemDirStringBool_LegalCase() {
		Date tBefore = new Date();
		Item newItem = new Item(parentDir, "newItem", true);
		Date tAfter = new Date();
		assertEquals(newItem.getDirectory(), parentDir);
		assertEquals(newItem.getName(), "newItem");
		assertEquals(newItem.isWritable(), true);
		assertNull(newItem.getModificationTime());
		assertFalse(tBefore.after(newItem.getCreationTime()));
		assertFalse(newItem.getCreationTime().after(tAfter));
	}
	
	@Test
	void testItemDirStringBool_illegalCase() {
		Date tBefore = new Date();
		Item newItemWrongName = new Item(parentDir, "$newItemWrongName$", true);
		Date tAfter = new Date();
		assertEquals(newItemWrongName.getName(), "new_item");
		assertEquals(newItemWrongName.getDirectory(), parentDir);
		assertTrue(newItemWrongName.isWritable());
		assertNull(newItemWrongName.getModificationTime());
		assertFalse(tBefore.after(newItemWrongName.getCreationTime()));
		assertFalse(newItemWrongName.getCreationTime().after(tAfter));
	}
	
	// same as earlier but without parentDir
	@Test
	void testItemStringBool_LegalCase() {
		Date tBefore = new Date();
		Item newItem = new Item("newItem", true);
		Date tAfter = new Date();
		assertEquals(newItem.getDirectory(), null);
		assertEquals(newItem.getName(), "newItem");
		assertEquals(newItem.isWritable(), true);
		assertNull(newItem.getModificationTime());
		assertFalse(tBefore.after(newItem.getCreationTime()));
		assertFalse(newItem.getCreationTime().after(tAfter));
	}
	
	@Test
	void testItemStringBool_illegalCase() {
		Date tBefore = new Date();
		Item newItemWrongName = new Item("$newItemWrongName$", true);
		Date tAfter = new Date();
		assertEquals(newItemWrongName.getName(), "new_item");
		assertEquals(newItemWrongName.getDirectory(), null);
		assertEquals(newItemWrongName.isWritable(), true);
		assertNull(newItemWrongName.getModificationTime());
		assertFalse(tBefore.after(newItemWrongName.getCreationTime()));
		assertFalse(newItemWrongName.getCreationTime().after(tAfter));
	}
	
	@Test
	public void testIsValidName_LegalCase() {
		assertTrue(File.isValidName("abcDEF123-_."));
	}

	@Test
	public void testIsValidName_IllegalCase() {
		assertFalse(File.isValidName(null));
		assertFalse(File.isValidName(""));
		assertFalse(File.isValidName("%illegalSymbol"));
		
	}

	@Test
	public void testChangeName_LegalCase() {
		Date timeBeforeSetName = new Date();
		normalItem.changeName("NewLegalName");
		Date timeAfterSetName = new Date();
		
		assertEquals("NewLegalName",normalItem.getName());
		assertNotNull(normalItem.getModificationTime());
		assertFalse(normalItem.getModificationTime().before(timeBeforeSetName));
		assertFalse(timeAfterSetName.before(normalItem.getModificationTime()));
	}
	
	@Test
	public void testChangeName_NotWritable() {
		assertThrows(NotWritableException.class, () -> nonWritableItem.changeName("NewLegalName"));
		
	}
	
	@Test
	public void testChangeName_IllegalName() {
		String startName = normalItem.getName();
		normalItem.changeName("$IllegalName$");
		assertEquals(startName,normalItem.getName());
		assertNull(normalItem.getModificationTime());
		
		//only 1 same name allowed in dir
		assertThrows(AlreadyExistsException.class, () -> normalItem.changeName(nonWritableItem.getName()));
		assertEquals(startName,normalItem.getName());
		assertNull(normalItem.getModificationTime());
	}
	
	@Test
	public void testIsValidCreationTime_LegalCase() {
		Date now = new Date();
		assertTrue(Item.isValidCreationTime(now));
	}
	
	@Test
	public void testIsValidCreationTime_IllegalCase() {
		assertFalse(Item.isValidCreationTime(null));
		Date inFuture = new Date(System.currentTimeMillis() + 1000*60*60);
		assertFalse(Item.isValidCreationTime(inFuture));
	}
	
	@Test
	public void testcanHaveAsModificationTime_LegalCase() {
		assertTrue(normalItem.canHaveAsModificationTime(null));
		assertTrue(normalItem.canHaveAsModificationTime(new Date()));
	}
	
	@Test
	public void testcanHaveAsModificationTime_IllegalCase() {
		assertFalse(normalItem.canHaveAsModificationTime(new Date(timeAfterConstruction.getTime() - 1000*60*60)));
		assertFalse(normalItem.canHaveAsModificationTime(new Date(System.currentTimeMillis() + 1000*60*60)));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_UnmodifiedItems() {
		// one = implicit argument ; other = explicit argument
		Item one = new Item("one", true);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		Item other = new Item("other", true);
		
		//1 Test unmodified case
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//2 Test one unmodified case
		other.changeName("newName");
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//3 Test other unmodified case
		//so re-initialise the other file
		other = new Item("other", true);
		one.changeName("otherName");
		assertFalse(one.hasOverlappingUsePeriod(other));
		
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedNoOverlap() {
		// one = implicit argument ; other = explicit argument
		Item one, other;
		one = new Item("one", true);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new Item("other", true);
		
		//1 Test one created and modified before other created and modified case
		one.changeName("newName");
        sleep();
        //re-initialise the other
        other = new Item("other", true);
        other.changeName("otherName");
	    assertFalse(one.hasOverlappingUsePeriod(other));
	    
	    //2 Test other created and modified before one created and modified
		other.changeName("newOtherName");
        sleep();
        one = new Item("one", true);
        one.changeName("newNewName");
        assertFalse(one.hasOverlappingUsePeriod(other));
	
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_A() {
		// one = implicit argument ; other = explicit argument
		//A Test one created before other created before one modified before other modified
	    Item one, other;
		one = new Item("one", true);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new Item("other", true);
	
		one.changeName("newName");
        sleep();
        other.changeName("otherName");
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_B() {
		// one = implicit argument ; other = explicit argument
		//B Test one created before other created before other modified before one modified
       	Item one, other;
		one = new Item("one", true);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new Item("other", true);
	
		other.changeName("newName");
        sleep();
        one.changeName("otherName");
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_C() {
		// one = implicit argument ; other = explicit argument
		//C Test other created before one created before other modified before one modified
        Item one, other;
		other = new Item("other", true);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new Item("one", true);
		
		other.changeName("newName");
        sleep();
        one.changeName("otherName");
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_D() {
		// one = implicit argument ; other = explicit argument
		//D Test other created before one created before one modified before other modified
		Item one, other;
		other = new Item("one", true);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new Item("other", true);
	
		one.changeName("newName");
        sleep();
        other.changeName("otherName");
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testSetWritable() {
		normalItem.setWritable(false);
		nonWritableItem.setWritable(true);
		assertFalse(normalItem.isWritable());
		assertTrue(nonWritableItem.isWritable());
	}
	
	/**
	 * Tests the effect of making an item a root.
	 */
	@Test
	public void testMakeRoot() {
		normalItem.makeRoot();
		assertEquals(null, normalItem.getDirectory());
	}
	
	@Test
	void testDelete_LegalCase() {
		normalItem.delete();
		assertEquals(normalItem.getDirectory(), null);
		assertEquals(parentDir.exists(normalItem.getName()), false);
	}
	
	@Test
	void testDelete_IllegalCase() {
		assertThrows(NotWritableException.class, () -> {nonWritableItem.delete();});
	}
	
	
	@Test
	public void testMove_LegalCase() {
		normalItem.move(childDir);
		assertEquals(normalItem.getDirectory(), childDir);
		assertTrue(childDir.hasAsItem(normalItem));
		assertFalse(parentDir.hasAsItem(normalItem));
		
	}
	
	@Test
	public void testMove_IllegalCase() {
		assertThrows(AlreadyExistsException.class, () -> {normalItemRoot.move(parentDir);});
		assertThrows(IsOwnAncestorException.class, () -> {parentDir.move(childDir);});
	}
	
	
	@Test
	public void testGetRoot_rootItem() {
		assertEquals(parentDir.getRoot(), parentDir);
	}
	
	@Test
	public void testGetRoot_noRootItem() {
		assertEquals(normalItem.getRoot(), parentDir);
	}
	
	
	private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
