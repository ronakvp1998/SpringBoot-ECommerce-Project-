package com.ronaksales.electronic.store.ElectronicStore.services;

import com.ronaksales.electronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.ronaksales.electronic.store.ElectronicStore.dtos.CartDto;

public interface CartService {

    // add items to cart
    // case1: if cart for the user is not available: then we create the cart and then add the items
    // case2: cart available for the user then add the items to the same cart
    // case3: if the item is already present and we need to add the same item then just increase the quantity
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    // remove items from cart:
    void removeItemFromCart(String userId,int cartItemID);

    // clear Cart:- remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
