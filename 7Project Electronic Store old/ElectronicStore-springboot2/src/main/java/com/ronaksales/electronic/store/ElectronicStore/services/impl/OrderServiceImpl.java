package com.ronaksales.electronic.store.ElectronicStore.services.impl;

import com.ronaksales.electronic.store.ElectronicStore.dtos.CreateOrderRequest;
import com.ronaksales.electronic.store.ElectronicStore.dtos.OrderDto;
import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.UpdateOrderRequest;
import com.ronaksales.electronic.store.ElectronicStore.entities.*;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.BadApiRequestException;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ronaksales.electronic.store.ElectronicStore.helper.Helper;
import com.ronaksales.electronic.store.ElectronicStore.repositories.CartRepository;
import com.ronaksales.electronic.store.ElectronicStore.repositories.OrderRepository;
import com.ronaksales.electronic.store.ElectronicStore.repositories.UserRepository;
import com.ronaksales.electronic.store.ElectronicStore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {

        String userId = createOrderRequest.getUserId();
        String cartId = createOrderRequest.getCartId();

        // fetch user
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user with id not found"));
        // fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("cart with id not found"));

        // get the Cartitems list from the cart
        List<CartItem> cartItems = cart.getItems();

        // throw exception for invalid list size
        if(cartItems.size() <=0){
            throw new BadApiRequestException("Invalid number of items in the cart !!");
        }

        // Other checks and validations
        // -

        // generate an order
        Order order = Order.builder().billingName(createOrderRequest.getBillingName())
                .billingPhone(createOrderRequest.getBillingPhone())
                .billingAddress(createOrderRequest.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(createOrderRequest.getPaymentStatus())
                .orderStatus(createOrderRequest.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user).build();

//        orderItems, amount not set yet we will set in further steps

         // using atomic reference for order amount which is used in the lambda expression
         AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
         List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {

//       create OrderItemList from CartItems list      CartItem -> OrderItem
         OrderItem orderItem = OrderItem.builder()
                 .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity()* cartItem.getProduct().getDiscountedPrice())
                    .order(order).build();

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItemList(orderItems);
        order.setOrderAmount(orderAmount.get());

        // clear the cart
        cart.getItems().clear();

        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String order_id) {

        Order order = orderRepository.findById(order_id).orElseThrow(()-> new ResourceNotFoundException("order with id not found"));
        // if order is deleted than all the orderItems will also get deleted
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
         User user  = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with id not found"));
         List<Order> orders = orderRepository.findByUser(user);
         List<OrderDto> orderDtos = orders.stream().map(order -> mapper.map(order,OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page,OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(String orderId, UpdateOrderRequest request) {

        // fetch order
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with id not found"));

        order.setBillingAddress(request.getBillingAddress());
        order.setBillingName(request.getBillingName());
        order.setBillingPhone(request.getBillingPhone());
        order.setOrderStatus(request.getOrderStatus());
        order.setPaymentStatus(request.getPaymentStatus());
        order.setDeliveredDate(new Date());
        this.orderRepository.save(order);

        return mapper.map(order,OrderDto.class);


    }
}
