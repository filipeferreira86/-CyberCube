package com.cybcube.steps;

import com.cybcube.utils.Container;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Hooks {

    Container container;

    @Before
    public void before() throws Exception {
        this.container = new Container();
    }
}
