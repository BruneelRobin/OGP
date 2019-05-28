package Main;

import java.util.HashSet;
import java.util.Set;

import qahramon.*;
import qahramon.Character;

public class Main {

	public static void main(String[] args) {
		Weapon beagalltach = new Weapon(40, 1.7f, 130);
		
		Hero tim = new Hero("Tim: de vriendt", 100, 10f);
		tim.equip(AnchorType.LEFT_HAND, beagalltach);
		
		System.out.println("Total hero value: " + String.valueOf(tim.getTotalValue()));
		
		HashSet<Item> odonsItems = new HashSet<Item>();
		Weapon yawarakai = new Weapon(20, 3.3f, 70);
		odonsItems.add(yawarakai);
		
		Monster odon = new Monster ("Odontotyrannos", 250, 10, 10, 10, 200f, odonsItems);
		
		System.out.println("Total monster value: " + String.valueOf(odon.getTotalValue()));
		
		boolean monsterFirst = MathHelper.getRandomIntBetweenRange(0, 2) == 0;
		
		if (monsterFirst) {
			odon.hit(tim);
		}
		
		while (!odon.isDead() && !tim.isDead()) {
			
			
			tim.hit(odon);
			
			
			if (!odon.isDead()) {
				odon.hit(tim);
				System.out.println("Odontotyrannos: " + odon.getHitpoints());
				System.out.println("Tim: " + tim.getHitpoints());
			}
		}
		
		if (odon.isDead()) {
			System.out.println("Tim slaughtered the beast");
			System.out.println("Total hero value: " + tim.getTotalValue());
		} else {
			System.out.println("Odontotyrannos devoured Tim");
			System.out.println("Total monster value: " + tim.getTotalValue());
		}
		
	}

}
