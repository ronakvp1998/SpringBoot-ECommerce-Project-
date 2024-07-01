package com.ronaksales.electronic.store.ElectronicStore.repositories;

import com.ronaksales.electronic.store.ElectronicStore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {


}
