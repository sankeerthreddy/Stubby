package fk.core.provider.impl;

import fk.core.provider.IDataProvider;

/**
 * Created by sankeerth.reddy on 07/01/15.
 */
public class TestProvider {
    public static void main(String[] args) {
        IDataProvider dp = new RandomStringProvider("7");
        System.out.println(dp.getData());


    }
}
