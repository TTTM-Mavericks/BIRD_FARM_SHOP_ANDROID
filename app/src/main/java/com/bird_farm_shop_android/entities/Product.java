package com.bird_farm_shop_android.entities;

import java.util.List;

public class Product {
    private Integer productID;
    private String productName;
    private Float price;
    private String description;
    private Integer quantity;
    private String image;

    public Product(String productName, Float price, String description, Integer quantity) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public Product(String productName, Float price, String description, Integer quantity, String image) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
    }

    public Product(Integer productID, String productName, Float price, String description, Integer quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", listImages=" + image +
                '}';
    }
}