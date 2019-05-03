package filesystem;

import static org.junit.jupiter.api.Assertions.*;

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
	
	
	
	
	
	
	
	
	
	

}




