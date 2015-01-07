package fk.core.provider.impl;

import fk.core.provider.IDataProvider;

import java.util.Date;
import java.util.Set;

/**
 * Generates a new time stamp.
 * @author sankeerth.reddy
 *
 */
public class TimestampProvider implements IDataProvider {

	/**
	 * Time unit for producing increasing time stamps with larger gaps.
	 */
	private final long multiplier;

	/**
	 * Invocation count to space time stamps.
	 */
	private int count;

	/**
	 * Constructor.
	 */
	public TimestampProvider(final String expression) {
		multiplier = Long.parseLong(expression);
		count = -1;
	}

	@Override
	public final String getData() {
		count++;
		return String.valueOf(new Date().getTime()
				+ (count * multiplier));
	}

	@Override
	public final void resetContext() {
		count = -1;
	}

	@Override
	public final Set<String> getPossibleValues() {
		return null;
	}

}
