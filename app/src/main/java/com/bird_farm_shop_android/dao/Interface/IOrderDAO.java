package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.Order;

import java.util.List;

public interface IOrderDAO {
    public boolean createOrder(Order order);
    public List<Order> getAllOrder();
    public Order getOrderByID(Integer orderID);
    public List<Order> getOrderByCustomerID(Integer customerID);
    public boolean updateOrder(Order order);
    public boolean deleteOrder(Order order);
    public boolean deleteOrderByID(Integer orderID);
}
