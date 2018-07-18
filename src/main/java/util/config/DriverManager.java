package util.config;

import com.sun.javafx.PlatformUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static WebDriver driver;
    private static final Logger LOGGER = Logger.getLogger(DriverManager.class);

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }


    private static WebDriver createInstance(String browserName) {
        setDriverPath();
        if (browserName.equalsIgnoreCase("IE")) {
            driver = new InternetExplorerDriver();
        } else if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }
        setDriver(driver);
        return driver;
    }


    private static void setDriverPath() {
        if (PlatformUtil.isMac()) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "driver\\mac\\" +"chromedriver");
        }
        if (PlatformUtil.isWindows()) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "driver\\win\\" + "chromedriver.exe");
    }
        if (PlatformUtil.isLinux()) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "driver\\linux\\" +"chromedriver_linux");
        }
    }

    public static void destroyDriverInstance(WebDriver driver) {
        try {
            if (driver != null) {

                driver.quit();
            }
        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
        }
    }
}