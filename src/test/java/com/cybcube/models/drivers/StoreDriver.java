package com.cybcube.models.drivers;

import com.cybcube.models.api.base.DeleteResponse;
import com.cybcube.models.api.response.pets.ResponseDriver;
import com.cybcube.models.api.response.store.InventoryResponse;
import com.cybcube.models.api.response.store.OrderResponse;
import com.cybcube.utils.Container;

public class StoreDriver extends BaseDriver {

    private final String INVENTORY_ENDPOINT = "store/inventory";
    private final String ORDER_ENDPOINT = "store/order";
    private final String ORDER_BY_ID_ENDPOINT = ORDER_ENDPOINT+"/%s";
    public StoreDriver(Container container) {
        super(container.apiConfig, container.getScenario());
    }

    public ResponseDriver<InventoryResponse> getStoreInventory() {
        return new ResponseDriver<>(sendRequest
                (INVENTORY_ENDPOINT, "GET"), InventoryResponse.class);
    }

    public ResponseDriver<OrderResponse> createOrder(Object body) {
        return new ResponseDriver<>(sendRequest
                (ORDER_ENDPOINT, "POST", body), OrderResponse.class);
    }
    public ResponseDriver<OrderResponse> getOrderById(int orderId) {
        return new ResponseDriver<>(sendRequest
                (String.format(ORDER_BY_ID_ENDPOINT, orderId), "GET"), OrderResponse.class);
    }

    public ResponseDriver<DeleteResponse> deleteOrderById(int orderId){
        return new ResponseDriver<>(sendRequest
                (String.format(ORDER_BY_ID_ENDPOINT, orderId), "DELETE"), DeleteResponse.class);
    }

}
