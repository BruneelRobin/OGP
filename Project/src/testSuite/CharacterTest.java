package testSuite; 

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import qahramon.*;
import qahramon.exceptions.DeadException;

import org.junit.jupiter.api.*;



/**
 * Testcase for the character class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron Edward Wiels
 * @version 1.0
 *
 */
class CharacterTest {
	
	static Monster monster, smallMonster, deadMonster;
	static Hero hero;
	static Weapon weapon1, weapon2, heavyWeapon, smallWeapon, terminatedWeapon;
	static Backpack backpack;
	
	@BeforeAll
	public static void setUpBeforeAll() {
		terminatedWeapon = new Weapon(20, 15);
		terminatedWeapon.terminate();
		
		deadMonster = new Monster("LegalName", 499, 20, 70, 10, 50);
		deadMonster.takeDamage(499);
	}
	
	@BeforeEach
	public void setUp() {
		monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		smallMonster = new Monster("LegalName", 7, 1, 10, 2, 50);
		hero = new Hero("LegalName", 97, 10);
		weapon1 = new Weapon(10,5);
		weapon2 = new Weapon(25,10);
		heavyWeapon = new Weapon(30, 100);
		smallWeapon = new Weapon(2,1);
		backpack = new Backpack(100, 10, 2);
	}

	@Test
	public void testCanHaveAsName_LegalCase() {
		assertTrue(monster.canHaveAsName("ABCdef' 'ghiJKL"));
	}
	
	@Test
	public void testCanHaveAsName_IllegalCase() {
		assertFalse(monster.canHaveAsName("IllegalN*me"));
		assertFalse(monster.canHaveAsName(null));
		assertFalse(monster.canHaveAsName("noCapital"));
	}
	
	@Test
	public void testChangeName_LegalCase() {
		monster.changeName("NewLegalName");
		assertEquals("NewLegalName", monster.getName());
	}
	
	@Test
	public void testChangeName_IllegalCase() {
		assertThrows(IllegalArgumentException.class, () -> {
				monster.changeName("NewIllegalN*me");
		});
	}
	
	@Test
	public void testCanHaveAsHitpoints_LegalCase() {
		assertTrue(monster.canHaveAsHitpoints(499));
		monster.takeDamage(5);
		assertTrue(monster.canHaveAsHitpoints(100));
	}
	
	@Test
	public void testCanHaveAsHitpoints_IllegalCase() {
		assertFalse(monster.canHaveAsHitpoints(100));
		assertFalse(monster.canHaveAsHitpoints(-1));
	}
	
	@Test
	public void testTakeDamage() {
		monster.takeDamage(5);
		assertTrue(monster.isFighting());
		assertEquals(494, monster.getHitpoints());
		monster.takeDamage(500);
		assertTrue(monster.isDead());
		assertEquals(0, monster.getHitpoints());
	}
	
	@Test
	public void testIsValidMaxHitpoints_LegalCase() {
		assertTrue(Monster.isValidMaxHitpoints(499));
	}
	
	@Test
	public void testIsValidMaxHitpoints_IllegalCase() {
		assertFalse(Monster.isValidMaxHitpoints(500));
		assertFalse(Monster.isValidMaxHitpoints(-2));
	}
	
	@Test
	public void testIncreaseMaxHitpoints() {
		monster.increaseMaxHitpoints(4);
		assertEquals(503, monster.getMaxHitpoints());
	}
	
	@Test
	public void testLowerMaxHitpoints() {
		monster.lowerMaxHitpoints(8);
		assertEquals(491, monster.getMaxHitpoints());
	}
	
	@Test
	public void testcanHaveAsItemAt_LegalCase() {
		assertTrue(monster.canHaveAsItemAt(1, weapon1));
		monster.pickUp(weapon1);
		assertTrue(monster.canHaveAsItemAt(1, weapon1));
	}
	
