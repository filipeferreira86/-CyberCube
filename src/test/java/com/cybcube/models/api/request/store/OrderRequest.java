package com.cybcube.models.api.request.store;

import com.cybcube.models.api.base.Order;

import java.util.Date;

public class OrderRequest extends Order {

    public OrderRequest(int id, double petId, int quantity,
                        String shipDate, String status,
                        boolean complete) {
        super(id, petId, quantity, shipDate, status, complete);
    }
}
