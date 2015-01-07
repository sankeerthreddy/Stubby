package fk.core.provider.impl;

import fk.core.provider.IDataProvider;

import java.util.*;

/**
 * Maintains an array of values and successively returns
 * increasingly indexed values in a looped manner.
 * @author sankeerth.reddy
 */
public final class ArrayProvider implements IDataProvider {

	/**
	 * Pool of data to provide from.
	 */
	private final List<String> pool;

	/**
	 * Index of data to retrieve.
	 */
	private int index;

	/**
	 * @param expression Space separated values.
	 */
	public ArrayProvider(final String expression) {
		index = -1;
		pool = new LinkedList<String>(Arrays.asList(
				expression.trim().split("[,][ ]")));
	}

	@Override
	public String getData() {
		index++;
		index = index % pool.size();
		return pool.get(index);
	}

	@Override
	public void resetContext() {
		index = -1;
	}

	@Override
	public Set<String> getPossibleValues() {
		return new HashSet<String>(pool);
	}

}
