package fk.core.stub.impl;

import fk.core.stub.IStubService;
import fk.utilities.CommandPromptUtility;
import org.apache.log4j.Logger;

/**
 * Created by sankeerth.reddy on 12/12/14.
 * Invokes the major stub service which can handle all the mock services underneath
 */
public class StubService implements IStubService {
    /**
     * Root logger instance.
     */
    private static Logger LOGGER = Logger.getLogger(StubService.class);

    /**
     * Place holder for process initiated by invokeStubService method
     */
    private Process process;

    /**
     * invokes the StubService
     *
     * @param stubJarFilePath file path of the stubby jar file
     * @param yamlFilePath    file path of the yaml file which is the config for stubby jar
     */
    public void startStubService(String stubJarFilePath, String yamlFilePath) {
        CommandPromptUtility cmdUtil = new CommandPromptUtility();
        String command = "java -jar " + stubJarFilePath + " -d " + yamlFilePath;
        process = cmdUtil.startProcess(command);
        if (process != null) {
            LOGGER.info("Mock service started!");
        } else
            System.exit(1);
    }

    /**
     * Kills the stub service which is started
     */
    public void stopStubService() {
        CommandPromptUtility cmdUtil = new CommandPromptUtility();
        cmdUtil.killProcess(process);
        LOGGER.info("Mock service stopped");
    }
}
