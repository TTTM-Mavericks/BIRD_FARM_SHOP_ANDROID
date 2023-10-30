package com.bird_farm_shop_android.models;

public class OrderDetail {
    private Integer orderDetailID;
    private Integer productID;
    private Integer orderID;

    public OrderDetail(Integer orderDetailID, Integer productID, Integer orderID) {
        this.orderDetailID = orderDetailID;
        this.productID = productID;
        this.orderID = orderID;
    }

    public OrderDetail(Integer productID, Integer orderID) {
        this.productID = productID;
        this.orderID = orderID;
    }

    public OrderDetail() {
    }

    public Integer getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Integer orderDetailID) {
        this.orderDetailID = orderDetailID;
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
                "orderDetailID=" + orderDetailID +
                ", productID=" + productID +
                ", orderID=" + orderID +
                '}';
    }
}
