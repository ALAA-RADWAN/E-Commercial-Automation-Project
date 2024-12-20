package Pages;

import Utilities.LogsUtil;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

import static Utilities.Utility.generalWait;

public class P02_HomePage {
    static float totalPrices = 0;
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;

    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnTheCart = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='Remove']");
    private final By cartIconButton = By.className("shopping_cart_link");
    private final By pricesOfSelectedProductsLocator = By.xpath("//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price']");

    private final WebDriver driver;

    public P02_HomePage(WebDriver driver){
        this.driver = driver;
    }
    public By getnumberOfProductsOnTheCart(){
        return numberOfProductsOnTheCart;
    }

    public P02_HomePage addAllProductsToCart(){
        allProducts = driver.findElements(addToCartButtonForAllProducts);
        LogsUtil.info("Number Of All Products " + allProducts.size());
        for(int i = 1 ; i <= allProducts.size(); i++){
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])["+i+"]");
            Utility.clickOnElement(driver,addToCartButtonForAllProducts);
        }
        return this;
    }
    public String getNumberOfProductsOnTheCart(){
        try {
            LogsUtil.info("Number Of products on the cart " + Utility.getText(driver, numberOfProductsOnTheCart));
            return Utility.getText(driver, numberOfProductsOnTheCart);
        } catch (Exception e){
            LogsUtil.error(e.getMessage());
            return "0";
        }
    }

    public String getNumberOfSelectedProducts(){
        try {
            selectedProducts = driver.findElements(numberOfSelectedProducts);
            LogsUtil.info("Number Of selected products " + selectedProducts.size());
            return String.valueOf(selectedProducts.size());
        } catch (Exception e){
            LogsUtil.error(e.getMessage());
            return "0";
        }
    }
    public P02_HomePage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts){
        Set<Integer> randomNumbers = Utility.generateUniqueNumber(numberOfProductsNeeded,totalNumberOfProducts);
        for(int random : randomNumbers){
            LogsUtil.info("random number " + random);
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])["+random+"]");
            Utility.clickOnElement(driver,addToCartButtonForAllProducts);
        }
        return this;
    }
    public boolean comparingNumberOfSelectedProductsWithCart(){
        return getNumberOfProductsOnTheCart().equals(getNumberOfSelectedProducts());
    }
    public P03_CartPage clickOnCartIcon(){
     Utility.clickOnElement(driver,cartIconButton);
     return new P03_CartPage(driver);
    }

    public String getTotalPricesOfSelectedProducts(){
        try{
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for(int i=1; i<=pricesOfSelectedProducts.size(); i++){
                By elements = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price'])["+ i +"]");
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
}
