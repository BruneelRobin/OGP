package filesystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * A test class of Link.
 *
 * @author 	Robin Bruneel, Edward Wiels, Jean-Louis Carron
 * @version	2.3 - 2019  
 */

class LinkTest {
	
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
