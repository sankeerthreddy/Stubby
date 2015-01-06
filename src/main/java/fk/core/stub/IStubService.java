package fk.core.stub;

/**
 * Created by sankeerth.reddy on 12/12/14.
 * Interface for StubService
 */
public interface IStubService {

    /**
     * invokes the central mock service
     * @param stubJarFilePath file path of the stubby jar file
     * @param yamlFilePath file path of the yaml file which is the config for stubby jar
     */
    public void startStubService(String stubJarFilePath, String yamlFilePath);

    /**
     * stops the stub service which is running
     */
    public void stopStubService();
}
