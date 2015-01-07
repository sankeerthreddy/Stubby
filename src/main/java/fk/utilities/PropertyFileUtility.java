package fk.utilities;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Utility for reading from a property file.
 * @author sankeerth.reddy
 */
public final class PropertyFileUtility {

	/**
	 * Configuration object populated from the specified properties file.
	 */
	private Properties config;

	/**
	 * Property file to be accessed.
	 * Used for logging purposes.
	 */
	private String fileName;

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER =
			Logger.getLogger(PropertyFileUtility.class);

	/**
	 * Property file utility constructor.
	 * @param name Property file to be read.
	 */
	public PropertyFileUtility(final String name) {
		try {
			this.fileName = name;
			File file = new File(this.fileName);
			FileReader reader = new FileReader(file);
			this.config = new Properties();
			this.config.load(reader);
			reader.close();
		} catch (IOException e) {
			LOGGER.error("Error in reading file: "
					+ this.fileName, e);
			System.exit(1);
		}
	}

	/**
	 * @return Map of all the properties.
	 */
	public Map<String, String> getAllProperties() {
		Iterator<Entry<Object, Object>> iter =
				this.config.entrySet().iterator();
		Map<String, String> allProperties = new HashMap<String, String>();
		while (iter.hasNext()) {
			Entry<Object, Object> entry = iter.next();
			allProperties.put(
					entry.getKey().toString(),
					entry.getValue().toString()
					);
		}
		return allProperties;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, string value of the specified property is returned.
	 * Else, null is returned.
	 */
	public String getStringValue(final String key) {
		if (this.config.containsKey(key)) {
			return this.config.getProperty(key);
		}

		LOGGER.error("Specified key not found in "
				+ this.fileName + ": " + key);
		return null;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, string value of the property is returned.
	 * Else, the default value is returned.
	 */
	public String getStringValue(final String key,
			final String defaultValue) {
		if (this.config.containsKey(key)) {
			return this.config.getProperty(key);
		}

		LOGGER.warn("Specified key not found in "
				+ this.fileName + ": " + key);
		return defaultValue;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, boolean value of the specified
	 * property is returned. Else, false is returned.
	 * False is also returned, in case the string does
	 * not match true.
	 */
	public boolean getBooleanValue(final String key) {
		if (this.config.containsKey(key)) {
			String val = this.config.getProperty(key)
					.trim().toLowerCase();
			if (val.equals("true")) {
				return true;
			}
			return false;
		}
		LOGGER.error("Specified key not found in "
				+ this.fileName + ": " + key);
		return false;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, boolean value of the property is returned.
	 * Else, the default value is returned.
	 * The default value is also returned in case the string value
	 * does not match true.
	 */
	public boolean getBooleanValue(final String key,
			final boolean defaultValue) {
		if (this.config.containsKey(key)) {
			String val = this.config.getProperty(key)
					.trim().toLowerCase();

			if (val.equals("true")) {
				return true;
			}

			return false;
		}

		LOGGER.warn("Specified key not found in "
				+ this.fileName + ": " + key);
		return defaultValue;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, integer value of the specified
	 * property is returned. Else, -1 is returned.
	 * -1 is also returned, in case of parse exception.
	 */
	public int getIntegerValue(final String key) {
		if (this.config.containsKey(key)) {
			String val = this.config.getProperty(key)
					.trim().toLowerCase();
			try {
				return Integer.parseInt(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as integer value: "
						+ val + "\nIn " + this.fileName
						+ " for key: " + key + e);
				return -1;
			}
		}
		LOGGER.error("Specified key not found in "
				+ this.fileName + ": " + key);
		return -1;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, integer value of the property is returned.
	 * Else, the default value is returned.
	 * The default value is also returned in case of a parse exception.
	 */
	public int getIntegerValue(final String key, final int defaultValue) {
		if (this.config.containsKey(key)) {
			String val = this.config.getProperty(key)
					.trim().toLowerCase();
			try {
				return Integer.parseInt(val);
			} catch (NumberFormatException e) {
				LOGGER.warn("Error parsing as integer value: "
						+ val + "\nIn " + this.fileName
						+ " for key: " + key + e);
				return defaultValue;
			}
		}
		LOGGER.warn("Specified key not found in "
				+ this.fileName + ": " + key);
		return defaultValue;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, integer value of the specified
	 * property is returned. Else, -1 is returned.
	 * -1 is also returned, in case of parse exception.
	 */
	public double getDoubleValue(final String key) {
		if (this.config.containsKey(key)) {
			String val = this.config.getProperty(key)
					.trim().toLowerCase();
			try {
				return Double.parseDouble(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as double value: "
						+ val + "\nIn " + this.fileName
						+ " for key: " + key +  e);
				return -1.0;
			}
		}
		LOGGER.error("Specified key not found in "
				+ this.fileName + ": " + key);
		return -1.0;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, double value of the property is returned.
	 * Else, the default value is returned.
	 * The default value is also returned in case of a parse exception.
	 */
	public double getDoubleValue(final String key,
			final double defaultValue) {
		if (this.config.containsKey(key)) {
			String val = this.config.getProperty(key)
					.trim().toLowerCase();
			try {
				return Double.parseDouble(val);
			} catch (NumberFormatException e) {
				LOGGER.warn("Error parsing as double value: "
						+ val + "\nIn " + this.fileName
						+ " for key: " + key + e);
				return defaultValue;
			}
		}
		LOGGER.warn("Specified key not found in "
				+ this.fileName + ": " + key);
		return defaultValue;
	}
}
