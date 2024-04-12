package com.cybcube.models.api.response.store;

import com.cybcube.models.api.base.Order;

import java.util.Date;

public class OrderResponse extends Order {


    public OrderResponse(){
        super(0, 0, 0, null, null, false);
    }

    public OrderResponse(int id, int petId,
                         int quantity, String shipDate,
                         String status, boolean complete) {
        super(id, petId, quantity, shipDate, status, complete);
        }
}
