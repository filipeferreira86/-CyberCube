package com.cybcube.utils;

import com.cybcube.models.api.request.pets.AddPet;
import com.cybcube.models.api.request.pets.auxiliar.Category;
import com.cybcube.models.data.api.config.ApiConfig;
import com.cybcube.models.data.ui.config.Config;
import com.cybcube.models.data.ui.model.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.exception.CucumberException;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

public class Container {
    private WebDriver driver;
    public Config config;
    public ApiConfig apiConfig;
    public Login login;
    private final boolean headless;
    public HashMap<String, Object> bag = new HashMap<>();
    private Scenario scenario;

    public Container() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            config = mapper.readValue(new File("src/test/resources/data/config.json"),
                    Config.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e);
        }


        try {
            apiConfig = mapper.readValue(new File("src/test/resources/data/apiConfig.json"),
                    ApiConfig.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e);
        }

        this.login = new Login();

        headless = config.browserData.getHeadless();
    }

    public WebDriver getDriver(){
        if(!haveDriver()){
            driver = createDriver();
        }
        return driver;
    }

    public void quitDriver(Scenario c){
        if(haveDriver()){
            if(c.isFailed())
                c.attach(capture(),"image/png", "failed_" + c.getId());
            driver.quit();
        }
    }

    public boolean haveDriver(){
        return driver != null;
    }

    public void setValidUsername() {
        login.setUsername(config.envData.getValidLogin().getUsername());
    }

    public void setEmptyUsername() {
        login.setUsername("");
    }

    public void setInvalidUsername() {
        login.setUsername(config.envData.getInvalidLogin().getUsername());
    }

    public void setValidPassword() {
        login.setPassword(config.envData.getValidLogin().getPassword());
    }

    public void setInvalidPassword() {
        login.setPassword(config.envData.getInvalidLogin().getPassword());
    }

    public void setEmptyPassword() {
        login.setPassword("");
    }



    public void setVar(String name, Object item){
        bag.put(name, item);
    }

    public Object getVar(String name){
        return bag.get(name);
    }

    // private methods

    private WebDriver createDriver(){
        WebDriver d;
        switch (config.browserData.getBrowser().toLowerCase()){
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments( headless? "--headless" : "start-maximized" );
                d = new ChromeDriver(options);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments( headless? "headless" : "start-maximized" );
                d = new EdgeDriver(edgeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments( headless? "-headless" : "start-maximized" );
                d = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new CucumberException("Invalid driver: " + config.browserData.getBrowser() + ". Please use one of: chrome, edge or firefox");
        }
        return d;
    }

    private byte[] capture() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        TakesScreenshot ts = (TakesScreenshot)driver;

        return ts.getScreenshotAs(OutputType.BYTES);
    }

    public void setScenario(Scenario scenario) {
        if(this.scenario == null)
            this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public AddPet getAddPetBody() {
        return new AddPet(
                1,
                new Category(1, "Dogs"),
                "Rex",
                new String[]{"https://www.google.com"},
                new Category[]{new Category(1, "Dog")},
                "available"
        );
    }

}
