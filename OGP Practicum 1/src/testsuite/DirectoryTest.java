package testsuite;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.*;

import filesystem.*;
import filesystem.exception.DiskItemNotWritableException;


/**
 * Testcase for Directory class			
 * 
 * @author 	Tommy Messelis
 * @version	2.2 - 2016
 */
public class DirectoryTest {

	static File existingFileInRootDirectory, fileA, fileB, fileC;
	static Directory terminatedDirectory, existingDirectoryInRootDirectory, existingEmptyDirectoryInRootDirectory, rootDirectory, directory;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		terminatedDirectory = new Directory("terminatedRootFolder");
		terminatedDirectory.terminate();
	}

	@Before
	public void setUp() {
		rootDirectory = new Directory("rootFolder");
		existingFileInRootDirectory = new File(rootDirectory,"existingFile",Type.TEXT);
		existingDirectoryInRootDirectory = new Directory(rootDirectory,"existingDirectory");	
		fileA = new File(existingDirectoryInRootDirectory, "f_A",Type.PDF, 100, true);
		fileB = new File(existingDirectoryInRootDirectory, "f_B",Type.JAVA, 10, true);
		fileC = new File(existingDirectoryInRootDirectory, "f_C",Type.TEXT, 200, true);
		existingEmptyDirectoryInRootDirectory = new Directory(rootDirectory,"existingEmptyDirectory");
	}
	
	
	
	@Test
	public void testDirectoryStringBoolean_LegalCase() {
		Date timeBeforeConstruction = new Date();
		sleep();
		directory = new Directory("name",false);
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(directory.getCreationTime());
		assertTrue(timeBeforeConstruction.before(directory.getCreationTime()));
		assertTrue(timeAfterConstruction.after(directory.getCreationTime()));
		
		assertNull(directory.getModificationTime());
		
		assertTrue(directory.isRoot());
		assertEquals("name",directory.getName());
		assertFalse(directory.isWritable());
		
		assertEquals(directory.getNbItems(),0);
	}
	
	@Test
	public void testDirectoryStringBoolean_InvalidName() {
		Date timeBeforeConstruction = new Date();
		sleep();
		directory = new Directory("A^very$wrong*name",false);
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(directory.getCreationTime());
		assertTrue(timeBeforeConstruction.before(directory.getCreationTime()));
		assertTrue(timeAfterConstruction.after(directory.getCreationTime()));
		
		assertNull(directory.getModificationTime());
		
		assertTrue(directory.isRoot());
		assertNotEquals("A^very$wrong*name",directory.getName());
		assertTrue(directory.canHaveAsName(directory.getName()));
		assertFalse(directory.isWritable());
		
		assertEquals(directory.getNbItems(),0);
		
	}
	
	@Test
	public void testDirectoryString_LegalCase() {
		Date timeBeforeConstruction = new Date();
		sleep();
		directory = new Directory("name");
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(directory.getCreationTime());
		assertTrue(timeBeforeConstruction.before(directory.getCreationTime()));
		assertTrue(timeAfterConstruction.after(directory.getCreationTime()));
		
		assertNull(directory.getModificationTime());
		
		assertTrue(directory.isRoot());
		assertEquals("name",directory.getName());
		assertTrue(directory.isWritable());
		
		assertEquals(directory.getNbItems(),0);
	}
	
	@Test
	public void testDirectoryString_IllegalCase() {
		//tested through the extended constructor
	}
	
	@Test
	public void testDirectoryDirectoryStringBoolean_LegalCase() {
		Date timeBeforeConstruction = new Date();
		sleep();
		directory = new Directory(rootDirectory,"name",false);
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(directory.getCreationTime());
		assertTrue(timeBeforeConstruction.before(directory.getCreationTime()));
		assertTrue(timeAfterConstruction.after(directory.getCreationTime()));
		
		assertNull(directory.getModificationTime());
		
		assertEquals("name",directory.getName());
		assertFalse(directory.isWritable());
		assertEquals(rootDirectory,directory.getParentDirectory());
		assertTrue(rootDirectory.hasAsItem(directory));
		
		assertEquals(directory.getNbItems(),0);
	}
	
	@Test
	public void testDirectoryDirectoryStringBoolean_InvalidName() {
		Date timeBeforeConstruction = new Date();
		sleep();
		directory = new Directory(rootDirectory,"A^very$wrong*name",false);
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(directory.getCreationTime());
		assertTrue(timeBeforeConstruction.before(directory.getCreationTime()));
		assertTrue(timeAfterConstruction.after(directory.getCreationTime()));
		
		assertNull(directory.getModificationTime());
		
		assertNotEquals("A^very$wrong*name",directory.getName());
		assertTrue(directory.canHaveAsName(directory.getName()));
		assertFalse(directory.isWritable());
		assertEquals(rootDirectory,directory.getParentDirectory());
		assertTrue(rootDirectory.hasAsItem(directory));
		
		assertEquals(directory.getNbItems(),0);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDirectoryDirectoryStringBoolean_NullParent() {
		directory = new Directory(null,"name",true);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDirectoryDirectoryStringBoolean_TerminatedParent() {
		directory = new Directory(terminatedDirectory,"name",true);
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testDirectoryDirectoryStringBoolean_NotWritableParent() {
		rootDirectory.changeWritabilityStatus(false);
		directory = new Directory(rootDirectory,"name",true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDirectoryDirectoryStringBoolean_ParentAlreadyContainsName() {
		directory = new Directory(rootDirectory,"existingDirectory",true);
	}
	
	@SuppressWarnings("unused")
	@Test (expected = IllegalArgumentException.class)
	public void testDirectoryDirectoryStringBoolean_ParentAlreadyContainsDefaultName() {
		Directory directoryDefaultName = new Directory(rootDirectory,"A^very$wrong*name",true);
		directory = new Directory(rootDirectory,"Another^very$wrong*name",true);
	}
	
	@Test
	public void testDirectoryDirectoryString_LegalCase() {
		Date timeBeforeConstruction = new Date();
		sleep();
		directory = new Directory(rootDirectory,"name");
		sleep();
		Date timeAfterConstruction = new Date();
		
		assertNotNull(directory.getCreationTime());
		assertTrue(timeBeforeConstruction.before(directory.getCreationTime()));
		assertTrue(timeAfterConstruction.after(directory.getCreationTime()));
		
		assertNull(directory.getModificationTime());
		
		assertEquals("name",directory.getName());
		assertTrue(directory.isWritable());
		assertEquals(rootDirectory,directory.getParentDirectory());
		assertTrue(rootDirectory.hasAsItem(directory));
		
		assertEquals(directory.getNbItems(),0);
	}
	
	@Test
	public void testDirectoryDirectoryString_IllegalCase() {
		//Illegal cases tested through extended constructor
	}
	
	
	@Test
	public void testCanBeTerminated_LegalCase() {
		assertTrue(existingEmptyDirectoryInRootDirectory.canBeTerminated());
	}
	
	@Test
	public void testCanBeTerminated_AlreadyTerminated() {
		assertFalse(terminatedDirectory.canBeTerminated());
	}

	@Test 
	public void testCanBeTerminated_DirectoryNotWritable() {
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		assertFalse(existingDirectoryInRootDirectory.canBeTerminated());
	}
	
	@Test 
	public void testCanBeTerminated_ParentNotWritable() {
		rootDirectory.changeWritabilityStatus(false);
		assertFalse(existingDirectoryInRootDirectory.canBeTerminated());
	}
	
	
	@Test
	public void testTerminate_LegalCase() {
		existingEmptyDirectoryInRootDirectory.terminate();
	}
	
	@Test (expected = IllegalStateException.class)
	public void testTerminate_IllegalCase() {
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		existingDirectoryInRootDirectory.terminate();
	}
	
	
	@Test
	public void testCanBeRecursivelyDeleted_LegalCase() {
		new File(rootDirectory,"aaa",Type.JAVA); //index 1
		new File(rootDirectory,"bbb",Type.JAVA); //index 2
		new File(existingDirectoryInRootDirectory,"yyy",Type.JAVA); 
		new File(existingDirectoryInRootDirectory,"zzz",Type.JAVA); 
		
		//if it can be recursively deleted (which it should)
		assertTrue(rootDirectory.canBeRecursivelyDeleted());
		//then it should not be terminated already, and it should be a root, or its parent should be writable
		assertTrue(!rootDirectory.isTerminated() && (rootDirectory.isRoot() || rootDirectory.getParentDirectory().isWritable()));
		//and all its items should be recursively deletable
		for(int i=1; i<=rootDirectory.getNbItems(); i++){
			assertTrue(rootDirectory.getItemAt(i).canBeRecursivelyDeleted());
		}
		
		//now try with a directory which is not a root!
		assertTrue(existingDirectoryInRootDirectory.canBeRecursivelyDeleted());
		assertTrue(!existingDirectoryInRootDirectory.isTerminated() && (existingDirectoryInRootDirectory.isRoot() || existingDirectoryInRootDirectory.getParentDirectory().isWritable()));
		
	}
	
	@Test
	public void testCanBeRecursivelyDeleted_IllegalCase() {
		//a terminated dir
		assertFalse(terminatedDirectory.canBeRecursivelyDeleted());
		//a dir whose parent is not writable
		rootDirectory.changeWritabilityStatus(false);
		assertFalse(existingDirectoryInRootDirectory.canBeRecursivelyDeleted());
		//a non-writable dir
		assertFalse(rootDirectory.canBeRecursivelyDeleted());
		//a writable dir with a non-writable content
		Directory otherDir = new Directory("otherDir");
		new File(otherDir,"name",Type.TEXT,100,false);
		assertFalse(otherDir.canBeRecursivelyDeleted());
	}
	
	
	
	@Test
	public void testDeleteRecursive_LegalCase() {
		//recursively delete an empty dir
		existingEmptyDirectoryInRootDirectory.deleteRecursive();
		// it should be terminated
		assertTrue(existingEmptyDirectoryInRootDirectory.isTerminated());
		// it should be empty
		assertEquals(existingEmptyDirectoryInRootDirectory.getNbItems(), 0);
		//it should be root
		assertTrue(existingEmptyDirectoryInRootDirectory.isRoot());
		assertFalse(rootDirectory.hasAsItem(existingEmptyDirectoryInRootDirectory));
		
		
		//do it with a non-empty dir
		existingDirectoryInRootDirectory.deleteRecursive();
		assertTrue(existingDirectoryInRootDirectory.isTerminated());
		assertEquals(existingDirectoryInRootDirectory.getNbItems(), 0);
		assertTrue(existingDirectoryInRootDirectory.isRoot());
		assertFalse(rootDirectory.hasAsItem(existingDirectoryInRootDirectory));
		
		assertTrue(fileA.isTerminated());
		assertTrue(fileB.isTerminated());
		assertTrue(fileC.isTerminated());
		assertTrue(fileA.isRoot());
		assertTrue(fileB.isRoot());
		assertTrue(fileC.isRoot());
		assertFalse(existingDirectoryInRootDirectory.hasAsItem(fileA));
		assertFalse(existingDirectoryInRootDirectory.hasAsItem(fileB));
		assertFalse(existingDirectoryInRootDirectory.hasAsItem(fileC));	
		
		//there is a third legal case! an already terminated dir!
		//nothing should happen
		assertTrue(terminatedDirectory.isTerminated());
		// it should be empty
		assertEquals(terminatedDirectory.getNbItems(), 0);
		//it should be root
		assertTrue(terminatedDirectory.isRoot());
		assertFalse(rootDirectory.hasAsItem(terminatedDirectory));

		terminatedDirectory.deleteRecursive();
		
		// it should still be terminated
		assertTrue(terminatedDirectory.isTerminated());
		// it should be empty
		assertEquals(terminatedDirectory.getNbItems(), 0);
		//it should be root
		assertTrue(terminatedDirectory.isRoot());
		assertFalse(rootDirectory.hasAsItem(terminatedDirectory));	
	}
	
	@Test (expected = IllegalStateException.class)
	public void testDeleteRecursive_ParentNotWritable() {
		//make a directory which is not recursively deleted
		rootDirectory.changeWritabilityStatus(false);
		existingDirectoryInRootDirectory.deleteRecursive();		
	}
	
	@Test (expected = IllegalStateException.class)
	public void testDeleteRecursive_DirNotWritable() {
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		existingDirectoryInRootDirectory.deleteRecursive();		
	}
	
	@Test (expected = IllegalStateException.class)
	public void testDeleteRecursive_SubitemNotWritable() {
		fileA.changeWritabilityStatus(false);
		existingDirectoryInRootDirectory.deleteRecursive();		
	}
	
	
	@Test
	public void testCanAcceptAsNewWritability() {
		// different from files!
		assertTrue(existingDirectoryInRootDirectory.canAcceptAsNewWritability(true));
		assertTrue(existingDirectoryInRootDirectory.canAcceptAsNewWritability(false));
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		assertFalse(existingDirectoryInRootDirectory.canAcceptAsNewWritability(true));
		assertFalse(existingDirectoryInRootDirectory.canAcceptAsNewWritability(false));
	}
	
	
	@Test
	public void testChangeWritabilityStatus() {
		//true > true
		existingDirectoryInRootDirectory.changeWritabilityStatus(true);
		assertTrue(existingDirectoryInRootDirectory.isWritable());
		//true > false
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		assertFalse(existingDirectoryInRootDirectory.isWritable());
		//false > false
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		assertFalse(existingDirectoryInRootDirectory.isWritable());
		//false > true
		existingDirectoryInRootDirectory.changeWritabilityStatus(true);
		//this should not have changed!
		assertFalse(existingDirectoryInRootDirectory.isWritable());		
	}
	
	
	@Test
	public void testCanHaveAsName_LegalCase() {
		assertTrue(existingDirectoryInRootDirectory.canHaveAsName("abcDEF123-_"));
	}
	
	@Test
	public void testCanHaveAsName_IllegalCase() {
		assertFalse(existingDirectoryInRootDirectory.canHaveAsName(null));
		assertFalse(existingDirectoryInRootDirectory.canHaveAsName("somethingWithSt*rs"));
		assertFalse(existingDirectoryInRootDirectory.canHaveAsName("somethingWithD.ts"));
		assertFalse(existingDirectoryInRootDirectory.canHaveAsName(""));
	}
	
	@Test
	public void testCanAcceptAsNewName_LegalCase() {
		assertTrue(existingDirectoryInRootDirectory.canAcceptAsNewName("abcDEF123-_"));
	}
	
	@Test
	public void testCanAcceptAsNewName_DirectoryNotWritable() {
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		assertFalse(existingDirectoryInRootDirectory.canAcceptAsNewName("aValidName"));		
	}
	
	@Test
	public void testCanAcceptAsNewName_DirectoryTerminated() {
		assertFalse(terminatedDirectory.canAcceptAsNewName("aValidName"));	
	}
	
	@Test
	public void testCanAcceptAsNewName_InvalidName() {
		assertFalse(existingDirectoryInRootDirectory.canAcceptAsNewName("anInvalid.N*me"));	
	}
	
	@Test
	public void testCanAcceptAsNewName_SameName() {
		assertFalse(existingDirectoryInRootDirectory.canAcceptAsNewName("existingDirectory"));	
	}
	
	@Test
	public void testCanAcceptAsNewName_ParentAlreadyHasName() {
		directory = new Directory(rootDirectory,"anotherDir",true);
		assertFalse(existingDirectoryInRootDirectory.canAcceptAsNewName("anotherDir"));	
	}
	
	
	@Test
	public void testChangeName_LegalCase() {
		Date timeBeforeSetName = new Date();
		existingDirectoryInRootDirectory.changeName("NewLegalName");
		Date timeAfterSetName = new Date();
		
		assertEquals("NewLegalName",existingDirectoryInRootDirectory.getName());
		assertNotNull(existingDirectoryInRootDirectory.getModificationTime());
		assertFalse(existingDirectoryInRootDirectory.getModificationTime().before(timeBeforeSetName));
		assertFalse(timeAfterSetName.before(existingDirectoryInRootDirectory.getModificationTime()));
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testChangeName_DirectoryNotWritable() {
		existingDirectoryInRootDirectory.changeWritabilityStatus(false);
		existingDirectoryInRootDirectory.changeName("NewLegalName");
	}
	
	@Test (expected = IllegalStateException.class)
	public void testChangeName_AlreadyTerminated() {
		existingDirectoryInRootDirectory.terminate();
		existingDirectoryInRootDirectory.changeName("NewLegalName");
	}
	
	
	@Test
	public void testIsOrderedBeforeOrAfterStringOrItem() {
		//these tests are already done in the File testcase
	}
	
	
	@Test
	public void testGetAbsolutePath() {
		assertEquals(rootDirectory.getAbsolutePath(),"/rootFolder");
		assertEquals(existingDirectoryInRootDirectory.getAbsolutePath(),"/rootFolder/existingDirectory");
	}
	
	@Test
	public void testMakeRoot_LegalCase() {
		//two cases: one already is a root (mod time should not change)
		Date earlierModTime = rootDirectory.getModificationTime();
		rootDirectory.makeRoot();
		assertEquals(earlierModTime,rootDirectory.getModificationTime());
		assertTrue(rootDirectory.isRoot());
		
		//the other is a real makeroot
		
		//now place two files before the existing directory in the root dir, and two files after that
		//after making the existingDirectoryInRootDirectory a root, those after this should be moved
		//those before shouldn't
		File f1 = new File(rootDirectory,"aaa",Type.JAVA); //index 1
		File f2 = new File(rootDirectory,"bbb",Type.JAVA); //index 2
		File f3 = new File(rootDirectory,"yyy",Type.JAVA); //index 5
		File f4 = new File(rootDirectory,"zzz",Type.JAVA); //index 6
		//remind: we have placed a file "existingFile" in there too (index 4)
		int indexOfDir = rootDirectory.getIndexOf(existingDirectoryInRootDirectory); //should be 3
		assertEquals(3, indexOfDir); //not really part of this test, just an extra check
		int nrItems = rootDirectory.getNbItems();
		assertEquals(7, nrItems); //not really part of this test, just an extra check
		
		
		earlierModTime = existingDirectoryInRootDirectory.getModificationTime();
		Date earlierModTimeRoot = rootDirectory.getModificationTime();
		Date timeBefore = new Date();
		sleep();
		existingDirectoryInRootDirectory.makeRoot();
		sleep();
		Date timeAfter = new Date();
		
		//its modification date should be changed
		assertNotNull(existingDirectoryInRootDirectory.getModificationTime());
		assertNotEquals(earlierModTime,existingDirectoryInRootDirectory.getModificationTime());
		assertTrue(timeBefore.before(existingDirectoryInRootDirectory.getModificationTime()));
		assertTrue(timeAfter.after(existingDirectoryInRootDirectory.getModificationTime()));
		//that of the root directory too!
		assertNotNull(rootDirectory.getModificationTime());
		assertNotEquals(earlierModTimeRoot,rootDirectory.getModificationTime());
		assertTrue(timeBefore.before(rootDirectory.getModificationTime()));
		assertTrue(timeAfter.after(rootDirectory.getModificationTime()));
		//the dir should be out of the root dir
		assertFalse(rootDirectory.hasAsItem(existingDirectoryInRootDirectory));
		//indices should be changed, for the later files
		assertEquals(1,rootDirectory.getIndexOf(f1));
		assertEquals(2,rootDirectory.getIndexOf(f2));
		assertEquals(3,rootDirectory.getIndexOf(existingEmptyDirectoryInRootDirectory));
		assertEquals(4,rootDirectory.getIndexOf(existingFileInRootDirectory));
		assertEquals(5,rootDirectory.getIndexOf(f3));
		assertEquals(6,rootDirectory.getIndexOf(f4));
		//number of items should be 1 less than before
		assertEquals(nrItems -1, rootDirectory.getNbItems());
		//the dir should be a root now!
		assertTrue(existingDirectoryInRootDirectory.isRoot());
		
	}
	
	@Test (expected = DiskItemNotWritableException.class)
	public void testMakeRoot_ParentNotWritable() {
		rootDirectory.changeWritabilityStatus(false);
		existingDirectoryInRootDirectory.makeRoot();
	}
	
	@Test
	public void testGetTotalDiskUsage() {
		//should be 310 to start with
		assertEquals(rootDirectory.getTotalDiskUsage(),310);
		//add another file to the dir
		new File(existingDirectoryInRootDirectory, "newF", Type.PDF, 100, true);
		int sumSizes = 0;
		DirectoryIterator it = rootDirectory.getDirectoryIterator();
		while(it.getNbRemainingItems() > 0){
			sumSizes += it.getCurrentItem().getTotalDiskUsage();
			it.advance();
		}
		assertEquals(sumSizes,410);
		assertEquals(rootDirectory.getTotalDiskUsage(),sumSizes);
	}
	
	@Test
	public void testIsValidCreationTime() {
		// already tested in File Testcase
	}
	
	
	@Test
	public void testcanHaveAsModificationTime() {
		// already tested in File Testcase
	}
	
	@Test
	public void testHasOverlappingUsePeriod() {
		// already tested in File Testcase
	}
	
	@Test
	public void testIsRoot() {
		assertFalse(existingDirectoryInRootDirectory.isRoot());
		assertTrue(rootDirectory.isRoot());
	}
	
	@Test
	public void testGetRoot() {
		assertEquals(rootDirectory,existingDirectoryInRootDirectory.getRoot());
		assertEquals(rootDirectory,rootDirectory.getRoot());
	}
	
	@Test
	public void testCanHaveAsParentDirectory(){
		// tested in the File Testcase
	}
	
	@Test
	public void testCanBeMoved() {
		// tested in the File Testcase
	}
	
	@Test
	public void testMove() {
		// tested in the File Testcase
	}
	
	@Test
	public void testIsDirectOrIndirectParentOf() {
		assertFalse(rootDirectory.isDirectOrIndirectParentOf(null));
		assertTrue(rootDirectory.isDirectOrIndirectParentOf(existingFileInRootDirectory));
		File newFile = new File(existingDirectoryInRootDirectory,"newFile",Type.PDF);
		assertTrue(rootDirectory.isDirectOrIndirectParentOf(newFile));
		Directory otherDirectory = new Directory("otherDir");
		assertFalse(otherDirectory.isDirectOrIndirectParentOf(newFile));
	}
	

	//NOW everything about the Items of a directory.
	
	@Test
	public void testCanHaveAsItem() {
		//be sure to test all possible outcomes!
		assertFalse(rootDirectory.canHaveAsItem(null));
		assertFalse(rootDirectory.canHaveAsItem(terminatedDirectory));
		assertFalse(terminatedDirectory.canHaveAsItem(existingFileInRootDirectory));
		assertFalse(existingDirectoryInRootDirectory.canHaveAsItem(rootDirectory));
		assertTrue(rootDirectory.canHaveAsItem(existingFileInRootDirectory));
		Directory otherDir = new Directory("otherDir");
		File otherFile = new File(otherDir,"otherFile",Type.TEXT);
		assertTrue(rootDirectory.canHaveAsItem(otherFile));
		assertTrue(rootDirectory.canHaveAsItem(otherDir));
		Directory otherDirWithSameName = new Directory("existingDirectory");
		File otherFileWithSameName = new File(otherDir,"existingFile",Type.TEXT);
		assertFalse(rootDirectory.canHaveAsItem(otherFileWithSameName));
		assertFalse(rootDirectory.canHaveAsItem(otherDirWithSameName));
		otherDir.changeWritabilityStatus(false);
		assertFalse(rootDirectory.canHaveAsItem(otherFile));
		
	}
	
	@Test
	public void testCanHaveAsItemAt() {
		//false for all items which it can not have at any position (see above)
		assertFalse(rootDirectory.canHaveAsItemAt(null,1));
		assertFalse(rootDirectory.canHaveAsItemAt(terminatedDirectory,1));
		assertFalse(terminatedDirectory.canHaveAsItemAt(existingFileInRootDirectory,1));
		assertFalse(existingDirectoryInRootDirectory.canHaveAsItemAt(rootDirectory,1));
		Directory otherDir = new Directory("otherDir");
		File otherFile = new File(otherDir,"otherFile",Type.TEXT);
		Directory otherDirWithSameName = new Directory("existingDirectory");
		File otherFileWithSameName = new File(otherDir,"existingFile",Type.TEXT);
		assertFalse(rootDirectory.canHaveAsItemAt(otherFileWithSameName,1));
		assertFalse(rootDirectory.canHaveAsItemAt(otherDirWithSameName,1));
		otherDir.changeWritabilityStatus(false);
		assertFalse(rootDirectory.canHaveAsItemAt(otherFile,1));
		
		//if the index is out of bounds for an item which it can have
		assertFalse(rootDirectory.canHaveAsItemAt(existingFileInRootDirectory,0));
		assertFalse(rootDirectory.canHaveAsItemAt(existingFileInRootDirectory,rootDirectory.getNbItems()+1));
		
		//if it has it as item, it should follow the ordering
		assertTrue(rootDirectory.canHaveAsItem(existingFileInRootDirectory));
			//add a few items
		File f1 = new File(rootDirectory,"aaa",Type.JAVA); //index 1
		File f2 = new File(rootDirectory,"bbb",Type.JAVA); //index 2
		new File(rootDirectory,"yyy",Type.JAVA); //index 5
		File f4 = new File(rootDirectory,"zzz",Type.JAVA); //index 6
			//remind: we have placed a file "existingFile" in there too (index 4)
		//index 1
		assertTrue(rootDirectory.canHaveAsItemAt(f1, 1));
		assertTrue(rootDirectory.getItemAt(2).isOrderedAfter(f1));
		//index > 1 && index < getNbItems()
		assertTrue(rootDirectory.canHaveAsItemAt(f2, 2));
		assertTrue(rootDirectory.getItemAt(3).isOrderedAfter(f2));
		assertTrue(rootDirectory.getItemAt(1).isOrderedBefore(f2));
		//index = getNbItems
		assertTrue(rootDirectory.canHaveAsItemAt(f4, 7));
		assertTrue(rootDirectory.getItemAt(5).isOrderedBefore(f4));
		
		//if it can have it as an item, but does not yet have it as an item
		otherDir = new Directory("otherDir");
		otherFile = new File(otherDir,"otherFile",Type.TEXT);
		assertTrue(rootDirectory.canHaveAsItem(otherFile));
		//it can only have it at index 5!
		assertFalse(rootDirectory.canHaveAsItemAt(otherFile, 1));
		assertFalse(rootDirectory.getItemAt(2).isOrderedAfter(otherFile));
		assertFalse(rootDirectory.canHaveAsItemAt(otherFile, 2));
		assertFalse(rootDirectory.getItemAt(1).isOrderedBefore(otherFile) && rootDirectory.getItemAt(2).isOrderedAfter(otherFile));
		assertFalse(rootDirectory.canHaveAsItemAt(otherFile, 3));
		assertFalse(rootDirectory.getItemAt(2).isOrderedBefore(otherFile) && rootDirectory.getItemAt(3).isOrderedAfter(otherFile));
		assertFalse(rootDirectory.canHaveAsItemAt(otherFile, 4));
		assertFalse(rootDirectory.getItemAt(3).isOrderedBefore(otherFile) && rootDirectory.getItemAt(4).isOrderedAfter(otherFile));
		assertTrue(rootDirectory.canHaveAsItemAt(otherFile, 6));
		assertTrue(rootDirectory.getItemAt(5).isOrderedBefore(otherFile) && rootDirectory.getItemAt(6).isOrderedAfter(otherFile));
		assertFalse(rootDirectory.canHaveAsItemAt(otherFile, 7));
		assertFalse(rootDirectory.getItemAt(6).isOrderedBefore(otherFile));
				
	}

	@Test
	public void testHasProperItems() {
		new File(rootDirectory,"aaa",Type.JAVA); //index 1
		new File(rootDirectory,"bbb",Type.JAVA); //index 2
		new File(rootDirectory,"yyy",Type.JAVA); //index 5
		new File(rootDirectory,"zzz",Type.JAVA); //index 6
		for(int i=1; i<=rootDirectory.getNbItems(); i++){
			assertTrue(rootDirectory.canHaveAsItemAt(rootDirectory.getItemAt(i),i));
		}
	}
	
	
	@Test
	public void testHasAsItem() {
		new File(rootDirectory,"aaa",Type.JAVA); //index 1
		new File(rootDirectory,"bbb",Type.JAVA); //index 2
		new File(rootDirectory,"yyy",Type.JAVA); //index 5
		new File(rootDirectory,"zzz",Type.JAVA); //index 6
		
		Directory otherDir = new Directory("otherDir");
		File otherFile = new File(otherDir,"otherFile",Type.TEXT);
		//if this method returns false (which it should) *
		assertFalse(rootDirectory.hasAsItem(otherFile));
		boolean existingFileFound = false;
		for(int i=1; i<=rootDirectory.getNbItems(); i++){
			//* then none of the items should be equal to otherFile
			assertNotEquals(rootDirectory.getItemAt(i),otherFile);
			//look if one of the items equals existingFileInRootDirectory
			if(rootDirectory.getItemAt(i) == existingFileInRootDirectory) existingFileFound = true;
		}
		//both should be true:
		assertTrue(rootDirectory.hasAsItem(existingFileInRootDirectory));
		assertTrue(existingFileFound);
		
	}
	
	@Test
	public void testIsDirectOrIndirectSubdirectoryOf_LegalCase() {
		assertTrue(existingDirectoryInRootDirectory.isDirectOrIndirectSubdirectoryOf(rootDirectory));
		assertFalse(rootDirectory.isDirectOrIndirectSubdirectoryOf(existingDirectoryInRootDirectory));
		Directory otherDir = new Directory("otherDir");
		assertFalse(rootDirectory.isDirectOrIndirectSubdirectoryOf(otherDir));
		assertFalse(otherDir.isDirectOrIndirectSubdirectoryOf(rootDirectory));				
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testIsDirectOrIndirectSubdirectoryOf_Null() {
		if(rootDirectory.isDirectOrIndirectSubdirectoryOf(null))
			assert(false);
	}
	
	@Test
	public void testContainsDiskItemWithName() {
		assertEquals(rootDirectory.exists("existingFile"),rootDirectory.containsDiskItemWithName("existingFile"));
		assertEquals(rootDirectory.exists("nonExistingFile"),rootDirectory.containsDiskItemWithName("nonExistingFile"));	
	}
	
	@Test
	public void testExists() {
		new File(rootDirectory,"aaa",Type.JAVA); //index 1
		new File(rootDirectory,"bbb",Type.JAVA); //index 2
		new File(rootDirectory,"yyy",Type.JAVA); //index 5
		new File(rootDirectory,"zzz",Type.JAVA); //index 6
		
		boolean shouldBeFound = false;
		boolean shouldNotBeFound = false;
		for(int i=1; i<=rootDirectory.getNbItems(); i++){
			if(rootDirectory.getItemAt(i).getName().equalsIgnoreCase("existingFile")) shouldBeFound = true;
			if(rootDirectory.getItemAt(i).getName().equalsIgnoreCase("nonExistingFile")) shouldNotBeFound = true;
		}
		
		assertTrue(shouldBeFound);
		assertFalse(shouldNotBeFound);
	}
	
	@Test
	public void testGetItem() {
		new File(rootDirectory,"aaa",Type.JAVA); //index 1
		File f2 = new File(rootDirectory,"bbb",Type.JAVA); //index 2
		new File(rootDirectory,"yyy",Type.JAVA); //index 5
		new File(rootDirectory,"zzz",Type.JAVA); //index 6
		
		//a few that should be found
		assertTrue(rootDirectory.exists("aaa") && rootDirectory.hasAsItem(rootDirectory.getItem("aaa")) && rootDirectory.getItem("aaa").getName().equalsIgnoreCase("aaa"));
		assertTrue(rootDirectory.exists("bbb") && rootDirectory.hasAsItem(rootDirectory.getItem("bbb")) && rootDirectory.getItem("bbb").getName().equalsIgnoreCase("bbb"));
		assertEquals(f2,rootDirectory.getItem("bbb"));
		//and one that shouldn't
		assertNull(rootDirectory.getItem("non"));
		
	}
	
	@Test
	public void testGetIndexOf_LegalCase() {
		new File(rootDirectory,"aaa",Type.JAVA); //index 1
		File f2 = new File(rootDirectory,"bbb",Type.JAVA); //index 2
		new File(rootDirectory,"yyy",Type.JAVA); //index 5
		new File(rootDirectory,"zzz",Type.JAVA); //index 6
		
		assertEquals(f2,rootDirectory.getItemAt(rootDirectory.getIndexOf(f2)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetIndexOf_IllegalCase() {
		Directory otherDirectory = new Directory("otherDir");
		rootDirectory.getIndexOf(otherDirectory);
		assert(false);
	}
	
	
	
	
	
	private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
