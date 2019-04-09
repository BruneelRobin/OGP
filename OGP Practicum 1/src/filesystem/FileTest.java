package filesystem;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.*;

/**
 * A JUnit test class for testing the public methods of the File Class  
 * @author Tommy Messelis, Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 2.0
 *
 */
public class FileTest {

	File fileDirStringIntBooleanString;
	File fileStringIntBooleanString;
	File fileDirStringString;
	File fileStringString;
	Date timeBeforeConstruction, timeAfterConstruction;
	
	File fileNotWritable;
	Date timeBeforeConstructionNotWritable, timeAfterConstructionNotWritable;
	
	Directory parentDir;
	
	@Before
	public void setUpFixture(){
		timeBeforeConstruction = new Date();
		
		parentDir = new Directory("parentDir");
		
		fileDirStringIntBooleanString = new File(parentDir,"bestand1",100, true,"txt");
		fileDirStringString = new File(parentDir,"bestand2","txt");
		fileStringIntBooleanString = new File("bestand3",100, true,"txt");
		fileStringString = new File("bestand4","txt");
		timeAfterConstruction = new Date();

		timeBeforeConstructionNotWritable = new Date();
		fileNotWritable = new File("bestand5",100,false,"txt");
		timeAfterConstructionNotWritable = new Date();
	}

	@Test
	public void testFileDirStringIntBooleanString_LegalCase() {
		assertEquals("bestand1",fileDirStringIntBooleanString.getName());
		assertEquals(100,fileDirStringIntBooleanString.getSize());
		assertEquals("txt",fileDirStringIntBooleanString.getType());
		assertEquals(parentDir,fileDirStringIntBooleanString.getDirectory());
		assertTrue(fileDirStringIntBooleanString.isWritable());
		assertNull(fileDirStringIntBooleanString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringIntBooleanString.getCreationTime()));
		assertFalse(fileDirStringIntBooleanString.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileDirStringIntBooleanString_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileDirStringIntBooleanString = new File(parentDir,"$illegalName$",File.getMaximumSize(),false,"txt");
		timeAfterConstruction = new Date();
		assertTrue(File.isValidName(fileDirStringIntBooleanString.getName()));
		assertEquals(File.getMaximumSize(),fileDirStringIntBooleanString.getSize());
		assertEquals(parentDir,fileDirStringIntBooleanString.getDirectory());
		assertFalse(fileDirStringIntBooleanString.isWritable());
		assertNull(fileDirStringIntBooleanString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringIntBooleanString.getCreationTime()));
		assertFalse(fileDirStringIntBooleanString.getCreationTime().after(timeAfterConstruction));
		
