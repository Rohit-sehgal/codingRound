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
    public static void updateProperties() {
        try {
            if (!ArePropertiesSet) {
                setProperties();
            }
            String releaseVersion = "";
            String env = "";
            String browser = "";
            String runningEnv = "";


            try {
                runningEnv = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                System.out.println("Failed to get host Name:" + e.getLocalizedMessage());
            }

            if (System.getenv("env") != null && !System.getenv("env").isEmpty()) {
                env = System.getenv("env");
            } else {
                env = commonProp.getProperty("env");
            }
            if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
                browser = System.getenv("browser");
            } else {
                browser = commonProp.getProperty("browser");
            }

            switch (env.trim().toLowerCase()) {
                case "prod":
                case "pre-prod":
                    commonProp.setProperty("environment", "Prod");
                    commonProp.setProperty("userID", commonProp.getProperty("userID_prod"));
                    commonProp.setProperty("url", commonProp.getProperty("pre_url"));
                    break;

                case "qa":
                    commonProp.setProperty("environment", "QA");
                    commonProp.setProperty("url", commonProp.getProperty("qa_url"));
                    commonProp.setProperty("user", commonProp.getProperty("user"));
                    break;
                case "dev":
                    commonProp.setProperty("environment", "DEV");
                    commonProp.setProperty("url", commonProp.getProperty("dev_url"));
//                    commonProp.setProperty("user", commonProp.getProperty("user"));

                default:
                    break;
            }
            commonProp.setProperty("version", releaseVersion);
            commonProp.setProperty("browser",browser);
            ArePropertiesUpdated = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred in ConfigManager's UpdateProperties Method");
        }
    }
}
