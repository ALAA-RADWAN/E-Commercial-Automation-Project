package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_LoginPage {
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");

    private final WebDriver driver;

    public P01_LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public P01_LoginPage enterUsername(String userNameText){
        Utility.sendData(driver,username,userNameText);
        return this;
    }
    public P01_LoginPage enterPassword(String passwordText){
        Utility.sendData(driver,password,passwordText);
        return this;
    }
    public P02_HomePage clickLoginButton(){
        Utility.clickOnElement(driver,loginButton);
        return new P02_HomePage(driver);
    }
    public boolean assertLogin(String expectedvalue){
     return driver.getCurrentUrl().equals(expectedvalue);
    }
}
