/**
 * 
 */
package fk.utilities;

import java.util.Random;

/**
 * Generates random numbers with a given number of digits.
 * @author sankeerth.reddy
 */
public final class RandomNumberGenerator {

	/**
	 * Private constructor.
	 */
	private RandomNumberGenerator() { }

	/**
	 * @param digits Number of digits of the random number.
	 * Max digits allowed is 18. Enter 0 for a random number
	 * with random number of digits.
	 * @return Random number with the specified number of digits.
	 */
	public static String getRandomNumber(final int digits) {
		int numDigits = digits;
		if (digits < 1) {
			numDigits = Integer.parseInt(getRandomNumber(1));
		}

		final int maxDigitsInLong = 18;
		if (digits > maxDigitsInLong) {
			numDigits = maxDigitsInLong;
		}

		String num = String.valueOf(Math.abs(new Random().nextLong()));
		try {
			return num.substring(0, numDigits);
		} catch (StringIndexOutOfBoundsException e) {
			num = num + String.valueOf(Math.abs(
					new Random().nextLong()));
			return num.substring(0, numDigits);
		}
	}

}
