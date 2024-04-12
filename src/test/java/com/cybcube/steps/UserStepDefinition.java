package com.cybcube.steps;

import com.cybcube.models.api.base.DeleteResponse;
import com.cybcube.models.api.request.pets.AddPet;
import com.cybcube.models.api.request.store.OrderRequest;
import com.cybcube.models.api.request.user.UserRequest;
import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.models.api.response.store.OrderResponse;
import com.cybcube.models.api.response.user.*;
import com.cybcube.models.drivers.UserDriver;
import com.cybcube.utils.Container;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class UserStepDefinition {

    private final Container container;
    private UserDriver userEndpoint;

    public UserStepDefinition(Container container) {
        this.container = container;
    }
    @Before(value = "@User", order = 1)
    public void before(){
        userEndpoint = new UserDriver(container);
    }

    // given region
    @Given("the data to create a new user is prepared")
    public void theDataToCreateANewUserIsPrepared() {
        container.setVar("userRequest", prepareUserRequest(2231));
    }

    // when region
    @When("I create a new user")
    @When("I create a new user with same data")
    @Given("I created a new user")
    public void iCreateANewUser() {
        ResponseDriver<CreationResponse> response =  userEndpoint.addUser(
                container.getVar("userRequest"));
        container.setVar("response", response);
    }

    @When("I create a new user with missing data")
    public void iCreateANewUserWithMissingData() {
        UserRequest userRequest = prepareUserRequest(2231);
        userRequest.setPassword(null);
        container.setVar("userRequest", userRequest);
        ResponseDriver<CreationResponse> response =  userEndpoint.addUser(
                container.getVar("userRequest"));
        container.setVar("response", response);
    }

    @When("I delete the user")
    public void iDeleteTheUser() {
        ResponseDriver<DeleteResponse> response =  userEndpoint.deleteUser(
                ((UserRequest)container.getVar("userRequest")).getUsername());
        container.setVar("response", response);
    }

    @When("I delete a user that does not exist")
    public void iDeleteAUserThatDoesNotExist() {
        ResponseDriver<DeleteResponse> response =  userEndpoint.deleteUser(
                "nonexistent");
        container.setVar("response", response);
    }

    @When("I request the user by username")
    public void iRequestTheUserByUsername() {
        ResponseDriver<UserResponse> response =  userEndpoint.getUserByUsername(
                ((UserRequest)container.getVar("userRequest")).getUsername());
        container.setVar("response", response);
    }

    @When("I create a new user with missing username")
    public void iCreateANewUserWithMissingUsername() {
        UserRequest userRequest = prepareUserRequest(2231);
        userRequest.setUsername(null);
        container.setVar("userRequest", userRequest);
        ResponseDriver<CreationResponse> response =  userEndpoint.addUser(
                container.getVar("userRequest"));
        container.setVar("response", response);
    }

    @When("I request the user by username that doesnt exist")
    public void iRequestTheUserByUsernameThatDoesntExist() {
        ResponseDriver<UserResponse> response =  userEndpoint.getUserByUsername(
                "nonexistent");
        container.setVar("response", response);
    }

    @When("I check if the user is logged in")
    public void iCheckIfTheUserIsLoggedIn() {
        UserRequest userRequest = (UserRequest)container.getVar("userRequest");
        ResponseDriver<LoginResponse> response =  userEndpoint.loginUser(
                userRequest.getUsername(), userRequest.getPassword());
        container.setVar("response", response);
    }

    @When("I check if the user is logged in using a wrong password")
    public void iCheckIfTheUserIsLoggedInUsingAWrongPassword() {
        UserRequest userRequest = (UserRequest)container.getVar("userRequest");
        ResponseDriver<LoginResponse> response =  userEndpoint.loginUser(
                userRequest.getUsername(), "wrongpassword");
        container.setVar("response", response);
    }

    @When("I check if the user is logged in using a wrong username")
    public void iCheckIfTheUserIsLoggedInUsingAWrongUsername() {
        UserRequest userRequest = (UserRequest)container.getVar("userRequest");
        ResponseDriver<LoginResponse> response =  userEndpoint.loginUser(
                "wrongUsername", userRequest.getPassword());
        container.setVar("response", response);
    }

    @When("I get the logout")
    public void iGetTheLogout() {
        ResponseDriver<LogoutResponse> response =  userEndpoint.logoutUser();
        container.setVar("response", response);
    }

    @When("I update the user data")
    public void iUpdateTheUserData() {
        String originalUsername = ((UserRequest) container.getVar("userRequest")).getUsername();
        UserRequest userRequest = prepareUserRequest(2231);
        userRequest.setFirstName("newFirstName");
        userRequest.setUsername("newUsername");
        userRequest.setLastName("newLastName");
        userRequest.setEmail("newEmail");
        userRequest.setPassword("newPassword");
        userRequest.setPhone("newPhone");
        userRequest.setUserStatus(2);
        container.setVar("userRequest", userRequest);
        ResponseDriver<UpdateResponse> response =  userEndpoint.updateUser(
                originalUsername, container.getVar("userRequest"));
        container.setVar("response", response);
    }

    @When("I update the user data with wrong username")
    public void iUpdateTheUserDataWithWrongUsername() {
        String originalUsername = ((UserRequest) container.getVar("userRequest")).getUsername();
        UserRequest userRequest = prepareUserRequest(2231);
        userRequest.setFirstName("newFirstName");
        userRequest.setUsername("newUsername");
        userRequest.setLastName("newLastName");
        userRequest.setEmail("newEmail");
        userRequest.setPassword("newPassword");
        userRequest.setPhone("newPhone");
        userRequest.setUserStatus(2);
        container.setVar("userRequest", userRequest);
        ResponseDriver<UpdateResponse> response =  userEndpoint.updateUser(
                "wrongUsername", container.getVar("userRequest"));
        container.setVar("response", response);
    }

    @When("I update the user data without new username")
    public void iUpdateTheUserDataWithoutNewUsername() {
        String originalUsername = ((UserRequest) container.getVar("userRequest")).getUsername();
        UserRequest userRequest = prepareUserRequest(2231);
        userRequest.setFirstName("newFirstName");
        userRequest.setUsername(null);
        userRequest.setLastName("newLastName");
        userRequest.setEmail("newEmail");
        userRequest.setPassword("newPassword");
        userRequest.setPhone("newPhone");
        userRequest.setUserStatus(2);
        container.setVar("userRequest", userRequest);
        ResponseDriver<UpdateResponse> response =  userEndpoint.updateUser(
                originalUsername, container.getVar("userRequest"));
        container.setVar("response", response);
    }

    @When("I create {int} new users with array")
    @When("I create {int} new users with List")
    public void iCreateNewUsersWithArray(int numberOfUsers) {
        UserRequest[] userRequests = new UserRequest[numberOfUsers];
        for (int i = 0; i < numberOfUsers; i++) {
            userRequests[i] = prepareUserRequest(2231 + i);
        }
        container.setVar("userRequest", userRequests);
        container.setVar("response", userEndpoint.addUserWithArray(userRequests));
    }

    @When("I create {int} new users with array and one missing data")
    @When("I create {int} new users with List and one missing data")
    public void iCreateNewUsersWithArrayAndOneMissingData(int numberOfUsers) {
        UserRequest[] userRequests = new UserRequest[numberOfUsers];
        for (int i = 0; i < numberOfUsers; i++) {
            userRequests[i] = prepareUserRequest(2231 + i);
        }
        userRequests[0].setUsername(null);
        container.setVar("userRequest", userRequests);
        container.setVar("response", userEndpoint.addUserWithArray(userRequests));
    }


    // Then region
    @Then("the user is deleted")
    public void theUserIsDeleted() {
        ResponseDriver<DeleteResponse> response = (ResponseDriver<DeleteResponse>)
                container.getVar("response");
        DeleteResponse deleteResponse = response.getResponse();
        assertEquals(200, deleteResponse.getCode());
        assertNotEquals("", deleteResponse.getType());
        assertEquals(String.valueOf(((UserRequest)container.getVar("userRequest"))
                .getUsername()), deleteResponse.getMessage());

    }

    @Then("the user should be created")
    public void theUserShouldBeCreated() {
        ResponseDriver<CreationResponse> response = (ResponseDriver<CreationResponse>)
                container.getVar("response");
        CreationResponse creationResponse = response.getResponse();
        assertEquals(200, creationResponse.getCode());
        assertNotEquals("", creationResponse.getType());
        assertEquals(String.valueOf(((UserRequest)container.getVar("userRequest"))
                .getId()), creationResponse.getMessage());
    }

    @Then("the user should have the correct data")
    public void theUserShouldHaveTheCorrectData() {
        ResponseDriver<UserResponse> response = (ResponseDriver<UserResponse>)
                container.getVar("response");
        UserResponse userResponse = response.getResponse();
        UserRequest userRequest = (UserRequest)container.getVar("userRequest");
        assertEquals(userRequest.getUsername(), userResponse.getUsername());
        assertEquals(userRequest.getFirstName(), userResponse.getFirstName());
        assertEquals(userRequest.getLastName(), userResponse.getLastName());
        assertEquals(userRequest.getEmail(), userResponse.getEmail());
        assertEquals(userRequest.getPassword(), userResponse.getPassword());
        assertEquals(userRequest.getPhone(), userResponse.getPhone());
        assertEquals(userRequest.getUserStatus(), userResponse.getUserStatus());
    }

    @Then("I should see that the user is logged in")
    public void iShouldSeeThatTheUserIsLoggedIn() {
        ResponseDriver<LoginResponse> response = (ResponseDriver<LoginResponse>)
                container.getVar("response");
        LoginResponse loginResponse = response.getResponse();
        assertEquals(200, loginResponse.getCode());
        assertNotEquals("", loginResponse.getType());
        assertTrue(loginResponse.getMessage().contains("logged in user session:"));
    }

    @Then("the message should be {string}")
    public void theMessageShouldBe(String msg) {
        ResponseDriver<LogoutResponse> response = (ResponseDriver<LogoutResponse>)
                container.getVar("response");
        assertEquals(msg, response.getResponse().getMessage());
    }

    @Then("the user data is updated")
    public void theUserDataIsUpdated() {
        UpdateResponse updateResponse = ((ResponseDriver<UpdateResponse>)
                container.getVar("response")).getResponse();
        UserRequest updateRequest = (UserRequest)container.getVar("userRequest");
        ResponseDriver<UserResponse> updatedUser = userEndpoint.getUserByUsername(
                ((UserRequest)container.getVar("userRequest")).getUsername());

        assertEquals(200, updateResponse.getCode());
        assertNotEquals("", updateResponse.getType());
        assertEquals(String.valueOf(updateRequest.getId()),updateResponse.getMessage());

        compareUserAfterChange(updateRequest,updatedUser.getResponse());
    }

    @Then("the users should be created")
    public void theUsersShouldBeCreated() {
        ResponseDriver<CreationResponse> response = (ResponseDriver<CreationResponse>)
                container.getVar("response");
        CreationResponse creationResponse = response.getResponse();
        assertEquals(200, creationResponse.getCode());
        assertNotEquals("", creationResponse.getType());
        assertEquals("ok", creationResponse.getMessage());

        UserRequest[] userRequests = (UserRequest[]) container.getVar("userRequest");
        for (UserRequest userRequest : userRequests) {
            UserResponse responseUser = (userEndpoint.getUserByUsername(
                    userRequest.getUsername())).getResponse();
            assertEquals(userRequest.getUsername(), responseUser.getUsername());
            assertEquals(userRequest.getFirstName(), responseUser.getFirstName());
            assertEquals(userRequest.getLastName(), responseUser.getLastName());
            assertEquals(userRequest.getEmail(), responseUser.getEmail());
            assertEquals(userRequest.getPassword(), responseUser.getPassword());
            assertEquals(userRequest.getPhone(), responseUser.getPhone());
            assertEquals(userRequest.getUserStatus(), responseUser.getUserStatus());
        }
    }

    @After(value = "@User")
    public void afterUser(){
        try{
            ResponseDriver<DeleteResponse> response =  userEndpoint.deleteUser(
                    ((UserRequest)container.getVar("userRequest")).getUsername());
        }catch (Exception e){
            System.out.println("User not deleted");
        }
    }

    // private methods region
    private UserRequest prepareUserRequest(int userId) {
        return new UserRequest(userId, "username" + userId,
                "firstName", "lastName",
                "email", "password",
                "phone", 1);
    }

    private void compareUserAfterChange(UserRequest userRequest, UserResponse userResponse) {
        assertEquals(userRequest.getUsername(), userResponse.getUsername());
        assertEquals(userRequest.getFirstName(), userResponse.getFirstName());
        assertEquals(userRequest.getLastName(), userResponse.getLastName());
        assertEquals(userRequest.getEmail(), userResponse.getEmail());
        assertEquals(userRequest.getPassword(), userResponse.getPassword());
        assertEquals(userRequest.getPhone(), userResponse.getPhone());
        assertEquals(userRequest.getUserStatus(), userResponse.getUserStatus());
    }
}
