package com.cybcube.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json"},
        features = {
                // pets
                "src/test/resources/features/api/pets/addPictureToPet.feature",
                "src/test/resources/features/api/pets/addPet.feature",
                "src/test/resources/features/api/pets/deletePet.feature",
                "src/test/resources/features/api/pets/getPetById.feature",
                "src/test/resources/features/api/pets/getPetByStatus.feature",
                "src/test/resources/features/api/pets/updatePet.feature",
                // store
                "src/test/resources/features/api/store/createOrder.feature",
                "src/test/resources/features/api/store/deleteOrder.feature",
                "src/test/resources/features/api/store/getOrderById.feature",
                "src/test/resources/features/api/store/getStoreInventory.feature",
                // user
                "src/test/resources/features/api/user/createUser.feature",
                "src/test/resources/features/api/user/deleteUser.feature",
                "src/test/resources/features/api/user/getUserByName.feature",
                "src/test/resources/features/api/user/login.feature",
                "src/test/resources/features/api/user/logout.feature",
                "src/test/resources/features/api/user/updateUser.feature"
        },
        glue = "com.cybcube.steps",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunnerApi {
}
