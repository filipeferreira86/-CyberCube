package com.cybcube.models.api.request;

import com.cybcube.models.api.request.auxiliar.Category;

public class AddPet {

    public AddPet() {
    }

    public AddPet(int id, Category category, String name, String[] photoUrls, Category[] tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    private int id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Category[] tags;
    private String status;

    public int getId() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPhotoUrls(String[] photoUrls) {
        this.photoUrls = photoUrls;
    }

    public void setTags(Category[] tags) {
        this.tags = tags;
    }
}
