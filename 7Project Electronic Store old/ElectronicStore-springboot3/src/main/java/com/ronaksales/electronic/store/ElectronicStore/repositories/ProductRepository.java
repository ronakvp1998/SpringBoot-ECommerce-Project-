package com.ronaksales.electronic.store.ElectronicStore.repositories;

import com.ronaksales.electronic.store.ElectronicStore.entities.Category;
import com.ronaksales.electronic.store.ElectronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,String> {

    // search
    Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
    // live products
    Page<Product> findByLiveTrue(Pageable pageable);

    // return products of specific category provided
    Page<Product> findByCategory(Category category,Pageable pageable);

}
