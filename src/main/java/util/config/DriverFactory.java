package util.config;

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
    private static String downloadFilepath;
    private static String gridURL;
    private static String chromeDriverExePath;

    public static void setService(ChromeDriverService service) {
        DriverFactory.service = service;
    }

    private static ChromeDriverService service;

    public static void setBrowserFileDownloadLocation(String path) {
        downloadFilepath = path;
    }

    public static void setGridURL(String url) {
        gridURL = url;
    }

    public static void setchromeDriverExePath(String path) {
        chromeDriverExePath = path;
    }

    public static WebDriver createInstance(String browserName) {
        WebDriver driver = null;
        try {
            if (browserName.toLowerCase().contains("firefox")) {
                driver = new FirefoxDriver();
                DriverManager.setDriver(driver);
                return driver;
            } else if (browserName.equalsIgnoreCase("phantomjs")) {
                try {
                    DesiredCapabilities cap = DesiredCapabilities.phantomjs();
                    driver = new RemoteWebDriver(new URL("http://localhost:4444"), cap);
                    driver.manage().window().maximize();
                    DriverManager.setDriver(driver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return driver;
            } else if (browserName.equalsIgnoreCase("IE")) {
                SystemUtils.killProcess("IEDriverServer.exe");
                SystemUtils.killProcess("iexplore.exe");
                String exePath = System.getProperty("user.dir") + File.separator + "driver\\IEDriverServer.exe";
                DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
                ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
                System.setProperty("webdriver.ie.driver", exePath);
                driver = new InternetExplorerDriver();
                DriverManager.setDriver(driver);
                return driver;
            } else if (browserName.equalsIgnoreCase("chrome")) {
                String chromedriver_path = System.getProperty("user.dir") + File.separator + "driver\\chromedriver.exe";
                System.setProperty("webdriver.chrome.driver", chromedriver_path);
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--explicitly-allowed-ports=95");
                options.setExperimentalOption("prefs", chromePrefs);

                DesiredCapabilities crcapabilities = DesiredCapabilities.chrome();
                crcapabilities.setCapability("chrome.binary", chromedriver_path);
                crcapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                crcapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                driver = new ChromeDriver(crcapabilities);
                DriverManager.setDriver(driver);
                return driver;
            } else if (browserName.equalsIgnoreCase("remote")) {
                if (downloadFilepath.equals("")) {
                    downloadFilepath = "/";
                }
                String chromedriver_path = chromeDriverExePath;
                System.setProperty("webdriver.chrome.driver", chromedriver_path);
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("download.default_directory", downloadFilepath);

                ChromeOptions options = new ChromeOptions();
                options.addArguments("test-type");
                options.addArguments(Arrays.asList("--start-maximized"));
                options.addArguments(Arrays.asList("--ssl-protocol=any"));
                options.addArguments(Arrays.asList("--ignore-ssl-errors=true"));
                options.addArguments(Arrays.asList("--disable-extensions"));
                options.addArguments(Arrays.asList("--ignore-certificate-errors"));
                options.setExperimentalOption("prefs", chromePrefs);

                DesiredCapabilities crcapabilities = DesiredCapabilities.chrome();
                crcapabilities.setCapability("chrome.binary", chromedriver_path);
                crcapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                crcapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                LOGGER.info("Hitting the RemoteWebdriver: " + gridURL);
                try {
                    RemoteWebDriver rwd = new RemoteWebDriver(new URL(gridURL), crcapabilities);
                    DriverManager.setDriver(rwd);

                } catch (WebDriverException e) {
                    e.printStackTrace();
                }
                return driver;
            } else if (browserName.equalsIgnoreCase("chromeservice")) {
                service = initiateChromeDriverService();
                setService(service);
                if (downloadFilepath.equals("")) {
                    downloadFilepath = "/";
                }
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("download.default_directory", downloadFilepath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("test-type");
                options.addArguments(Arrays.asList("--start-maximized"));
                options.addArguments(Arrays.asList("--ssl-protocol=any"));
                options.addArguments(Arrays.asList("--ignore-ssl-errors=true"));
                options.addArguments(Arrays.asList("--disable-extensions"));
                options.addArguments(Arrays.asList("--ignore-certificate-errors"));
                options.setExperimentalOption("prefs", chromePrefs);

                DesiredCapabilities crcapabilities = DesiredCapabilities.chrome();
//                crcapabilities.setCapability("chrome.binary", chromedriver_path);
                crcapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                crcapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                LOGGER.info("Starting the Local Chrome: ");
//                driver = new ChromeDriver(crcapabilities);
                RemoteWebDriver rwd = new RemoteWebDriver(new URL(service.getUrl().toString()), crcapabilities);
                DriverManager.setDriver(rwd);
                return rwd;

            }
        } catch (MalformedURLException e1) {
            LOGGER.error("Error", e1);
        } catch (Exception e1) {
            e1.printStackTrace();

        }
        return driver;
    }

    public static ChromeDriverService getService() throws IOException {
        if (DriverFactory.service == null) {
            initiateChromeDriverService();
        }

        return service;
    }

    public static void stopChromeDriverService() {
        if (service != null) {
            service.stop();
            LOGGER.info("Chrome Service Stopped");
        }
    }

    private static ChromeDriverService initiateChromeDriverService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromeDriverExePath))
                .usingAnyFreePort()
                .build();
        service.start();
        return service;

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
