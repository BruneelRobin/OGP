package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import qahramon.*;
import qahramon.exceptions.DeadException;

import org.junit.jupiter.api.*;

/**
 * Testcase for the hero class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class HeroTest {
	
	static Hero hero, deadHero;
	static Monster monster, smallMonster, weakMonster, deadMonster;
	static Weapon weapon1, weapon2, smallWeapon;
	static Armor armor1, armor2, armor3;
	static Purse purse1, purse2;
	static Backpack backpack1, backpack2;
	
	@BeforeAll
	public static void setUpBeforeAll() {
	deadMonster = new Monster("LegalName", 499, 20, 70, 10, 50);
	deadMonster.takeDamage(499);
	deadHero = new Hero("LegalName", 97, 10);
	deadHero.takeDamage(97);
	}

	@BeforeEach
	public void setUp() {
		hero = new Hero("LegalName", 97, 10);
		monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		smallMonster = new Monster("LegalName", 7, 1, 10, 2, 50);
		weakMonster = new Monster("LegalName", 80, 0, 0, 5, 50);
		weapon1 = new Weapon(10,5);
		weapon2 = new Weapon(25,10);
		smallWeapon = new Weapon(2,1);
		armor1 = new Armor(7, 20, 10, 15);
		armor2 = new Armor(11, 50, 30, 50);
		armor3 = new Armor(13, 10, 5, 10);
		purse1 = new Purse(2, 500, 20);
		purse2 = new Purse(3, 700, 30);
		backpack1 = new Backpack(100, 10, 2);
		backpack2 = new Backpack(200, 20, 4);
	}
	
	@Test
	public void testHeroStringIntFloat_LegalCase() {
		Hero hero2 = new Hero ("Legalname", 50, 10.51f);
		
		assertEquals ("Legalname", hero2.getName());
		assertEquals (AnchorType.values().length, hero2.getNumberOfAnchors());
		assertEquals (10.51, hero2.getStrength(), 0.01);
		assertEquals (50, hero2.getHitpoints());
		
		assertThrows(DeadException.class, () -> { new Hero("LegalName", 0, 10); });
	}
	
	@Test 
	public void testHeroStringIntFloat_InvalidName() {
		assertThrows(IllegalArgumentException.class, () -> {new Hero ("Illegalname:", 50, 10.51f);});
	}
	
	@Test
	public void testHeroStringIntFloat_InvalidStrength() {
		Hero hero2 = new Hero ("Legalname", 50, -10.51f);
		
		assertEquals ("Legalname", hero2.getName());
		assertEquals (AnchorType.values().length, hero2.getNumberOfAnchors());
		assertEquals (Hero.getDefaultStrength(), hero2.getStrength(), 0.01);
		assertEquals (50, hero2.getHitpoints());
	}
	
	/**************************************************************************************
	 * Test of most extensive constructor of Hero
	 ***************************************************************************************/
	
	
	@Test
	public void testHeroStringIntFloatHashMap_LegalCase() {
		HashMap<AnchorType, Item> items = new HashMap<AnchorType, Item>();
		items.put(AnchorType.BODY, armor1);
		
		Hero hero2 = new Hero ("Legalname", 50, 10.51f, items);
		
		assertEquals ("Legalname", hero2.getName());
		assertEquals (AnchorType.values().length, hero2.getNumberOfAnchors());
		assertEquals (10.51, hero2.getStrength(), 0.01);
		assertEquals (50, hero2.getHitpoints());
		assertEquals(true, hero2.hasItem(armor1));
	}
	
	@Test
	public void testHeroStringIntFloatHashMap_IllegalSet() {
		HashMap<AnchorType, Item> items = new HashMap<AnchorType, Item>();
		items.put(AnchorType.BODY, armor2);
		
		Hero hero2 = new Hero ("Legalname", 50, 0f, items);
		
		assertEquals ("Legalname", hero2.getName());
		assertEquals (AnchorType.values().length, hero2.getNumberOfAnchors());
		assertEquals (Hero.getDefaultStrength(), hero2.getStrength(), 0.01);
		assertEquals (50, hero2.getHitpoints());
		assertEquals(false, hero2.hasItem(armor2));
	}
	
	/**************************************************************************************
	 * End of test of most extensive constructor of Hero
	 ***************************************************************************************/
	
	
	
	@Test
	public void testCanHaveAsName_LegalCase() {
		assertTrue(hero.canHaveAsName("ABCdef': 'ghiJKL"));
	}
	
	@Test
	public void testCanHaveAsName_IllegalCase() {
		assertFalse(hero.canHaveAsName("IllegalN*me"));
		assertFalse(hero.canHaveAsName("ColonWithoutSpace:"));
		assertFalse(hero.canHaveAsName(null));
		assertFalse(hero.canHaveAsName("noCapital"));
	}
	
	@Test
	public void testIsValidStrength_LegalCase() {
		assertTrue(Hero.isValidStrength(10));
	}
	
	@Test
	public void testIsValidStrength_IllegalCase() {
		assertFalse(Hero.isValidStrength(-1));
	}
	
	@Test
	public void testMultiplyStrength_LegalCase() {
		hero.multiplyStrength(2);
		assertEquals(20, hero.getStrength(), 0.1);
	}
	
	@Test
	public void testMultiplyStrength_IllegalCase() {
		hero.multiplyStrength(-1);
		assertEquals(10, hero.getStrength(), 0.1);
	}
	
	@Test
	public void testDivideStrength_LegalCase() {
		hero.divideStrength(2);
		assertEquals(5, hero.getStrength(), 0.1);
	}
	
	@Test
	public void testDivideStrength_IllegalCase() {
		hero.divideStrength(-1);
		assertEquals(10, hero.getStrength(), 0.1);
	}
	
	@Test
	public void testGetBestArmor() {
		hero.equip(AnchorType.BODY, armor3);
		hero.pickUp(armor1);
		assertEquals(20, hero.getBestArmor().getFullProtection());
	}
	
	@Test
	public void testGetBestWeapon() {
		hero.pickUp(weapon1);
		hero.pickUp(smallWeapon);
		assertEquals(10, hero.getBestWeapon().getDamage());
	}
	

	
	@Test
	public void testWantsToTake() {
		hero.pickUp(weapon1);
		assertTrue(hero.wantsToTake(weapon2));
		assertFalse(hero.wantsToTake(smallWeapon));
		hero.pickUp(armor1);
		assertTrue(hero.wantsToTake(armor2));
	}
	
	/**************************************************************************************
	 * Test of hit
	 ***************************************************************************************/
	
	@Test
	public void testHit() {
		hero.equip(AnchorType.RIGHT_HAND, weapon1);
		while (monster.getHitpoints() == monster.getMaxHitpoints()) {
			hero.hit(monster);
		}
		assertEquals(monster.getHitpoints(), monster.getMaxHitpoints() - hero.getDamage());
		assertTrue(monster.isFighting());
		assertThrows(DeadException.class, () -> { deadHero.hit(monster); });
	}
	
	@Test
	public void testHit_DeathBlow() {
		hero.equip(AnchorType.RIGHT_HAND, weapon1);
		weakMonster.pickUp(armor2);
		hero.takeDamage(90);
		while(weakMonster.getHitpoints() != 0) {
			hero.hit(weakMonster);
		}
		assertFalse(hero.isFighting());
		assertTrue(weakMonster.isDead());
		assertTrue(hero.getHitpoints() > 7);
		
		}
	
	/**************************************************************************************
	 * End of test of hit
	 ***************************************************************************************/
		
	@Test
	public void testHeal() {
		hero.takeDamage(50);
		int hitpointsBefore = hero.getHitpoints();
		hero.heal();
		int hitpointsAfter = hero.getHitpoints();
		assertTrue(hero.canHaveAsHitpoints(hitpointsAfter));
		assertTrue(hitpointsBefore <= hitpointsAfter);
		
		assertThrows(DeadException.class, () -> { deadHero.heal(); });
	}
	
	@Test
	public void testCanPickUp_TooMuchArmor() {
		hero.pickUp(armor1);
		hero.pickUp(armor2);
		assertFalse(hero.canPickUp(armor3));
	}
	
	@Test
	public void testCanHaveAsItemAt_PurseCase() {
		assertTrue(hero.canHaveAsItemAt(2, purse1));
		assertFalse(hero.canHaveAsItemAt(2, weapon1));
		assertFalse(hero.canHaveAsItemAt(1, purse1));
	}
	
	@Test
	public void testCollectTreasures_Purse() {
		smallMonster.pickUp(purse2);
		hero.equip(AnchorType.BELT, purse1);
		hero.equip(AnchorType.RIGHT_HAND, weapon1);
		smallMonster.takeDamage(smallMonster.getHitpoints());
		hero.collectTreasures(smallMonster);
		assertEquals(hero, purse2.getHolder());
		assertEquals(50, purse2.getContent());
	}
	
	@Test
	public void testCollectTreasures_Backpack() {
		weapon2.moveTo(backpack1);
		armor2.moveTo(backpack1);
		backpack2.moveTo(backpack1);
		monster.pickUp(backpack1);
		hero.equip(AnchorType.RIGHT_HAND, weapon1);
		monster.takeDamage(monster.getHitpoints());
		hero.collectTreasures(monster);
		assertEquals(hero, weapon1.getHolder());
		assertEquals(hero, armor2.getHolder());
		assertEquals(hero, backpack2.getHolder());
		assertEquals(monster, backpack1.getHolder());
	}

}
