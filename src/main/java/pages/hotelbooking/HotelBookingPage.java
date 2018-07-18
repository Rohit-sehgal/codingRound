package pages.hotelbooking;

import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.base.BasePage;

import java.io.File;

public class HotelBookingPage extends BasePage {

    @FindBy(linkText = "Hotels")
    private WebElement hotelLink;

    @FindBy(id = "Tags")
    private WebElement localityTextBox;

    @FindBy(id = "SearchHotelsButton")
    private WebElement searchButton;

    @FindBy(id = "travellersOnhome")
    private WebElement travellerSelection;

    HotelBookingPage hotelBookingPage;

    public HotelBookingPage load() {
        return (HotelBookingPage) openPage(HotelBookingPage.class);
    }

    public void searchForHotels() {
        hotelLink.click();
        localityTextBox.sendKeys("Indiranagar, Bangalore");
        new Select(travellerSelection).selectByVisibleText("1 room, 2 adults");
        travellerSelection.sendKeys(Keys.ESCAPE);
        searchButton.click();
    }

    @Override
    public ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOf(hotelLink);
    }
}
