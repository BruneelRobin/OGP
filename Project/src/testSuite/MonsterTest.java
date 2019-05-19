package testSuite;

import MindCraft.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

/**
 * Testcase for the monster class.
 * 
 * @author 	Jean-Louis Carron, Robin Bruneel, Edward Wiels
 * @version 1.0
 *
 */
class MonsterTest {
	
	static Monster monster;
	static Hero hero;
	static Armor armor;

	@BeforeEach
	public void setUp() {
		monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		hero = new Hero("LegalName", 97, 10);
		armor = new Armor(7, 70, 50, 200);
	}
	
	@Test
	public void monsterStringIntIntIntFloat_LegalCase() {
		
	}
	
	@Test
	public void monsterStringIntIntIntFloatSet_LegalCase() {
		
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
	
	@Test
	public void testHit() {
		while (hero.getHitpoints() == hero.getMaxHitpoints()) {
			monster.hit(hero);
		}
		assertEquals(hero.getHitpoints(), hero.getMaxHitpoints() - monster.getDamage());
		assertTrue(hero.isFighting());
		hero.equip(AnchorType.BODY, armor);
		monster.takeDamage(450);
		monster.hit(hero);
		assertEquals(hero.getHitpoints(), hero.getMaxHitpoints() - monster.getDamage());
	}
	
	

}
