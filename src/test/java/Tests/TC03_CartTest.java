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

public class TC03_CartTest extends DriverSetUp{
    private final String USERNAME = DataUtil.getJsonData("validLogin","Username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin","Password");
    @Test
    public void comparingPricesTC() {
        //TODO Login
       new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
       //TODO: Adding items Steps
        String totalPrice =  new P02_HomePage(getDriver())
                .addRandomProducts(2,6)
                .getTotalPricesOfSelectedProducts();
        new P02_HomePage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrices(totalPrice));
    }
}