		assertThrows(TypeNotAllowedException.class,() -> new File(parentDir, "illegalType", 0, false, "illegalType"));
	}

	@Test
	public void testFileDirStringString_LegalCase() {
		assertEquals("bestand2",fileDirStringString.getName());
		assertEquals(0,fileDirStringString.getSize());
		assertEquals("txt",fileDirStringString.getType());
		assertEquals(parentDir, fileDirStringString.getDirectory());
		assertTrue(fileDirStringString.isWritable());
		assertNull(fileDirStringString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringString.getCreationTime()));
		assertFalse(fileDirStringString.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileDirStringString_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileDirStringString = new File(parentDir, "$illegalName$", "txt");
		timeAfterConstruction = new Date();
		assertTrue(File.isValidName(fileDirStringString.getName()));
		assertEquals(0,fileDirStringString.getSize());
		assertEquals(parentDir, fileDirStringString.getDirectory());
		assertTrue(fileDirStringString.isWritable());
		assertNull(fileDirStringString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringString.getCreationTime()));
		assertFalse(fileDirStringString.getCreationTime().after(timeAfterConstruction));
		
		assertThrows(TypeNotAllowedException.class,() -> new File(parentDir, "illegalType", "illegalType"));
	}
	
	// Same but for a rootFile
	
	@Test
	public void testFileStringIntBooleanString_LegalCase() {
		assertEquals("bestand3",fileStringIntBooleanString.getName());
		assertEquals(100,fileStringIntBooleanString.getSize());
		assertEquals("txt",fileStringIntBooleanString.getType());
		assertNull(fileStringIntBooleanString.getDirectory());
		assertTrue(fileStringIntBooleanString.isWritable());
		assertNull(fileStringIntBooleanString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringIntBooleanString.getCreationTime()));
		assertFalse(fileStringIntBooleanString.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileStringIntBooleanString_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileStringIntBooleanString = new File("$illegalName$",File.getMaximumSize(),false,"txt");
		timeAfterConstruction = new Date();
		assertTrue(File.isValidName(fileStringIntBooleanString.getName()));
		assertEquals(File.getMaximumSize(),fileStringIntBooleanString.getSize());
		assertNull(fileStringIntBooleanString.getDirectory());
		assertFalse(fileStringIntBooleanString.isWritable());
		assertNull(fileStringIntBooleanString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringIntBooleanString.getCreationTime()));
		assertFalse(fileStringIntBooleanString.getCreationTime().after(timeAfterConstruction));
		
		assertThrows(TypeNotAllowedException.class,() -> new File("illegalType",0, false, "illegalType"));
	}

	@Test
	public void testFileStringString_LegalCase() {
		assertEquals("bestand4",fileStringString.getName());
		assertEquals(0,fileStringString.getSize());
		assertEquals("txt",fileStringString.getType());
		assertNull(fileStringString.getDirectory());
		assertTrue(fileStringString.isWritable());
		assertNull(fileStringString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringString.getCreationTime()));
		assertFalse(fileStringString.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileStringString_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileStringString = new File("$illegalName$", "txt");
		timeAfterConstruction = new Date();
		assertTrue(File.isValidName(fileStringString.getName()));
		assertEquals(0,fileStringString.getSize());
		assertNull(fileStringString.getDirectory());
		assertTrue(fileStringString.isWritable());
		assertNull(fileStringString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringString.getCreationTime()));
		assertFalse(fileStringString.getCreationTime().after(timeAfterConstruction));
		assertThrows(TypeNotAllowedException.class,() -> new File("illegalType", "illegalType"));
	}
	

	/*@Test
	public void testIsValidName_LegalCase() {
		assertTrue(File.isValidName("abcDEF123-_."));
	}*/

	/*@Test
	public void testIsValidName_IllegalCase() {
		assertFalse(File.isValidName(null));
		assertFalse(File.isValidName(""));
		assertFalse(File.isValidName("%illegalSymbol"));
		
	}*/

	/*@Test
	public void testChangeName_LegalCase() {
		Date timeBeforeSetName = new Date();
		fileStringString.changeName("NewLegalName");
		Date timeAfterSetName = new Date();
		
		assertEquals("NewLegalName",fileString.getName());
		assertNotNull(fileString.getModificationTime());
		assertFalse(fileString.getModificationTime().before(timeBeforeSetName));
		assertFalse(timeAfterSetName.before(fileString.getModificationTime()));
	}*/
	
	/*@Test (expected = FileNotWritableException.class)
	public void testChangeName_FileNotWritable() {
		fileNotWritable.changeName("NewLegalName");
	}*/
	
	/*@Test
	public void testChangeName_IllegalName() {
		fileString.changeName("$IllegalName$");
		assertEquals("bestand",fileStringString.getName());
		assertNull(fileStringString.getModificationTime());
	}*/

	@Test
	public void testIsValidSize_LegalCase() {
		assertTrue(File.isValidSize(0));
		assertTrue(File.isValidSize(File.getMaximumSize()/2));
		assertTrue(File.isValidSize(File.getMaximumSize()));
	}
	
	@Test
	public void testIsValidSize_IllegalCase() {
		assertFalse(File.isValidSize(-1));
		if (File.getMaximumSize() < Integer.MAX_VALUE) {
			assertFalse(File.isValidSize(File.getMaximumSize()+1));
		}
	}

	@Test
	public void testEnlarge_LegalCase() {
		File file = new File("bestand",File.getMaximumSize()-1,true, "txt");
		Date timeBeforeEnlarge = new Date();
		file.enlarge(1);
		Date timeAfterEnlarge = new Date();		
		assertEquals(file.getSize(),File.getMaximumSize());
		assertNotNull(file.getModificationTime());
		assertFalse(file.getModificationTime().before(timeBeforeEnlarge));
		assertFalse(timeAfterEnlarge.before(file.getModificationTime()));  
	}
	
	@Test
	public void testEnlarge_FileNotWritable() {
		assertThrows(NotWritableException.class, ()->{fileNotWritable.enlarge(1);});
	}
	
	@Test
	public void testShorten_LegalCase() {
		fileStringIntBooleanString.shorten(1);
		Date timeAfterShorten = new Date();		
		assertEquals(fileStringIntBooleanString.getSize(),99);
		assertNotNull(fileStringIntBooleanString.getModificationTime());
		assertFalse(fileStringIntBooleanString.getModificationTime().before(timeAfterConstruction));
		assertFalse(timeAfterShorten.before(fileStringIntBooleanString.getModificationTime()));
	}
	
	@Test
	public void testShorten_FileNotWritable() {
		assertThrows(NotWritableException.class, ()->fileNotWritable.shorten(1));
	}
	
	@Test
	public void testIsValidType_LegalCase() {
		assertTrue(File.isValidType("txt"));
		assertTrue(File.isValidType("pdf"));
		assertTrue(File.isValidType("java"));
	}
	
	@Test
	public void testIsValidType_IllegalCase() {
		assertFalse(File.isValidType("illegal"));
	}

	/*@Test
	public void testIsValidCreationTime_LegalCase() {
		Date now = new Date();
		assertTrue(File.isValidCreationTime(now));
	}*/
	
	/*@Test
	public void testIsValidCreationTime_IllegalCase() {
		assertFalse(File.isValidCreationTime(null));
		Date inFuture = new Date(System.currentTimeMillis() + 1000*60*60);
		assertFalse(File.isValidCreationTime(inFuture));		
	}*/
	
	/*@Test
	public void testcanHaveAsModificationTime_LegalCase() {
		assertTrue(fileString.canHaveAsModificationTime(null));
		assertTrue(fileString.canHaveAsModificationTime(new Date()));
	}*/
	
	/*@Test
	public void testcanHaveAsModificationTime_IllegalCase() {
		assertFalse(fileString.canHaveAsModificationTime(new Date(timeAfterConstruction.getTime() - 1000*60*60)));
		assertFalse(fileString.canHaveAsModificationTime(new Date(System.currentTimeMillis() + 1000*60*60)));
	}*/

	/*@Test
	public void testHasOverlappingUsePeriod_UnmodifiedFiles() {
		// one = implicit argument ; other = explicit argument
		File one = new File("one");
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		File other = new File("other");
		
		//1 Test unmodified case
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//2 Test one unmodified case
		other.enlarge(File.getMaximumSize());
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//3 Test other unmodified case
		//so re-initialise the other file
		other = new File("other");
		one.enlarge(File.getMaximumSize());
		assertFalse(one.hasOverlappingUsePeriod(other));
		
	}*/
	
	/*@Test
	public void testHasOverlappingUsePeriod_ModifiedNoOverlap() {
		// one = implicit argument ; other = explicit argument
		File one, other;
		one = new File("one");
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File("other");
		
		//1 Test one created and modified before other created and modified case
		one.enlarge(File.getMaximumSize());
        sleep();
        //re-initialise the other
        other = new File("other");
        other.enlarge(File.getMaximumSize());
	    assertFalse(one.hasOverlappingUsePeriod(other));
	    
	    //2 Test other created and modified before one created and modified
		other.enlarge(File.getMaximumSize());
        sleep();
        one = new File("one");
        one.enlarge(File.getMaximumSize());
        assertFalse(one.hasOverlappingUsePeriod(other));
	
	}*/
	
	/*@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_A() {
		// one = implicit argument ; other = explicit argument
		//A Test one created before other created before one modified before other modified
	    File one, other;
		one = new File("one");
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File("other");
	
		one.enlarge(File.getMaximumSize());
        sleep();
        other.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_B() {
		// one = implicit argument ; other = explicit argument
		//B Test one created before other created before other modified before one modified
       	File one, other;
		one = new File("one");
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File("other");
	
		other.enlarge(File.getMaximumSize());
        sleep();
        one.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}*/
	
	/*@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_C() {
		// one = implicit argument ; other = explicit argument
		//C Test other created before one created before other modified before one modified
        File one, other;
		other = new File("other");
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new File("one");
		
		other.enlarge(File.getMaximumSize());
        sleep();
        one.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}*/
	
	/*@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_D() {
		// one = implicit argument ; other = explicit argument
		//D Test other created before one created before one modified before other modified
		File one, other;
		other = new File("one");
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new File("other");
	
		one.enlarge(File.getMaximumSize());
        sleep();
        other.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}*/

	/*@Test
	public void testSetWritable() {
		fileString.setWritable(false);
		fileNotWritable.setWritable(true);
		assertFalse(fileString.isWritable());
		assertTrue(fileNotWritable.isWritable());
	}*/
	
	private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
