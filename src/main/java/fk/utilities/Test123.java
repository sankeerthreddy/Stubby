package fk.utilities;

/**
 * Created by sankeerth.reddy on 05/01/15.
 */
public class Test123 {
    public static void main(String[] args) {
        String filePath = "config.properties";
        PropertyFileUtility util = new PropertyFileUtility(filePath);
        System.out.println(util.getStringValue("yamlFilePath"));
    }
}
