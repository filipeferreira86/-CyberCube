package com.cybcube.models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public  class Base {
    WebDriver driver;
    WebDriverWait wait;
    public Base(WebDriver driver){
        this.driver = driver;
        long TIMEOUT = 10;
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    protected void navigateTo(String url){
        driver.get(url);
    }

    public void checkUrl(String url){
        assertEquals(url, driver.getCurrentUrl());
    }

    //region By based
    protected WebElement find(By locator){
        return driver.findElement(locator);
    }


    protected WebElement find(WebElement parent, By locator){
        return parent.findElement(locator);
    }

    protected List<WebElement> findList(By locator){
        return driver.findElements(locator);
    }
    protected List<WebElement> findList(WebElement parent, By locator){
        return parent.findElements(locator);
    }

    protected void sendKeys(By locator, String keys){
        find(locator).sendKeys(keys);
    }

    protected void sendKeys(By locator, Keys key){
        find(locator).sendKeys(key);
    }

    protected void waitElement(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitElement(By locator, int item){
        wait.until(ExpectedConditions.elementToBeClickable(findList(locator).get(item)));
    }

    protected void click(By locator){
        find(locator).click();
    }

    protected void click(WebElement parent, By locator){
        find(parent, locator).click();
    }

    protected void clickSelected(By locator, int item){
        findList(locator).get(item).click();
    }

    protected String getText(By locator){
        return find(locator).getText();
    }

    protected String getText(WebElement parent, By locator){
        return find(parent, locator).getText();
    }

    protected String getUrl(){
        return driver.getCurrentUrl();
    }

}
