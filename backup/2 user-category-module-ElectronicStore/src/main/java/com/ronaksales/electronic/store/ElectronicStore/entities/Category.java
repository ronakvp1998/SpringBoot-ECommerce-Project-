package com.ronaksales.electronic.store.ElectronicStore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

}
