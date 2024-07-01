package com.ronaksales.electronic.store.ElectronicStore.controllers;

import com.ronaksales.electronic.store.ElectronicStore.dtos.*;
import com.ronaksales.electronic.store.ElectronicStore.services.CategoryService;
import com.ronaksales.electronic.store.ElectronicStore.services.FileService;
import com.ronaksales.electronic.store.ElectronicStore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Value("${categorie.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private FileService fileService;

    // create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Valid
            @RequestBody CategoryDto categoryDto,
            @PathVariable("categoryId") String categoryId){
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }


    // delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(
            @PathVariable String categoryId){

        categoryService.deleteCategory(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Category is deleted successfully !!")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    // get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDesc", defaultValue = "asc",required = false) String sortDesc){

        PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDesc);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    // get single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(

            @PathVariable("categoryId") String categoryId){
        CategoryDto categoryDto = categoryService.getCategory(categoryId);

        return ResponseEntity.ok(categoryDto);
    }

    // search Category on title
    @GetMapping("/search/{title}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByTitle(
            @PathVariable("title") String title){
        return new ResponseEntity<>(categoryService.getCategoriesByTitle(title),HttpStatus.OK);
    }



    // upload Category image
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("categoryImage") MultipartFile image,
            @PathVariable("categoryId") String categoryId ) throws IOException {

        // get the image and upload it in the specified folder(imageUploadPath)
        String imageName = fileService.uploadFile(image,imageUploadPath);

        // update the image name for the given category Id
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        categoryDto.setCoverImage(imageName);
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto,categoryId);

        // create the Imageresponse
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }


    // serve Category image
    @GetMapping("/image/{categoryId}")
    public void serveUserImage(@PathVariable("categoryId") String categoryId,
                               HttpServletResponse response) throws IOException {

        // get the image using the categoryId
        CategoryDto categoryDto= categoryService.getCategory(categoryId);
        logger.info("Category cover Image name : {} ",categoryDto.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath,categoryDto.getCoverImage());

        // create the response one's we got the image
        // user HttpServletResponse, copy resource and set the content type as image jpeg
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        // copy the source-> resource into Destination-> response as getOutputStream
        StreamUtils.copy(resource,response.getOutputStream());
    }

    // create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto){

        ProductDto productWithCategory = productService.createWithCategory(productDto,categoryId);
        return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);

    }


    //updated category of products
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("productId") String productId){

        ProductDto productDto = productService.updateProductCategory(productId,categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    // get products of specified category
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir) {

     PageableResponse<ProductDto> response =  productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
     return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
