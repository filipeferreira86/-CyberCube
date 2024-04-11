package com.cybcube.models.pages;

import com.cybcube.models.data.Product;
import com.cybcube.utils.Container;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

public class CheckoutOverviewPage extends Base{

    private final Container container;

    private final By itemName = new By.ByClassName("inventory_item_name");
    private final By itemPrice = new By.ByClassName("inventory_item_price");
    private final By itemDescription = new By.ByClassName("inventory_item_desc");
    private final By lblPaymentInformation = new By.ByXPath("//div[contains(@data-test,'payment-info-value')]");
    private final By lblShippingInformation = new By.ByXPath("//div[contains(@data-test,'shipping-info-value')]");
    private final By lblPriceItemTotal = new By.ByXPath("//div[contains(@data-test,'subtotal-label')]");
    private final By lblPriceTax = new By.ByXPath("//div[contains(@data-test,'tax-label')]");
    private final By lblPriceTotal = new By.ByXPath("//div[@data-test='total-label']");
    private final By btnFinish = new By.ByClassName("btn_action");

    public CheckoutOverviewPage(Container container) {
        super(container.getDriver());
        this.container = container;
    }

    public void checkUrl() {
        super.checkUrl(container.config.pages.getCheckoutOverviewPage());
    }

    public void checkItem() {
        Product product = (Product) container.getVar("item");
        assertEquals(product.getName(), find(itemName).getText());
        assertEquals(product.getPrice(), find(itemPrice).getText());
        assertEquals(product.getDescription(), find(itemDescription).getText());
    }

    public void clickFinishButton() {
        click(btnFinish);
    }

    public void checkPaymentInformation() {
        assertEquals(container.config.envData.getPaymentInformation(),
                find(lblPaymentInformation).getText());
    }

    public void checkShippingInformation() {
        assertEquals(container.config.envData.getShippingInformation(),
                find(lblShippingInformation).getText());
    }

    public void checkTax() {
        assertEquals(container.config.envData.getTax()*
                        Float.parseFloat(getText(lblPriceItemTotal).replace("Item total: $", "")),
                Float.parseFloat(getText(lblPriceTax).replace("Tax: $", "")), 0.01);
    }

    public void checkTotalPrice() {
        System.out.println(getText(lblPriceTotal).replace("Total: $", ""));
        assertEquals(container.config.envData.getTax()*
                        Float.parseFloat(getText(lblPriceItemTotal).replace("Item total: $", "")) +
                        Float.parseFloat(getText(lblPriceItemTotal).replace("Item total: $", "")),
                Float.parseFloat(getText(lblPriceTotal).replace("Total: $", "")), 0.01);
    }
}
