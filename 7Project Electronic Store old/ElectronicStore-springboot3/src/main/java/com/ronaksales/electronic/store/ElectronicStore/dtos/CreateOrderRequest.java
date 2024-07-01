package com.ronaksales.electronic.store.ElectronicStore.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    @NotBlank(message = "cart id is required")
    private String cartId;
    @NotBlank(message = "user id is required")
    private String userId;
    // PENDING,DISPATCHED, DELIVERED
    @NotBlank(message = "order status is required")
    private String orderStatus = "PENDING";
    //NotPaid, Paid
    @NotBlank(message = "payment status is required")
    private String paymentStatus = "NOTPAID";
    @NotBlank(message = "Billing Address is required")
    private String billingAddress;
    @NotBlank(message = "Billing Phone is required")
    private String billingPhone;
    @NotBlank(message = "Billing Name is required")
    private String billingName;


}
