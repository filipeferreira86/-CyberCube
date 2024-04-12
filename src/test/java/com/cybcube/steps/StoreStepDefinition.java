package com.cybcube.steps;

import com.cybcube.models.api.base.DeleteResponse;
import com.cybcube.models.api.request.store.OrderRequest;
import com.cybcube.models.api.response.pets.AddPetResponse;
import com.cybcube.models.api.response.pets.ErrorResponse;
import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.models.api.response.store.InventoryResponse;
import com.cybcube.models.api.response.store.OrderResponse;
import com.cybcube.models.drivers.StoreDriver;
import com.cybcube.utils.Container;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class StoreStepDefinition {

    private final Container container;
    private StoreDriver storeEndpoint;

    public StoreStepDefinition(Container container) {
        this.container = container;
    }


    @Before(value = "@Store", order=0)
    public void beforeStore(){
        storeEndpoint = new StoreDriver(container);
    }

    // Given region
    @Given("the data to create a new order is prepared")
    public void iPrepareTheDataToCreateANewOrder() {
        ResponseDriver<AddPetResponse> response = (ResponseDriver<AddPetResponse>)
                container.getVar("response");
        container.setVar("orderRequest",
                createOrderRequest(response.getResponse().getId()));
    }

    @Given("the pet does not exist")
    public void thePetDoesNotExist() {
        OrderRequest body = (OrderRequest) container.getVar("orderRequest");
        body.setPetId(877546);
        container.setVar("orderRequest", body);
    }

    @Given("the quantity is invalid")
    public void theQuantityIsInvalid() {
        OrderRequest body = (OrderRequest) container.getVar("orderRequest");
        body.setQuantity(-1);
        container.setVar("orderRequest", body);
    }

    @Given("I have an order that does not exist")
    public void iHaveAnOrderThatDoesNotExist() {
        OrderRequest body = (OrderRequest) container.getVar("orderRequest");
        body.setId(877546);
        container.setVar("orderRequest", body);
    }

    @Given("I have an invalid order Id")
    public void iHaveAnInvalidOrderId() {
        OrderRequest body = (OrderRequest) container.getVar("orderRequest");
        body.setId(-1);
        container.setVar("orderRequest", body);
    }

    // When region
    @When("I create a new order")
    @Given("I send a POST request to add the order")
    public void iCreateANewOrder() {
        ResponseDriver<OrderResponse> response = storeEndpoint.createOrder(
                container.getVar("orderRequest"));
        container.setVar("response", response);
    }

    @When("I delete the order")
    public void iDeleteTheOrder() {
        ResponseDriver<DeleteResponse> response = storeEndpoint.deleteOrderById(
                ((OrderRequest)(container.getVar("orderRequest"))).getId());
        container.setVar("response", response);
    }

    @When("I get the order by Id")
    public void iGetTheOrderById() {
        OrderRequest orderRequest = (OrderRequest) container.getVar("orderRequest");
        ResponseDriver<OrderResponse> response = storeEndpoint.getOrderById(orderRequest.getId());
        container.setVar("response", response);
    }

    @When("I request the inventory of the store")
    public void iRequestTheInventoryOfTheStore() {
        ResponseDriver<InventoryResponse> response = storeEndpoint.getStoreInventory();
        container.setVar("response", response);
    }

    // Then region
    @Then("the order should be created")
    public void theOrderShouldBeCreated() {
        ResponseDriver<OrderResponse> response = (ResponseDriver<OrderResponse>)
                container.getVar("response");

        assertEquals(200,response.getResponseCode());
        OrderResponse orderResponse =
                (storeEndpoint.getOrderById(response.getResponse().getId())).getResponse();
        assertEquals(response.getResponse().getId(), orderResponse.getId());
        assertEquals(response.getResponse().getPetId(), orderResponse.getPetId(), 0.0);
        assertEquals(response.getResponse().getQuantity(), orderResponse.getQuantity());
        assertEquals(response.getResponse().getShipDate(), orderResponse.getShipDate());
        assertEquals(response.getResponse().getStatus(), orderResponse.getStatus());
        assertEquals(response.getResponse().isComplete(), orderResponse.isComplete());
    }

    @Then("the order should not be created")
    public void theOrderShouldNotBeCreated() {
        ResponseDriver<OrderResponse> response = (ResponseDriver<OrderResponse>)
                container.getVar("response");
        assertEquals(400,response.getResponseCode());
    }

    @Then("I should see the order details")
    public void iShouldSeeTheOrderDetails() {
        OrderRequest orderRequest = (OrderRequest) container.getVar("orderRequest");
        ResponseDriver<OrderResponse> response = (ResponseDriver<OrderResponse>) container.getVar("response");
        assertEquals(orderRequest.getId(), response.getResponse().getId());
        assertEquals(orderRequest.getPetId(), response.getResponse().getPetId(), 0.0);
        assertEquals(orderRequest.getQuantity(), response.getResponse().getQuantity());
        assertEquals(orderRequest.getShipDate(),response.getResponse().getShipDate());
        assertEquals(orderRequest.getStatus(), response.getResponse().getStatus());
        assertEquals(orderRequest.isComplete(), response.getResponse().isComplete());
    }

    @Then("the error message should be {string}")
    public void theErrorMessageShouldBe(String errorMessage) {
        ResponseDriver<ErrorResponse> response = (ResponseDriver<ErrorResponse>) container.getVar("response");
        assertEquals(errorMessage, response.getResponse().getMessage());
    }

    @Then("the response should contain the number of pets by status")
    public void theResponseShouldContainTheNumberOfPetsByStatus() {
        HashMap<String, Integer> inventoryResponse = ((ResponseDriver<InventoryResponse>)
                container.getVar("response")).getResponse().getInventory();

        HashMap<String, Integer> available = (HashMap<String, Integer>)
                container.getVar("numberOfPets");

        assertEquals(available.get("available"), inventoryResponse.get("available"));
        assertEquals(available.get("pending"), inventoryResponse.get("pending"));
        assertEquals(available.get("sold"), inventoryResponse.get("sold"));
    }

    @And("the order should be deleted")
    public void theOrderShouldBeDeleted() {
        int id = ((OrderRequest)container.getVar("orderRequest")).getId();
        ResponseDriver<OrderResponse> orderResponse = storeEndpoint.getOrderById(id);
        assertEquals(404, orderResponse.getResponseCode());
    }

    public void afterScenario(Scenario scenario) {
        try {
            OrderRequest order = (OrderRequest) container.getVar("orderRequest");
            storeEndpoint.deleteOrderById(order.getId());
        } catch (Exception e) {
            System.out.println("Order not deleted");
        }
    }

    // private methods
    private OrderRequest createOrderRequest(double petId){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0000");
        return new OrderRequest(1, petId,
                1, formatter.format(LocalDateTime.now()),
                "placed", true);
    }
}
