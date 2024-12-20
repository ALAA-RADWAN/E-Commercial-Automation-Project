package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Pages.P04_CheckoutPage;
import Utilities.DataUtil;
import Utilities.LogsUtil;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.*;
import com.github.javafaker.Faker;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC04_CheckoutTest{
    private final String USERNAME = DataUtil.getJsonData("validLogin","Username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin","Password");
    private final String FIRSTNAME = DataUtil.getJsonData("information","fName") + "-" + Utility.getTimeStamp();
    private final String LASTNAME = DataUtil.getJsonData("information","lName") + "-" + Utility.getTimeStamp();
    private final String ZIPCODE = new Faker().number().digits(5);
    @BeforeMethod
    public void setUp() throws IOException {
        setUpDriver(getPropertyValue("enviroment", "Browser"));
        LogsUtil.info("Browser is opened");
        getDriver().get(getPropertyValue("enviroment", "Base_URL"));
        LogsUtil.info("we are in the login page now");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }
    @Test
    public void checkoutStepOneTC() throws IOException {
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
        Assert.assertTrue(Utility.verifyURL(getDriver(),getPropertyValue("enviroment", "Checkout_URL")));
    }
    @AfterMethod
    public void quit(){
        quitDriver();
    }
}
