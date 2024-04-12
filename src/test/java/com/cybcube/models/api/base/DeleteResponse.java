package com.cybcube.models.api.base;

import com.cybcube.models.api.response.pets.ErrorResponse;
import io.restassured.response.Response;

public class DeleteResponse extends GenericResponse {


    public DeleteResponse() {
    }

    public DeleteResponse(Response response) {
        super(response);
    }

}
