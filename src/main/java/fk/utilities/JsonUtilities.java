package fk.utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fk.core.entity.IDataEntity;
import fk.references.AutomationReferences;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * Additional utilities for JSON objects to handle
 * JSON paths for variables.
 * @author sankeerth.reddy
 */
public class JsonUtilities {
	
	/**
	 * Private constructor.
	 */
	private JsonUtilities() { }

	/**
	 * Root logger instance.
	 */
	private final static Logger LOGGER =
			Logger.getLogger(JsonUtilities.class);

	/**
	 * @param json JSON object.
	 * @param jsonPath Successive path of JSON to fetch
	 * e.g. e.f.v.ri
	 * @return Inner JSON object.
	 * Null is returned if any key doesn't exist.
	 * Null is returned if at any the element is an array
	 * and not an object.
	 * Null is returned if the last key doesn't represent
	 * an object.
	 */
	public static JsonObject getInnerJSONObjectObject(
			final JsonObject json, final String jsonPath) {
		String[] path = jsonPath.split("[.]");
		int size = path.length;

		JsonObject temp = null;
		for (int i = 0; i < size; i++) {
			String key = path[i];
			if (!json.has(key)) {
				return null;
			}

			JsonElement jsonElement = json.get(key);
			if (!jsonElement.isJsonObject()) {
				return null;
			}

			temp = jsonElement.getAsJsonObject();
		}
		return temp;
	}

	/**
	 * @param json JSON object to be modified.
	 * @param entity Data entity to be modified.
	 * @return Modified JSON object.
	 */
	public static JsonObject setInnerJSONObject(
			final JsonObject json, final IDataEntity entity) {
		String s = entity.getDataType();
		if (s.equals("STRING")) {
			return setStringInnerJSONObject(json,
					entity.getJsonPath(),
					entity.getData());
		} else if (s.equals("NUMBER")) {
			return setNumericInnerJSONObject(json,
					entity.getJsonPath(),
					new BigDecimal(entity.getData()));
		} else {
			LOGGER.error("Unhandled data type: "
					+ entity.getDataType()
					+ ". Returning JSON unmodified.");
			return json;
		}
	}

	/**
	 * @param json JSON object to be modified.
	 * @param path JSON path of the variable to be modified.
	 * @param value String value to be set.
	 * @return Modified JSON object.
	 */
	private static JsonObject setStringInnerJSONObject(
			final JsonObject json, final String path,
			final String value) {
		if (path.indexOf(AutomationReferences.
				JSON_PATH_SEPARATOR) == -1) {
			json.addProperty(path, value);
			return json;
		}
		else {
			String key = StringUtils.substringBeforeFirst(path,
					AutomationReferences.
						JSON_PATH_SEPARATOR);

			if (!json.has(key)) {
				json.add(key, new JsonObject());
			}

			JsonObject modJson = setStringInnerJSONObject(
					json.get(key).getAsJsonObject(),
					StringUtils.substringAfterFirst(path,
							AutomationReferences.
								JSON_PATH_SEPARATOR),
							value);

			json.add(key, modJson);
			return json;
		}
	}

	/**
	 * @param json JSON object to be modified.
	 * @param path JSON path of the variable to be modified.
	 * @param value Integer value to be set.
	 * @return Modified JSON object.
	 */
	private static JsonObject setNumericInnerJSONObject(
			final JsonObject json, final String path,
			final Number value) {
		if (path.indexOf(AutomationReferences
				.JSON_PATH_SEPARATOR) == -1) {
			json.addProperty(path, value);
			return json;
		}
		else {
			String key = StringUtils.substringBeforeFirst(path,
					AutomationReferences.
						JSON_PATH_SEPARATOR);

			if (!json.has(key)) {
				json.add(key, new JsonObject());
			}

			JsonObject modJson = setNumericInnerJSONObject(
					json.get(key).getAsJsonObject(),
					StringUtils.substringAfterFirst(path,
							AutomationReferences.
								JSON_PATH_SEPARATOR),
							value);

			json.add(key, modJson);
			return json;
		}
	}

}
