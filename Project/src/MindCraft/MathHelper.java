package MindCraft;

import java.util.Random;

public class MathHelper {
	/**
	 * Return the closest prime number between the given boundaries
	 * @param 	number
	 * 			The number to find the closest prime for
	 * @param 	min
	 * 			The minimum boundary
	 * @param 	max
	 * 			The maximum boundary
	 * @return	Return the closest prime number between the given boundaries
	 */
	public static int getClosestPrime(long number, long min, long max) {
		return 2;
	}
	
	/**
	 * Return whether the given number is a prime number
	 * @param 	number
	 * 			The number to check
	 * @return	Return true when the given number is a prime number
	 * @return	Return false otherwise
	 */
	public static boolean isPrime(long number) 
	{
	        if (number == 2) 
	            return true;
	        if (number < 2 || number % 2 == 0) 
	            return false;
	        for (int i = 3; i * i <= number; i += 2)
	            if (number % i == 0) 
	                return false;
	        return true;
	}
	
	/**
	 * Return a random long number in the given range
	 * @param 	min
	 * 			The minimum value
	 * @param 	max
	 * 			The maximum value
	 * @return	Return a random long number in the given range (between min and max)
	 */
	public static long getRandomLongBetweenRange(long min, long max){
		Random r = new Random();
		long number = min+((long)(r.nextDouble()*(max-min)));
		return number;
	}
}
