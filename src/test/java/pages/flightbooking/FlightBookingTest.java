package pages.flightbooking;

import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.base.BaseTest;

import java.io.File;
import java.util.List;

public class FlightBookingTest extends BaseTest {


    @Test
    public void testThatResultsAppearForAOneWayJourney() {
        FlightBookingPage flightBookingPage = new FlightBookingPage().load();
        flightBookingPage.fillDataForAOneWayJourney();

    }


}
