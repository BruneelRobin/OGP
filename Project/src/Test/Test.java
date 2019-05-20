package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import MindCraft.*;
import MindCraft.Character;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Monster monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		Monster smallMonster = new Monster("LegalName", 7, 1, 10, 2, 10);
		Hero hero = new Hero("LegalName", 97, 10);
		Weapon weapon1 = new Weapon(10,5);
		Weapon weapon2 = new Weapon(25,10);
		Weapon heavyWeapon = new Weapon(30, 100);
		Weapon smallWeapon = new Weapon(2,1);
		Backpack backpack = new Backpack(100, 10, 2);
		
		smallMonster.pickUp(weapon1);
		hero.equip(AnchorType.RIGHT_HAND, smallWeapon);
		
		while (smallMonster.isDead() == false) {
			hero.hit(smallMonster);
			//System.out.println(smallMonster.getHitpoints());
		}
		assertEquals(hero, weapon1.getHolder());
	}

}
