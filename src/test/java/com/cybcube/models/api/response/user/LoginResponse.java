package com.cybcube.models.api.response.user;

import com.cybcube.models.api.base.GenericResponse;
import io.restassured.response.Response;

public class LoginResponse extends GenericResponse {

    public LoginResponse() {
        super();
    }

    public LoginResponse(Response response) {
        super(response);
    }
}
