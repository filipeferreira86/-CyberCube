package com.cybcube.models.api.response.pets;

import io.restassured.response.Response;

public class ResponseDriver<T> {
    private final T response;
    private final int responseCode;

    public ResponseDriver(Response response, Class<T> classType) {
        this.responseCode = response.getStatusCode();
        try {
            if (responseCode == 200) {
                this.response = classType.cast(response.getBody().as(classType));
            } else if (response.getBody().asString().isEmpty()) {
                this.response = (T) new ErrorResponse();
            } else {
                this.response = (T) response.getBody().as(ErrorResponse.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse response", e);
        }
    }

    public T getResponse() {
        return response;
    }

    public int getResponseCode() {
        return responseCode;
    }
}