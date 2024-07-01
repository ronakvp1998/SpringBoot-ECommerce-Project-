package com.ronaksales.electronic.store.ElectronicStore.dtos;

import com.ronaksales.electronic.store.ElectronicStore.entities.CartItem;
import com.ronaksales.electronic.store.ElectronicStore.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CartDto {
    private String cartId;
    private Date createdAt;

    //    private int totalAmount;
    private int quantity;

    // mapping with user
    private UserDto user;

    // mapping with cart item
    private List<CartItemDto> items = new ArrayList<>();

}
