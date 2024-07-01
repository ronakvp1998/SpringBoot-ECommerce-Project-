package com.ronaksales.electronic.store.ElectronicStore.controllers;

import com.ronaksales.electronic.store.ElectronicStore.dtos.*;
import com.ronaksales.electronic.store.ElectronicStore.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // create
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request){
        OrderDto orderDto = orderService.createOrder(request);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);

    }

    // delete
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){
         orderService.removeOrder(orderId);

         ApiResponseMessage responseMessage = ApiResponseMessage.builder().status(HttpStatus.OK)
                 .message("Order is removed !!")
                 .success(true).build();

         return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    // get orders of the user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId){

        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrder(
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderDate",required = false) String sortBy,
            @RequestParam(value = "sortDesc", defaultValue = "asc",required = false) String sortDesc){

        PageableResponse<OrderDto> ordersOfUser = orderService.getOrders(pageNumber,pageSize,sortBy,sortDesc);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    // update order
    @PutMapping("/{orderId}")
    public ResponseEntity updateOrder(
            @PathVariable String orderId,
            @RequestBody UpdateOrderRequest updateOrderRequest
    ){
        OrderDto updateOrder = orderService.updateOrder(orderId,updateOrderRequest);
        return new ResponseEntity<>(updateOrder,HttpStatus.OK);
    }

}
