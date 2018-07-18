package pages.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import util.config.DriverManager;
import util.system.SystemUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 7/18/2018.
 */
public abstract class BasePage {
    private static WebDriver driver;
    private long LOAD_TIMEOUT = 30;
    private long REFRESH_RATE = 2;
    static Logger logger = Logger.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverManager.getDriver();
    }

    public BasePage(long loadTimeout, long pollingRate) {
        this.driver = DriverManager.getDriver();
        this.LOAD_TIMEOUT = loadTimeout;
        this.REFRESH_RATE = pollingRate;
    }

    public abstract ExpectedCondition getPageLoadCondition();
    public Object openPage(Class clazz) {
        Object page = null;
        try {
            driver = DriverManager.getDriver();
            page = PageFactory.initElements(DriverManager.getDriver(), clazz);
            ExpectedCondition pageLoadCondition = ((BasePage) page).getPageLoadCondition();
            if(pageLoadCondition!=null) {
                waitForPageToLoad(pageLoadCondition);
            }else{
                logger.error(String.format("%s Page Loading condition is not defined",clazz.getSimpleName()));
                SystemUtils.addScreenshotToReport();
            }
        } catch (NoSuchElementException e) {
            String error_screenshot = System.getProperty("user.dir") + "/target/screenshots/" + clazz.getSimpleName() + "_error.png";
            SystemUtils.addScreenshotToReport();
            throw new NoSuchElementException(String.format("This is not the %s page", clazz.getSimpleName()));
        } catch (TimeoutException e) {
            SystemUtils.addScreenshotToReport();
            throw new TimeoutException(e);
        }
        return page;
    }
    public void waitForPageToLoad(ExpectedCondition pageLoadCondition) {
        Wait wait = new FluentWait(DriverManager.getDriver())
                .withTimeout(LOAD_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(REFRESH_RATE, TimeUnit.SECONDS);

        wait.until(pageLoadCondition);
    }


}
