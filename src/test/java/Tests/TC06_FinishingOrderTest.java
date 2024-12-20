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

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC06_FinishingOrderTest {
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
    public void finishOrderTC() {
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
        //TODO: Go to Finish Order Page Step
        new P05_OverviewPage(getDriver()).clickOnFinishButton();
        Assert.assertTrue(new P06_FinishingOrderPage(getDriver()).checkMessageVisibility());
    }
    @AfterMethod
    public void quit(){
        quitDriver();
    }
}
