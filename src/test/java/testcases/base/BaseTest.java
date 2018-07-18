package testcases.base;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.*;
import util.config.ConfigManager;
import util.config.DriverFactory;
import util.config.DriverManager;
import util.system.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by USER on 7/18/2018.
 */
public class BaseTest {

    @BeforeSuite
    public void initializeFramework() {
        configureLogging();
        ConfigManager.setProperties();
        ConfigManager.updateProperties();

    }

    public void configureLogging() {
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "src/main/resources" + File.separator +
                "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
    }

    @AfterSuite
    public void destroyFramework() {
        DriverFactory.destroyDriverInstance(DriverManager.getDriver());
        try {
            SystemUtils.openReportInChrome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void setUpDriver() {
        WebDriver webDriver = DriverManager.getDriver();
        if (webDriver == null) {
            DriverFactory.createInstance(ConfigManager.getProperties().getProperty("browser"));
            try {
                openBaseUrl();
            } catch (WebDriverException e) {
                dismissIfAlertAppear();
                openBaseUrl();
            }
        } else {
//            DriverManager.getDriver().manage().deleteAllCookies();
            DriverManager.getDriver().get(ConfigManager.getProperties().getProperty("url"));
//            DriverManager.getDriver().manage().deleteAllCookies();
        }
    }

    private void openBaseUrl() {
        DriverManager.getDriver().get(String.valueOf(ConfigManager.getProperties().getProperty("url")));
    }

    private void dismissIfAlertAppear() {
        DriverManager.getDriver().switchTo().alert().dismiss();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {

        if (DriverManager.getDriver() == null) {
            setUpDriver();
        }
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
            DriverManager.getDriver().navigate().refresh();
        try {
            if (result.getStatus() == ITestResult.SUCCESS) {
                //Do your excel writing stuff here
            } else if (result.getStatus() == ITestResult.FAILURE) {

                SystemUtils.addScreenshotToReport();
                System.out.println("Log Message:: @AfterMethod: Method-" + result.getMethod().getMethodName() + "- has Failed");
                //Do your excel writing stuff here
                destroyFramework();
            } else if (result.getStatus() == ITestResult.SKIP) {
                System.out.println("Log Message::@AfterMethod: Method-" + result.getMethod().getMethodName() + "- has Skipped");
            }
        } catch (Exception e) {
            System.out.println("\nLog Message::@AfterMethod: Exception caught");
            e.printStackTrace();
        } finally {

        }
    }


}
