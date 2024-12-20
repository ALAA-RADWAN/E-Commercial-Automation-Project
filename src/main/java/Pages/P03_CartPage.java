package Pages;

import Utilities.LogsUtil;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {
    static float totalPrices = 0;

    private final WebDriver driver;
    private final By pricesOfSelectedProductsLocator = By.xpath("//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price']");
    private final By checkoutLocator = By.id("checkout");

    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrices(){
        try{
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for(int i=1; i<=pricesOfSelectedProducts.size(); i++){
                By elements = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price'])["+i+"]");
                String fullText = Utility.getText(driver,elements);
                totalPrices += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtil.info("Total Prices " + totalPrices);
            return String.valueOf(totalPrices);
        } catch (Exception e){
            LogsUtil.error(e.getMessage());
            return "0";
        }
    }
    public boolean comparingPrices(String Price){
        return getTotalPrices().equals(Price);
    }
    public P04_CheckoutPage clickOnCheckoutButton(){
        Utility.clickOnElement(driver,checkoutLocator);
       return new P04_CheckoutPage(driver);
    }
}
