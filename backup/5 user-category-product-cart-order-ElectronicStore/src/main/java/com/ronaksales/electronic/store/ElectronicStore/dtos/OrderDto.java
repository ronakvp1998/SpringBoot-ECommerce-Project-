package com.ronaksales.electronic.store.ElectronicStore.dtos;

import com.ronaksales.electronic.store.ElectronicStore.entities.OrderItem;
import com.ronaksales.electronic.store.ElectronicStore.entities.User;
import jakarta.persistence.*;
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
public class OrderDto {


    private String orderId;

    // PENDING,DISPATCHED, DELIVERED
    private String orderStatus = "PENDING";

    //NotPaid, Paid
    private String paymentStatus = "NOTPAID";

    private int orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderedDate = new Date();

    private Date deliveredDate;

//    private UserDto user;

    private List<OrderItemDto> orderItemList = new ArrayList<>();

}
