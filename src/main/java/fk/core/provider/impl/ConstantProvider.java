/**
 * 
 */
package fk.core.provider.impl;

import fk.core.provider.IDataProvider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A constant data provider which will always
 * provide the same data.
 * @author sankeerth.reddy
 */
public final class ConstantProvider implements IDataProvider {

	/**
	 * Constant data to provide.
	 */
	private final String data;

	/**
	 * @param constant Constant data to provide.
	 */
	public ConstantProvider(final String constant) {
		data = constant;
	}

	@Override
	public String getData() {
		return data;
	}

	@Override
	public void resetContext() {
		// No context maintained.
	}

	@Override
	public Set<String> getPossibleValues() {
		return new HashSet<String>(Arrays.asList(data));
	}

}
