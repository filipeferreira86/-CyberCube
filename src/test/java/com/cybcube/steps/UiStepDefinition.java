package com.cybcube.steps;

import com.cybcube.models.pages.*;
import com.cybcube.utils.Container;
import io.cucumber.core.exception.CucumberException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UiStepDefinition{

    WebDriver driver;

    public final Container container;
    private LoginPage loginPage;
    private HomePage homePage;
    private CartPage cartPage;
    private ItemPage itemPage;
    private CheckoutFormPage checkoutFormPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutComplete checkoutComplete;

    public UiStepDefinition(Container container){
        this.container = container;
    }

    @Before("@ui")
    public void before(){
        driver = container.getDriver();
    }

    // Given group

    @Given("that I have a valid username")
    public void setValidUsername() {
        container.setValidUsername();
    }

    @Given("a valid password")
    public void setValidPassword() {
        container.setValidPassword();
    }

    @Given("that I have a {string} username")
    public void thatIHaveAUsername(String username) {
        switch (username) {
            case "valid" -> container.setValidUsername();
            case "invalid" -> container.setInvalidUsername();
            case "empty" -> container.setEmptyUsername();
            default -> throw new CucumberException("Invalid username type");
        }
    }

    @Given("a {string} password")
    public void aPassword(String password) {
        switch (password) {
            case "valid" -> container.setValidPassword();
            case "invalid" -> container.setInvalidPassword();
            case "empty" -> container.setEmptyPassword();
            default -> throw new CucumberException("Invalid password type");
        }
    }

    @Given("the error message is presented")
    public void theErrorMessageIsPresented() {
        loginPage.checkErrorMsg();
    }

    @Given("that Im at the home page")
    @Given("Im logged in")
    public void thatImOnTheHomePage() throws Exception {
        container.setValidUsername();
        container.setValidPassword();
        loginPage = new LoginPage(container);
        loginPage.executeLogin();
        homePage = new HomePage(container);
        homePage.checkSuccessfulLogin();
    }

    @Given("that Im on the cart page")
    public void thatImOnTheCartPage() throws Exception {
        homePage.goToCart();
        cartPage = new CartPage(container);
    }

    // When group

    @When("I try to login on the page")
    @Given("I try to login")
    public void iTryToLoginOnThePage() throws Exception {
        loginPage = new LoginPage(container);
        loginPage.executeLogin();
    }

    @When("I click to close the error message")
    public void iClickToCloseTheErrorMessage() {
        loginPage.closeErrorMessage();
    }

    @When("I click on add to cart on the {int}th item")
    @And("the {int} item is in the cart")
    public void iClickOnAddToCartOnTheThItem(int item) {
        homePage.addItemToCart(item);
    }

    @When("I go to the cart page")
    public void iGoToTheCartPage() throws Exception {
        homePage.goToCart();
        cartPage = new CartPage(container);
    }

    @When("Add {int} random items")
    public void addRandomItems(int numberOfItems) {
        homePage.addItemsToCart(getItemsToAdd(numberOfItems, homePage.getNumberOfItems()));
    }

    @When("I access the {int}st item page")
    @Given("Im on {int} item page")
    public void iAccessTheFirstItemPage(int index) throws Exception {
        homePage.goToItemPage(index);
        itemPage = new ItemPage(container);
    }

    @When("I add the item to the cart")
    public void iAddTheItemToTheCart() {
        itemPage.addItemToCart();
    }

    @When("I click to remove the item")
    public void iClickToRemoveTheItem() {
        cartPage.removeItem(0);
    }

    @When("I click to remove the item {int}")
    public void iClickToRemoveTheItem(int itemToRemove) {
        cartPage.removeItem(itemToRemove-1);
    }

    @When("I click to remove the item {int} at the home page")
    public void iClickToRemoveTheItemAtTheHomePage(int itemNumber) {
        homePage.removeItem(itemNumber);
    }

    @When("I click to remove the item at the item page")
    public void iClickToRemoveTheItemAtTheItemPage() {
        itemPage.removeItem();
    }

    @When("I apply the order {string}")
    public void iApplyTheOrderByPriceFilter(String order) {
        homePage.applyOrder(order);
    }

    @When("I logout")
    public void iLogout() {
        homePage.logout();
    }

    @When("I try to access the home page")
    public void iTryToAccessTheHomePage() throws Exception {
        homePage = new HomePage(container);
        homePage.navigateToHome();
    }

    @When("I click the reset button")
    public void iClickTheResetButton() {
        homePage.reset();
    }

    @When("I click on the checkout button")
    public void iClickOnTheCheckoutButton() {
        cartPage.goToCheckout();
        checkoutFormPage = new CheckoutFormPage(container);
        checkoutFormPage.checkUrl();
    }

    @When("I fill name with {string}")
    public void iFillNameWith(String name) {
        checkoutFormPage.enterName(name);
    }

    @When("I fill last name with {string}")
    public void iFillLastNameWith(String lastName) {
        checkoutFormPage.enterLastName(lastName);
    }

    @When("I fill zip-postal code with {string}")
    public void iFillZipPostalCodeWith(String zip) {
        checkoutFormPage.enterZip(zip);
    }

    @When("I click on the continue button")
    public void iClickOnTheContinueButton() {
        checkoutFormPage.clickContinue();
        checkoutOverviewPage = new CheckoutOverviewPage(container);
    }

    // Then group

    @Then("the login is accepted")
    @Then("I should see the home page")
    public void iTheLoginIsAccepted() throws Exception {
        homePage = new HomePage(container);
        homePage.checkSuccessfulLogin();
    }

    @Then("the home page is presented")
    public void theHomePageIsPresented() {
        homePage.checkAppLogo();
    }

    @Then("the login is rejected")
    public void theLoginIsRejected() {
        loginPage.checkUrl();
    }

    @Then("the error message {string} is presented")
    public void theErrorMessageIsPresented(String msg) {
        loginPage.checkFailedLogin(msg);
    }

    @Then("the error message is not presented")
    public void theErrorMessageIsNotPresented() {
        loginPage.checkErrorMsgGone();
    }

    @Then("the item is presented with correct data")
    public void theItemIsPresented() {
        cartPage.checkItem();
    }

    @Then("the items are presented with correct data")
    public void theItemsArePresentedWithCorrectData() {
        cartPage.checkItems();
    }

    @Then("the item is removed")
    public void theItemIsRemoved() {
        cartPage.checkThatItemWasRemoved();
    }

    @Then("cart should be empty")
    public void cartShouldBeEmpty() {
        cartPage.checkEmptyCart();
    }

    @Then("the products should be sorted by {string}")
    public void theProductsShouldBeSortedBy(String order) {
        homePage.checkOrder(order);
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        loginPage.checkUrl();
    }

    @Then("the item {int} should be ready to be added to the cart")
    public void theItemShouldBeReadyToBeAddedToTheCart(int item) {
        homePage.checkAddButton(item);
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() throws Exception {
        homePage.goToCart();
        cartPage = new CartPage(container);
        cartPage.checkEmptyCart();
    }

    @After
    public void after(Scenario c){
        this.container.quitDriver(c);
    }

    // private methods
    private List<Integer> getItemsToAdd(int numberOfItems, int size){
        Random rng = new Random();
        List<Integer> indexes = new ArrayList<>();
        if(size<numberOfItems){
            throw new CucumberException("Number of items to add cannot be bigger than the items in the site");
        }
        while(numberOfItems > indexes.size()){
            int add = rng.nextInt(size);
            if(!indexes.contains(add)){
                indexes.add(add);
            }
        }

        return indexes;
    }

    @Then("I should see the checkout overview page")
    public void iShouldSeeTheCheckoutOverviewPage() {
        checkoutOverviewPage.checkUrl();
    }

    @Then("I should see the item data")
    public void iShouldSeeTheItemData() {
        checkoutOverviewPage.checkItem();
    }

    @Then("I should see the Payment Information")
    public void iShouldSeeThePaymentInformation() {
        checkoutOverviewPage.checkPaymentInformation();
    }

    @And("I should see the Shipping Information")
    public void iShouldSeeTheShippingInformation() {
        checkoutOverviewPage.checkShippingInformation();
    }

    @And("I should see the tax price")
    public void iShouldSeeTheTaxPrice() {
        checkoutOverviewPage.checkTax();
    }

    @And("I should see the total price")
    public void iShouldSeeTheTotalPrice() {
        checkoutOverviewPage.checkTotalPrice();
    }

    @And("I click on the finish button")
    public void iClickOnTheFinishButton() {
        checkoutOverviewPage.clickFinishButton();
        checkoutComplete = new CheckoutComplete(container);

    }

    @Then("I should see the checkout complete page")
    public void iShouldSeeTheCheckoutCompletePage() {
        checkoutComplete.checkCheckoutComplete();
    }

    @Then("error message should be displayed {string}")
    public void errorMessageShouldBeDisplayed(String errorMessage) {
        checkoutFormPage.checkErrorMessage(errorMessage);
    }

    @And("I click on the cancel button")
    public void iClickOnTheCancelButton() {
        checkoutFormPage.clickCancel();
    }

    @Then("I should see the cart page")
    public void iShouldSeeTheCartPage() {
        cartPage.checkUrl();
    }
}
