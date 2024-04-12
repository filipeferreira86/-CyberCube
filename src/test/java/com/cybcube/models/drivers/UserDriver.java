package com.cybcube.models.drivers;

import com.cybcube.models.api.base.DeleteResponse;
import com.cybcube.models.api.request.user.UserRequest;
import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.models.api.response.user.*;
import com.cybcube.utils.Container;

import java.util.List;

public class UserDriver extends BaseDriver{

    private final String USER_ENDPOINT = "user";
    private final String USER_BY_USERNAME_ENDPOINT = USER_ENDPOINT+"/%s";
    private final String USER_LOGIN_ENDPOINT = USER_ENDPOINT+"/login/%s/%s";
    private final String USER_LOGOUT_ENDPOINT = USER_ENDPOINT+"/logout";
    private final String USER_CREATE_WITH_ARRAY_ENDPOINT = USER_ENDPOINT+"/createWithArray";
    private final String USER_CREATE_WITH_LIST_ENDPOINT = USER_ENDPOINT+"/createWithList";


    public UserDriver(Container container){
        super(container.apiConfig, container.getScenario());
    }

    public ResponseDriver<CreationResponse> addUser(Object body){
        return new ResponseDriver<>(sendRequest
                (USER_ENDPOINT, "POST", body), CreationResponse.class);
    }

    public ResponseDriver<CreationResponse> addUserWithArray(Object[] body){
        return new ResponseDriver<>(sendRequest
                (USER_CREATE_WITH_ARRAY_ENDPOINT, "POST", body),
                CreationResponse.class);
    }

    public ResponseDriver<CreationResponse> addUserWithList(List<UserRequest> body){
        return new ResponseDriver<>(sendRequest
                (USER_CREATE_WITH_LIST_ENDPOINT, "POST", body),
                CreationResponse.class);
    }

    public ResponseDriver<UserResponse> getUserByUsername(String username){
        return new ResponseDriver<>(sendRequest
                (String.format(USER_BY_USERNAME_ENDPOINT, username), "GET"), UserResponse.class);
    }

    public ResponseDriver<UpdateResponse> updateUser(String username, Object body){
        return new ResponseDriver<>(sendRequest
                (String.format(USER_BY_USERNAME_ENDPOINT, username), "PUT", body), UpdateResponse.class);
    }

    public ResponseDriver<DeleteResponse> deleteUser(String username){
        if(username == null)
            username = " ";
        return new ResponseDriver<>(sendRequest
                (String.format(USER_BY_USERNAME_ENDPOINT, username), "DELETE"), DeleteResponse.class);
    }

    public ResponseDriver<LoginResponse> loginUser(String username, String password){
        return new ResponseDriver<>(sendRequest
                (String.format(USER_LOGIN_ENDPOINT, username, password), "GET"), LoginResponse.class);
    }

    public ResponseDriver<LogoutResponse> logoutUser(){
        return new ResponseDriver<>(sendRequest
                (USER_LOGOUT_ENDPOINT, "GET"), LogoutResponse.class);
    }
}
