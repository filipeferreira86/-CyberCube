package com.cybcube.models.pages;

import com.cybcube.models.data.Product;
import com.cybcube.utils.Container;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CartPage extends Base{

    private final Container container;

    private final By cartItems = new By.ByClassName("cart_item");
    private final By lblItemName = new By.ByClassName("inventory_item_name");
    private final By lblItemDescription = new By.ByClassName("inventory_item_desc");
    private final By lblItemPrice = new By.ByClassName("inventory_item_price");
    private final By btnRemoveItem = new By.ById("remove-sauce-labs-backpack");
    private final By cartList = new By.ByClassName("cart_list");
    private final By btnCheckout = new By.ById("checkout");

    public CartPage(Container container) throws Exception {
        super(container.getDriver());
        this.container = container;
    }

    public void checkUrl() {
        super.checkUrl(container.config.pages.getCartPage());
    }

    public void checkItem(){
        Product expectedItem = (Product) container.getVar("item");
        WebElement item = findList(cartItems).getFirst();
        assertEquals(expectedItem.getName(), getText(item, lblItemName));
        assertEquals(expectedItem.getDescription(), getText(item, lblItemDescription));
        assertEquals(expectedItem.getPrice(), getText(item, lblItemPrice));
    }

    public void removeItem(int index){
        List<WebElement> el = findList(find(cartList), cartItems);
        el.remove(index);
        container.addVar("itemList", el);
        click(findList(cartItems).get(index), btnRemoveItem);
    }

    public void checkItems(){
        List<WebElement> items = findList(cartItems);
        for(int c = 0; c < items.size(); c++) {
            @SuppressWarnings("unchecked")
            List<Product> i = (List<Product>) container.getVar("items");
            WebElement item = items.get(c);
            assertEquals(i.get(c).getName(), getText(item, lblItemName));
            assertEquals(i.get(c).getDescription(), getText(item, lblItemDescription));
            assertEquals(i.get(c).getPrice(), getText(item, lblItemPrice));
        }
    }

    public void checkThatItemWasRemoved() {
        assertEquals(container.getVar("itemList"), findList(find(cartList), cartItems));
    }

    public void checkEmptyCart(){
        assertEquals("Cart should be empty but isn't",
                0, findList(find(cartList), cartItems).size());
    }

    public void goToCheckout(){
        click(btnCheckout);
    }
}
