package fk.core.provider.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fk.core.provider.DataFactory;
import fk.core.provider.IDataProvider;
import fk.utilities.JsonParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This data provider will generate data depending on the data
 * provided by another provider.
 * @author sankeerth.reddy
 */
public class DependentProvider implements IDataProvider {

	/**
	 * Name of parent variable.
	 */
	private String parent;

	/**
	 * Mapping of parent provider data to this provider's data.
	 */
	private Map<String, String> map;

	/**
	 * Constructor.
	 * @param expression Will contain name of parent provider and
	 * mapping of data.
	 */
	public DependentProvider(final String expression) {
		JsonParser jp = JsonParser.getInstance();
		JsonObject expr = jp.parseObject(expression);

		if (!expr.has("parent") || !expr.has("map")) {
			throw new IllegalArgumentException(
					"Invalid expression provided.");
		}

		parent = expr.get("parent").getAsString();

		map = new HashMap<String, String>();
		JsonObject mapping = expr.get("map").getAsJsonObject();
		Iterator<Entry<String, JsonElement>> iter =
				mapping.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> entry = iter.next();
			map.put(entry.getKey(), entry.getValue().getAsString());
		}
	}

	@Override
	public final String getData() {
		String parentData = DataFactory.getLastGeneratedData(parent);
		if (parentData == null) {
			throw new IllegalStateException(
					"Parent provider hasn't generated"
					+ " any data yet.");
		}

		if (!map.containsKey(parentData)) {
			throw new IllegalArgumentException(
					"No mapping found for: " + parentData);
		}

		return map.get(parentData);
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
