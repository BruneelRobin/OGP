/**
 * 
 */
package testsuite;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.*;

import filesystem.*;
import filesystem.exception.*;

/**
 * Testcase for File class			
 * 
 * @author 	Tommy Messelis
 * @version	2.0 - 2015
 */
public class FileTest {

	static File existingFileInDir, file;
	static Directory terminatedDirectory, dir;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		terminatedDirectory = new Directory("terminatedRootFolder");
		terminatedDirectory.terminate();
	}

	@Before
	public void setUp() {
		dir = new Directory("rootFolder");
		existingFileInDir = new File(dir,"existingFile",Type.TEXT);
	}

	
	
	@Test
	public void testFileDirStringTypeIntBoolean_LegalCase() {
		Date timeBeforeConstruction = new Date();
		sleep();
		file = new File(dir,"name",Type.JAVA,100,false);
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(file.getCreationTime());
		assertTrue(timeBeforeConstruction.before(file.getCreationTime()));
		assertTrue(timeAfterConstruction.after(file.getCreationTime()));
		
		assertNull(file.getModificationTime());
		
		assertEquals("name",file.getName());
		assertFalse(file.isWritable());
		assertEquals(dir,file.getParentDirectory());
		assertTrue(dir.hasAsItem(file));
		
		assertEquals(100,file.getSize());
		assertEquals(Type.JAVA,file.getType());
		
	}
	
	@Test
	public void testFileDirStringTypeIntBoolean_InvalidName() {
		Date timeBeforeConstruction = new Date();
		sleep();
		file = new File(dir,"A^very$wrong*name",Type.JAVA,100,true);
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(file.getCreationTime());
		assertTrue(timeBeforeConstruction.before(file.getCreationTime()));
		assertTrue(timeAfterConstruction.after(file.getCreationTime()));
		
		assertNull(file.getModificationTime());
		
		assertNotEquals("A^very$wrong*name",file.getName());
		assertTrue(file.canHaveAsName(file.getName()));
		assertTrue(file.isWritable());
		assertEquals(dir,file.getParentDirectory());
		assertTrue(dir.hasAsItem(file));
		
		assertEquals(100,file.getSize());
		assertEquals(Type.JAVA,file.getType());
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testFileDirStringTypeIntBoolean_NullParent() {
		file = new File(null,"name",Type.JAVA,100,true);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testFileDirStringTypeIntBoolean_TerminatedParent() {
		file = new File(terminatedDirectory,"name",Type.JAVA,100,true);
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testFileDirStringTypeIntBoolean_NotWritableParent() {
		dir.changeWritabilityStatus(false);
		file = new File(dir,"name",Type.JAVA,100,true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testFileDirStringTypeIntBoolean_ParentAlreadyContainsName() {
		file = new File(dir,"existingFile",Type.JAVA,100,true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testFileDirStringTypeIntBoolean_ParentAlreadyContainsDefaultName() {
		new File(dir,"A^very$wrong*name",Type.JAVA,100,true);
		file = new File(dir,"Another^very$wrong*name",Type.JAVA,100,true);
	}
	
	
	@Test
	public void testFileDirStringType_LegalCase() {
		Date timeBeforeConstruction = new Date();
		sleep();
		file = new File(dir,"name",Type.JAVA);
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(file.getCreationTime());
		assertTrue(timeBeforeConstruction.before(file.getCreationTime()));
		assertTrue(timeAfterConstruction.after(file.getCreationTime()));
		
		assertNull(file.getModificationTime());
		
		assertEquals("name",file.getName());
		assertTrue(file.isWritable());
		assertEquals(dir,file.getParentDirectory());
		assertTrue(dir.hasAsItem(file));
		
		assertEquals(0,file.getSize());
		assertEquals(Type.JAVA,file.getType());
		
	}
	
	@Test
	public void testFileDirStringType_IllegalCase() {
		//illegal cases tested through the extended constructor
	}
	
	
	@Test
	public void testCanBeTerminated_LegalCase() {
		assertTrue(existingFileInDir.canBeTerminated());
	}
	
	@Test
	public void testCanBeTerminated_AlreadyTerminated() {
		existingFileInDir.terminate();
		assertFalse(existingFileInDir.canBeTerminated());
	}

	@Test 
	public void testCanBeTerminated_FileNotWritable() {
		existingFileInDir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.canBeTerminated());
	}
	
	@Test 
	public void testCanBeTerminated_ParentNotWritable() {
		dir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.canBeTerminated());
	}
	
	
	@Test
	public void testTerminate_LegalCase() {
		existingFileInDir.terminate();
	}
	
	@Test (expected = IllegalStateException.class)
	public void testTerminate_IllegalCase() {
		existingFileInDir.changeWritabilityStatus(false);
		existingFileInDir.terminate();
	}
	
	
	@Test
	public void testCanBeRecursivelyDeleted() {
		//tested via canBeTerminated()
	}
	
	
	@Test
	public void testDeleteRecursive() {
		//tested via terminate()
	}
	
	
	@Test
	public void testCanAcceptAsNewWritability() {
		assertTrue(existingFileInDir.canAcceptAsNewWritability(true));
		assertTrue(existingFileInDir.canAcceptAsNewWritability(false));
	}
	
	
	@Test
	public void testChangeWritabilityStatus() {
		//true > true
		existingFileInDir.changeWritabilityStatus(true);
		assertTrue(existingFileInDir.isWritable());
		//true > false
		existingFileInDir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.isWritable());
		//false > false
		existingFileInDir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.isWritable());
		//false > true
		existingFileInDir.changeWritabilityStatus(true);
		assertTrue(existingFileInDir.isWritable());		
	}
	
	
	@Test
	public void testCanHaveAsName_LegalCase() {
		assertTrue(existingFileInDir.canHaveAsName("abcDEF123-_."));
	}
	
	@Test
	public void testCanHaveAsName_IllegalCase() {
		assertFalse(existingFileInDir.canHaveAsName(null));
		assertFalse(existingFileInDir.canHaveAsName("somethingWithSt*rs"));
		assertFalse(existingFileInDir.canHaveAsName(""));
	}
	
	@Test
	public void testCanAcceptAsNewName_LegalCase() {
		assertTrue(existingFileInDir.canAcceptAsNewName("abcDEF123-_."));
	}
	
	@Test
	public void testCanAcceptAsNewName_FileNotWritable() {
		existingFileInDir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.canAcceptAsNewName("aValidName"));		
	}
	
	@Test
	public void testCanAcceptAsNewName_FileTerminated() {
		existingFileInDir.terminate();
		assertFalse(existingFileInDir.canAcceptAsNewName("aValidName"));	
	}
	
	@Test
	public void testCanAcceptAsNewName_InvalidName() {
		assertFalse(existingFileInDir.canAcceptAsNewName("anInvalidN*me"));	
	}
	
	@Test
	public void testCanAcceptAsNewName_SameName() {
		assertFalse(existingFileInDir.canAcceptAsNewName("existingFile"));	
	}
	
	@Test
	public void testCanAcceptAsNewName_ParentAlreadyHasName() {
		file = new File(dir,"anotherFile",Type.JAVA,100,true);
		assertFalse(existingFileInDir.canAcceptAsNewName("anotherFile"));	
	}
	
	
	@Test
	public void testChangeName_LegalCase() {
		Date timeBeforeSetName = new Date();
		existingFileInDir.changeName("NewLegalName");
		Date timeAfterSetName = new Date();
		
		assertEquals("NewLegalName",existingFileInDir.getName());
		assertNotNull(existingFileInDir.getModificationTime());
		assertFalse(existingFileInDir.getModificationTime().before(timeBeforeSetName));
		assertFalse(timeAfterSetName.before(existingFileInDir.getModificationTime()));
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testChangeName_FileNotWritable() {
		existingFileInDir.changeWritabilityStatus(false);
		existingFileInDir.changeName("NewLegalName");
	}
	
	@Test (expected = IllegalStateException.class)
	public void testChangeName_AlreadyTerminated() {
		existingFileInDir.terminate();
		existingFileInDir.changeName("NewLegalName");
	}
	
	
	@Test
	public void testIsOrderedAfterString() {
		assertTrue(existingFileInDir.isOrderedAfter("a"));
		assertFalse(existingFileInDir.isOrderedAfter("z"));
		assertFalse(existingFileInDir.isOrderedAfter("existingFile"));
	}
	
	@Test
	public void testIsOrderedBeforeString() {
		assertTrue(existingFileInDir.isOrderedBefore("z"));
		assertFalse(existingFileInDir.isOrderedBefore("a"));
		assertFalse(existingFileInDir.isOrderedBefore("existingFile"));
	}
	
	@Test
	public void testIsOrderedAfterDiskItem() {
		assertFalse(existingFileInDir.isOrderedAfter((DiskItem)null));
		// rest is tested through testIsOrderedAfterString(.)
	}
	
	@Test
	public void testIsOrderedBeforeDiskItem() {
		assertFalse(existingFileInDir.isOrderedBefore((DiskItem)null));
		// rest is tested through testIsOrderedBeforeString(.)
	}
	
	
	@Test
	public void testGetAbsolutePath() {
		assertEquals(existingFileInDir.getAbsolutePath(),"/rootFolder/existingFile.txt");
	}
	
	@Test
	public void testToString() {
		assertEquals(existingFileInDir.toString(),"existingFile.txt");
	}
	
	
	@Test
	public void testIsValidCreationTime_LegalCase() {
		Date now = new Date();
		assertTrue(File.isValidCreationTime(now));
	}
	
	@Test
	public void testIsValidCreationTime_IllegalCase() {
		assertFalse(File.isValidCreationTime(null));
		Date inFuture = new Date(System.currentTimeMillis() + 1000*60*60);
		assertFalse(File.isValidCreationTime(inFuture));		
	}
	
	
	@Test
	public void testcanHaveAsModificationTime_LegalCase() {
		assertTrue(existingFileInDir.canHaveAsModificationTime(null));
		assertTrue(existingFileInDir.canHaveAsModificationTime(new Date()));
	}
	
	@Test
	public void testcanHaveAsModificationTime_IllegalCase() {
		//we assume that this testsuite runs over a timespan of less than one day!
		Date yesterday = new Date(System.currentTimeMillis() - 1000*60*60*24);
		assertFalse(existingFileInDir.canHaveAsModificationTime(yesterday));
		Date tomorrow = new Date(System.currentTimeMillis() + 1000*60*60*24);
		assertFalse(existingFileInDir.canHaveAsModificationTime(tomorrow));
	}
	
	
	@Test
	public void testHasOverlappingUsePeriod_UnmodifiedFiles() {
		// one = implicit argument ; other = explicit argument
		File one = new File(dir,"one",Type.TEXT);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		File other = new File(dir,"other",Type.TEXT);
		
		//1 Test unmodified case
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//2 Test one unmodified case
		other.enlarge(File.getMaximumSize());
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//3 Test other unmodified case
		//so re-initialise the other file
		other.terminate();
		other = new File(dir,"other",Type.TEXT);
		one.enlarge(File.getMaximumSize());
		assertFalse(one.hasOverlappingUsePeriod(other));
		
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedNoOverlap() {
		// one = implicit argument ; other = explicit argument
		File one, other;
		one = new File(dir,"one",Type.TEXT);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File(dir,"other",Type.TEXT);
		
		//1 Test one created and modified before other created and modified case
		one.enlarge(File.getMaximumSize());
        sleep();
        //re-initialise the other
        other.terminate();
        other = new File(dir,"other",Type.TEXT);
        other.enlarge(File.getMaximumSize());
	    assertFalse(one.hasOverlappingUsePeriod(other));
	    
	    //2 Test other created and modified before one created and modified
		other.enlarge(File.getMaximumSize());
        sleep();
        one.terminate();
        one = new File(dir,"one",Type.TEXT);
        one.enlarge(File.getMaximumSize());
        assertFalse(one.hasOverlappingUsePeriod(other));
	
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_A() {
		// one = implicit argument ; other = explicit argument
		//A Test one created before other created before one modified before other modified
	    File one, other;
		one = new File(dir,"one",Type.TEXT);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File(dir,"other",Type.TEXT);
	
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
		one = new File(dir,"one",Type.TEXT);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File(dir,"other",Type.TEXT);
	
		other.enlarge(File.getMaximumSize());
        sleep();
        one.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_C() {
		// one = implicit argument ; other = explicit argument
		//C Test other created before one created before other modified before one modified
        File one, other;
		other = new File(dir,"other",Type.TEXT);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new File(dir,"one",Type.TEXT);
		
		other.enlarge(File.getMaximumSize());
        sleep();
        one.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_D() {
		// one = implicit argument ; other = explicit argument
		//D Test other created before one created before one modified before other modified
		File one, other;
		other = new File(dir,"one",Type.TEXT);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new File(dir,"other",Type.TEXT);
	
		one.enlarge(File.getMaximumSize());
        sleep();
        other.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	
	@Test
	public void testIsRoot() {
		assertFalse(existingFileInDir.isRoot());
	}
	
	@Test
	public void testGetRoot() {
		assertEquals(dir,existingFileInDir.getRoot());
	}
	
	@Test
	public void testCanHaveAsParentDirectory_LegalCase(){
		//its current parent is legal
		assertTrue(existingFileInDir.canHaveAsParentDirectory(existingFileInDir.getParentDirectory()));
		//another parent which does not contain its name is legal
		Directory otherDir = new Directory("otherDir");
		assertTrue(existingFileInDir.canHaveAsParentDirectory(otherDir));
		//null is valid for a terminated file
		existingFileInDir.terminate();
		assertTrue(existingFileInDir.canHaveAsParentDirectory(null));
	}
	
	@Test
	public void testCanHaveAsParentDirectory_NullParent(){
		//invalid for non-terminated files
		assertFalse(existingFileInDir.canHaveAsParentDirectory(null));
		//valid for terminated files
		existingFileInDir.terminate();
		assertTrue(existingFileInDir.canHaveAsParentDirectory(null));		
	}
	
	@Test
	public void testCanHaveAsParentDirectory_TerminatedParent(){
		Directory otherDir = new Directory("otherDir");
		otherDir.terminate();
		assertFalse(existingFileInDir.canHaveAsParentDirectory(otherDir));		
	}
	
	@Test
	public void testCanHaveAsParentDirectory_NewParentNotWritable(){
		Directory otherDir = new Directory("otherDir");
		otherDir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.canHaveAsParentDirectory(otherDir));		
	}
	
	@Test
	public void testCanHaveAsParentDirectory_NewParentAlreadyHasName(){
		Directory otherDir = new Directory("otherDir");
		new File(otherDir,"existingFile",Type.TEXT);
		assertFalse(existingFileInDir.canHaveAsParentDirectory(otherDir));		
	}
	
	@Test
	public void testCanHaveAsParentDirectory_CurrentParentNotWritable(){
		Directory otherDir = new Directory("otherDir");
		dir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.canHaveAsParentDirectory(otherDir));		
	}
	
	@Test
	public void testCanBeMoved() {
		assertTrue(existingFileInDir.canBeMoved());
		existingFileInDir.changeWritabilityStatus(false);
		assertFalse(existingFileInDir.canBeMoved());
	}
	
	@Test
	public void testMove_LegalCase() {
		Directory otherDir = new Directory("otherDir");
		File lastFileDir = new File(dir,"zzz",Type.JAVA);
		File lastFileOtherDir = new File(otherDir,"zzz",Type.JAVA);
		int indexOfLastFileDir = dir.getIndexOf(lastFileDir);
		int nbItemsDir = dir.getNbItems();
		int indexOfLastFileOtherDir = otherDir.getIndexOf(lastFileOtherDir);
		int nbItemsOtherDir = otherDir.getNbItems();
		
		Date timeBeforeMove = new Date();
		sleep();
		existingFileInDir.move(otherDir);
		sleep();
		Date timeAfterMove = new Date();
		
		assertFalse(dir.hasAsItem(existingFileInDir));
		assertEquals(dir.getIndexOf(lastFileDir),indexOfLastFileDir-1);
		assertEquals(dir.getNbItems(),nbItemsDir-1);
		
		assertTrue(timeBeforeMove.before(dir.getModificationTime()));
		assertTrue(timeBeforeMove.before(otherDir.getModificationTime()));
		assertTrue(timeAfterMove.after(dir.getModificationTime()));
		assertTrue(timeAfterMove.after(otherDir.getModificationTime()));
		
		assertTrue(otherDir.hasAsItem(existingFileInDir));
		assertEquals(otherDir.getIndexOf(lastFileOtherDir),indexOfLastFileOtherDir+1);
		assertEquals(otherDir.getNbItems(),nbItemsOtherDir+1);
		
		assertTrue(timeBeforeMove.before(existingFileInDir.getModificationTime()));
		assertTrue(timeAfterMove.after(existingFileInDir.getModificationTime()));
		
		assertEquals(otherDir,existingFileInDir.getParentDirectory());
	}
	
	@Test (expected = IllegalStateException.class)
	public void testMove_CannotBeMoved() {
		existingFileInDir.changeWritabilityStatus(false);
		Directory otherDir = new Directory("otherDir");
		existingFileInDir.move(otherDir);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMove_TargetNull() {
		existingFileInDir.move(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMove_TargetIsParent() {
		existingFileInDir.move(dir);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMove_TargetCantHaveFile() {
		Directory otherDir = new Directory("otherDir");
		new File(otherDir,"existingFile",Type.JAVA);
		existingFileInDir.move(otherDir);
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testMove_TargetNotWritable() {
		Directory otherDir = new Directory("otherDir");
		otherDir.changeWritabilityStatus(false);
		existingFileInDir.move(otherDir);
	}
	
	
	@Test
	public void testGetTotalDiskUsage() {
		assertEquals(existingFileInDir.getTotalDiskUsage(),existingFileInDir.getSize());
	}
	
	
	@Test
	public void testIsValidType() {
		assertFalse(File.isValidType(null));
		assertTrue(File.isValidType(Type.JAVA));
		assertTrue(File.isValidType(Type.TEXT));
		assertTrue(File.isValidType(Type.PDF));
	}
	
	
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
		File file = new File(dir,"bestand",Type.JAVA,File.getMaximumSize()-1,true);
		
		Date timeBeforeEnlarge = new Date();
		sleep();
		file.enlarge(1);
		sleep();
		Date timeAfterEnlarge = new Date();		
		
		assertEquals(file.getSize(),File.getMaximumSize());
		assertNotNull(file.getModificationTime());
		
		assertTrue(file.getModificationTime().after(timeBeforeEnlarge));
		assertTrue(timeAfterEnlarge.after(file.getModificationTime()));  
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testEnlarge_FileNotWritable() {
		existingFileInDir.changeWritabilityStatus(false);
		existingFileInDir.enlarge(1);
	}
	
	@Test
	public void testShorten_LegalCase() {
		File file = new File(dir,"bestand",Type.JAVA,100,true);
		
		Date timeBeforeEnlarge = new Date();
		sleep();
		file.shorten(1);
		sleep();
		Date timeAfterEnlarge = new Date();		
		
		assertEquals(file.getSize(),99);
		assertNotNull(file.getModificationTime());
		
		assertTrue(file.getModificationTime().after(timeBeforeEnlarge));
		assertTrue(timeAfterEnlarge.after(file.getModificationTime()));
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testShorten_FileNotWritable() {
		existingFileInDir.changeWritabilityStatus(false);
		existingFileInDir.shorten(1);
	}
	
	
	
	
	private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
