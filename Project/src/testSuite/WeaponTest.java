package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import qahramon.*;

import org.junit.jupiter.api.*;


/**
 * Testcase for the weapon class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class WeaponTest {
	
	static Weapon weapon;
	
	@BeforeAll
	public static void setUpBeforeAll() {
		
	}

	@BeforeEach
	public void setUp() {
		weapon = new Weapon(10,5);
	}
	
	@Test
	public void testIntFloat_LegalCase() {
		
	}
	
	@Test
	public void testIntFloatInt_LegalCase() {
		
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
	public void testUpgrade() {
		
	}
	
	@Test
	public void testDowngrade() {
		
	}
	
	

}
