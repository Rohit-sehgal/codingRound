package util.config;

import com.sun.javafx.PlatformUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import util.system.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by rohitsehgal on 18/7/18.
 */

public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);

    public static WebDriver createInstance(String browserName) {
        WebDriver driver = null;
        setDriverPath();
        if (browserName.equalsIgnoreCase("IE")) {
            driver = new InternetExplorerDriver();
        } else if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }
        DriverManager.setDriver(driver);
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
                driver.close();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


}
