package fk.utilities;

import com.google.gson.JsonObject;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.*;

/**
 * Provides static methods to find all possible combinations
 *  of keys and their possible values.
 * @author Sankeerth.reddy
 */
public class CombinationGenerator {

	/**
	 * @param parameters Map of request keys and set of possible values.
	 * @return Set of all request JSONs encompassing all possible
	 * combinations of all sizes.
	 */
	public static Set<JsonObject> getAllParameterStrings(
			final Map<String, Set<String>> parameters) {
		Set<Set<String>> keyCombinations =
				getKeyCombinations(parameters.keySet());
		Set<Map<String, String>> combinations =
				getCombinationSet(parameters, keyCombinations);

		Set<JsonObject> queries = new HashSet<JsonObject>();
		Iterator<Map<String, String>> iter = combinations.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();
			JsonObject json = convertToJSON(currMap);
			queries.add(json);
		}

		return queries;
	}

	/**
	 * @param parameters Map of request keys and set of possible values.
	 * @param combination Set of request strings for which combinations
	 * of values will be returned.
	 * @return Set of all request JSONs encompassing all possible
	 * combinations of values belonging to the specified keys.
	 */
	public static Set<JsonObject> getParameterStringsByCombination(
			final Map<String, Set<String>> parameters,
			final Set<String> combination) {
		Set<Set<String>> keyCombinations = new HashSet<Set<String>>(
				Arrays.asList(combination));
		Set<Map<String, String>> combinations =
				getCombinationSet(parameters, keyCombinations);

		Set<JsonObject> queries = new HashSet<JsonObject>();
		Iterator<Map<String, String>> iter = combinations.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();
			JsonObject json = convertToJSON(currMap);
			queries.add(json);
		}

		return queries;
	}

	/**
	 * @param parameters Map of request keys to possible values.
	 * @return Set of all request JSONs having all the keys and
	 * all possible combinations of their values.
	 */
	public static Set<JsonObject> getFullLengthParameterStrings(
			final Map<String, Set<String>> parameters) {
		Set<Set<String>> keyCombinations = new HashSet<Set<String>>(
				Arrays.asList(parameters.keySet()));
		Set<Map<String, String>> combinations =
				getCombinationSet(parameters, keyCombinations);

		Set<JsonObject> queries = new HashSet<JsonObject>();
		Iterator<Map<String, String>> iter = combinations.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();
			JsonObject json = convertToJSON(currMap);
			queries.add(json);
		}

		return queries;
	}

	/**
	 * @param parameters Map of request keys to possible values.
	 * @return Set of combinations. Each combination is a map of name of
	 * variable to its value.
	 */
	public static Set<Map<String, String>> getFullLengthCombinations(
			final Map<String, Set<String>> parameters) {
		Set<Set<String>> keyCombinations = new HashSet<Set<String>>(Arrays.asList(
				parameters.keySet()));
		return getCombinationSet(parameters, keyCombinations);
	}

	/**
	 * @param parameters Map of request keys and their possible values.
	 * @param keyCombinations Set of key combinations for which the
	 * combinations of values will be formed.
	 * @return Set of all possible combinations.
	 */
	private static Set<Map<String, String>> getCombinationSet(
			final Map<String, Set<String>> parameters,
			final Set<Set<String>> keyCombinations) {
		Set<Map<String, String>> allCombinations = new HashSet<Map<String, String>>();

		Iterator<Set<String>> iter1 = keyCombinations.iterator();
		while (iter1.hasNext()) {
			Set<String> combination = iter1.next();
			allCombinations.addAll(
					getFixedCombinationSet(parameters,
							combination)
						);
		}

		return allCombinations;
	}

	/**
	 * @param keys Set of keys.
	 * @return All possible combinations of input keys in all sizes.
	 */
	public static Set<Set<String>> getKeyCombinations(
			final Set<String> keys) {
		Set<Set<String>> keyCombinations = new HashSet<Set<String>>();
		ICombinatoricsVector<String> initialVector =
				Factory.createVector(keys);

		for (int i = 1; i <= keys.size(); i++) {
			Generator<String> generator = Factory
					.createSimpleCombinationGenerator(
							initialVector, i);
			for (ICombinatoricsVector<String> combination
					: generator) {
				keyCombinations.add(new HashSet<String>(
						combination.getVector()));
			}
		}

		return keyCombinations;
	}

	/**
	 * @param parameters Map of keys and their possible values.
	 * @param combination Combination set for which the
	 * combinations of values will be formed.
	 * @return Set of all combinations of values for the
	 * input key combination.
	 */
	private static Set<Map<String, String>> getFixedCombinationSet(
			final Map<String, Set<String>> parameters,
			final Set<String> combination) {
		Set<Map<String, String>> allCombinations = new HashSet<Map<String, String>>();

		Set<Map<String, String>> currCombinations = new HashSet<Map<String, String>>();
		Iterator<String> iter2 = combination.iterator();
		while (iter2.hasNext()) {
			String key = iter2.next();
			currCombinations = combineMaps(key,
					parameters.get(key), currCombinations);
		}

		allCombinations.addAll(currCombinations);
		return allCombinations;
	}

	/**
	 * @param key Key to be added.
	 * @param values Possible values of the specified key.
	 * @param combination Incremental map of all the keys and values.
	 * @return Updated map with the new key and its values included
	 * and combinations.
	 */
	private static Set<Map<String, String>> combineMaps(
			final String key, final Set<String> values,
			final Set<Map<String, String>> combination) {

		if (combination.size() == 0) {
			Iterator<String> iter = values.iterator();
			while (iter.hasNext()) {
				String currValue = iter.next();
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put(key, currValue);
				combination.add(map1);
			}
			return combination;
		}

		Set<Map<String, String>> tempSet = new HashSet<Map<String, String>>();
		Iterator<Map<String, String>> iter = combination.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();

			Iterator<String> iter2 = values.iterator();
			while (iter2.hasNext()) {
				String currValue = iter2.next();
				Map<String, String> map1 = new HashMap<String, String>();
				map1.putAll(currMap);
				map1.put(key, currValue);
				tempSet.add(map1);
			}
		}

		return tempSet;
	}

	/**
	 * @param currMap Input map to be converted.
	 * @return JSON object equivalent of the input map.
	 */
	private static JsonObject convertToJSON(
			final Map<String, String> currMap) {
		JsonParser jp = JsonParser.getInstance();
		return jp.<String, String>parseMap(currMap);
	}
}
