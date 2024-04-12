package com.cybcube.models.api.request.pets.auxiliar;

public class Category {
    public double id;
    public String name;

    public Category() {
    }

    public Category(double id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(double id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
