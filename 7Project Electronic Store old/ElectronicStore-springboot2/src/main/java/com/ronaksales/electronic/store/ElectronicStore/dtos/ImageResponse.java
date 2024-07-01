package com.ronaksales.electronic.store.ElectronicStore.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ImageResponse {
    private String imageName;
        private String message;
        private boolean success;
        private HttpStatus status;
}


