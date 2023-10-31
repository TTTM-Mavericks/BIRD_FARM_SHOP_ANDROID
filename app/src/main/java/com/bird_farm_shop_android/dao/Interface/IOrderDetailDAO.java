package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.OrderDetail;

import java.util.List;

public interface IOrderDetailDAO {
    public boolean createOrderDetail(OrderDetail orderDetail);
    public List<OrderDetail> getAllOrderDetail();
    public OrderDetail getOrderDetailByID(Integer orderDetailID);
    public List<OrderDetail> getOrderDetailByOrderID(Integer orderID);
    public boolean updateOrderDetail(OrderDetail orderDetail);
    public boolean deleteOrderDetail(OrderDetail orderDetail);
    public boolean deleteOrderDetailByID(Integer orderDetailID);
}
