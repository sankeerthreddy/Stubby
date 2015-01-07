package fk.utilities;

import com.google.gson.*;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Wrapper on GSON parser.
 * @author sankeerth.reddy
 */
public final class JsonParser {

	/**
	 * Logger instance.
	 */
	private static Logger LOGGER =
			Logger.getLogger(JsonParser.class);

	/**
	 * GSON instance.
	 */
	private Gson gson;

	/**
	 * Private JSON Parser constructor.
	 */
	private JsonParser() {
		this.gson = new GsonBuilder().create();
	}

	/**
	 * @param in JSON string input.
	 * @return JSON element which can be converted to
	 * either a JSON object or an array depending on the
	 * input.
	 */
	public JsonElement parseJson(final String in) {
		try {
			return this.gson.fromJson(in, JsonElement.class);
		} catch (RuntimeException e) {
			LOGGER.error("Unable to parse json: " + in, e);
			return null;
		}
	}

	/**
	 * @param in JSON object input string.
	 * @return Parsed JSON object.
	 */
	public JsonObject parseObject(final String in) {
		JsonElement element = parseJson(in);
		if (element == null) {
			return null;
		}

		if (element.isJsonObject()) {
			return element.getAsJsonObject();
		}

		LOGGER.warn("Input string is not a JSON object."
				+ " Returning null.\n" + in);
		return null;
	}

	/**
	 * @param in JSON array input string.
	 * @return Parsed JSON array.
	 */
	public JsonArray parseArray(final String in) {
		JsonElement element = parseJson(in);
		if (element.isJsonArray()) {
			return element.getAsJsonArray();
		}

		LOGGER.warn("Input string is not a JSON array."
				+ "Returning null.\n" + in);
		return null;
	}

	/**
	 * @param map Map to be converted into a JSON.
	 * @param <K> Type of keys.
	 * @param <V> Type of values.
	 * @return Converted JSON object.
	 */
	public <K, V> JsonObject parseMap(final Map<K, V> map) {
		JsonElement element = this.gson.toJsonTree(map);
		if (element == null) {
			return null;
		}

		if (element.isJsonObject()) {
			return element.getAsJsonObject();
		}

		LOGGER.warn("Could not convert map into JSON."
				+ "Returning null.\n" + map);
		return null;
	}

	/**
	 * @param json JSON element.
	 * @param classOfT type of object.
	 * @param <T> Type of target class.
	 * @return Instance of the specified type.
	 */
	public <T> T fromJson(final JsonElement json, final Class<T> classOfT) {
		return this.gson.fromJson(json, classOfT);
	}

	/**
	 * @param json JSON element.
	 * @param typeOfT type of object.
	 * @param <T> Type of target class.
	 * @return Instance of the specified type.
	 */
	public <T> T fromJson(final JsonElement json, final Type typeOfT) {
		return this.gson.fromJson(json, typeOfT);
	}

	/**
	 * @param json JSON element.
	 * @param classOfT type of object.
	 * @param <T> Type of target class.
	 * @return Instance of the specified type.
	 */
	public <T> T fromJson(final String json, final Class<T> classOfT) {
		return this.gson.fromJson(json, classOfT);
	}

	/**
	 * @param json JSON element.
	 * @param typeOfT type of object.
	 * @param <T> Type of target class.
	 * @return Instance of the specified type.
	 */
	public <T> T fromJson(final String json, final Type typeOfT) {
		return this.gson.fromJson(json, typeOfT);
	}

	/**
	 * @param jsonElement JSON element.
	 * @return JSON string of the object.
	 */
	public String toJson(final JsonElement jsonElement) {
		return this.gson.toJson(jsonElement);
	}

	/**
	 * @param src Instance of any type.
	 * @return JSON string of the object.
	 */
	public String toJson(final Object src) {
		return this.gson.toJson(src);
	}

	/**
	 * @param src Instance of any type.
	 * @param type Type of the object.
	 * @return JSON string of the object.
	 */
	public String toJson(final Object src, final Type type) {
		String str = this.gson.toJson(src, type);
		while (str.startsWith("\"") && str.endsWith("\"")) {
			str = str.substring(1, str.length() - 1);
		}

		return str;
	}

	/**
	 * @return Instance of JSON Parser.
	 */
	public static JsonParser getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * Holder class for GSON and adapter instances which will contain
	 * it in a thread safe manner.
	 * @author sankeerth.reddy
	 */
	private static final class Holder {

		/**
		 * Instance of JSON parser.
		 */
		private static final JsonParser INSTANCE = new JsonParser();

	}

}
