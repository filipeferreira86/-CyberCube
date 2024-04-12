package com.cybcube.models.drivers;

import com.cybcube.models.api.base.DeleteResponse;
import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.models.api.response.store.OrderResponse;
import com.cybcube.models.api.response.user.*;
import com.cybcube.utils.Container;

import java.util.List;

public class UserDriver extends BaseDriver{

    public UserDriver(Container container){
        super(container.apiConfig, container.getScenario());
    }

    public ResponseDriver<CreationResponse> addUser(Object body){
        return new ResponseDriver<>(sendRequest
                ("user", "POST", body), CreationResponse.class);
    }

    public ResponseDriver<CreationResponse> addUserWithArray(Object[] body){
        return new ResponseDriver<>(sendRequest
                ("user/createWithArray", "POST", body),
                CreationResponse.class);
    }

    public ResponseDriver<CreationResponse> addUserWithList(List<Object> body){
        return new ResponseDriver<>(sendRequest
                ("user/createWithArray", "POST", body),
                CreationResponse.class);
    }

    public ResponseDriver<UserResponse> getUserByUsername(String username){
        return new ResponseDriver<>(sendRequest
                ("user/" + username, "GET"), UserResponse.class);
    }

    public ResponseDriver<UpdateResponse> updateUser(String username, Object body){
        return new ResponseDriver<>(sendRequest
                ("user/" + username, "PUT", body), UpdateResponse.class);
    }

    public ResponseDriver<DeleteResponse> deleteUser(String username){
        if(username == null)
            username = " ";
        return new ResponseDriver<>(sendRequest
                ("user/" + username, "DELETE"), DeleteResponse.class);
    }

    public ResponseDriver<LoginResponse> loginUser(String username, String password){
        return new ResponseDriver<>(sendRequest
                ("user/login" + username + password, "GET"), LoginResponse.class);
    }

    public ResponseDriver<LogoutResponse> logoutUser(){
        return new ResponseDriver<>(sendRequest
                ("user/logout", "GET"), LogoutResponse.class);
    }
}
