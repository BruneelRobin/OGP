package testSuite;

import static org.junit.jupiter.api.Assertions.*;

import qahramon.*;
import qahramon.exceptions.*;

import org.junit.jupiter.api.*;


/**
 * Testcase for the purse class.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0
 *
 */
class PurseTest {
	
	static Purse purse1, purse2, fullPurse, tornPurse;
	
	@BeforeAll
	public static void setUpBeforeAll() {
		tornPurse = new Purse(0.2f, 200, 10);
		tornPurse.add(201);
	}

	@BeforeEach
	public void setUp() {
		purse1 = new Purse(2, 500, 20);
		purse2 = new Purse(1, 400, 15);
		fullPurse = new Purse(3, 700, 700);
	}
	
	@Test
	public void testPurseFloatIntInt_LegalCase() {
		Purse purse3 = new Purse(0.5f, 600, 30);
		assertEquals(0.5f, purse3.getWeight());
		assertEquals(0.5f + 30 * Purse.getDucateWeight(), purse3.getTotalWeight());
		assertEquals(600, purse3.getCapacity());
		assertEquals(30, purse3.getContent());
	}
	
	@Test
	public void testPurseFloatIntInt_IllegalCase() {
		Purse purse3 = new Purse(-0.5f, 600, 30);
		assertEquals(100, purse3.getWeight());
		assertEquals(100 + 30 * Purse.getDucateWeight(), purse3.getTotalWeight());
		assertEquals(600, purse3.getCapacity());
		assertEquals(30, purse3.getContent());
	}
	
	@Test
	public void testIsValidCapacity_LegalCase() {
		assertTrue(Purse.isValidCapacity(500));
	}
	
	@Test
	public void testIsValidCapacity_IllegalCase() {
		assertFalse(Purse.isValidCapacity(-1));
	}
	
	@Test
	public void testCanHaveAsContent_LegalCase() {
		assertTrue(purse1.canHaveAsContent(500));
		assertTrue(purse1.canHaveAsContent(0));
	}
	
	@Test
	public void testCanHaveAsContent_IllegalCase() {
		assertFalse(purse1.canHaveAsContent(501));
		assertFalse(purse1.canHaveAsContent(-1));
		assertFalse(tornPurse.canHaveAsContent(200));
	}
	
	@Test
	public void testAddAmount_LegalCase() {
		purse1.add(10);
		assertEquals(30, purse1.getContent());
		purse1.add(471);
		assertTrue(purse1.isTorn());
	}
	
	@Test
	public void testAddAmount_IllegalCase() {
		assertThrows(TornException.class, () -> { tornPurse.add(10);});
		assertThrows(IllegalArgumentException.class, () -> { purse1.add(-10);});
	}
	
	@Test
	public void testRemoveAmount_LegalCase() {
		purse1.remove(10);
		assertEquals(10, purse1.getContent());
	}
	
	@Test
	public void testRemoveAmount_IllegalCase() {
		assertThrows(TornException.class, () -> { tornPurse.remove(10);});
		assertThrows(IllegalArgumentException.class, () -> { purse1.remove(21);});
		assertThrows(IllegalArgumentException.class, () -> { purse1.remove(-10);});
	}
	
	@Test
	public void testAddPurse_LegalCase() {
		purse1.add(purse2);
		assertEquals(35, purse1.getContent());
		assertEquals(0, purse2.getContent());
		purse1.add(fullPurse);
		assertTrue(purse1.isTorn());
		assertEquals(0, fullPurse.getContent());
	}
	
	@Test
	public void testAddPurse_IllegalCase() {
		assertThrows(TornException.class, () -> { purse1.add(tornPurse);});
	}

}
