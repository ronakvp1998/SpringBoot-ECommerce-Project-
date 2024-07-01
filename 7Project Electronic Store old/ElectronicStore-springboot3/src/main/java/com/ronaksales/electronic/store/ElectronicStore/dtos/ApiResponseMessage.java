package com.ronaksales.electronic.store.ElectronicStore.dtos;


import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMessage {
    private String message;
    private boolean success;
    private HttpStatus status;
}
