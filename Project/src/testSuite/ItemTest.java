package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import qahramon.*;

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
	static Weapon weapon1, weapon2, smallWeapon, terminatedWeapon;
	static Armor armor1, armor2, armor3;
	static Purse purse;
	static Backpack backpack;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		terminatedWeapon = new Weapon(20, 15);
		terminatedWeapon.terminate();
	}

	@BeforeEach
	public void setUp() {
		hero = new Hero("LegalName", 97, 10);
		monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		weapon1 = new Weapon(10,5);
		weapon2 = new Weapon(25,10);
		smallWeapon = new Weapon(2,1);
		armor1 = new Armor(7, 20, 10, 15);
		armor2 = new Armor(11, 50, 30, 50);
		armor3 = new Armor(13, 10, 5, 10);
		purse = new Purse(2, 500, 20);
		backpack = new Backpack(100, 10, 2);
	}
	
	@Test
	public void testClassTypes () {
		assertTrue(weapon1.isWeapon());
		assertFalse(armor1.isWeapon());
		assertFalse(purse.isWeapon());
		assertFalse(backpack.isWeapon());
		
		assertFalse(weapon1.isArmor());
		assertTrue(armor1.isArmor());
		assertFalse(purse.isArmor());
		assertFalse(backpack.isArmor());
		
		assertFalse(weapon1.isPurse());
		assertFalse(armor1.isPurse());
		assertTrue(purse.isPurse());
		assertFalse(backpack.isPurse());
		
		assertFalse(weapon1.isBackpack());
		assertFalse(armor1.isBackpack());
		assertFalse(purse.isBackpack());
		assertTrue(backpack.isBackpack());
	}
	
	@Test
	public void testTerminate () {
		assertFalse(weapon1.isTerminated());
		
		assertTrue(terminatedWeapon.isTerminated());
		assertEquals(terminatedWeapon.getCharacter(), null);
		assertEquals(terminatedWeapon.getHolder(), null);
	}
	
	@Test
	public void testIsValidWeight () {
		assertFalse(Item.isValidWeight(-10f));
		assertTrue(Item.isValidWeight(10f));
	}
	
	@Test
	public void testAnchor () {
		assertEquals(weapon1.getCharacter(), null);
		monster.equip(2,weapon1);
		assertEquals(weapon1.getCharacter(), monster);
	}
	
	@Test
	public void testBackpack () {
		assertEquals(weapon1.getParentBackpack(), null);
		weapon1.moveTo(backpack);
		assertEquals(weapon1.getParentBackpack(), backpack);
	}
	
	@Test
	public void testDrop () {
		weapon1.moveTo(backpack);
		assertEquals(weapon1.getParentBackpack(), backpack);
		assertTrue(backpack.contains(weapon1));
		weapon1.drop();
		assertEquals(weapon1.getParentBackpack(), null);
		assertFalse(backpack.contains(weapon1));
		//
		monster.equip(2,weapon1);
		assertEquals(weapon1.getCharacter(), monster);
		assertTrue(monster.hasItem(weapon1));
		weapon1.drop();
		assertEquals(weapon1.getCharacter(), null);
		assertFalse(monster.hasItem(weapon1));
	}
	
	@Test
	public void testGetHolder () {
		assertEquals(weapon1.getHolder(), null);
		weapon1.moveTo(backpack);
		assertEquals(weapon1.getHolder(), null);
		monster.equip(2,weapon1);
		assertEquals(weapon1.getHolder(), monster);
		weapon1.drop();
		assertEquals(weapon1.getHolder(), null);
	}

}
