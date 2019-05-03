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

class LinkTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	
	Directory dir;
	File file;
	Link linkToDir;
	Link linkToFile;


	@BeforeEach
	void setUp() throws Exception {
	
		dir = new Directory("Dir", true);
		file = new File(dir, "file", null);
		linkToDir = new Link(dir, "linkToDir", dir);
		linkToFile = new Link(dir, "linkToFile", file);
		
	}
		
	
	@Test
	void testcanHaveAsName_legalCaseDirectoryLink() {
		assertEquals(linkToDir.canHaveAsName("adF7_-"), true);
	}
	
	@Test
	void testcanHaveAsName_illegalCaseDirectoryLink() {
		assertEquals(linkToDir.canHaveAsName("adF7_.-"), false);
	}
	
	@Test
	void testcanHaveAsName_legalCaseFileLink() {
		assertEquals(linkToFile.canHaveAsName("adF7_.-"), true);
	}
	
	
	@Test
	void testcanHaveAsName_illegalCaseFileLink() {
		assertEquals(linkToFile.canHaveAsName("adF.@7_-"), false);
	}
		
		
		

//	}
	
	@Test
	void Test_getReference () { 
		Directory dir = new Directory("d", true); 
		Directory dirTerminated = new Directory("d2", true); 
		 
		Link l = new Link (dir, "link1", dir); 
		Link lTerminated = new Link(dir, "link2", dirTerminated); 
		 
		dirTerminated.terminate(); 
		 
		assertEquals(l.getReference(), dir); 
		assertThrows(IllegalStateException.class, () -> lTerminated.getReference()); 
	}
	
	@Test
	void Test_isValidReference () { 
		Directory dir = new Directory("d", true); 
		Directory dirTerminated = new Directory("d2", true); 
		 
		dirTerminated.terminate(); 
//		 
//		assertEquals(Link.isValidReference(dirTerminated), false); 
//		assertEquals(Link.isValidReference(dir), true);
	}

}

