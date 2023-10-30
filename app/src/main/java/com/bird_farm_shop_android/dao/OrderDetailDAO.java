package com.bird_farm_shop_android.dao;

import android.util.Log;
import com.bird_farm_shop_android.DBUltils;
import com.bird_farm_shop_android.models.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    public boolean createOrderDetail(OrderDetail orderDetail) {
        Boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String orderDetailSql = "INSERT INTO ORDER_DETAIL (PRODUCT_ID, ORDER_ID) VALUES (?, ?)";
                stm = con.prepareStatement(orderDetailSql);
                stm.setInt(1, orderDetail.getProductID());
                stm.setInt(2, orderDetail.getOrderID());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
            return result;
        }
    }

    public List<OrderDetail> getAllOrderDetail() {
        List<OrderDetail> orderDetailList = null;
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM ORDER_DETAIL";
                PreparedStatement stm = con.prepareStatement(sql);
                if (stm != null) {
                    ResultSet rs = stm.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            Integer ID = rs.getInt("ID");
                            Integer PRODUCT_ID = rs.getInt("PRODUCT_ID");
                            Integer ORDER_ID = rs.getInt("ORDER_ID");
                            OrderDetail od = new OrderDetail(ID, PRODUCT_ID, ORDER_ID);

                            if (orderDetailList == null) {
                                orderDetailList = new ArrayList<>();
                            }
                            orderDetailList.add(od);
                        }
                        rs.close();
                    }
                    stm.close();
                }
                con.close();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return orderDetailList;
        }
    }

    public OrderDetail getOrderDetailByID(Integer orderDetailID) {
        OrderDetail orderDetail = null;
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM ORDER_DETAIL WHERE ID = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                if (stm != null) {
                    stm.setInt(1, orderDetailID);
                    ResultSet rs = stm.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            Integer ID = rs.getInt("ID");
                            Integer PRODUCT_ID = rs.getInt("PRODUCT_ID");
                            Integer ORDER_ID = rs.getInt("ORDER_ID");
                            orderDetail = new OrderDetail(ID, PRODUCT_ID, ORDER_ID);
                        }
                        rs.close();
                    }
                    stm.close();
                }
                con.close();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return orderDetail;
        }
    }

    public List<OrderDetail> getOrderDetailByOrderID(Integer orderID) {
        List<OrderDetail> orderDetailList = null;
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM ORDER_DETAIL WHERE ORDER_ID = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                if (stm != null) {
                    stm.setInt(1, orderID);
                    ResultSet rs = stm.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            Integer ID = rs.getInt("ID");
                            Integer PRODUCT_ID = rs.getInt("PRODUCT_ID");
                            Integer ORDER_ID = rs.getInt("ORDER_ID");
                            OrderDetail od = new OrderDetail(ID, PRODUCT_ID, ORDER_ID);

                            if (orderDetailList == null) {
                                orderDetailList = new ArrayList<>();
                            }
                            orderDetailList.add(od);
                        }
                        rs.close();
                    }
                    stm.close();
                }
                con.close();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return orderDetailList;
        }
    }

    public boolean updateOrderDetail(OrderDetail orderDetail) {
        Boolean result = false;
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "UPDATE ORDER_DETAIL SET PRODUCT_ID=?, ORDER_ID=? WHERE ID=?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, orderDetail.getProductID());
                stm.setInt(2, orderDetail.getOrderID());
                stm.setInt(3, orderDetail.getOrderDetailID());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }

                stm.close();
                con.close();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return result;
        }
    }

    public boolean deleteOrderDetail(OrderDetail orderDetail) {
        Boolean result = false;
        try {
            result = deleteOrderDetailByID(orderDetail.getOrderDetailID());
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return result;
        }
    }

    public boolean deleteOrderDetailByID(Integer orderDetailID) {
        Boolean result = false;
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "DELETE FROM ORDER_DETAIL WHERE ID=?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, orderDetailID);

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }

                stm.close();
                con.close();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return result;
        }
    }
}
