package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Utilities.DataUtil;
import Utilities.LogsUtil;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.*;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_HomeTest {
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
        public void checkNumberOfSelectedProductsTC(){
            new P01_LoginPage(getDriver())
                    .enterUsername(DataUtil.getJsonData("validLogin","Username"))
                    .enterPassword(DataUtil.getJsonData("validLogin","Password"))
                    .clickLoginButton()
                    .addAllProductsToCart();
            Assert.assertTrue(new P02_HomePage(getDriver()).comparingNumberOfSelectedProductsWithCart());
        }
        @Test
        public void addingRandomProductsTC(){
            new P01_LoginPage(getDriver())
                    .enterUsername(DataUtil.getJsonData("validLogin","Username"))
                    .enterPassword(DataUtil.getJsonData("validLogin","Password"))
                    .clickLoginButton()
                    .addRandomProducts(3,6);
            Assert.assertTrue(new P02_HomePage(getDriver()).comparingNumberOfSelectedProductsWithCart());
        }
    @Test
    public void clickOnCartIconTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtil.getJsonData("validLogin","Username"))
                .enterPassword(DataUtil.getJsonData("validLogin","Password"))
                .clickLoginButton()
                .clickOnCartIcon();
        Assert.assertTrue(Utility.verifyURL(getDriver(),getPropertyValue("enviroment","Cart_URL")));
    }
        @AfterMethod
        public void quit(){
            quitDriver();
        }
}

