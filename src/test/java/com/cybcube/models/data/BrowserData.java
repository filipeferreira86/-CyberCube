package com.cybcube.models.data;

public class BrowserData{
    private String browser;
    private boolean headless;

    public String getBrowser(){
        return browser;
    }
    public void setBrowser(String input){
        this.browser = input;
    }
    public boolean getHeadless(){
        return headless;
    }
    public void setHeadless(boolean input){
        this.headless = input;
    }
}
