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


	@BeforeEach
	void setUp() throws Exception {
	
		dirUnwritable = new Directory("DirUnwr", false);

	}
	
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

}

