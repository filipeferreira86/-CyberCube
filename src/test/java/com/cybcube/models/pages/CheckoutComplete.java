package com.cybcube.models.pages;

import com.cybcube.utils.Container;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

public class CheckoutComplete extends Base{

    private final By checkoutComplete = new By.ByClassName("complete-header");

    public CheckoutComplete(Container container){
        super(container.getDriver());
    }

    public void checkCheckoutComplete(){
        waitElement(checkoutComplete);
        assertEquals("Thank you for your order!", find(checkoutComplete).getText());
    }
}
