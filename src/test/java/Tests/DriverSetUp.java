package Tests;

import Utilities.LogsUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

public class DriverSetUp {

    @BeforeMethod
    public void setUp() throws IOException {
        setUpDriver(getPropertyValue("enviroment", "Browser"));
        LogsUtil.info("Browser is opened");
        getDriver().get(getPropertyValue("enviroment", "Base_URL"));
        LogsUtil.info("we are in the login page now");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void quit(){
        quitDriver();
    }
}
