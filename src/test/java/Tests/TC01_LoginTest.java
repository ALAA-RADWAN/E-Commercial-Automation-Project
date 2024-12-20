package Tests;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import DriverFactory.DriverFactory;
import Pages.P01_LoginPage;
import Utilities.DataUtil;
import Utilities.LogsUtil;
import org.testng.Assert;
import org.testng.annotations.*;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

import java.io.IOException;
import java.time.Duration;


@Listeners({IInvokedMethodListenerClass.class,ITestResultListenerClass.class})
public class TC01_LoginTest {
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
    public void validLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtil.getJsonData("validLogin","Username"))
                .enterPassword(DataUtil.getJsonData("validLogin","Password"))
                .clickLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLogin(getPropertyValue("enviroment", "Home_URL")));
    }
    @AfterMethod
    public void quit(){
        quitDriver();
    }
}
