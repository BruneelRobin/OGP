package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import qahramon.*;

import org.junit.jupiter.api.*;


/**
 * Testcase for the backpack class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class BackpackTest {

	static Hero hero;
	static Monster monster;
	static Weapon weapon, terminatedWeapon;
	static Armor armor;
	static Purse purse;
	static Backpack backpack, terminatedBackpack;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		terminatedWeapon = new Weapon(20, 15);
		terminatedWeapon.terminate();
		
		terminatedBackpack = new Backpack(100, 10, 2);
		terminatedBackpack.terminate();
	}

	@BeforeEach
	public void setUp() {
		hero = new Hero("LegalName", 97, 10);
		monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		weapon = new Weapon(10,5);
		armor = new Armor(7, 20, 10, 15);
		purse = new Purse(2, 500, 20);
		backpack = new Backpack(100, 10, 2);
	}
	
	@Test
	public void testBackpackFloatFloatInt_LegalCase () {
		Backpack bp = new Backpack(100, 10, 2);
		assertEquals(bp.getCapacity(), 100f, 0.01);
		assertEquals(bp.getWeight(), 10f, 0.01);
		assertEquals(bp.getValue(), 2);
		
		assertTrue(bp.canHaveAsIdentification(bp.getIdentification()));
	}
	
	@Test
	public void testBackpackFloatFloatInt_IllegalCase () {
		Backpack bp = new Backpack(100, -10f, -10);
		assertEquals(bp.getCapacity(), 100f, 0.01);
		assertEquals(bp.getWeight(), bp.getDefaultWeight(), 0.01);
		assertEquals(bp.getValue(), bp.getMinValue());
		
		Backpack bp2 = new Backpack(100, 10f, Integer.MAX_VALUE);
		//assertEquals(bp.getCapacity(), 100f, 0.01);
		//assertEquals(bp.getWeight(), bp.getDefaultWeight(), 0.01);
		assertEquals(bp2.getValue(), bp2.getMaxValue());
	}
	
	@Test
	public void testIsValidCapacity_LegalCase() {
		assertTrue(Backpack.isValidCapacity(10f));
	}
	
	@Test
	public void testIsValidCapacity_IllegalCase() {
		assertFalse(Backpack.isValidCapacity(-10f));
	}
	
	@Test
	public void testContains() {
		weapon.moveTo(backpack);
		
		assertTrue(backpack.contains(weapon));
		assertFalse(backpack.contains(armor));
	}
	
	@Test
	public void testCanHaveAsItem_LegalCase() {
		assertTrue(backpack.canHaveAsItem(weapon));
	}
	
	@Test
	public void testCanHaveAsItem_IllegalCase() {
		assertFalse(backpack.canHaveAsItem(terminatedWeapon));
		assertFalse(terminatedBackpack.canHaveAsItem(weapon));
		assertFalse(backpack.canHaveAsItem(purse));
		monster.equip(2, weapon);
		assertFalse(backpack.canHaveAsItem(weapon));
		Weapon superHeavy = new Weapon(10, 10000f);
		assertFalse(backpack.canHaveAsItem(superHeavy));
		Backpack bp = new Backpack(100, 10f, 10);
		bp.moveTo(backpack);
		assertFalse(bp.canHaveAsItem(backpack));
	}
	
	@Test
	public void testIsDirectOrIndirectSubBackpackOf () {
		Backpack bp = new Backpack(100, 10f, 10);
		bp.moveTo(backpack);
		
		assertTrue(bp.isDirectOrIndirectSubBackpackOf(backpack));
		assertFalse(backpack.isDirectOrIndirectSubBackpackOf(bp));
	}
	
	@Test
	public void testHasProperItems () {
		weapon.moveTo(backpack);
		armor.moveTo(backpack);
		assertTrue (backpack.hasProperItems());
	}
	
	@Test
	public void testChangeValue () {
		backpack.changeValue(10);
		assertEquals(backpack.getValue(), 12);
		backpack.changeValue(-5);
		assertEquals(backpack.getValue(), 7);
	}
	
	@Test
	public void testGetTotalWeight () {
		weapon.moveTo(backpack);
		armor.moveTo(backpack);
		
		assertEquals(backpack.getTotalWeight(), backpack.getWeight() + weapon.getWeight() + armor.getWeight());
		
	}
	
	@Test
	public void testGetTotalValue () {
		weapon.moveTo(backpack);
		armor.moveTo(backpack);
		
		assertEquals(backpack.getTotalValue(), backpack.getValue() + weapon.getValue() + armor.getValue());	
	}
	
	@Test
	public void testGetArmorCount () {
		assertEquals(backpack.getArmorCount(), 0);
		armor.moveTo(backpack);
		assertEquals(backpack.getArmorCount(), 1);
		Armor armor2 = new Armor(7, 20, 10, 15);
		armor2.moveTo(backpack);
		assertEquals(backpack.getArmorCount(), 2);
		
	}

}
