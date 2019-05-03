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
		
		
		

	}



