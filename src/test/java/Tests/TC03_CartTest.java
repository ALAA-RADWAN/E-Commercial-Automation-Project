package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Utilities.DataUtil;
import Utilities.LogsUtil;
import org.testng.Assert;
import org.testng.annotations.*;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC03_CartTest {
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
    public void comparingPricesTC() {
       String totalPrice =  new P01_LoginPage(getDriver())
                .enterUsername(DataUtil.getJsonData("validLogin","Username"))
                .enterPassword(DataUtil.getJsonData("validLogin","Password"))
                .clickLoginButton()
                .addRandomProducts(2,6)
                .getTotalPricesOfSelectedProducts();
        new P02_HomePage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrices(totalPrice));
    }
    @AfterMethod
    public void quit(){
        quitDriver();
    }
}
