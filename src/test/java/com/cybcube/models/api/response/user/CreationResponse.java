package com.cybcube.models.api.response.user;

import com.cybcube.models.api.base.GenericResponse;
import io.restassured.response.Response;

public class CreationResponse extends GenericResponse {

    public CreationResponse() {
        super();
    }

    public CreationResponse(Response response) {
        super(response);
    }

}
