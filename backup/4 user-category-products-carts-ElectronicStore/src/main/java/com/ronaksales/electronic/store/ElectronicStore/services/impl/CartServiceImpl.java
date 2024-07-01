package com.ronaksales.electronic.store.ElectronicStore.services.impl;

import com.ronaksales.electronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.ronaksales.electronic.store.ElectronicStore.dtos.CartDto;
import com.ronaksales.electronic.store.ElectronicStore.entities.Cart;
import com.ronaksales.electronic.store.ElectronicStore.entities.CartItem;
import com.ronaksales.electronic.store.ElectronicStore.entities.Product;
import com.ronaksales.electronic.store.ElectronicStore.entities.User;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.BadApiRequestException;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ronaksales.electronic.store.ElectronicStore.repositories.CartItemRepository;
import com.ronaksales.electronic.store.ElectronicStore.repositories.CartRepository;
import com.ronaksales.electronic.store.ElectronicStore.repositories.ProductRepository;
import com.ronaksales.electronic.store.ElectronicStore.repositories.UserRepository;
import com.ronaksales.electronic.store.ElectronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    // add items to cart
    // case1: if cart for the user is not available: then we create the cart and then add the items
    // case2: cart available for the user then add the items to the same cart
    // case3: if the item is already present and we need to add the same item then just increase the quantity
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity<=0){
            throw new BadApiRequestException("Requested quantity is not valid, quantity should be greater than 0  !!");
        }

        // fetch the product using the productId from db
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with id not found"));
        // fetch the user using the userId from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with id not found"));

        // if cart for the user is not available: then we create the cart and then add the items
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        }catch (NoSuchElementException ex){
            logger.info("cart for user is not present creating a new one");
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        // perform cart operation
        // if cart items already present; then update existing CartItem otherwise add new CartItem
        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        // create items for cart
        // if cart is newely created then we will get null items, otherwise we will get the list of cartItems
        List<CartItem> updatedItems = cart.getItems();

        // Case 1  item already present in cart just update the existing CartItems values
        updatedItems.stream().map(item -> {
            if(item.getProduct().getProductId().equalsIgnoreCase(productId)){
                // item already present in cart updated the quantity
                item.setQuantity(quantity);
                // calculate the new dicsounted price with new quantity
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        // add the updated cartItem list to the cart :-> List<CartItems> items
        cart.setItems(updatedItems);

        // Case 2 product is not present in the cart,
        // create new cartItems with product in it and that will get added to the cart list
        // updated flage is false which means product is not present
        if(! updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity*product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            // add the new cartItem to the cart list :-  List<CartItems> items
            cart.getItems().add(cartItem);
        }

        // if cart is new then we will set its userId to set the user to its specific cart
        cart.setUser(user);

        // save the cart in DB
        Cart updatedCart = cartRepository.save(cart);

        return mapper.map(updatedCart,CartDto.class);
    }

    // remove items from cart:
    @Override
    public void removeItemFromCart(String userId, int cartItemID) {

        // conditions if the cartItem id is not present for this userId then throw exception

        // find the cartItem using cartItemId
        CartItem cartItem1 = cartItemRepository.findById(cartItemID).orElseThrow(() -> new ResourceNotFoundException("CartItem not found !!"));
        cartItemRepository.delete(cartItem1);
    }

    // clear Cart:- remove all items from cart
    @Override
    public void clearCart(String userId) {
        // fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with id not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with id not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));
        return mapper.map(cart,CartDto.class);
    }
}
