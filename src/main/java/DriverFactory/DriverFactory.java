package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver>driverThreadLocal= new ThreadLocal<>();

     public static void setUpDriver(String browser){
         switch (browser.toLowerCase()){
             case "chrome":
                 ChromeOptions chromeOptions = new ChromeOptions();
                 chromeOptions.addArguments("--start-maximized");
                 driverThreadLocal.set(new ChromeDriver(chromeOptions));
                 break;
             default:
                 EdgeOptions edgeOptions = new EdgeOptions();
                 edgeOptions.addArguments("--start-maximized");
                 driverThreadLocal.set(new EdgeDriver(edgeOptions));
         }
     }
     public static WebDriver getDriver(){
          return driverThreadLocal.get();
     }
     public static void quitDriver(){
         getDriver().quit();
         driverThreadLocal.remove();
     }
}
