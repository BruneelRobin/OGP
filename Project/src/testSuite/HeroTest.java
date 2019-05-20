package testSuite;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import qahramon.*;

import org.junit.jupiter.api.*;

/**
 * Testcase for the hero class.
 * 
 * @author 	Jean-Louis Carron, Robin Bruneel, Edward Wiels
 * @version 1.0
 *
 */
class HeroTest {
	
	static Hero hero;
	static Monster monster;
	static Weapon weapon1, weapon2, smallWeapon;
	static Armor armor1, armor2, armor3;
	static Purse purse;

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
	}
	
	@Test
	public void testHeroStringIntFloat_LegalCase() {
		Hero hero2 = new Hero ("Legalname", 50, 10.51f);
		
		assertEquals ("Legalname", hero2.getName());
		assertEquals (AnchorType.values().length, hero2.getNumberOfAnchors());
		assertEquals (10.51, hero2.getStrength(), 0.01);
		assertEquals (50, hero2.getHitpoints());
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
		assertEquals (0, hero2.getStrength(), 0.01);
		assertEquals (50, hero2.getHitpoints());
	}
	
	@Test
	public void testHeroStringIntFloatHashMap_LegalCase() {
		
	}
	
	@Test
	public void testHeroStringIntFloatHashMap_IllegalSet() {
		
	}
	
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
	public void testWantsToTake() {
		hero.pickUp(weapon1);
		assertTrue(hero.wantsToTake(weapon2));
		assertFalse(hero.wantsToTake(smallWeapon));
		hero.pickUp(armor1);
		assertTrue(hero.wantsToTake(armor2));
	}
	
	@Test
	public void testHit() {
		hero.equip(AnchorType.RIGHT_HAND, weapon1);
		while (monster.getHitpoints() == monster.getMaxHitpoints()) {
			hero.hit(monster);
		}
		assertEquals(monster.getHitpoints(), monster.getMaxHitpoints() - hero.getDamage());
		assertTrue(monster.isFighting());
	}
		
	@Test
	public void testHeal() {
		hero.takeDamage(50);
		int hitpointsBefore = hero.getHitpoints();
		hero.heal();
		int hitpointsAfter = hero.getHitpoints();
		assertTrue(hero.canHaveAsHitpoints(hitpointsAfter));
		assertTrue(hitpointsBefore <= hitpointsAfter);
	}
	
	@Test
	public void testCanPickUp_TooMuchArmor() {
		hero.pickUp(armor1);
		hero.pickUp(armor2);
		assertFalse(hero.canPickUp(armor3));
	}
	
	@Test
	public void testCanHaveAsItemAt_PurseCase() {
		assertTrue(hero.canHaveAsItemAt(2, purse));
		assertFalse(hero.canHaveAsItemAt(2, weapon1));
		assertFalse(hero.canHaveAsItemAt(1, purse));
	}

}
