package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import qahramon.*;
import qahramon.exceptions.*;

import org.junit.jupiter.api.*;


/**
 * Testcase for the weapon class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class WeaponTest {
	
	static Weapon weapon, terminatedWeapon;
	
	@BeforeAll
	public static void setUpBeforeAll() {
		terminatedWeapon = new Weapon(15,7);
		terminatedWeapon.terminate();
	}

	@BeforeEach
	public void setUp() {
		weapon = new Weapon(10,5);
	}
	
	@Test
	public void testIntFloat_LegalCase() {
		Weapon weapon2 = new Weapon(20, 10, 100);
		assertEquals(20, weapon2.getDamage());
		assertEquals(10, weapon2.getWeight());
		assertEquals(100, weapon2.getValue());
	}
	
	@Test
	public void testIntFloat_IllegalCase() {
		Weapon weapon2 = new Weapon(20, -10, 100);
		assertEquals(20, weapon2.getDamage());
		assertEquals(100, weapon2.getWeight());
		assertEquals(100, weapon2.getValue());
		
		Weapon weapon3 = new Weapon(20, 10, -100);
		assertEquals(20, weapon3.getDamage());
		assertEquals(10, weapon3.getWeight());
		assertEquals(0, weapon3.getValue());
		
		Weapon weapon4 = new Weapon(20, 10, 300);
		assertEquals(20, weapon4.getDamage());
		assertEquals(10, weapon4.getWeight());
		assertEquals(200, weapon4.getValue());
	}
	
	@Test
	public void testIntFloatInt_LegalCase() {
		Weapon weapon2 = new Weapon(20, 10);
		assertEquals(20, weapon2.getDamage());
		assertEquals(10, weapon2.getWeight());
		assertEquals(40, weapon2.getValue());
	}
	
	@Test
	public void testIntFloatInt_IllegalCase() {
		Weapon weapon2 = new Weapon(20, -10);
		assertEquals(20, weapon2.getDamage());
		assertEquals(100, weapon2.getWeight());
		assertEquals(40, weapon2.getValue());
	}
	
	@Test
	public void testCanHaveAsIdentification_LegalCase() {
		assertTrue(weapon.canHaveAsIdentification(6));
	}
	
	@Test
	public void testCanHaveAsIdentification_IllegalCase() {
		assertFalse(weapon.canHaveAsIdentification(5));
		assertFalse(weapon.canHaveAsIdentification(-6));
	}
	
	@Test
	public void testIsValidDamage_LegalCase() {
		assertTrue(Weapon.isValidDamage(7));
	}
	
	@Test
	public void testIsValidDamage_IllegalCase() {
		assertFalse(Weapon.isValidDamage(6));
		assertFalse(Weapon.isValidDamage(-7));
	}
	
	@Test
	public void testUpgrade_LegalCase() {
		weapon.upgrade(5);
		assertEquals(15, weapon.getDamage());
	}
	
	@Test
	public void testUpgrade_Terminated() {
		assertThrows(TerminatedException.class, () -> {
			terminatedWeapon.upgrade(5);
	});
	}
	
	@Test
	public void testDowngrade_LegalCase() {
		weapon.downgrade(5);
		assertEquals(5, weapon.getDamage());
	}
	
	@Test
	public void testDowngrade_Terminated() {
		assertThrows(TerminatedException.class, () -> {
			terminatedWeapon.downgrade(5);
	});
	}
	
	

}
