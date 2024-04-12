package com.cybcube.models.data.ui.config;

public class EnvData{
    private String url;
    private ValidLogin validLogin;
    private InvalidLogin invalidLogin;
    private String paymentInformation;
    private String shippingInformation;
    private float tax;

    public String getUrl(){
        return url;
    }
    public void setUrl(String input){
        this.url = input;
    }
    public ValidLogin getValidLogin(){
        return validLogin;
    }
    public void setValidLogin(ValidLogin input){
        this.validLogin = input;
    }
    public InvalidLogin getInvalidLogin(){
        return invalidLogin;
    }
    public void setInvalidLogin(InvalidLogin input){
        this.invalidLogin = input;
    }
    public String getPaymentInformation(){
        return paymentInformation;
    }
    public String getShippingInformation(){
        return shippingInformation;
    }

    public float getTax(){
        return tax;
    }
}
