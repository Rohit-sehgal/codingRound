package pages.signin;

import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.base.BaseTest;

import java.io.File;

public class SignInTest extends BaseTest {


    @Test
    public void shouldThrowAnErrorIfSignInDetailsAreMissing() throws ClassNotFoundException {
        SignInPage signInPage = new SignInPage();
        String actualMsg = signInPage.addSignInDetailsAndGetMissingErrorText();
        Assert.assertTrue(actualMsg.contains("There were errors in your submission"));
    }


}
