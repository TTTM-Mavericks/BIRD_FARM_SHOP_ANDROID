package com.bird_farm_shop_android.dao.Implements;

import android.util.Log;
import com.bird_farm_shop_android.database.DBUtils;
import com.bird_farm_shop_android.dao.Interface.IOrderDAO;
import com.bird_farm_shop_android.enums.OrderStatus;
import com.bird_farm_shop_android.entities.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements IOrderDAO {

    @Override
    public boolean createOrder(Order order) {
        Boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                Log.e("LOG", "THIS IS CREATE ORDER METHOD INSIDE HERRE");
                String orderSql = "INSERT INTO [ORDER] (CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_PHONE, CUSTOMER_ADDRESS, " +
                        "TOTAL_PRICE, ORDER_DATE, ORDER_STATUS, NOTE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(orderSql);
                stm.setInt(1, order.getCustomerID());
                stm.setString(2, order.getCustomerName());
                stm.setString(3, order.getCustomerPhone());
                stm.setString(4, order.getCustomerAddress());
                stm.setFloat(5, order.getTotalPrice());
                stm.setString(6, order.getOrderDate());
                stm.setString(7, order.getOrderStatus().toString());
                stm.setString(8, order.getNote());

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

    @Override
    public List<Order> getAllOrder() {
        List<Order> orderList = null;
        try {
            Connection con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM [ORDER]";
                PreparedStatement stm = con.prepareStatement(sql);
                if (stm != null) {
                    ResultSet rs = stm.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            Integer ID = rs.getInt("ID");
                            Integer CUSTOMER_ID = rs.getInt("CUSTOMER_ID");
                            String NOTE = rs.getString("NOTE");
                            String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
                            String CUSTOMER_PHONE = rs.getString("CUSTOMER_PHONE");
                            String CUSTOMER_ADDRESS = rs.getString("CUSTOMER_ADDRESS");
                            Float TOTAL_PRICE = rs.getFloat("TOTAL_PRICE");
                            String ORDER_DATE = rs.getString("ORDER_DATE");
                            String status = rs.getString("ORDER_STATUS");
                            OrderStatus ORDER_STATUS = null;
                            if(status.equals("PENDING")) {
                                ORDER_STATUS = OrderStatus.PENDING;
                            }else if(status.equals("CANCEL")) {
                                ORDER_STATUS = OrderStatus.CANCEL;
                            } else {
                                ORDER_STATUS = OrderStatus.FINISH;
                            }
                            Order o = new Order(ID, CUSTOMER_ID,  CUSTOMER_NAME,
                                    CUSTOMER_PHONE, CUSTOMER_ADDRESS, TOTAL_PRICE, ORDER_DATE,
                                    ORDER_STATUS, NOTE
                                    );

                            if (orderList == null) {
                                orderList = new ArrayList<>();
                            }
                            orderList.add(o);
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
            return orderList;
        }
    }

    @Override
    public Order getOrderByID(Integer orderID) {
        Order order = null;
        try {
            Connection con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM [ORDER] WHERE ID = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                if (stm != null) {
                    Log.e("ERROR", "THIS IS GET ORDER BY ID INSIDE HERE");
                    stm.setInt(1, orderID);
                    ResultSet rs = stm.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            Log.e("ERROR", "THIS IS INSERT RESULT SET");
                            Integer ID = rs.getInt("ID");
                            Integer CUSTOMER_ID = rs.getInt("CUSTOMER_ID");
                            String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
                            String CUSTOMER_PHONE = rs.getString("CUSTOMER_PHONE");
                            String CUSTOMER_ADDRESS = rs.getString("CUSTOMER_ADDRESS");
                            Float TOTAL_PRICE = rs.getFloat("TOTAL_PRICE");
                            String ORDER_DATE = rs.getString("ORDER_DATE");
                            String status = rs.getString("ORDER_STATUS");
                            String NOTE = rs.getString("NOTE");
                            OrderStatus ORDER_STATUS = null;
                            if(status.equals("PENDING")) {
                                ORDER_STATUS = OrderStatus.PENDING;
                            }else if(status.equals("CANCEL")) {
                                ORDER_STATUS = OrderStatus.CANCEL;
                            } else {
                                ORDER_STATUS = OrderStatus.FINISH;
                            }
                            order = new Order(ID, CUSTOMER_ID,  CUSTOMER_NAME,
                                    CUSTOMER_PHONE, CUSTOMER_ADDRESS, TOTAL_PRICE, ORDER_DATE,
                                    ORDER_STATUS, NOTE
                            );
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
            return order;
        }
    }

    @Override
    public List<Order> getOrderByCustomerID(Integer customerID) {
        List<Order> orderList = null;
        try {
            Connection con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM [ORDER] WHERE CUSTOMER_ID = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                if (stm != null) {
                    stm.setInt(1, customerID);
                    ResultSet rs = stm.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            Integer ID = rs.getInt("ID");
                            Integer CUSTOMER_ID = rs.getInt("CUSTOMER_ID");
                            String CUSTOMER_NAME = rs.getString("CUSTOMER_NAME");
                            String CUSTOMER_PHONE = rs.getString("CUSTOMER_PHONE");
                            String CUSTOMER_ADDRESS = rs.getString("CUSTOMER_ADDRESS");
                            Float TOTAL_PRICE = rs.getFloat("TOTAL_PRICE");
                            String ORDER_DATE = rs.getString("ORDER_DATE");
                            String status = rs.getString("ORDER_STATUS");
                            String NOTE = rs.getString("NOTE");
                            OrderStatus ORDER_STATUS = null;
                            if(status.equals("PENDING")) {
                                ORDER_STATUS = OrderStatus.PENDING;
                            }else if(status.equals("CANCEL")) {
                                ORDER_STATUS = OrderStatus.CANCEL;
                            } else {
                                ORDER_STATUS = OrderStatus.FINISH;
                            }
                            Order o = new Order(ID, CUSTOMER_ID,  CUSTOMER_NAME,
                                    CUSTOMER_PHONE, CUSTOMER_ADDRESS, TOTAL_PRICE, ORDER_DATE,
                                    ORDER_STATUS, NOTE
                            );

                            if (orderList == null) {
                                orderList = new ArrayList<>();
                            }
                            orderList.add(o);
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
            return orderList;
        }
    }

    @Override
    public boolean updateOrder(Order order) {
        Boolean result = false;
        try {
            Connection con = DBUtils.getConnection();
            if (con != null) {
                String sql = "UPDATE [ORDER] SET CUSTOMER_NAME=?, CUSTOMER_PHONE=?, CUSTOMER_ADDRESS=?, " +
                        "TOTAL_PRICE=?, ORDER_DATE=?, ORDER_STATUS=?, NOTE=? WHERE ID=?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, order.getCustomerName());
                stm.setString(2, order.getCustomerPhone());
                stm.setString(3, order.getCustomerAddress());
                stm.setFloat(4, order.getTotalPrice());
                stm.setString(5, order.getOrderDate());
                stm.setString(6, order.getOrderStatus().toString());
                stm.setString(7, order.getNote());
                stm.setInt(8, order.getId());

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

    @Override
    public boolean deleteOrder(Order order) {
        Boolean result = false;
        try {
            result = deleteOrderByID(order.getId());
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return result;
        }
    }

    @Override
    public boolean deleteOrderByID(Integer orderID) {
        Boolean result = false;
        try {
            Connection con = DBUtils.getConnection();
            if (con != null) {
                String sql = "DELETE FROM [ORDER] WHERE ID=?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, orderID);

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
