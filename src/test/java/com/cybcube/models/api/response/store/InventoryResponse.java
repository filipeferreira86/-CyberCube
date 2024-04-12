package com.cybcube.models.api.response.store;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;

public class InventoryResponse {
    private HashMap<String, Integer> inventory;

    public InventoryResponse() {
        this.inventory = new HashMap<>();
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    @JsonAnySetter
    public void setInventory(String key, Integer value) {
        this.inventory.put(key, value);
    }
}