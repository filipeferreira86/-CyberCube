package com.cybcube.models.api.response.pets;

public class AddImageResponse {
    private final String message;
    private final String type;
    private final int code;

    public AddImageResponse() {
        this.message = null;
        this.type = null;
        this.code = 0;
    }

    public AddImageResponse(String message, String type, int code) {
        this.message = message;
        this.type = type;
        this.code = code;
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
