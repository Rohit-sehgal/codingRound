package testcases.signin;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.signin.SignInPage;
import testcases.base.BaseTest;

public class SignInTest extends BaseTest {


    @Test
    public void shouldThrowAnErrorIfSignInDetailsAreMissing() throws ClassNotFoundException {
        SignInPage signInPage = new SignInPage();
        String actualMsg = signInPage.addSignInDetailsAndGetMissingErrorText();
        Assert.assertTrue(actualMsg.contains("There were errors in your submission"));
    }


}
