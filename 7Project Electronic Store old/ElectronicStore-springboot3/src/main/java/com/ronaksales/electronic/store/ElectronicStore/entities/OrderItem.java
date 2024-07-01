package com.ronaksales.electronic.store.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "order_items")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    @OneToOne
    @JoinColumn(name = "product_id_fk")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id_fk")
    private Order order;

}
