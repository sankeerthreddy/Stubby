/**
 * 
 */
package fk.core.provider.impl;

import fk.core.provider.IDataProvider;
import fk.utilities.TimestampParser;
import org.apache.log4j.lf5.util.DateFormatManager;

import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

/**
 * Generates date in the following format:
 * yyyy-MM-dd HH:mm:ss,S.
 * @author sankeerth.reddy
 *
 */
public class DateProvider implements IDataProvider {

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
	 * @param expression Multiplier value to be used
	 * to space the generated dates.
	 */
	public DateProvider(final String expression) {
		multiplier = Long.parseLong(expression);
		count = -1;
	}

	@Override
	public final String getData() {
		count++;
		String timestamp = String.valueOf(new Date().getTime()
				+ (count * multiplier));
		Date date = TimestampParser.convertTimestamp(timestamp);
		DateFormatManager mgr = new DateFormatManager(
				TimeZone.getTimeZone("UTC"),
				"yyyy-MM-dd HH:mm:ss,S");
		return mgr.format(date);
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
