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
	
	
	@Test (expected = UnauthorizedException.class)
	public void setName_LegalName_UnWritableFile() {
		unwritableFile.setName("validName");
	
	}

	

	@Test (expected = UnauthorizedException.class)
	public void setName_IllegalName_UnWritableFile() {
		unwritableFile.setName("invalidName@");
	
	}

	


	@Test
	public void enlarge_LegalSize_WritableFile() {
		int size = writableFile.getSize();
		writableFile.enlarge(10);
		assertEquals(size + 10, writableFile.getSize());
		
	}
	
	
@Test (expected = UnauthorizedException.class)
	public void enlarge_LegalSize_UnWritableFile() {
		unwritableFile.enlarge(10);
	
	}

	

@Test
public void shorten_LegalSize_WritableFile() {
	int size = writableFile.getSize();
	writableFile.shorten(5);
	assertEquals(size - 5, writableFile.getSize());
	
}


@Test (expected = UnauthorizedException.class)
public void shorten_LegalSize_UnWritableFile() {
	unwritableFile.shorten(5);

}


@Test
public void isWritable_SetFalse() {
	writableFile.setWritable(false);
	assertEquals(false,writableFile.isWritable());
	
	
}


@Test
public void isWritable_SetTrue() {
	unwritableFile.setWritable(true);
	assertEquals(true,unwritableFile.isWritable());
	
	
}

	
	
	
	
}






