package testcases.flightbooking;

import org.testng.annotations.Test;
import pages.flightbooking.FlightBookingPage;
import testcases.base.BaseTest;

public class FlightBookingTest extends BaseTest {


    @Test
    public void testThatResultsAppearForAOneWayJourney() {

        FlightBookingPage flightBookingPage = new FlightBookingPage().load();
        flightBookingPage.fillDataForAOneWayJourney();

    }


}
