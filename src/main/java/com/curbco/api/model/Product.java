package com.curbco.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String title;
    private String price;
    private String category;
    private String tag;
    private String image;

    // Constructors
    public Product() {}

    public Product(String brand, String title, String price, String category, String tag, String image) {
        this.brand = brand;
        this.title = title;
        this.price = price;
        this.category = category;
        this.tag = tag;
        this.image = image;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}