	@Test
	public void testcanHaveAsItemAt_IllegalCase() {
		assertFalse(monster.canHaveAsItemAt(10, weapon1));
		assertFalse(monster.canHaveAsItemAt(1, terminatedWeapon));
		assertFalse(monster.canHaveAsItemAt(1, heavyWeapon));
		hero.pickUp(weapon1);
		assertFalse(monster.canHaveAsItemAt(1, weapon1));
	}
	
	@Test
	public void testEquip_LegalCase() {
		monster.equip(1, weapon1);
		assertEquals(weapon1, monster.getItemAt(1));
		monster.equip(2, weapon2);
		assertEquals(weapon2, monster.getItemAt(2));
		monster.equip(1, weapon2);
		assertEquals(weapon2, monster.getItemAt(1));
		assertEquals(weapon1.getHolder(), null);
	}
	
	@Test
	public void testEquip_IllegalCase() {
		Monster smallMonster2 = new Monster("LegalName", 7, 1, 10, 2, 10);
		smallMonster2.equip(0, weapon1);
		smallMonster2.equip(1, weapon2);
		assertEquals(weapon2.getHolder(), null);
	}
	
	/**************************************************************************************
	 * Test of taking away an item from an anchor
	 ***************************************************************************************/
	
	@Test
	public void testUnequip_ToBackpack() {
		hero.equip(AnchorType.BACK, backpack);
		hero.equip(AnchorType.RIGHT_HAND, weapon1);
		hero.unequip(AnchorType.RIGHT_HAND);
		assertTrue(backpack.contains(weapon1));
	}
	
	@Test
	public void testUnequip_ToGround() {
		hero.equip(AnchorType.RIGHT_HAND, weapon1);
		hero.unequip(AnchorType.RIGHT_HAND);
		assertEquals(weapon1.getHolder(), null);
	}
	
	
	/**************************************************************************************
	 * End of test of taking away an item from an anchor
	 ***************************************************************************************/
	
	@Test
	public void testcanPickUp_LegalCase() {
		assertTrue(monster.canPickUp(weapon1));
		monster.pickUp(weapon1);
		monster.takeDamage(500);
		assertTrue(hero.canPickUp(weapon1));
	}
	
	@Test
	public void testcanPickUp_IllegalCase() {
		assertFalse(monster.canPickUp(heavyWeapon));
		assertFalse(monster.canPickUp(terminatedWeapon));
		monster.pickUp(weapon1);
		assertFalse(hero.canPickUp(weapon1));
	}
	
	@Test
	public void testhasItem() {
		monster.equip(2, weapon1);
		assertTrue(monster.hasItem(weapon1));
		assertFalse(monster.hasItem(weapon2));
	}
	
	@Test
	public void testPickUp() {
		smallMonster.pickUp(backpack);
		smallMonster.pickUp(smallWeapon);
		smallMonster.pickUp(weapon1);
		assertEquals(smallMonster.getItemAt(0), backpack);
		assertEquals(smallMonster.getItemAt(1), smallWeapon);
		assertTrue(backpack.contains(weapon1));
		
		
	}

	
	@Test
	public void testPickUp_IllegalCase() {
		smallMonster.pickUp(heavyWeapon);
		assertEquals(heavyWeapon.getHolder(), null);
	}
	
	@Test
	public void testIsValidNumberOfAnchors_LegalCase() {
		assertTrue(Monster.isValidNumberOfAnchors(1));
	}
	
	@Test
	public void testIsValidNumberOfAnchors_IllegalCase() {
		assertFalse(Monster.isValidNumberOfAnchors(0));
	}
	
	// hasProperItems testen of niet?
	
	@Test
	public void testCollectTreasures() {
		smallMonster.pickUp(weapon1);
		hero.equip(AnchorType.RIGHT_HAND, smallWeapon);
		while (smallMonster.isDead() == false) {
			hero.hit(smallMonster);
		}
		assertEquals(hero, weapon1.getHolder());
		
		assertThrows(DeadException.class, () -> {deadMonster.collectTreasures(smallMonster);});
	}
	
}
