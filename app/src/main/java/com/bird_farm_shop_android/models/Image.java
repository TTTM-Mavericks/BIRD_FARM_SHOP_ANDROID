package com.bird_farm_shop_android.models;

public class Image {
    private Integer imageID;
    private String imageUrl;
    private Integer productID;

    public Image(Integer imageID, String imageUrl, Integer productID) {
        this.imageID = imageID;
        this.imageUrl = imageUrl;
        this.productID = productID;
    }
}
