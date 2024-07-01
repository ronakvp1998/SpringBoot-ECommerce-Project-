package com.ronaksales.electronic.store.ElectronicStore.services.impl;

import com.ronaksales.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.ProductDto;
import com.ronaksales.electronic.store.ElectronicStore.entities.Category;
import com.ronaksales.electronic.store.ElectronicStore.entities.Product;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ronaksales.electronic.store.ElectronicStore.helper.Helper;
import com.ronaksales.electronic.store.ElectronicStore.repositories.CategoryRepository;
import com.ronaksales.electronic.store.ElectronicStore.repositories.ProductRepository;
import com.ronaksales.electronic.store.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {


    @Value("${product.profile.image.path}")
    private String imagePath;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);

        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        // product added Date
        product.setAddedDate(new Date());
        // save the product
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with specified id not found"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        //save the entity
        Product product1 = productRepository.save(product);

        return mapper.map(product1,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with specified id not found"));
        // delete Product profile image -> images/Product/abc.png
        String fullPath =  imagePath+ product.getProductImageName();
        // remove the file
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch(NoSuchFileException ex){
            logger.info("Product image not found in folder");
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        // delete Product

        productRepository.delete(product);
    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with specified id not found"));

        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber,int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(int pageNumber,int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber,int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        // fetch the category
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with id not found"));
        Product product = mapper.map(productDto,Product.class);

        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        // product added Date
        product.setAddedDate(new Date());
        // set the category for the product
        product.setCategory(category);
        // save the  product with category
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);


    }

    @Override
    public ProductDto updateProductCategory(String productId, String categoryId) {

        // Product fetch
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product with id not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category with id not found"));

        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Category category =  categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category with id not found"));
        Page<Product> page = productRepository.findByCategory(category,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
