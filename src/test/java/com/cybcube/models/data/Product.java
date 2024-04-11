package com.cybcube.models.data;

public class Product {
    int index;
    private final String name;
    private final String description;
    private final String price;

    public Product(int index, String name, String description, String price) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

}
