package com.cybcube.models.pages;

import com.cybcube.utils.Container;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

public class CheckoutFormPage extends Base{

    Container container;

    public CheckoutFormPage(Container container){
        super(container.getDriver());
        this.container = container;
    }

    private final By txtBoxName = By.id("first-name");
    private final By txtBoxLastName = By.id("last-name");
    private final By txtBoxZip = By.id("postal-code");
    private final By btnContinue = By.id("continue");
    private final By btnCancel = By.id("cancel");
    private final By errorMessage = By.xpath("//h3[@data-test='error']");

    public void checkUrl(){
        super.checkUrl(container.config.pages.getCheckoutPage());
    }

    public void enterName(String name){
        sendKeys(txtBoxName, name);
    }

    public void enterLastName(String lastName){
        sendKeys(txtBoxLastName, lastName);
    }

    public void enterZip(String zip){
        sendKeys(txtBoxZip, zip);
    }

    public void clickContinue(){
        click(btnContinue);
    }

    public void clickCancel(){
        click(btnCancel);
    }

    public void checkErrorMessage(String errorMessage) {
        assertEquals(errorMessage, find(this.errorMessage).getText());
    }
}
