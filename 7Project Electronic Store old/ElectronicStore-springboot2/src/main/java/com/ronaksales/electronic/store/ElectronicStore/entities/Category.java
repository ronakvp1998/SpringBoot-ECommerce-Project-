package com.ronaksales.electronic.store.ElectronicStore.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {

    @Id
    @Column(name = "id")
    private String categoryId;
    // title cannot store null value and max length id 60 char
    @Column(name = "category_title", length = 60,nullable = false)
    private String title;
    @Column(name = "category_desc", length = 500)
    private String description;
    private String coverImage;

    // mapping for list  of products
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();

}
