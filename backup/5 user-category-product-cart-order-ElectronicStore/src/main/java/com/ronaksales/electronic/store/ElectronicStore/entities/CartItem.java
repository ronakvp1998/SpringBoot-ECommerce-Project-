package com.ronaksales.electronic.store.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "cart_item")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    // mapping OneToOne unidirectional with products
    @OneToOne
    @JoinColumn(name = "product_id_fk")
    private Product product;

    private int quantity;

    // product price * quantity
    private int totalPrice;

    // mapping with cart bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id_fk")
    private Cart cart;
}
