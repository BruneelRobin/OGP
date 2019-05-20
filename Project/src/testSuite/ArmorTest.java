package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import qahramon.*;
import qahramon.exceptions.TerminatedException;

import org.junit.jupiter.api.*;


/**
 * Testcase for the armor class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class ArmorTest {
	
	
	static Armor armor, terminatedArmor;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		terminatedArmor = new Armor(13,70, 15f,200);
		terminatedArmor.terminate();
	}
	
	@BeforeEach
	public void setUp() {
		armor = new Armor(7, 70 ,50f ,200);
	}
	
	
	
	@Test
	public void armorLongIntFloatInt_LegalCase() {
		Armor armor2 = new Armor(83, 25, 12.5f, 250);
		assertEquals(83, armor2.getIdentification());
		assertEquals(25, armor2.getProtection());
		assertEquals(25, armor2.getFullProtection());
		assertEquals(12.5f, armor2.getWeight(),0.01);
		assertEquals(250, armor2.getValue());
		assertEquals(250, armor2.getFullValue());
		
		
	}
	
	
	
	@Test
	public void armorLongIntFloatInt_IllegalCase() {
		Armor armor2 = new Armor(7, 25, -12.5f, 1001);
		assertFalse(armor2.getIdentification() == 7);
		assertEquals(25, armor2.getProtection());
		assertEquals(25, armor2.getFullProtection());
		assertEquals(100f, armor2.getWeight(),0.01);
		assertEquals(1000, armor2.getValue());
		assertEquals(1000, armor2.getFullValue());
		
	}
	
	@Test
	public void testCanHaveAsIdentification_LegalCase() {
		assertTrue(armor.canHaveAsIdentification(4877L));
	}
	
	@Test
	public void testCanHaveAsIdentification_IllegalCase() {
		assertFalse(armor.canHaveAsIdentification(55555));
		assertFalse(armor.canHaveAsIdentification(-600));
		
		}
	
	@Test
	public void testCanHaveAsNewIdentification_LegalCase() {
		assertTrue(armor.canHaveAsNewIdentification(2803));
	}
	
	@Test
	public void testCanHaveAsNewIdentification_IllegalCase() {
		assertFalse(armor.canHaveAsNewIdentification(7));
	}
	
	@Test
	public void testCanHaveAsProtection_LegalCase() {
		assertTrue(armor.canHaveAsProtection(60));
	}
	
	@Test
	public void testCanHaveAsProtection_IllegalCase() {
		assertFalse(armor.canHaveAsProtection(71));
		assertFalse(armor.canHaveAsProtection(-6));
		}
	
	@Test
	public void testIsValidFullProtection_LegalCase() {
		assertTrue(Armor.isValidFullProtection(100));
		
	}
	
	@Test
	public void testIsValidFullProtection_IllegalCase() {
		assertFalse(Armor.isValidFullProtection(101));
		assertFalse(Armor.isValidFullProtection(-1));
	}
	
	@Test
	public void testWearOut_LegalCase() {
		armor.wearOut(10);
		assertEquals(armor.getProtection(),60);
	}
	
	@Test
	public void testWearOut_IllegalCase() {
		assertThrows(TerminatedException.class,() -> {terminatedArmor.wearOut(10);});
	}
	
	@Test
	public void testRepair_LegalCase() {
		armor.wearOut(20);
		armor.repair(10);
		assertEquals(60,armor.getProtection());
	}
	
	@Test
	public void testRepair_IllegalCase() {
		assertThrows(TerminatedException.class,() -> {terminatedArmor.repair(10);});
	}
	
	@Test
	public void testGetValue_LegalCase() {
		armor.wearOut(5);
		assertEquals(185,armor.getValue());
	}
	
	
	@Test
	public void testGetFullValue_LegalCase() {
		armor.wearOut(15);
		assertEquals(200, armor.getFullValue());
	}
	
	

	

}
