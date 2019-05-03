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
	
	Directory dir;
	
	Directory parentDir;
	Directory childDir;
	Directory otherDir;

	@BeforeEach
	void setUp() throws Exception {
	
		dir = new Directory("Dir", false);
	
		parentDir = new Directory("Parent");
		childDir = new Directory(parentDir, "ChildDir");	
		otherDir = new Directory("Other");
	}
	
	
	@Test
	void Test_DirectoryIterator () {
		Directory childDir2 = new Directory(parentDir, "ChildDir2");
		
		DirectoryIterator iterator = parentDir.getIterator();
		assertEquals(iterator.getCurrentItem(), childDir);
		assertEquals(iterator.getNbRemainingItems(), 2);
		iterator.advance();
		assertEquals(iterator.getCurrentItem(), childDir2);
		iterator.advance();
		
		assertEquals(iterator.getNbRemainingItems(), 0);
		iterator.reset();
		assertEquals(iterator.getNbRemainingItems(), 2);
	}
	
	@Test
	void Test_getTotalDiskUsage () {
		File childFile = new File(parentDir, "f", Type.JAVA, 199, true);
		File childFile2 = new File(childDir, "f", Type.JAVA, 99, true);
		
		assertEquals(parentDir.getTotalDiskUsage(), childFile.getSize() + childFile2.getSize());
	}
	
	
	
	
	
	
	

}




