package com.cybcube.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json"},
        features = {"src/test/resources/features/api/addPictureToPet.feature",
                "src/test/resources/features/api/addPet.feature",
                "src/test/resources/features/api/deletePet.feature",
                "src/test/resources/features/api/getPetById.feature",
                "src/test/resources/features/api/getPetByStatus.feature",
                "src/test/resources/features/api/updatePet.feature"
        },
        glue = "com.cybcube.steps",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunnerApi {
}
