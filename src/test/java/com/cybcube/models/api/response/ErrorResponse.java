package com.cybcube.models.api.response;

import io.restassured.response.Response;

public class ErrorResponse {
    private String message;
    private String type;
    private int code;

    public ErrorResponse() {
    }

    public ErrorResponse(Response response) {
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
