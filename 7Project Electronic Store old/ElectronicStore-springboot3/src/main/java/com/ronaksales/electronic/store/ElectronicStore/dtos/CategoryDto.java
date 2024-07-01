package com.ronaksales.electronic.store.ElectronicStore.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
        private String categoryId;
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 4, message = "Title must be of minimun 4 characters !!")
        private String title;
        @NotBlank(message = "Description cannot be blank")
        private String description;
        private String coverImage;


}
