package com.cybcube.models.data;

public class InvalidLogin{
    private String username;
    private String password;

    public String getUsername(){
        return username;
    }
    public void setUsername(String input){
        this.username = input;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String input){
        this.password = input;
    }
}
