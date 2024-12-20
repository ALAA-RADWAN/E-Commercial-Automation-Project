package Pages;

import Utilities.LogsUtil;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final WebDriver driver;
    private final By subtotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }
    public Float getSubTotal(){
      return Float.parseFloat(Utility.getText(driver,subtotal).replace("Item total: $",""));
    }
    public Float getTaxes(){
        return Float.parseFloat(Utility.getText(driver,tax).replace("Tax: $",""));
    }
    public Float getTotalPrice(){
        LogsUtil.info("Actual Total Price: $ " + Utility.getText(driver,total).replace("Total: $",""));
        return Float.parseFloat(Utility.getText(driver,total).replace("Total: $",""));
    }
    public String calculateTotalPrice(){
        LogsUtil.info("Calculated Total Price: $ " + (getSubTotal() + getTaxes()));
        return String.valueOf(getSubTotal() + getTaxes());
    }
    public boolean comparingTotalPrices(){

        return calculateTotalPrice().equals(String.valueOf(getTotalPrice()));
    }

    public P06_FinishingOrderPage clickOnFinishButton(){
        Utility.clickOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);
    }

}
