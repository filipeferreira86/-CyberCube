package com.cybcube.models.api.response.user;

import com.cybcube.models.api.base.GenericResponse;
import io.restassured.response.Response;

public class LogoutResponse extends GenericResponse {

        public LogoutResponse() {
            super();
        }

        public LogoutResponse(Response response) {
            super(response);
        }
}
