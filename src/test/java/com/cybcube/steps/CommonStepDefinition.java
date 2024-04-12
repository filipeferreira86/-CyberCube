package com.cybcube.steps;

import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.utils.Container;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;

public class CommonStepDefinition{

    Container container;

    public CommonStepDefinition(Container container){
        this.container = container;
    }
    @Then("the response should be {int}")
    public void theResponseShouldBe(int status) {
        assertEquals(status, ((ResponseDriver<?>)container.getVar("response"))
                .getResponseCode());
    }

}
