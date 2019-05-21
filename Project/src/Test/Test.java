package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import qahramon.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Purse p = new Purse(10f, 10, 5);
		Hero hero = new Hero("LegalName", 97, 10);
		Monster monster = new Monster("LegalName", 499, 20, 70, 10, 50);
		Weapon weapon = new Weapon(10,5);
		Armor armor = new Armor(7, 20, 10, 15);
		Purse purse = new Purse(2, 500, 20);
		Backpack backpack = new Backpack(100, 10, 2);
		
		System.out.println(monster);
		System.out.println(hero);
		//System.out.println(backpack);
	}

}
