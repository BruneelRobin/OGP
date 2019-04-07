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

class DirectoryTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	Directory parentDir;
	Directory childDir;
	File childFile;
	Directory otherDir;

	@BeforeEach
	void setUp() throws Exception {
		parentDir = new Directory("Parent");
		childDir = new Directory(parentDir, "ChildDir");
		childFile = new File(parentDir, "ChildFile", "txt");
		
		otherDir = new Directory("Other");
	}
	
	//Constructors are inherited from Item and hence not tested here
	
	//Test for the delete function
	@Test
	void testDelete_LegalCase() {
		childDir.delete();
		assertEquals(childDir.getDirectory(), null);
		assertEquals(parentDir.exists(childDir.getName()), false);
	}
	
	@Test
	void testDelete_IllegalCase() {
		assertThrows(DirectoryNotEmptyException.class, () -> {parentDir.delete();});
		childDir.setWritable(false);
		assertThrows(NotWritableException.class, () -> {childDir.delete();});
	}
	
	@Test
	void testgetIndexOf_LegalCase() {
		assertEquals(parentDir.getIndexOf(childDir), 1);
	}
	
	@Test
	void testgetIndexOf_illegalCase() {
		assertEquals(parentDir.getIndexOf(otherDir), 0);
	}
	
	@Test
	void testgetItem_LegalCase() {
		assertEquals(parentDir.getItem(childDir.getName()), childDir);
	}
	
	@Test
	void testgetItem_illegalCase() {
		assertEquals(parentDir.getItem(otherDir.getName()), null);
	}
	
	@Test
	void testexists_LegalCase() {
		assertEquals(parentDir.exists("childfile"), true);
	}
	
	@Test
	void testexists_illegalCase() {
		assertEquals(parentDir.exists("otherdir"), false);
	}
	
	@Test
	void testisDirectOrIndirectSubdirectoryOf_LegalCase() {
		Directory childchildDir = new Directory(childDir, "childchild");
		assertEquals(childchildDir.isDirectOrIndirectSubdirectoryOf(parentDir), true);
		assertEquals(childDir.isDirectOrIndirectSubdirectoryOf(parentDir), true);
	}
	
	@Test
	void testisDirectOrIndirectSubdirectoryOf_illegalCase() {
		assertEquals(parentDir.isDirectOrIndirectSubdirectoryOf(childDir), false);
		assertEquals(otherDir.isDirectOrIndirectSubdirectoryOf(parentDir), false);
	}
	
	@Test
	public void testSorting () {
		
		for (int i = 0; i<100;i++) {
			try {
				Item item = new Item(childDir, String.valueOf((int)(Math.random() * 100 + 1)), true);
				childDir.addChild(item);
			} catch (AlreadyExistsException e) {
				
			}
			
		}
		
		for (int i = 1; i<childDir.getNbItems();i++) {
			//lexicologically sorted
			assertTrue(childDir.getItemAt(i+1).compareName(childDir.getItemAt(i).getName()) == 1);
		}
		
		
		
	}

}
