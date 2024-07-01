package com.ronaksales.electronic.store.ElectronicStore.controllers;

import com.ronaksales.electronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.ronaksales.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.ronaksales.electronic.store.ElectronicStore.dtos.CartDto;
import com.ronaksales.electronic.store.ElectronicStore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request,
                                                 @PathVariable String userId){
        CartDto cartDto = cartService.addItemToCart(userId,request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    // remove item from cart
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable int itemId,
                                                                 @PathVariable String userId){

        cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Iteam is removed !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    // create cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(
            @PathVariable String userId){

        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Cart is blank")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);

    }


    // getcart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
