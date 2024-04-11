package com.cybcube.models.pages;

import com.cybcube.models.data.Product;
import com.cybcube.utils.Container;
import org.openqa.selenium.By;

import java.util.HashMap;

public class ItemPage extends Base{

    private final By lblItemName = new By.ByClassName("inventory_details_name");
    private final By lblItemDescription = new By.ByClassName("inventory_details_desc");
    private final By lblItemPrice = new By.ByClassName("inventory_details_price");
    private final By btnAddItem = new By.ById("add-to-cart");
    private final By btnRemoveItem = new By.ById("remove");
    private final Container container;

    public ItemPage(Container container) throws Exception {
        super(container.getDriver());
        this.container = container;
    }

    public void addItemToCart(){
        click(btnAddItem);

        // Adding item data to check later
        container.addVar("item", new Product(0,
                getText(lblItemName),
                getText(lblItemDescription),
                getText(lblItemPrice)));
    }

    public void removeItem() {
        click(btnRemoveItem);
    }
}
