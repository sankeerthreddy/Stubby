package fk.core.provider;

import java.util.Set;

/**
 * Interface for a data provider, implementation will vary depending
 * on the kind of data required.
 * @author sankeerth.reddy
 */
public interface IDataProvider {

	/**
	 * @return Generated data as a string.
	 */
	String getData();

	/**
	 * Resets any context maintained in the provider.
	 */
	void resetContext();

	/**
	 * @return Applicable only when the data provider will provide
	 * from a fixed pool of values. Returns a set of all the possible
	 * values.
	 */
	Set<String> getPossibleValues();

}
