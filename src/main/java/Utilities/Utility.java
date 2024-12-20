package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
    private static final String SCREENSHOT_PATH = "test-outputs/Screenshots/";
    //TODO click on element
    public static void clickOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();

    }
    //TODO send data to element
    public static void sendData(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
    }
    //TODO get data from element
    public static String getText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }
    //TODO General Waiting
    public static WebDriverWait generalWait(WebDriver driver){
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    //TODO By to Web Element
    public static WebElement findWebElement(WebDriver driver, By locator){
        return driver.findElement(locator);
    }
    //TODO Scrolling
    public static void scrolling(WebDriver driver, By locator){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",findWebElement(driver,locator));
    }
    //TODO Selecting from dropdown
    public static void selectingFromDropDown(WebDriver driver, By locator, String option){
        new Select(findWebElement(driver,locator)).selectByVisibleText(option);
    }
    //TODO Time stamp function is very important to do the same pattern in email-screenshot-testcase for better tracibilty
    public static String getTimeStamp(){
        return new SimpleDateFormat("YYYY-MM-dd-h-m-ssa").format(new Date());
    }
    //TODO Screenshot with try and catch for file exception
    // to prevent code stopping if any error in the exception happened (Exception Handling)
    public static void takeScreenShot(WebDriver driver, String screenshotName){
      try {
          //Capture screen shot using Takescreenshot
          File screenshotSrc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

          //Save screenshot
          File screenshotFile = new File(SCREENSHOT_PATH + screenshotName +"-"+getTimeStamp()+ ".png");
          FileUtils.copyFile(screenshotSrc,screenshotFile );

          //Add to allure
          Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotFile.getPath())));

      } catch (Exception e) {
          LogsUtil.error(e.getMessage());
      }
    }
    public static void takeFullScreenShotShutterBug(WebDriver driver, By locator){
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(findWebElement(driver, locator))
                    .save(SCREENSHOT_PATH);
        }catch (Exception e){
            LogsUtil.error(e.getMessage());
        }
    }
    public static int generateRandomNumber(int upperBound){
        return new Random().nextInt(upperBound) + 1;
    }
    // Set accept only unique numbers
   public static Set<Integer> generateUniqueNumber (int numberOfProductsNeeded, int totalNumberOfProducts){
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfProductsNeeded){
            int randomNumber = generateRandomNumber(totalNumberOfProducts);
            generatedNumbers.add(randomNumber);
        }
        return generatedNumbers;
   }
    public static boolean verifyURL(WebDriver driver,String expectedURL){
        try{
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static File getLatestFile(String folderPath){
     File folder = new File(folderPath);
     File[] files = folder.listFiles();
     assert files != null;
     if (files.length == 0)
         return null;
     Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

     return files[0];
    }

}
