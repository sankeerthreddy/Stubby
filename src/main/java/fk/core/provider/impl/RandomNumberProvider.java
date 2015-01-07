package fk.core.provider.impl;

import fk.core.provider.IDataProvider;
import fk.utilities.RandomNumberGenerator;

import java.util.Set;

/**
 * Provides random number with specified number of digits.
 * @author sankeerth.reddy
 */
public class RandomNumberProvider implements IDataProvider {

	/**
	 * Number of digits in the random number.
	 */
	private final int digits;

	/**
	 * @param expression Number of digits in the random number.
	 */
	public RandomNumberProvider(final String expression) {
		digits = Integer.parseInt(expression);
	}

	@Override
	public final String getData() {
		return String.valueOf(RandomNumberGenerator
				.getRandomNumber(digits));
	}

	@Override
	public void resetContext() {
		// No context maintained.
	}

	@Override
	public final Set<String> getPossibleValues() {
		return null;
	}

}
