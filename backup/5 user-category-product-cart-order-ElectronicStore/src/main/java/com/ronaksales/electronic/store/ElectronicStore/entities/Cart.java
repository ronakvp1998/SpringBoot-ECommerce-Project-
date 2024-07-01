package com.ronaksales.electronic.store.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "cart")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cart {

    @Id
    private String cartId;
    private Date createdAt;

//    private int totalAmount;
    private int quantity;

    // mapping OneToOne unidirectional with user
    @OneToOne
    private User user;

    // mapping with cart item bidirectional
//    orphanRemoval = true: This attribute specifies that if a CartItem is removed from the items collection,
//    it should be deleted from the database as well.
//    This is particularly useful when you want to ensure that there are no orphaned CartItem records in the database
//    that are not associated with any Cart.
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
}
