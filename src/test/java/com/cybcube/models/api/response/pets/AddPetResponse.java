package com.cybcube.models.api.response.pets;

import com.cybcube.models.api.request.pets.auxiliar.Category;

public class AddPetResponse {


    public AddPetResponse() {
    }

    public AddPetResponse(String id, Category category, String name, String[] photoUrls, Category[] tags, String status) {
        this.id = Double.parseDouble(id);
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    private double id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Category[] tags;
    private String status;
    private int responseCode;

    public double getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public Category[] getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }

    public void setId(double id) {
        this.id = id;
    }


}
