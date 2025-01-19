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
public class TC01_LoginTest extends DriverSetUp{
    private final String USERNAME = DataUtil.getJsonData("validLogin","Username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin","Password");
    @Test
    public void validLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLogin(getPropertyValue("enviroment", "Home_URL")));
    }
}
