package MindCraft;

import java.util.Random;

/**
 * A class of helper methods.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */
public class MathHelper {
	/**
	 * Return a prime lower than the given value and higher than the min value
	 * @param 	number
	 * 			The number to find the closest prime for
	 * @param 	min
	 * 			The minimum boundary
	 * @return	Return a prime lower than the given value and higher than the min value
	 */
	public static int getLowerPrime(int number, int min) {
		if (number % 2 == 0) {
			number -= 1;
		}
		
		while (number > min && !isPrime(number)) {
			number -= 2;
		}
		
		if (number <= min)
			return min; // no primes found
		else
			return number; // number is a prime
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
	
	/**
	 * Return the value clamped between the min and max
	 * @param	value
	 * 			The value to clamp
	 * @param	min
	 * 			The minimum value
	 * @param	max
	 * 			The maximum value
	 * @return	Return the minimum value when value is smaller than min, the maximum value when value is
	 * 			higher than max and value otherwise.
	 */
	public static int clamp (int value, int min, int max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}
		return value;
	}
}
