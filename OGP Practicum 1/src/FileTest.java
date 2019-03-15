import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 * 
 * @author Robin Bruneel, Jean-Louis Carron en Edward Wiels
 * @version 1.0
 *
 */


public class FileTest {
	
	private static File writableFile;
	
	private static File unwritableFile;
	
	
	
	
	@Before 
	public void setUpMutableFixture() {
		
		writableFile = new File("writableFile", 10, true);
		unwritableFile = new File("unwritableFile", 10, false);
		
		

	}
	
	@Test
	public void setName_LegalName_WritableFile() {
		writableFile.setName("validName");
		assertEquals("validName", writableFile.getName());
		
	}
	
	
	@Test
	public void setName_IllegalName_WritableFile() {
		writableFile.setName("invalidName@");
		assertEquals("File_1", writableFile.getName());
	
	}
	
	
	@Test
	public void setName_LegalName_UnWritableFile() {
		unwritableFile.setName("validName");
		assertEquals("validName", unwritableFile.getName());
	
	}

	

	@Test
	public void setName_IllegalName_UnWritableFile() {
		String name = unwritableFile.getName();
		unwritableFile.setName("invalidName@");
		assertEquals(name, unwritableFile.getName());
	
	}

	
	

	
	
	
	
}






