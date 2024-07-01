package com.ronaksales.electronic.store.ElectronicStore.dtos;

import com.ronaksales.electronic.store.ElectronicStore.entities.Order;
import com.ronaksales.electronic.store.ElectronicStore.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {

    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

//    private OrderDto order;


}
