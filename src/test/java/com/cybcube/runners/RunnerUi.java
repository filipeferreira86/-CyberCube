package com.cybcube.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json"},
        features = {"src/test/resources/features/ui/login.feature",
                "src/test/resources/features/ui/addProduct.feature",
                "src/test/resources/features/ui/removeProduct.feature",
                "src/test/resources/features/ui/filter.feature",
                "src/test/resources/features/ui/logout.feature",
                "src/test/resources/features/ui/resetSite.feature",
                "src/test/resources/features/ui/checkout.feature"
        },
        glue = "com.cybcube.steps",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)

public class RunnerUi {

}
