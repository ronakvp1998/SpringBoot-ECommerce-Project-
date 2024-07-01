package com.example.orm.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="jpa_category")
public class Catogory {
    @Id
    private String Cid;
    private String title;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Product> productList = new ArrayList<>();

    public Catogory(String cid, String title) {
        Cid = cid;
        this.title = title;
    }
    public Catogory(){

    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Catogory{" +
                "Cid='" + Cid + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
