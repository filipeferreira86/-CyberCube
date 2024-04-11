package com.cybcube.models.drivers;

import com.cybcube.models.api.response.*;
import com.cybcube.utils.Container;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;

public class PetDriver extends Base{

    private final String PET_ENDPOINT = "pet/";
    public PetDriver(Container c) {
        super(c.apiConfig, c.getScenario());
    }

    public ResponseDriver<AddPetResponse> getPetById(int petId) {
        return new ResponseDriver<>(
                sendRequest(PET_ENDPOINT + petId, "GET"), AddPetResponse.class);
    }

    public ResponseDriver<AddPetResponse[]> getPetByStatus(String status) {
        return new ResponseDriver<>(
                sendRequest(PET_ENDPOINT + "findByStatus?status=" + status, "GET"), AddPetResponse[].class);
    }

    public ResponseDriver<AddPetResponse> createPet(Object body) {

        return new ResponseDriver<>(
                sendRequest(PET_ENDPOINT, "POST", body), AddPetResponse.class);
    }

    public ResponseDriver<AddPetResponse> updatePet(Object body) {
        return new ResponseDriver<>(sendRequest(PET_ENDPOINT, "PUT", body), AddPetResponse.class);
    }

    public ResponseDriver<ErrorResponse> updatePetWithFormData(int petId, String name, String status) {
        List<String> body = List.of("name", name, "status", status);
        return new ResponseDriver<>(sendMultipartRequest(
                PET_ENDPOINT + petId, "POST", body), ErrorResponse.class);
    }

    public ResponseDriver<DeletePetResponse> deletePet(int petId) {
        return new ResponseDriver<>(
                sendRequest(PET_ENDPOINT + petId, "DELETE"),
                DeletePetResponse.class);
    }

    public ResponseDriver<AddImageResponse> uploadImage(File body, int petId) {
        return new ResponseDriver<>(
                sendRequest(body, petId), AddImageResponse.class);
    }
}
