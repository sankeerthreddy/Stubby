package fk.core;

import fk.core.network.IHTTPCallActions;
import fk.core.network.IHTTPResponse;
import fk.core.network.impl.HTTPFactory;
import fk.core.proxy.CoreProxy;
import fk.core.stub.IStubService;
import fk.core.stub.impl.StubService;
import fk.references.ConfigReferences;
import fk.references.HTTPReferences;
import fk.utilities.CommandPromptUtility;
import fk.utilities.PropertyFileUtility;
import org.apache.log4j.Logger;

/**
 * Created by sankeerth.reddy on 12/12/14.
 */
public class Test {
    public static void main(String[] args) {
        Logger LOGGER = Logger.getLogger(CommandPromptUtility.class);
        PropertyFileUtility propUtil = new PropertyFileUtility(ConfigReferences.CONFIG_FILE);
        IStubService stubService = new StubService();
        stubService.startStubService(propUtil.getStringValue(ConfigReferences.STUB_FILE_PATH), propUtil.getStringValue(ConfigReferences.YAML_FILE_PATH));
        CoreProxy cp = new CoreProxy();
        IHTTPResponse response = cp.makeProxyGetCall(HTTPReferences.sampleURL);
        System.out.println(response.getRawResponse());

        //stubService.stopStubService();
    }
}
