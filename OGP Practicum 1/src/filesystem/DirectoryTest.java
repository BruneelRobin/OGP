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
	
	Directory dirUnwritable;
	Directory dirWritable;
	
	Directory parentDir;
	Directory childDir;
	Directory otherDir;

	@BeforeEach
	void setUp() throws Exception {
	
		dirUnwritable = new Directory("DirUnwr", false);
		dirWritable = new Directory("DirWr", true);
	
		parentDir = new Directory("Parent");
		childDir = new Directory(parentDir, "ChildDir");	
		otherDir = new Directory("Other");
	}
	
	@Test
	void testsetWritable_falseTofalse() {
		dirUnwritable.setWritable(false);
		assertEquals(dirUnwritable.isWritable(), false);
		
	}
	
	@Test
	void testsetWritable_falseTotrue() {
		dirUnwritable.setWritable(true);
		assertEquals(dirUnwritable.isWritable(), false);
		
	}
	
	@Test
	void testsetWritable_trueTofalse() {
		dirWritable.setWritable(false);
		assertEquals(dirWritable.isWritable(), false);
		
	}
	
	@Test
	void testsetWritable_trueTorue() {
		dirWritable.setWritable(true);
		assertEquals(dirWritable.isWritable(), true);
		
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
	
	
	
	
	
	
	

}




