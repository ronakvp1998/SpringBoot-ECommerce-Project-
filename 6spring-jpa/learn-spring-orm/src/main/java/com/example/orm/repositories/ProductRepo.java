package com.example.orm.repositories;

import com.example.orm.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,String> {

    //Custom Finder Method

    // Find the Products by ProductName
//    Product findByProductName(String productName);
    Optional<Product> findByProductName(String productName);

//    Product findByPId(int pId);

    // is
    Product findByProductNameIs(String productName);

    // =
    Product findByProductNameEquals(String productName);

    // is not
    Product findByProductNameIsNot(String productName);
    // is Null
    List<Product> findByProductNameIsNull();


    // True false
    List<Product> findByActiveTrue();
    List<Product> findByActiveFalse();

    // StartingWith
    List<Product> findByProductNameStartingWith(String prefix);
    // EndingWith
    List<Product> findByProductNameEndingWith(String suffix);
    // Containing
    List<Product> findByProductNameContaining(String infix);

    // like operator
    String pattern = "Samsung%";
    List<Product> findByProductNameLike(String pattern);

    // comparision operators
//    List<Product> findByPriceLessThan(double price);
//    List<Product> findByPriceLessThanEqual(double price);

    // greaterthan
//    List<Product> findByPriceGreaterThan(double price);

    // in operator
    List<Product> findByProductNameIn(List<String> productList);
//    List<Product> findByProductName(Collection<String> productList);

    // And
//    List<Product> findByProductNameAndPrice(String name, double price);
//    List<Product> findByProductNameOrPrice(String name, double price);


    // Sorting
    List<Product> findByProductNameOrderByProductNameAsc(String productName);
    List<Product> findByProductNameOrderByProductNameDesc(String productName);


//    ****************************************************************************************************

    //Query
    // select all products with query
    // JPQL: DB independent queries
    @Query(value = "SELECT p FROM Product p")
    List<Product> getAllProductWhileLearningJPA();

    // get all active products
    @Query(value = "SELECT p from Product p where p.active=1")
    List<Product> getAllActiveProduct();

    // get all active products with and condition
    @Query(value = "SELECT p from Product p where p.productName = 'Samsung s22' AND p.active=1")
    List<Product> getAllActiveProductWhereProductName();

    // Dynamic Query:- Named query
    @Query("SELECT p from Product p WHERE p.pId= ?1 AND p.productName= ?2")
    Product getSingleProductByIdAndName1(int pId,String productName);

    @Query("SELECT p from Product p WHERE p.pId= :productId AND p.productName= :productName")
    Product getSingleProductByIdAndName2(@Param("productId")int productId,@Param("productName") String productName);

    // for update query use @Modifying annotation
//    @Query("SELECT p from Product WHERE p.pId= :productId AND p.productName= :productName")
//    Product getSingleProductByIdAndName2(@Param("productId")int productId,@Param("productName") String productName);


    // Sorting in custom finder
    Optional<Product> findByProductName(String productName, Sort sort);

    // Native Query DB specific
    @Query(value = "select * from jpa_Product where product_name= :productName",nativeQuery = true)
    Product getProductByIdAndNameNative(@Param("productName") String productName);



}
