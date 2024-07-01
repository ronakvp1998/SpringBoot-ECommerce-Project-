package com.ronaksales.electronic.store.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private String productId;
    private String title;
    @Column(length = 10000)
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;

    // Mapping for category
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id_fk")
    private Category category;



}
