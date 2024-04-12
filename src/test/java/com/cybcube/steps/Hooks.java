package com.cybcube.steps;

import com.cybcube.utils.Container;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Hooks{

    Container container;

    public Hooks(Container container) throws Exception {
        this.container = container;

    }

    @Before(order = -1)
    public void before(Scenario s){
        container.setScenario(s);
    }


}
