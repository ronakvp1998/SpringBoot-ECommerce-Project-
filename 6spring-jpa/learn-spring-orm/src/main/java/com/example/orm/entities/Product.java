package com.example.orm.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="jpa_product")
public class Product {
    @Id
    private String pId;
    private String productName;

    private boolean live;
    private Integer active;

    @ManyToMany(mappedBy = "productList",fetch = FetchType.EAGER)
    private List<Catogory> catogoryList = new ArrayList<>();

    public Product(String pId, String productName) {
        this.pId = pId;
        this.productName = productName;
    }

    public Product() {
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<Catogory> getCatogoryList() {
        return catogoryList;
    }

    public void setCatogoryList(List<Catogory> catogoryList) {
        this.catogoryList = catogoryList;
    }



    @Override
    public String toString() {
        return "Product{" +
                "pId='" + pId + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
