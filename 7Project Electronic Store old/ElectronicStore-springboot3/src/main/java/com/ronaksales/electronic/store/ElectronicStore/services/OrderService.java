package com.ronaksales.electronic.store.ElectronicStore.services;

import com.ronaksales.electronic.store.ElectronicStore.dtos.CreateOrderRequest;
import com.ronaksales.electronic.store.ElectronicStore.dtos.OrderDto;
import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.UpdateOrderRequest;

import java.util.List;

public interface OrderService {

    // create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    // remove order
    void removeOrder(String order_id);

    // get orders for user
    List<OrderDto> getOrdersOfUser(String userId);

    // get orders
    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);

    // update order
    OrderDto updateOrder(String orderId, UpdateOrderRequest request);


}
