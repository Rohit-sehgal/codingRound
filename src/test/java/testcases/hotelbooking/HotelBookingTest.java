package testcases.hotelbooking;

import org.testng.annotations.Test;
import pages.hotelbooking.HotelBookingPage;
import testcases.base.BaseTest;

public class HotelBookingTest extends BaseTest {


    @Test
    public void shouldBeAbleToSearchForHotels() {
        HotelBookingPage hotelBookingPage = new HotelBookingPage().load();
        hotelBookingPage.searchForHotels();

    }

}
