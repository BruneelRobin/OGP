package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import qahramon.*;
import qahramon.exceptions.DeadException;

import org.junit.jupiter.api.*;

/**
 * Testcase for the monster class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class MonsterTest {
	
	static Monster monster, weakMonster, deadMonster;
	static Hero hero;
	static Armor armor;

	@BeforeEach
	public void setUp() {
		monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		weakMonster = new Monster("LegalName", 80, 0, 30, 5, 50);
		hero = new Hero("LegalName", 97, 10);
		armor = new Armor(7, 70, 50, 200);
		deadMonster = new Monster("LegalName", 499, 20, 70, 10, 50);
		deadMonster.takeDamage(499);
	}
	
	@Test
	public void monsterStringIntIntIntFloat_LegalCase() {
		Monster monster2 = new Monster("LegalName", 499, 20, 70, 10, 50);
		assertEquals(499, monster2.getHitpoints());
		assertEquals(499, monster2.getMaxHitpoints());
		
		assertEquals(20, monster2.getDamage());
		assertEquals(70, monster2.getProtection());
		assertEquals(10, monster2.getNumberOfAnchors());
		assertEquals(50, monster2.getCapacity());
		
		Monster monster3 = new Monster("LegalName", 499, 20, 70, 10, -50);
		assertEquals(499, monster3.getHitpoints());
		assertEquals(499, monster3.getMaxHitpoints());
		
		assertEquals(20, monster3.getDamage());
		assertEquals(70, monster3.getProtection());
		assertEquals(10, monster3.getNumberOfAnchors());
		assertEquals(0, monster3.getCapacity());
	}
	
	@Test
	public void monsterStringIntIntIntFloat_IllegalCase() {
		assertThrows(IllegalArgumentException.class, () -> { new Monster("illegalName", 499, 20, 70, 10, 50); });
		
		Monster monster3 = new Monster("LegalName", 499, 20, 70, 10, -50);
		assertEquals(499, monster3.getHitpoints());
		assertEquals(499, monster3.getMaxHitpoints());
		
		assertEquals(20, monster3.getDamage());
		assertEquals(70, monster3.getProtection());
		assertEquals(10, monster3.getNumberOfAnchors());
		assertEquals(0, monster3.getCapacity());
	}
	
	@Test
	public void monsterStringIntIntIntFloatSet_LegalCase() {
		HashSet<Item> items = new HashSet<Item>();
		
		items.add(armor);
		
		Monster monster2 = new Monster("LegalName", 499, 20, 70, 10, 50, items);
		assertEquals(499, monster2.getHitpoints());
		assertEquals(499, monster2.getMaxHitpoints());
		
		assertEquals(20, monster2.getDamage());
		assertEquals(70, monster2.getProtection());
		assertEquals(10, monster2.getNumberOfAnchors());
		assertEquals(50, monster2.getCapacity());
		
		assertEquals(true, monster2.hasAnchored(armor));
	}
	
	@Test
	public void monsterStringIntIntIntFloatSet_IllegalCase() {
		assertThrows(IllegalArgumentException.class, () -> { new Monster("illegalName", 499, 20, 70, 10, 50); });
		
		
		HashSet<Item> items = new HashSet<Item>();
		
		items.add(armor);
		
		Monster monster2 = new Monster("LegalName", 499, 20, 70, 10, 1, items);
		assertEquals(499, monster2.getHitpoints());
		assertEquals(499, monster2.getMaxHitpoints());
		
		assertEquals(20, monster2.getDamage());
		assertEquals(70, monster2.getProtection());
		assertEquals(10, monster2.getNumberOfAnchors());
		assertEquals(50, monster2.getCapacity());
		
		assertEquals(true, monster2.hasAnchored(armor));
	}
	
	@Test
	public void testIsValidDamage_LegalCase() {
		assertTrue(Monster.isValidDamage(20));
	}
	
	@Test
	public void testIsValidDamage_IllegalCase() {
		assertFalse(Monster.isValidDamage(-1));
	}
	
	@Test
	public void testIsValidProtection_LegalCase() {
		assertTrue(Monster.isValidProtection(70));
	}
	
	@Test
	public void testIsValidProtection_IlegalCase() {
		assertFalse(Monster.isValidProtection(101));
		assertFalse(Monster.isValidProtection(-1));
	}
	
	@Test
	public void testIsValidCapacity_LegalCase() {
		assertTrue(Monster.isValidCapacity(50));
	}
	
	@Test
	public void testIsValidCapacity_IllegalCase() {
		assertFalse(Monster.isValidCapacity(-1));
	}
	
	@Test
	public void testWantsToTake() {
		assertTrue(monster.wantsToTake(armor));
		armor.wearOut(armor.getProtection());
		assertFalse(monster.wantsToTake(armor));
	}
	
	/**************************************************************************************
	 * Test of hit
	 ***************************************************************************************/
	
	@Test
	public void testHit_LegalCase() {
		while (hero.getHitpoints() == hero.getMaxHitpoints()) {
			monster.hit(hero);
		}
		assertEquals(hero.getHitpoints(), hero.getMaxHitpoints() - monster.getDamage());
		assertTrue(hero.isFighting());
		assertThrows(DeadException.class, () -> { deadMonster.hit(monster); });
	}
	
	@Test
	public void testHit_DeathBlow() {
		weakMonster.pickUp(armor);
		while(weakMonster.getHitpoints() != 0) {
			monster.hit(weakMonster);
			}
		assertTrue(weakMonster.isDead());
		assertFalse(monster.isFighting());
		assertTrue(monster.hasAnchored(armor));
	}
	
	@Test
	public void testHit_HeroHighProtection() {
		hero.equip(AnchorType.BODY, armor);
		monster.takeDamage(450);
		for (int i = 0; i < 100; i++) {
			monster.hit(hero);
		}
		assertEquals(hero.getHitpoints(), hero.getMaxHitpoints());
	}
	
	@Test
	public void testHit_HitterDead() {
		assertThrows(DeadException.class, () -> { deadMonster.hit(monster); });
	}
	
	/**************************************************************************************
	 * End of test of hit
	 ***************************************************************************************/
	
	

}
