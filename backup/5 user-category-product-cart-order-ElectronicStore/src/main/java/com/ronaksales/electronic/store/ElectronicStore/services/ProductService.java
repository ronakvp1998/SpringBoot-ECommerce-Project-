package com.ronaksales.electronic.store.ElectronicStore.services;

import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    // create
    ProductDto create(ProductDto productDto);

    // update
    ProductDto update(ProductDto productDto,String productId);

    // delete
    void delete(String productId);

    // get single
    ProductDto getProductById(String productId);

    // get all
    PageableResponse<ProductDto> getAllProducts(int pageNumber,int pageSize, String sortBy, String sortDir);

    // get all live
    PageableResponse<ProductDto> getAllLiveProducts(int pageNumber,int pageSize, String sortBy, String sortDir);

    // search product
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize, String sortBy, String sortDir);


    // create product with category

    ProductDto createWithCategory(ProductDto productDto, String categoryId);


    // update Category for products
    ProductDto updateProductCategory(String productId, String categoryId);


    // get products of particular category
    PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize,String sortBy,String sortDir);

}
