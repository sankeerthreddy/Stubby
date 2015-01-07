package fk.core.provider.impl;

import fk.core.provider.IDataProvider;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;
import java.util.Set;

/**
 * Created by sankeerth.reddy on 07/01/15.
 */
public class RandomStringProvider implements IDataProvider {

    private final int length;

    public RandomStringProvider(String expression){
        length = Integer.parseInt(expression);
    }

    @Override
    public String getData() {
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }

    @Override
    public void resetContext() {

    }

    @Override
    public Set<String> getPossibleValues() {
        return null;
    }
}
