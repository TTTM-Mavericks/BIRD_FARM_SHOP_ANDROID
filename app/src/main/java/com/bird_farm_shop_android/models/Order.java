package com.bird_farm_shop_android.models;

import com.bird_farm_shop_android.enums.OrderStatus;

public class Order {
    private Integer id;
    private Integer customerID;
    private String node;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private Float totalPrice;
    private String orderDate;
    private OrderStatus orderStatus;

    public Order(Integer id, Integer customerID, String node, String customerName,
                 String customerPhone, Float totalPrice,
                 String orderDate, OrderStatus orderStatus) {
        this.id = id;
        this.customerID = customerID;
        this.node = node;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public Order(Integer customerID, String node, String customerName, String customerPhone,
                 String customerAddress, Float totalPrice, String orderDate, OrderStatus orderStatus) {
        this.customerID = customerID;
        this.node = node;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerID=" + customerID +
                ", node='" + node + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
