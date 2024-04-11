package com.cybcube.models.drivers;

import com.cybcube.models.data.ApiConfig;
import com.google.gson.Gson;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class Base {

    ApiConfig apiConfig;
    Scenario scenario;

    public Base(ApiConfig apiConfig, Scenario s) {
        this.apiConfig = apiConfig;
        this.scenario = s;
    }

    protected Response sendRequest(String endpoint, String method) {
        return sendRequest(endpoint, method, "");
    }

    protected Response sendRequest(String endpoint, String method, Object body) {
        String jsonBody;
        if(body == ""){
            jsonBody = "";
        } else if (body instanceof String){
            jsonBody = (String) body;
        } else{
            jsonBody = new Gson().toJson(body);
        }
        logRequest(endpoint, method, jsonBody);
        Response response = given()
                .body(jsonBody)
                .baseUri(apiConfig.getBaseUrl())
                .contentType("application/json")
                .header("api_key", "special-key")
                .header("accept", "application/json")
                .when()
                .request(method, endpoint)
                .then()
                .extract()
                .response();
        logResponse(response);
        return response;
    }

    protected Response sendRequest(File body, int petId) {
        logRequest("pet/"+petId+"/uploadImage", "POST", body.toString());
        Response response = given().baseUri(apiConfig.getBaseUrl())
                .basePath("pet/"+petId+"/uploadImage")
                .contentType("multipart/form-data")
                .multiPart("file", body)
                .multiPart("additionalMetadata", "test")
                .when()
                .request("POST")
                .then()
                .extract()
                .response();
         logResponse(response);
         return response;
    }

    protected Response sendMultipartRequest(String endpoint, String method, List<String> body) {
        logRequest(endpoint, method, StringUtils.join(Collections.singletonList(body)));
        Response response = given().baseUri(apiConfig.getBaseUrl())
                .basePath(endpoint)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(body.get(0), body.get(1))
                .formParam(body.get(2), body.get(3))
                .when()
                .request(method)
                .then()
                .extract()
                .response();
        logResponse(response);
        return response;
    }

    private void logRequest(String endpoint, String method, String body) {
        scenario.log("\n{Request: \n Method: " + method + "\nEndpoint: " + apiConfig.getBaseUrl() + endpoint + "\nBody: " + body + "}\n");
    }

    private void logResponse(Response response) {
        String body = response.getBody().asString();
        scenario.log("\n{Response: \nStatus: " + response.getStatusCode() + "\nBody: " + response.getBody().asString() + "}\n");
    }
}
