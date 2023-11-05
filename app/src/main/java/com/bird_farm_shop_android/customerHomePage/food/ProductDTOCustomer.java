package com.bird_farm_shop_android.customerHomePage.food;


public class ProductDTOCustomer {
    private String productName;
    private String productDescription;
    private String productPrice;
    private String dataImage;
    private String key;

    public ProductDTOCustomer(String productName, String productDescription, String productPrice, String dataImage) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.dataImage = dataImage;
    }

    public ProductDTOCustomer(){
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getDataImage() {
        return dataImage;
    }

}