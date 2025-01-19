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
public class TC02_HomeTest extends DriverSetUp{

    private final String USERNAME = DataUtil.getJsonData("validLogin","Username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin","Password");

        @Test
        public void checkNumberOfSelectedProductsTC(){
            //ToDo: Login Steps
            new P01_LoginPage(getDriver())
                    .enterUsername(USERNAME)
                    .enterPassword(PASSWORD)
                    .clickLoginButton();
            //TODO: Adding items Steps
            new P02_HomePage(getDriver()).addAllProductsToCart();
            Assert.assertTrue(new P02_HomePage(getDriver()).comparingNumberOfSelectedProductsWithCart());
        }
        @Test
        public void addingRandomProductsTC(){
            //ToDo: Login Steps
            new P01_LoginPage(getDriver())
                    .enterUsername(USERNAME)
                    .enterPassword(PASSWORD)
                    .clickLoginButton();
            //TODO: Adding items Steps
            new P02_HomePage(getDriver()).addRandomProducts(3,6);
            Assert.assertTrue(new P02_HomePage(getDriver()).comparingNumberOfSelectedProductsWithCart());
        }
    @Test
    public void clickOnCartIconTC() throws IOException {
        //ToDo: Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        new P02_HomePage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(Utility.verifyURL(getDriver(),getPropertyValue("enviroment","Cart_URL")));
    }

}

