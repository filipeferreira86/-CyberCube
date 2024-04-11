package com.cybcube.models.pages;

import com.cybcube.utils.Container;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

public class LoginPage extends Base{

    private final Container container;

    By txtUsername = new By.ById("user-name");
    By txtPassword = new By.ById("password");
    By btnLogin = new By.ById("login-button");
    By lblErrorMessage = new By.ByClassName("error-message-container");
    By btnCloseErr = new By.ByClassName("error-button");

    public LoginPage(Container container) throws Exception {
        super(container.getDriver());
        this.container = container;
        super.navigateTo(this.container.config.envData.getUrl());
    }

    public void executeLogin(){
        sendKeys(txtUsername, container.login.getUsername());
        sendKeys(txtPassword, container.login.getPassword());
        click(btnLogin);
    }

    public void checkFailedLogin(String errMessage){
        waitElement(lblErrorMessage);
        assertEquals("Messages don't match!", getText(lblErrorMessage), errMessage);
    }

    public void checkErrorMsg(){
        waitElement(lblErrorMessage);
    }

    public void closeErrorMessage(){
        click(btnCloseErr);
    }

    public void checkErrorMsgGone(){
        assertEquals(getText(lblErrorMessage), "");
    }


    public void checkUrl(){
        super.checkUrl(container.config.envData.getUrl());
    }
}
