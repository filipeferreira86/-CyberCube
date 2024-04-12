package com.cybcube.models.api.response.pets;

import com.cybcube.models.api.base.GenericResponse;
import io.restassured.response.Response;

public class ErrorResponse extends GenericResponse {
    public ErrorResponse() {
    }

    public ErrorResponse(Response response) {
        super(response);
    }
}
