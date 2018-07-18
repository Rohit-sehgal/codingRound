package pages.hotelbooking;

import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.base.BaseTest;

import java.io.File;

public class HotelBookingTest extends BaseTest {


    @Test
    public void shouldBeAbleToSearchForHotels() {
        HotelBookingPage hotelBookingPage = new HotelBookingPage().load();
        hotelBookingPage.searchForHotels();

    }

}
