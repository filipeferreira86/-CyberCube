package com.cybcube.models.api.base;

import io.restassured.response.Response;

public class GenericResponse {

    private String message;
    private String type;
    private int code;


    public GenericResponse() {
        this.message = null;
        this.type = null;
        this.code = 0;
    }

    public GenericResponse(Response response) {
        this.message = response.jsonPath().getString("message");
        this.type = response.jsonPath().getString("type");
        this.code = response.jsonPath().getInt("code");
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }
}
