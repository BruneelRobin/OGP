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
	
	


	@BeforeEach
	void setUp() throws Exception {
	
		dir = new Directory("Dir", true);
		file = new File(dir, "file", null);

		
	}
		
	
	@Test
	void testcanHaveAsName_legalCase() {
		assertEquals(file.canHaveAsName("adF7_.-"), true);
	}
	
	
	@Test
	void testcanHaveAsName_illegalCase() {
		assertEquals(file.canHaveAsName("adF.@7_-"), false);
	}
		
		
		

	}