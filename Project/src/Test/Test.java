package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import qahramon.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Purse p = new Purse(10f, 10, 5);
		
		System.out.println(p.getTotalValue());
	}

}
