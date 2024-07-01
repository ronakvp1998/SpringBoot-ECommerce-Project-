package org.example.withoutboot.beans;

import org.springframework.stereotype.Component;

@Component
public class CartService {
    public void createCart(){
        System.out.println("One cart is created");
    }
}
