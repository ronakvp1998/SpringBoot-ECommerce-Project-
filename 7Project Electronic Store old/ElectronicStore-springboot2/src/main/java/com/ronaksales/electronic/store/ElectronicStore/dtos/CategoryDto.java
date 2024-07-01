package com.ronaksales.electronic.store.ElectronicStore.dtos;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
