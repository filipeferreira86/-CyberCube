package com.cybcube.steps;

import com.cybcube.models.api.request.AddPet;
import com.cybcube.models.api.request.auxiliar.Category;
import com.cybcube.models.api.response.AddPetResponse;
import com.cybcube.models.api.response.DeletePetResponse;
import com.cybcube.models.api.response.ErrorResponse;
import com.cybcube.models.api.response.ResponseDriver;
import com.cybcube.models.data.Pet;
import com.cybcube.models.drivers.PetDriver;
import com.cybcube.utils.Container;
import com.google.gson.Gson;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class ApiSteps {

    private final Container container;
    PetDriver petEndpoint;

    public ApiSteps(Container container) {
        this.container = container;
    }

    @Before
    public void before(Scenario s) {
        container.setScenario(s);
    }

    // Given region
    @Given("I have a pet with id {int}")
    public void iHaveAPetWithId(int petId) {
        petEndpoint = new PetDriver(container);
    }

    @Given("I have a pet")
    public void iHaveAPet() {
        petEndpoint = new PetDriver(container);
        container.addVar("pet", container.getAddPetBody());
    }

    @Given("I have a pet with a missing required field")
    public void iHaveAPetWithAMissingRequiredField() {
        petEndpoint = new PetDriver(container);
        AddPet addPet = container.getAddPetBody();
        addPet.setName("");
        container.addVar("pet", addPet);
    }

    // When region
    @When("I add a picture for the pet with id {int} with an invalid picture")
    public void iAddAPictureForThePetWithIdWithAnInvalidPicture(int petId) {
        ResponseDriver<?> response =
                petEndpoint.uploadImage(container.apiConfig.img.getInvalidImg(), petId);
        container.addVar("response", response);
    }

    @When("I add a picture for the pet with id {int}")
    public void iAddAPictureForThePetWithId(int petId) {
        ResponseDriver<?> response =
                petEndpoint.uploadImage(container.apiConfig.img.getValidImg(), petId);
        container.addVar("response", response);
    }

    @When("I send a POST request to add the pet")
    public void iSendAPOSTRequestToAddThePet() {
        ResponseDriver<AddPetResponse> response =
                petEndpoint.createPet(container.getVar("pet"));
        container.addVar("response", response);
    }

    @When("I delete the pet")
    public void iDeleteThePetWithId() {
        int petId = ((AddPet) container.getVar("pet")).getId();
        petEndpoint = new PetDriver(container);
        ResponseDriver<DeletePetResponse> response = petEndpoint.deletePet(petId);
        container.addVar("response", response);
    }

    @When("I delete a non-existing pet")
    public void iDeleteANonExistingPet() {
        ResponseDriver<?> response = petEndpoint.deletePet(0);
        container.addVar("response", response);
    }

    @When("I send a GET request to get the pet by id")
    public void iSendAGETRequestToGetThePetById() {
        AddPet pet = (AddPet) container.getVar("pet");
        ResponseDriver<AddPetResponse> response = petEndpoint.getPetById(pet.getId());
        container.addVar("response", response);
    }

    @When("I send a GET request to get the pet by invalid id")
    public void iSendAGETRequestToGetThePetByInvalidId() {
        ResponseDriver<?> response = petEndpoint.getPetById(0);
        container.addVar("response", response);
    }

    // Then region
    @Then("the pet with id {int} should have a picture")
    public void thePetWithIdShouldHaveAPicture(int petId) {
        assertEquals(((ResponseDriver<?>)container.getVar("response"))
                .getResponseCode(), 200);
    }

    @Then("the response should be {int}")
    public void theResponseShouldBe(int status) {
        assertEquals(status, ((ResponseDriver<?>)container.getVar("response"))
                .getResponseCode());
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
        assertEquals( response.getResponse().getMessage(), String.valueOf(petId));
        assertEquals( response.getResponse().getType(), "unknown");
        assertEquals( response.getResponse().getCode(), 200);
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
        assertEquals(Integer.parseInt(addPetResponse.getId()), pet.getId());
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

    @After
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
        petEndpoint = new PetDriver(container);
        List<AddPet> petAdded = new ArrayList<>();
        for (Pet pet : petList) {
            AddPet addPet = new AddPet();
            addPet.setId(pet.getId());
            addPet.setName(pet.getName());
            addPet.setStatus(pet.getStatus());
            container.addVar("pet", addPet);
            petEndpoint.createPet(addPet);
            petAdded.add(addPet);
        }
        container.addVar("petList", petAdded);
    }

    @When("I send a GET request to get the list of pets with the status {}")
    public void iSendAGETRequestToGetTheListOfPetsWithTheStatus(String status) {
        ResponseDriver<AddPetResponse[]> response = petEndpoint.getPetByStatus(status);
        container.addVar("response", response);
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

    // Private methods

    private List<AddPetResponse> getPetList(AddPetResponse[] addPetResponses,
                                            List<AddPet> petList,
                                            String status) {
        String petName = petList.stream().filter(p -> p.getStatus().equals(status)).findFirst()
                .orElseThrow(
                        () -> new AssertionError(
                        "Pet with status " + status + " not found")).getName();
        return  Arrays.stream(addPetResponses).filter(
                addPetResponseItem -> addPetResponseItem.getName().equals(
                        petName)).toList();
    }

    @And("the list should not contain the pet with the status {}")
    public void theListShouldNotContainThePetWithTheStatus(String status) {
        List<AddPet> petList = (List<AddPet>) container.getVar("petList");

        AddPetResponse[] addPetResponses = ((ResponseDriver<AddPetResponse[]>)
                container.getVar("response")).getResponse();

        assertEquals(0, getPetList(addPetResponses, petList, status).size());
    }

    @And("the list should be empty")
    public void theListShouldBeEmpty() {
        AddPetResponse[] addPetResponses = ((ResponseDriver<AddPetResponse[]>)
                container.getVar("response")).getResponse();
        assertEquals(0, addPetResponses.length);
    }

    public void iHaveAPetWithTheFollowingData() {
    }

    @When("I update the pet with the following data:$")
    public void iUpdateThePetWithTheFollowingData(DataTable dataTable) {
        petEndpoint = new PetDriver(container);
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
        container.addVar("pet", addPet);
        container.addVar("response", response);
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

    @When("I update the pet with invalid data")
    public void iUpdateThePetWithInvalidData() {
        petEndpoint = new PetDriver(container);
        ResponseDriver<AddPetResponse> response = petEndpoint.updatePet(
                "{'id':,'category':{'id':2,'name':'categoryNew'},'name':'cat','photoUrls':['www.123.com'],'tags':[{'id':2,'name':'tagNew'}],'status':'sold'}}");
        container.addVar("response", response);
    }

    @When("I update the pet with the following data through form data:$")
    public void iUpdateThePetWithTheFollowingDataThroughFormData(DataTable dataTable) {
        petEndpoint = new PetDriver(container);
        List<List<String>> rows = dataTable.cells();
        int petId = Integer.parseInt(rows.get(1).get(0));
        String name = rows.get(1).get(1);
        String status = rows.get(1).get(2);
        ResponseDriver<ErrorResponse> response = petEndpoint.updatePetWithFormData(petId, name, status);
        container.addVar("response", response);
    }
}
