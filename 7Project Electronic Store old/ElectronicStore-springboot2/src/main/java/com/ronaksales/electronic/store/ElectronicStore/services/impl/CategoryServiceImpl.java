package com.ronaksales.electronic.store.ElectronicStore.services.impl;

import com.ronaksales.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.entities.Category;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ronaksales.electronic.store.ElectronicStore.helper.Helper;
import com.ronaksales.electronic.store.ElectronicStore.repositories.CategoryRepository;
import com.ronaksales.electronic.store.ElectronicStore.services.CategoryService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // creating categoryId randomly
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        // map categoryDto to entity
        Category category = mapper.map(categoryDto, Category.class);
        // save the entity
        Category savedCategory = categoryRepository.save(category);
        // remap entity to dto and return it
        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        //get the category by id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category with id not found"));

        // update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        //get the category by id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category with id not found"));
        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page,CategoryDto.class);
        return pageableResponse;

    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category with id not found"));
        return mapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto>  getCategoriesByTitle(String title) {
        List<Category> categories = categoryRepository.findByTitleContaining(title);
        if(categories.isEmpty()){
            throw new ResourceNotFoundException("Category with title: " + title + " not found");
        }
        List<CategoryDto> categoryDtos= categories.stream().map(category -> mapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
