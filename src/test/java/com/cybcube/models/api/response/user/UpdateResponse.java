package com.cybcube.models.api.response.user;

import com.cybcube.models.api.base.GenericResponse;
import io.restassured.response.Response;

public class UpdateResponse extends GenericResponse {

    public UpdateResponse() {
        super();
    }

    public UpdateResponse(Response response) {
        super(response);
    }
}
