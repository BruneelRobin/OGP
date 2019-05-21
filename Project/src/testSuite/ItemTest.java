package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import qahramon.*;
import qahramon.exceptions.TerminatedException;

import org.junit.jupiter.api.*;


/**
 * Testcase for the item class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class ItemTest {

	static Hero hero;
	static Monster monster;
	static Weapon weapon, terminatedWeapon;
	static Armor armor;
	static Purse purse;
	static Backpack backpack, backpack2;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		terminatedWeapon = new Weapon(20, 15);
		terminatedWeapon.terminate();
	}

	@BeforeEach
	public void setUp() {
		hero = new Hero("LegalName", 97, 10);
		monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		weapon = new Weapon(10,5);
		armor = new Armor(7, 20, 10, 15);
		purse = new Purse(2, 500, 20);
		backpack = new Backpack(100, 10, 2);
		backpack2 = new Backpack(50, 1, 3);
	}
	
	@Test
	public void testClassTypes_LegalCase () {
		assertTrue(weapon.isWeapon());
		
		assertTrue(armor.isArmor());
		
		assertTrue(purse.isPurse());
		
		assertTrue(backpack.isBackpack());
	}
	
	@Test
	public void testClassTypes_IllegalCase () {
		assertFalse(armor.isWeapon());
		assertFalse(purse.isWeapon());
		assertFalse(backpack.isWeapon());
		
		assertFalse(weapon.isArmor());
		assertFalse(purse.isArmor());
		assertFalse(backpack.isArmor());
		
		assertFalse(weapon.isPurse());
		assertFalse(armor.isPurse());
		assertFalse(backpack.isPurse());
		
		assertFalse(weapon.isBackpack());
		assertFalse(armor.isBackpack());
		assertFalse(purse.isBackpack());
	}
	
	@Test
	public void testTerminate () {
		assertFalse(weapon.isTerminated());
		assertTrue(terminatedWeapon.isTerminated());
		
		assertEquals(terminatedWeapon.getCharacter(), null);
		assertEquals(terminatedWeapon.getHolder(), null);
	}
	
	@Test
	public void testIsValidWeight_LegalCase () {
		assertTrue(Item.isValidWeight(10f));
	}
	
	@Test
	public void testIsValidWeight_IllegalCase () {
		assertFalse(Item.isValidWeight(-10f));
	}
	
	@Test
	public void testCharacter () {
		assertEquals(weapon.getCharacter(), null);
		monster.equip(2,weapon);
		assertEquals(weapon.getCharacter(), monster);
	}
	
	@Test
	public void testBackpack () {
		assertEquals(weapon.getParentBackpack(), null);
		weapon.moveTo(backpack);
		assertEquals(weapon.getParentBackpack(), backpack);
	}
	
	
	/**************************************************************************************
	 * Test of adding an object to a backpack
	 ***************************************************************************************/
	
	
	@Test
	public void testMoveTo_LegalCase () {
		assertEquals(weapon.getParentBackpack(), null);
		weapon.moveTo(backpack);
		assertEquals(weapon.getParentBackpack(), backpack);
	}
	
	@Test
	public void testMoveTo_IllegalCase () {
		assertThrows(TerminatedException.class, () -> { terminatedWeapon.moveTo(backpack); });
	}
	
	/**************************************************************************************
	 * End of test of adding an object to a backpack
	 ***************************************************************************************/
	
	
	/**************************************************************************************
	 * Test of taking away an item from an anchor
	 ***************************************************************************************/
	
	@Test
	public void testDrop_FromBackpack () {
		weapon.moveTo(backpack);
		assertEquals(weapon.getParentBackpack(), backpack);
		assertTrue(backpack.contains(weapon));
		weapon.drop();
		assertEquals(weapon.getParentBackpack(), null);
		assertEquals(weapon.getHolder(),null);
		assertFalse(backpack.contains(weapon));
	}
	
	@Test
	public void testDrop_FromCharacter() {
		monster.equip(2,weapon);
		assertEquals(weapon.getCharacter(), monster);
		assertTrue(monster.hasItem(weapon));
		weapon.drop();
		assertEquals(weapon.getCharacter(), null);
		assertEquals(weapon.getHolder(),null);
		assertFalse(monster.hasItem(weapon));
	}
	
	@Test
	public void testDrop_FromBackpackInBackpackOnCharacter() {
		weapon.moveTo(backpack2);
		backpack2.moveTo(backpack);
		hero.equip(AnchorType.BACK, backpack);
		weapon.drop();
		assertEquals(weapon.getParentBackpack(), null);
		assertEquals(weapon.getHolder(),null);
		assertFalse(backpack.contains(weapon));
		assertFalse(backpack2.contains(weapon));
	}
	
	/**************************************************************************************
	 * End of test of taking away an item from an anchor
	 ***************************************************************************************/
	
	@Test
	public void testGetHolder () {
		assertEquals(weapon.getHolder(), null);
		weapon.moveTo(backpack);
		assertEquals(weapon.getHolder(), null);
		monster.equip(2,weapon);
		assertEquals(weapon.getHolder(), monster);
		weapon.drop();
		assertEquals(weapon.getHolder(), null);
	}

}
