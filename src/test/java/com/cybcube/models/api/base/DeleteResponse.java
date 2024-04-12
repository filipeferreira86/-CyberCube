package com.cybcube.models.api.base;
import io.restassured.response.Response;

public class DeleteResponse extends GenericResponse {


    public DeleteResponse() {
    }

    public DeleteResponse(Response response) {
        super(response);
    }

}
