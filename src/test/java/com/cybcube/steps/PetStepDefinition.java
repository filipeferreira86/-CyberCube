package com.cybcube.steps;

import com.cybcube.models.api.request.pets.AddPet;
import com.cybcube.models.api.request.pets.auxiliar.Category;
import com.cybcube.models.api.response.pets.AddPetResponse;
import com.cybcube.models.api.response.pets.DeletePetResponse;
import com.cybcube.models.api.response.pets.ErrorResponse;
import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.models.data.api.model.Pet;
import com.cybcube.models.drivers.PetDriver;
import com.cybcube.utils.Container;
import com.google.gson.Gson;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class PetStepDefinition{

    private final Container container;
    PetDriver petEndpoint;

    public PetStepDefinition(Container container){
        this.container = container;
    }



    @Before(value = "@Pet", order=1)
    public void beforePet(){
    }

    @Before
    public void before(){
        petEndpoint = new PetDriver(container);
    }

    // Given region

    @Given("I have a pet")
    public void iHaveAPet() {
        container.setVar("pet", container.getAddPetBody());
    }

    @Given("I have a pet with a missing required field")
    public void iHaveAPetWithAMissingRequiredField() {
        AddPet addPet = container.getAddPetBody();
        addPet.setName("");
        container.setVar("pet", addPet);
    }

    @Given("I have the following pets$")
    @Given("I have a pet with the following data:$")
    public void iHaveTheFollowingPets(DataTable pets) {
        List<Pet> petList = new ArrayList<>();
        pets.cells().forEach(row -> {
            Pet pet = new Pet();
            pet.setId(Long.parseLong(row.get(0)));
            pet.setName(row.get(1));
            pet.setStatus(row.get(2));
            petList.add(pet);
        });
        List<AddPet> petAdded = new ArrayList<>();
        for (Pet pet : petList) {
            AddPet addPet = new AddPet();
            addPet.setId(pet.getId());
            addPet.setName(pet.getName());
            addPet.setStatus(pet.getStatus());
            container.setVar("pet", addPet);
            petEndpoint.createPet(addPet);
            petAdded.add(addPet);
        }
        container.setVar("petList", petAdded);
    }

    // When region
    @When("I add a picture for the pet with id {int} with an invalid picture")
    public void iAddAPictureForThePetWithIdWithAnInvalidPicture(int petId) {
        ResponseDriver<?> response =
                petEndpoint.uploadImage(container.apiConfig.img.getInvalidImg(), petId);
        container.setVar("response", response);
    }

    @When("I add a picture for the pet with id {int}")
    public void iAddAPictureForThePetWithId(int petId) {
        ResponseDriver<?> response =
                petEndpoint.uploadImage(container.apiConfig.img.getValidImg(), petId);
        container.setVar("response", response);
    }

    @When("I send a POST request to add the pet")
    public void iSendAPOSTRequestToAddThePet() {
        ResponseDriver<AddPetResponse> response =
                petEndpoint.createPet(container.getVar("pet"));
        container.setVar("response", response);
    }

    @When("I delete the pet")
    public void iDeleteThePetWithId() {
        int petId = ((AddPet) container.getVar("pet")).getId();
        ResponseDriver<DeletePetResponse> response = petEndpoint.deletePet(petId);
        container.setVar("response", response);
    }

    @When("I delete a non-existing pet")
    public void iDeleteANonExistingPet() {
        ResponseDriver<?> response = petEndpoint.deletePet(0);
        container.setVar("response", response);
    }

    @When("I send a GET request to get the pet by id")
    public void iSendAGETRequestToGetThePetById() {
        AddPet pet = (AddPet) container.getVar("pet");
        ResponseDriver<AddPetResponse> response = petEndpoint.getPetById(pet.getId());
        container.setVar("response", response);
    }

    @When("I send a GET request to get the pet by invalid id")
    public void iSendAGETRequestToGetThePetByInvalidId() {
        ResponseDriver<?> response = petEndpoint.getPetById(0);
        container.setVar("response", response);
    }

    @When("I send a GET request to get the list of pets with the status {}")
    public void iSendAGETRequestToGetTheListOfPetsWithTheStatus(String status) {
        ResponseDriver<AddPetResponse[]> response = petEndpoint.getPetByStatus(status);
        container.setVar("response", response);
    }

    @When("I update the pet with the following data:$")
    public void iUpdateThePetWithTheFollowingData(DataTable dataTable) {
        AddPet addPet = new AddPet();
        List<List<String>> rows = dataTable.cells();
        addPet.setId(Integer.parseInt(rows.get(1).get(0)));
        addPet.setName(rows.get(1).get(1));
        addPet.setStatus(rows.get(1).get(2));
        addPet.setPhotoUrls(new String[]{rows.get(1).get(3)});
        addPet.setTags(new Category[]{new Category(Integer.parseInt(rows.get(1).get(4)),
                rows.get(1).get(5))});
        addPet.setCategory(new Category(Integer.parseInt(rows.get(1).get(6)), rows.get(1).get(7)));
        ResponseDriver<AddPetResponse> response = petEndpoint.updatePet(addPet);
        container.setVar("pet", addPet);
        container.setVar("response", response);
    }

    @When("I update the pet with invalid data")
    public void iUpdateThePetWithInvalidData() {
        ResponseDriver<AddPetResponse> response = petEndpoint.updatePet(
                "{'id':,'category':{'id':2,'name':'categoryNew'},'name':'cat','photoUrls':['www.123.com'],'tags':[{'id':2,'name':'tagNew'}],'status':'sold'}}");
        container.setVar("response", response);
    }

    @When("I update the pet with the following data through form data:$")
    public void iUpdateThePetWithTheFollowingDataThroughFormData(DataTable dataTable) {
        List<List<String>> rows = dataTable.cells();
        int petId = Integer.parseInt(rows.get(1).get(0));
        String name = rows.get(1).get(1);
        String status = rows.get(1).get(2);
        ResponseDriver<ErrorResponse> response = petEndpoint.updatePetWithFormData(petId, name, status);
        container.setVar("response", response);
    }

    @Given("I have a pet with a invalid status")
    public void iHaveAPetWithAInvalidStatus() {
        AddPet addPet = container.getAddPetBody();
        addPet.setStatus("invalid");
        container.setVar("pet", addPet);
    }

    @Given("that I have the number of pets by status in the store")
    public void thatIHaveTheNumberOfPetsByStatusInTheStore() {
        HashMap<String, Integer> numberOfPets = new HashMap<>(){
            {
                put("available", petEndpoint.getPetByStatus("available").getResponse().length);
                put("pending", petEndpoint.getPetByStatus("pending").getResponse().length);
                put("sold", petEndpoint.getPetByStatus("sold").getResponse().length);
            }
        };
        container.setVar("numberOfPets", numberOfPets);
    }

    // Then region
    @Then("the pet with should have a picture")
    public void thePetWithIdShouldHaveAPicture() {
        assertEquals(((ResponseDriver<?>)container.getVar("response"))
                .getResponseCode(), 200);
    }

    @Then("the response should contain an error message")
    public void theResponseShouldContainAnErrorMessage() {
        assertNotEquals("", ((ResponseDriver<ErrorResponse>)container
                .getVar("response")).getResponse().getMessage());
    }

    @Then("the response should contain the data for the pet")
    public void theResponseShouldContainTheDataForThePet() {
        AddPet addPet = (AddPet) container.getVar("pet");
        AddPetResponse addPetResponse =
                ((ResponseDriver<AddPetResponse>)container.getVar("response")).getResponse();

        // Compare the response with the pet data
        assertEquals(addPetResponse.getName(), addPet.getName());
        assertEquals(addPetResponse.getPhotoUrls()[0], addPet.getPhotoUrls()[0]);
    }

    @Then("the pet should be deleted")
    public void thePetWithIdShouldBeDeleted() {
        int petId = ((AddPet) container.getVar("pet")).getId();
        ResponseDriver<DeletePetResponse> response = (ResponseDriver<DeletePetResponse>)
                container.getVar("response");

        // Check if the pet was deleted
        assertEquals(String.valueOf(petId), response.getResponse().getMessage());
        assertNotEquals("", response.getResponse().getType());
        assertEquals(200, response.getResponse().getCode());
        assertEquals(petEndpoint.getPetById(petId).getResponseCode(), 404);
    }

    @Then("the response should contain the pet data")
    public void theResponseShouldContainThePetData() {
        AddPet pet = (AddPet) container.getVar("pet");
        AddPetResponse addPetResponse = ((ResponseDriver<AddPetResponse>)
                container.getVar("response")).getResponse();

        // Compare the response with the pet data
        assertEquals(addPetResponse.getName(), pet.getName());
        assertArrayEquals(addPetResponse.getPhotoUrls(), pet.getPhotoUrls());
        assertEquals(addPetResponse.getStatus(), pet.getStatus());
        assertEquals(addPetResponse.getId(), pet.getId());
        assertEquals(new Gson().toJson(addPetResponse.getCategory()),
                new Gson().toJson(pet.getCategory()));
        assertEquals(new Gson().toJson(addPetResponse.getTags()[0]),
                new Gson().toJson(pet.getTags()[0]));
    }

    @Then("the response should contain the error message {string}")
    public void theResponseShouldContainTheErrorMessage(String msg) {
        assertEquals(((ResponseDriver<ErrorResponse>)container
                .getVar("response")).getResponse().getMessage(), msg);
    }

    @Then("the response status be a list of all pets with the status {}")
    public void theResponseStatusBeAListOfAllPetsWithTheStatus(String status) {
        AddPetResponse[] addPetResponses = ((ResponseDriver<AddPetResponse[]>)
                container.getVar("response")).getResponse();
        for (AddPetResponse addPetResponse : addPetResponses) {
            assertEquals(addPetResponse.getStatus(), status);
        }
    }

    @Then("the list should contain the pet with the status {}")
    public void theListShouldContainThePetWithTheStatus(String status) {
        List<AddPet> petList = (List<AddPet>) container.getVar("petList");

        AddPetResponse[] addPetResponses = ((ResponseDriver<AddPetResponse[]>)
                container.getVar("response")).getResponse();

        assertEquals(1, getPetList(addPetResponses, petList, status).size());
    }

    @Then("the list should not contain the pet with the status {}")
    public void theListShouldNotContainThePetWithTheStatus(String status) {
        List<AddPet> petList = (List<AddPet>) container.getVar("petList");

        AddPetResponse[] addPetResponses = ((ResponseDriver<AddPetResponse[]>)
                container.getVar("response")).getResponse();

        assertEquals(0, getPetList(addPetResponses, petList, status).size());
    }

    @Then("the list should be empty")
    public void theListShouldBeEmpty() {
        AddPetResponse[] addPetResponses = ((ResponseDriver<AddPetResponse[]>)
                container.getVar("response")).getResponse();
        assertEquals(0, addPetResponses.length);
    }

    @Then("the pet should be updated with the following data:$")
    public void thePetShouldBeUpdatedWithTheFollowingData(DataTable dataTable) {
        AddPet addPet = (AddPet) container.getVar("pet");
        List<List<String>> rows = dataTable.cells();
        for (List<String> columns : rows) {
            switch (columns.get(0)) {
                case "name":
                    assertEquals(columns.get(1), addPet.getName());
                    break;
                case "status":
                    assertEquals(columns.get(1), addPet.getStatus());
                    break;
                case "photoUrls":
                    assertArrayEquals(new String[]{columns.get(1)}, addPet.getPhotoUrls());
                    break;
                case "category":
                    assertEquals(Integer.parseInt(columns.get(1)), addPet.getCategory().getId());
                    assertEquals(columns.get(2), addPet.getCategory().getName());
                    break;
                case "tags":
                    assertEquals(Integer.parseInt(columns.get(1)), addPet.getTags()[0].getId());
                    assertEquals(columns.get(2), addPet.getTags()[0].getName());
                    break;
            }
        }
    }

    @After(value = "@PetCreated")
    public void after() {
        try{
            AddPet pet = (AddPet) container.getVar("pet");
            petEndpoint.deletePet(pet.getId());
        }catch (Exception e){
            System.out.println("Pet not deleted");
        }
        try{
            List<AddPet> petList = (List<AddPet>) container.getVar("petList");
            for (AddPet pet : petList) {
                petEndpoint.deletePet(pet.getId());
            }
        }catch (Exception e){
            System.out.println("Pet list not deleted");
        }
    }

    @Then("the pet should be added to the database")
    public void thePetShouldBeAddedToTheDatabase() {
        AddPet addPet = (AddPet) container.getVar("pet");
        AddPetResponse response = (petEndpoint.getPetById(addPet.getId())).getResponse();
        assertEquals(addPet.getName(), response.getName());
        assertEquals(addPet.getStatus(), response.getStatus());
        assertArrayEquals(addPet.getPhotoUrls(), response.getPhotoUrls());
        assertEquals(addPet.getCategory().getId(), response.getCategory().getId(), 0.0);
        assertEquals(addPet.getCategory().getName(), response.getCategory().getName());
        assertEquals(addPet.getTags()[0].getId(), response.getTags()[0].getId(), 0.0);
        assertEquals(addPet.getTags()[0].getName(), response.getTags()[0].getName());
    }

    @Then("database should be updated")
    public void databaseShouldBeUpdated() {
        AddPet userRequest = (AddPet)container.getVar("pet");
        AddPetResponse response = petEndpoint.getPetById(userRequest.getId()).getResponse();
        assertEquals(userRequest.getName(), response.getName());
        assertEquals(userRequest.getStatus(), response.getStatus());
        assertArrayEquals(userRequest.getPhotoUrls(), response.getPhotoUrls());
        assertEquals(userRequest.getCategory().getId(), response.getCategory().getId(), 0.0);
        assertEquals(userRequest.getCategory().getName(), response.getCategory().getName());
        assertEquals(userRequest.getTags()[0].getId(), response.getTags()[0].getId(), 0.0);
        assertEquals(userRequest.getTags()[0].getName(), response.getTags()[0].getName());
    }

    // Private methods

    private List<AddPetResponse> getPetList(AddPetResponse[] addPetResponses,
                                            List<AddPet> petList,
                                            String status) {
        String petName = petList.stream().filter(p -> p.getStatus().equals(status)).findFirst()
                .orElseThrow(
                        () -> new AssertionError(
                        "Pet with status " + status + " not found")).getName();
        for(AddPetResponse addPetResponse : addPetResponses){
            // For some reason the API accepts pets without the field name,
            // so we need to handle it
            String name;
            if(addPetResponse.getName() == null)
                name = "";
            else
                name = addPetResponse.getName();

            if(name.equals(petName)){
                return List.of(addPetResponse);
            }
        }
        return new ArrayList<>();
    }
}
