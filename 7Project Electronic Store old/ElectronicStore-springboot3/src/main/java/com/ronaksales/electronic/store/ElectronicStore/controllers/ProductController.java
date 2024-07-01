package com.ronaksales.electronic.store.ElectronicStore.controllers;

import com.ronaksales.electronic.store.ElectronicStore.dtos.*;
import com.ronaksales.electronic.store.ElectronicStore.services.FileService;
import com.ronaksales.electronic.store.ElectronicStore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileService fileService;

    @Value("${product.profile.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    // create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.create(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable("productId") String productId) {

        ProductDto updatedProduct = productService.update(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId") String productId) {

        productService.delete(productId);
        ApiResponseMessage response = ApiResponseMessage.builder().
                message("product is deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") String productId) {

        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    // get all
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> pageableResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    // get all live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> pageableResponse = productService.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }


    // search by subtitle which is present in title
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductSubTitle(
            @PathVariable(value = "query") String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    // upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable("productId") String productId,
            @RequestParam("productImage")MultipartFile image) throws IOException {

        ProductDto productDto = productService.getProductById(productId);
        String fileName = fileService.uploadFile(image,imagePath);
        productDto.setProductImageName(fileName);
        ProductDto updatedProductdto = productService.update(productDto,productId);

        ImageResponse response = ImageResponse.builder().imageName(updatedProductdto.getProductImageName())
                .message("product image is successfully uploaded !!")
                .status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    // serve image

    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable("productId") String productId,
                               HttpServletResponse response) throws IOException {

        // get the image using the productId
        ProductDto productDto= productService.getProductById(productId);
        logger.info("product Image name : {} ",productDto.getProductImageName());
        InputStream resource = fileService.getResource(imagePath,productDto.getProductImageName());

        // create the response one's we got the image
        // user HttpServletResponse copy resource and set the content type as image jpeg
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        // copy the source-> resource into Destination-> response as getOutputStream
        StreamUtils.copy(resource,response.getOutputStream());
    }

}