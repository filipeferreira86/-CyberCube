package com.cybcube.models.pages;

import com.cybcube.models.data.ui.model.Product;
import com.cybcube.utils.Container;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HomePage extends Base{

    private final String url;
    private final Container container;

    private final By title = new By.ByClassName("app_logo");
    private final By items = new By.ByXPath("//div[@data-test='inventory-item']");
    private final By btnAddItem = new By.ByClassName("btn_primary");
    private final By btnRemoveItem = new By.ById("remove-sauce-labs-backpack");
    private final By btnCart = new By.ByClassName("shopping_cart_link");
    private final By lblItemName = new By.ByClassName("inventory_item_name");
    private final By lblItemDescription = new By.ByClassName("inventory_item_desc");
    private final By lblItemPrice = new By.ByClassName("inventory_item_price");
    private final By orderBy = new By.ByClassName("product_sort_container");
    private final By menuHamburger = new By.ById("react-burger-menu-btn");
    private final By logoutButton = new By.ById("logout_sidebar_link");
    private final By btnReset = new By.ById("reset_sidebar_link");
    private final By closeMenu = new By.ById("react-burger-cross-btn");

    public HomePage(Container container) throws Exception {
        super(container.getDriver());
        this.url = container.config.pages.getHomePage();
        this.container = container;
    }

    public void checkSuccessfulLogin() {
        super.checkUrl(url);
    }

    public void navigateToHome(){
        navigateTo(url);
    }

    public void goToItemPage(int index){
        click(lblItemName);
    }

    public void addItemToCart(int itemNumber){
        List<WebElement> itemsList = findList(items);
        click(itemsList.get(itemNumber - 1), btnAddItem);

        // Adding item data to check later
        container.setVar("item", new Product(itemNumber - 1,
                        getText(itemsList.get(itemNumber - 1), lblItemName),
                        getText(itemsList.get(itemNumber - 1), lblItemDescription),
                        getText(itemsList.get(itemNumber - 1), lblItemPrice)
        ));
    }

    public void addItemsToCart(List<Integer> itemsToAdd){
        List<WebElement> itemsList = findList(items);

        List<Product> products = new ArrayList<>();

        for(int i : itemsToAdd){
            click(itemsList.get(i), btnAddItem);
            products.add(new Product(
                    i, getText(itemsList.get(i), lblItemName),
                    getText(itemsList.get(i), lblItemDescription),
                    getText(itemsList.get(i), lblItemPrice)
                    )
            );
        }
        container.setVar("items", products);
    }

    public void removeItem(int itemNumber){
        List<WebElement> itemsList = findList(items);
        click(itemsList.get(itemNumber - 1), btnRemoveItem);

    }

    public int getNumberOfItems(){
        return findList(items).size();
    }

    public void checkAppLogo(){
        assertEquals(getText(title), "Swag Labs");
    }

    public void goToCart(){
        click(btnCart);
    }

    public void applyOrder(String text) {
        Select order = new Select(find(orderBy));
        order.selectByVisibleText(text);
    }

    public void checkOrder(String order) {
        List<WebElement> itemsList = findList(items);
        List<String> list = new ArrayList<>();
        if(order.equals("Price (low to high)") || order.equals("Price (high to low)")){
            for (WebElement item : itemsList) {
                list.add(getText(item, lblItemPrice));
            }
        } else if (order.equals("Name (A to Z)") || order.equals("Name (Z to A)")){
            for (WebElement item : itemsList) {
                list.add(getText(item, lblItemName));
            }

        }

        List<String> sortedList = new ArrayList<>(list);
        switch (order) {
            case "Price (low to high)" -> sortedList.sort((o1, o2) -> {
                double price1 = Double.parseDouble(o1.substring(1));
                double price2 = Double.parseDouble(o2.substring(1));
                return Double.compare(price1, price2);
            });
            case "Price (high to low)" -> sortedList.sort((o1, o2) -> {
                double price1 = Double.parseDouble(o1.substring(1));
                double price2 = Double.parseDouble(o2.substring(1));
                return Double.compare(price2, price1);
            });
            case "Name (A to Z)" -> sortedList.sort(String::compareTo);
            case "Name (Z to A)" -> sortedList.sort((o1, o2) -> o2.compareTo(o1));
        }

        assertEquals(list, sortedList);
    }

    public void logout() {
        click(menuHamburger);
        waitElement(logoutButton);
        click(logoutButton);
    }

    public void reset() {
        click(menuHamburger);
        waitElement(btnReset);
        click(btnReset);
        click(closeMenu);
    }

    public void checkAddButton(int item) {
        List<WebElement> itemsList = findList(items);
        assertEquals(1, findList(itemsList.get(item - 1), btnAddItem).size());
        assertEquals(0, findList(itemsList.get(item-1), btnRemoveItem).size());
    }
}