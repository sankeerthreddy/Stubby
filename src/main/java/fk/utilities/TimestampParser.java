package fk.utilities;

import java.util.Date;

/**
 * Converts long time stamp into Java date format.
 * @author sankeerth.reddy
 */
public final class TimestampParser {

	/**
	 * Private constructor.
	 */
	private TimestampParser() { }

	/**
	 * @return Parsed java.util.date
	 * @param timestamp String value of time stamp
	 */
	public static Date convertTimestamp(final String timestamp) {
		long millseconds = Long.parseLong(timestamp);
		return new Date(millseconds);
	}

}
