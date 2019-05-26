package qahramon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A class of helper methods.
 * 
 * @author 	Robin Bruneel, Jean-Louis Carron, Edward Wiels
 * @version 1.0 - 2019
 */
public class MathHelper {
	/**
	 * Return a prime lower than the given value and higher than the minimum value.
	 * 
	 * @param 	number
	 * 			the number to find the closest prime for
	 * @param 	min
	 * 			the minimum boundary
	 * @return	Return a prime lower than the given value and higher than the minimum value.
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
	 * Return a random prime number in the given range.
	 * 
	 * @return	Return a random prime number in the given range the current maximum number is 2^31
	 * 			to speed up this process, but this can be changed.
	 */
	public static long getRandomPrime() {
		long min = 3L;
		long max = Integer.MAX_VALUE; //not too big
		long candidate = MathHelper.getRandomLongBetweenRange(min, max);
		
		if (MathHelper.isPrime(candidate)) {
			return candidate;
		} else {
			return getRandomPrime();
		}
	}
	
	/**
	 * Check whether the given number is a prime number.
	 * 
	 * @param 	number
	 * 			the number to check
	 * @return	Return true when the given number is a prime number.
	 * 			Return false otherwise.
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
	 * Return a random long number in the given range.
	 * 
	 * @param 	min
	 * 			the minimum value
	 * @param 	max
	 * 			the maximum value
	 * @pre		Range is smaller than the maximum long value.
	 * 			| max-min <= Long.MAX_VALUE
	 * @return	Return a random long number in the given range (between min and max-1).
	 * 			| Math.abs((randomNumberGenerator.nextLong() % (max - min))) + min
	 */
	public static long getRandomLongBetweenRange(long min, long max){
		Random randomNumberGenerator = new Random();
		
		if (max - min == 0) {
			return min;
		}
		
		return Math.abs((randomNumberGenerator.nextLong() % (max - min))) + min;
	}
	
	public static long getRandomLong() {
		Random randomNumberGenerator = new Random();
		return randomNumberGenerator.nextLong();
	}
	
	/**
	 * Return a random integer in the given range.
	 * 
	 * @param 	min
	 * 			the minimum value
	 * @param 	max
	 * 			the maximum value
	 * @return	Return a random integer number in the given range (between min and max).
	 * 			| result == (new Random()).nextInt(max-min) + min;
	 */
	public static int getRandomIntBetweenRange(int min, int max){
		if (max <= min) {
			return min;
		}
		else{ 
			return (new Random()).nextInt(max-min) + min;
		}
	}
	
	/**
	 * Return the value clamped between the min and max.
	 * 
	 * @param	value
	 * 			the value to clamp
	 * @param	min
	 * 			the minimum value
	 * @param	max
	 * 			the maximum value
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
	
	/**
	 * Generate a random sequence of integers
	 * @param 	seqLen
	 * 			the length of the random sequence
	 * @param 	maxNum
	 * 			the maximum number that can be in this sequence
	 * @return	Generate a random sequence of unique integers where each number lays between 0 and maxNum-1.
	 */
	public static ArrayList<Integer> generateRandomIntegerSequence (int seqLen, int maxNum) {
		HashMap<Integer, Integer> swaps = new HashMap<Integer, Integer>();
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		
		// we create a sequence from 0..maxNum-1 and swap the seqLen first numbers
		for (int i = 0; i < seqLen; i++) {
			int rnd = getRandomIntBetweenRange(0, maxNum);
			int rndVal;
			int iVal;
			// swap rnd and this num
			if (swaps.get(rnd) != null) {
				rndVal = swaps.get(rnd);
			} else {
				rndVal = rnd;
			}
			
			if (swaps.get(i) != null) {
				iVal = swaps.get(i);
			} else {
				iVal = i;
			}
			
			swaps.put(i, rndVal);
			swaps.put(rnd, iVal);
		}
		
		for (int i = 0; i < seqLen; i++) {
			sequence.add(swaps.get(i));
		}
		
		return sequence;
	}
}
