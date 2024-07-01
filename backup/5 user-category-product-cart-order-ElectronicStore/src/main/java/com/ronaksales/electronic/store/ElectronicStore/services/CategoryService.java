package com.ronaksales.electronic.store.ElectronicStore.services;

import com.ronaksales.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface CategoryService {

    // create
    CategoryDto createCategory(CategoryDto categoryDto);

    // update
    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);


    // delete
    void deleteCategory (String categoryId);

    // get all
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy,String sortDir);


    // get single category detail
    CategoryDto getCategory(String categoryId);

    // search
    List<CategoryDto> getCategoriesByTitle(String title);
}
