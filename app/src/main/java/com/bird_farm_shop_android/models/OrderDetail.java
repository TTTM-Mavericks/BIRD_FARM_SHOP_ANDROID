package com.bird_farm_shop_android.models;

public class OrderDetail {
    private Integer id;
    private Integer productID;
    private Integer orderID;

    public OrderDetail(Integer id, Integer productID, Integer orderID) {
        this.id = id;
        this.productID = productID;
        this.orderID = orderID;
    }

    public OrderDetail(Integer productID, Integer orderID) {
        this.productID = productID;
        this.orderID = orderID;
    }

    public OrderDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", productID=" + productID +
                ", orderID=" + orderID +
                '}';
    }
}
