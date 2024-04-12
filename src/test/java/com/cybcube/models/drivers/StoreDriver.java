package com.cybcube.models.drivers;

import com.cybcube.models.api.base.DeleteResponse;
import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.models.api.response.store.InventoryResponse;
import com.cybcube.models.api.response.store.OrderResponse;
import com.cybcube.utils.Container;

public class StoreDriver extends BaseDriver {

    public StoreDriver(Container container) {
        super(container.apiConfig, container.getScenario());
    }

    public ResponseDriver<InventoryResponse> getStoreInventory() {
        return new ResponseDriver<>(sendRequest
                ("store/inventory", "GET"), InventoryResponse.class);
    }

    public ResponseDriver<OrderResponse> createOrder(Object body) {
        return new ResponseDriver<>(sendRequest
                ("store/order", "POST", body), OrderResponse.class);
    }
    public ResponseDriver<OrderResponse> getOrderById(int orderId) {
        return new ResponseDriver<>(sendRequest
                ("store/order/" + orderId, "GET"), OrderResponse.class);
    }

    public ResponseDriver<DeleteResponse> deleteOrderById(int orderId){
        return new ResponseDriver<>(sendRequest
                ("store/order/" + orderId, "DELETE"), DeleteResponse.class);
    }

}
