package com.example.mcv.springMVCProject.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProduceController {

    Logger logger = LoggerFactory.getLogger(ProduceController.class);
    @GetMapping("/getProduct")
    public String getProduct(@RequestParam("productName") String productName,
                             @RequestParam("productId") int productId,
                             @RequestParam("productRating") String productRating){
        System.out.println("productName " + productName);
        System.out.println("productId " + productId);
        System.out.println("productRating " + productRating);
        return "productName " + productName + " productId " + productId + " productRating " + productRating;
    }

    @RequestMapping("/checkProduct/{productId}/{productName}/{productRating}")
    public String checkProduct(
            @PathVariable("productId") int productId,
            @PathVariable("productName") String productName,
            @PathVariable("productRating") String productRating
    ){
//        System.out.println("productName " + productName);
//        System.out.println("productId " + productId);
//        System.out.println("productRating " + productRating);
        logger.error("ProductName {}" , productName);
        logger.warn("ProductId {}" , productId);
        logger.info("productRating {}" , productRating);
        logger.debug("This is debug testing");
        return productId + " " + productName + " " + productRating;
    }

}
