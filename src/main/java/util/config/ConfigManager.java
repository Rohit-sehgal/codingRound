package util.config;


import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;


public class ConfigManager {

    private static Properties commonProp = new Properties();
    public static Boolean ArePropertiesSet = new Boolean(false);
    public static Boolean ArePropertiesUpdated = new Boolean(false);

    public static void setProperties() {
        try {
            commonProp = new Properties();
            FileReader reader;
            String currDir  = System.getProperty("user.dir");
            reader = new FileReader(currDir+"/src/main/resources/"+"config.properties");
            commonProp.load(reader);
            ArePropertiesSet = true;
            ArePropertiesUpdated = false;
        } catch (IOException e) {
            e.printStackTrace();
            ArePropertiesSet = false;
            ArePropertiesUpdated = false;
        }
    }

    public static Properties getProperties() {
        return commonProp;
    }
}
