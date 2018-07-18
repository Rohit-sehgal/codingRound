package pages.flightbooking;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pages.base.BasePage;
import util.config.DriverManager;
import util.system.Util;

import java.util.List;

public class FlightBookingPage extends BasePage{

    @FindBy(id = "OneWay")
    private WebElement oneWay;

    @FindBy(id = "FromTag")
    private WebElement fromTag;

    @FindBy(id = "ToTag")
    private WebElement toTag;

    @FindBy(xpath = "//*[@id='ui-datepicker-div']/div[1]/table/tbody/tr[5]/td[7]/a")
    private WebElement calendarDate;

    @FindBy(id = "SearchBtn")
    private WebElement searchBtn;

    @FindBy(className = "searchSummary")
    private WebElement searchSummary;

    public FlightBookingPage load() {
        return (FlightBookingPage) openPage(FlightBookingPage.class);
    }


    public void fillDataForAOneWayJourney() {
        oneWay.click();
        fromTag.clear();
        fromTag.sendKeys("Bangalore");
        Util.getInstance().waitForPageToLoad(ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.id("ui-id-1"))),10);
        List<WebElement> originOptions = DriverManager.getDriver().findElement(By.id("ui-id-1")).findElements(By.tagName("li"));
        originOptions.get(0).click();
        toTag.clear();
        toTag.sendKeys("Delhi");
        Util.getInstance().waitForPageToLoad(ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.id("ui-id-2"))),10);
        //select the first item from the destination auto complete list
        List<WebElement> destinationOptions = DriverManager.getDriver().findElement(By.id("ui-id-2")).findElements(By.tagName("li"));
        destinationOptions.get(0).click();
        calendarDate.click();
        //all fields filled in. Now click on search
        searchBtn.click();
        Util.getInstance().waitForPageToLoad(ExpectedConditions.visibilityOf(searchSummary),10);
        Assert.assertTrue(isElementPresent(By.className("searchSummary")));
    }

    private boolean isElementPresent(By by) {
        try {
            DriverManager.getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOf(oneWay);
    }
}
