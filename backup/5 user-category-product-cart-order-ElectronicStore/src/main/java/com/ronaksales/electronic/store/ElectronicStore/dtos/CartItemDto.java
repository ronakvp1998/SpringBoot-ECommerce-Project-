package com.ronaksales.electronic.store.ElectronicStore.dtos;

import com.ronaksales.electronic.store.ElectronicStore.entities.Cart;
import com.ronaksales.electronic.store.ElectronicStore.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CartItemDto {
    private int cartItemId;
    private ProductDto product;
    private int quantity;
    // product price * quantity
    private int totalPrice;

}
