package Tests;

import Pages.*;
import Utilities.DataUtil;
import Utilities.LogsUtil;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC05_OverviewTest extends DriverSetUp{
    private final String USERNAME = DataUtil.getJsonData("validLogin","Username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin","Password");
    private final String FIRSTNAME = DataUtil.getJsonData("information","fName") + "-" + Utility.getTimeStamp();
    private final String LASTNAME = DataUtil.getJsonData("information","lName") + "-" + Utility.getTimeStamp();
    private final String ZIPCODE = new Faker().number().digits(5);

    @Test
    public void checkoutStepTwoTC() throws IOException {
        //ToDo: Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        //TODO: Adding items Steps
        new P02_HomePage(getDriver())
                .addRandomProducts(2,6)
                .clickOnCartIcon();
        //TODO: Go to checkout Steps
        new P03_CartPage(getDriver()).clickOnCheckoutButton();
        //TODO: Filling Information Step
        new P04_CheckoutPage(getDriver()).fillingInformationForm(FIRSTNAME,LASTNAME,ZIPCODE).clickOnContinueButton();
        LogsUtil.info(FIRSTNAME + " " + LASTNAME + " " + ZIPCODE);
        Assert.assertTrue(new P05_OverviewPage(getDriver()).comparingTotalPrices());
    }
}
