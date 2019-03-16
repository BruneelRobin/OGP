import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import java.lang.Thread;

/**
 * A class collecting tests for the class of files.
 * 
 * @author Robin Bruneel, Jean-Louis Carron en Edward Wiels
 * @version 1.0
 *
 */


public class FileTest {
	
	private static File writableFile;
	
	private static File unwritableFile;
	
	private static File firstFile;
	private static File secondFile;
	private static File thirdFile;
	private static File fourthFile;
	
	/**
	 * Sets up a mutable test fixture.
	 * 
	 * @post The variable writableFile references a new writable file of size 10.
	 *
	 * @post The variable unwritableFile references a new unwritable file of size 10.
	 */
	
	@Before 
	public void setUpMutableFixture() {
		
		writableFile = new File("writableFile", 10, true);
		unwritableFile = new File("unwritableFile", 10, false);
		
		

	}
	
	@BeforeClass
	public static void setUpImmutableFixture() throws InterruptedException {
		firstFile = new File ("firstFile");
		Thread.sleep(50);
		secondFile = new File("secondFile");
		Thread.sleep(50);
		thirdFile = new File("thirdFile");
		Thread.sleep(50);
		secondFile.enlarge(10);
		Thread.sleep(50);
		thirdFile.enlarge(10);
		Thread.sleep(50);
		firstFile.enlarge(10);
		Thread.sleep(50);
		fourthFile = new File("fourthFile");
		Thread.sleep(50);
		fourthFile.enlarge(10);
	}
	
	
	
	
	/**
	 * Tests the effect of setting a legal name of a writable file.
	 */
	
	@Test
	public void setName_LegalName_WritableFile() {
		writableFile.setName("validName");
		assertEquals("validName", writableFile.getName());
		
	}
	
	/**
	 * Tests the effect of setting an illegal name of a writable file.
	 */
	
	@Test
	public void setName_IllegalName_WritableFile() {
		writableFile.setName("invalidName@");
		assertEquals(true, writableFile.getName().matches("File_\\d+"));
	
	}
	
	/**
	 * Tests the effect of setting a legal name of an unwritable file.
	 */
	
	@Test (expected = UnauthorizedException.class)
	public void setName_LegalName_UnWritableFile() {
		unwritableFile.setName("validName");
	
	}

	/**
	 * Tests the effect of setting an illegal name of an unwritable file.
	 */

	@Test (expected = UnauthorizedException.class)
	public void setName_IllegalName_UnWritableFile() {
		unwritableFile.setName("invalidName@");
	
	}

	/**
	 * Tests the effect of enlarging the size of a writable file.
	 */

	@Test
	public void enlarge_LegalSize_WritableFile() {
		int size = writableFile.getSize();
		writableFile.enlarge(10);
		assertEquals(size + 10, writableFile.getSize());
		
	}
	
	/**
	 * Tests the effect of enlarging the size of an unwritable file.
	 */
	
	@Test (expected = UnauthorizedException.class)
	public void enlarge_LegalSize_UnWritableFile() {
		unwritableFile.enlarge(10);
	
	}

	/**
	 * Tests the effect of shortening the size of a writable file.
	 */

	@Test
	public void shorten_LegalSize_WritableFile() {
	int size = writableFile.getSize();
	writableFile.shorten(5);
	assertEquals(size - 5, writableFile.getSize());
	
	}

	/**
	 * Tests the effect of shortening the size of an unwritable file.
	 */
	
	@Test (expected = UnauthorizedException.class)
	public void shorten_LegalSize_UnWritableFile() {
	unwritableFile.shorten(5);

	}

	/**
	 * Tests the effect of setting a file to unwritable.
	 */
	
	@Test
	public void isWritable_SetFalse() {
	writableFile.setWritable(false);
	assertEquals(false,writableFile.isWritable());
	
	}
	
	/**
	 * Tests the effect of setting a file to writable.
	 */

	@Test
	public void isWritable_SetTrue() {
	unwritableFile.setWritable(true);
	assertEquals(true,unwritableFile.isWritable());
	
	
	}

	@Test
	public void File_ValidName() {
		File testFile = new File("validName",10,true);
		assertEquals(testFile.getName(),"validName");
		assertEquals(10,testFile.getSize());
		assertEquals(true,testFile.isWritable());
				
			
	}
	
	@Test
	public void File_InvalidName() {
		File testFile = new File("invalidName@",10,true);
		assertEquals(testFile.getName(),"File_1");
		assertEquals(10,testFile.getSize());
		assertEquals(true,testFile.isWritable());
				
			
	}
	
	@Test
	public void hasOverlappingUsePeriod_x1_y1_x2_y2() {
		assertEquals(true,secondFile.hasOverlappingUsePeriod(thirdFile));
		assertEquals(true,thirdFile.hasOverlappingUsePeriod(secondFile));
	}
	
	@Test
	public void hasOverlappingUsePeriod_x1_y1_y2_x2() {
		assertEquals(true,secondFile.hasOverlappingUsePeriod(firstFile));
		assertEquals(true,firstFile.hasOverlappingUsePeriod(secondFile));
	}
	
	@Test
	public void hasOverlappingUsePeriod_x1_x2_y1_y2() {
		assertEquals(false,firstFile.hasOverlappingUsePeriod(fourthFile));
		assertEquals(false,fourthFile.hasOverlappingUsePeriod(firstFile));
	}


	
	
	
}






