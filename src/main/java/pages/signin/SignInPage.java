package pages.signin;

import com.sun.javafx.PlatformUtil;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.base.BasePage;
import util.system.Util;

import java.io.File;

public class SignInPage extends BasePage {

    @FindBy(linkText = "Your trips")
    private WebElement yourTrips;

    @FindBy(id = "SignIn")
    private WebElement signIn;

    @FindBy(id = "signInButton")
    private WebElement signInButton;

    @FindBy(id = "errors1")
    private WebElement errors1;

    SignInPage signInPage;

    public String addSignInDetailsAndGetMissingErrorText() {
        signInPage = (SignInPage) openPage(SignInPage.class);
        signInPage.yourTrips.click();
        signInPage.signIn.click();
        Util.getInstance().waitForPageToLoad(ExpectedConditions.frameToBeAvailableAndSwitchToIt("modal_window"), 10);
        Util.getInstance().waitForPageToLoad(ExpectedConditions.visibilityOf(signInPage.signInButton), 10);
        signInPage.signInButton.click();
        return signInPage.errors1.getText();
    }

    @Override
    public ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOf(yourTrips);
    }
}
