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
	
	File fileWritable;
	
	Link link;
	
	Directory parentDir;
	Directory childDir;
	Directory otherDir;

	@BeforeEach
	void setUp() throws Exception {
	
		dirUnwritable = new Directory("DirUnwr", false);
		dirWritable = new Directory("DirWr", true);
		fileWritable = new File(dirWritable,"fileWritable", null, 10, true);
		link = new Link(dirWritable, "link", dirUnwritable);
	
		parentDir = new Directory("Parent");
		childDir = new Directory(parentDir, "Child");	
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
	void testsetWritable_trueTotrue() {
		dirWritable.setWritable(true);
		assertTrue(dirWritable.isWritable());
		
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
	
	@Test
	void testDeleteRecursive_legalCase() {
		dirWritable.deleteRecursive();
		assertTrue(dirWritable.isTerminated());
		assertTrue(fileWritable.isTerminated());
		assertTrue(link.isTerminated());
	}
	
	
	@Test
	void testDeleteRecursive_illegalCase() {
		fileWritable.setWritable(false);
		assertThrows(IllegalStateException.class, () -> dirWritable.deleteRecursive()); 
		assertFalse(dirWritable.isTerminated());
		assertFalse(fileWritable.isTerminated());
		assertFalse(link.isTerminated());

	}
	
	@Test
	void testCanHaveAsParentDirectory_legalCase() {
		assertTrue(parentDir.canHaveAsParentDirectory(dirWritable));
		assertTrue(childDir.canHaveAsParentDirectory(dirWritable));
		assertTrue(dirWritable.canHaveAsParentDirectory(null));
		dirWritable.terminate();
		assertTrue(dirWritable.canHaveAsParentDirectory(null));
	}
	
	@Test
	void testCanHaveAsParentDirectory_illegalCase() {
		Directory childFromUnwritable = new Directory(dirWritable,"childFromUnwr");
		dirWritable.setWritable(false);
		assertFalse(parentDir.canHaveAsParentDirectory(dirWritable));
		assertFalse(childFromUnwritable.canHaveAsParentDirectory(dirWritable));
		assertFalse(parentDir.canHaveAsParentDirectory(childDir));
		//assertFalse(dirWritable.canHaveAsParentDirectory(null));
		//dirWritable.terminate();
		//assertFalse(parentDir.canHaveAsParentDirectory(dirWritable));
		//assertFalse(dirWritable.canHaveAsParentDirectory(parentDir));
	}
	

	@Test
	void testGetAbsolutePath() {
		assertEquals(parentDir.getAbsolutePath(), "/Parent");
		assertEquals(childDir.getAbsolutePath(), "/Parent/Child");
	}
}




