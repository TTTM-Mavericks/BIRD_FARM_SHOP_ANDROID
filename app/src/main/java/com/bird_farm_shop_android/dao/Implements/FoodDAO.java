package com.bird_farm_shop_android.dao.Implements;

import android.util.Log;

import com.bird_farm_shop_android.database.DBUtils;
import com.bird_farm_shop_android.dao.Interface.IFoodDAO;
import com.bird_farm_shop_android.entities.Image;
import com.bird_farm_shop_android.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO implements IFoodDAO {

    @Override
    public Product getFoodByImageUrl(String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        Product food = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "SELECT p.ID, p.PRODUCT_NAME, p.PRICE, p.DESCRIPTION, p.QUANTITY, p.IMAGE " +
                        "FROM PRODUCT p INNER JOIN FOOD b ON p.ID = b.ID " +
                        "WHERE p.IMAGE = ?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, imageUrl);
                ResultSet rs = stm.executeQuery();

                if (rs.next()) {
                    food = extractProduct(rs);
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return food;
    }

    @Override
    public boolean createFood(Product food) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "INSERT INTO PRODUCT (PRODUCT_NAME, PRICE, DESCRIPTION, QUANTITY, IMAGE) VALUES (?, ?, ?, ?, ?)";
                stm = con.prepareStatement(productSql, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, food.getProductName());
                stm.setFloat(2, food.getPrice());
                stm.setString(3, food.getDescription());
                stm.setInt(4, food.getQuantity());
                stm.setString(5, food.getImage());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = stm.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedProductId = generatedKeys.getInt(1);

                        String foodSql = "INSERT INTO FOOD (ID) VALUES (?)";
                        stm = con.prepareStatement(foodSql);
                        stm.setInt(1, generatedProductId);
                        rowsAffected = stm.executeUpdate();

                        if (rowsAffected > 0) {
                            result = true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return result;
    }

    @Override
    public boolean updateFoodByImageUrl(Product food, String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "UPDATE PRODUCT SET PRODUCT_NAME=?, PRICE=?, DESCRIPTION=?, QUANTITY=?, IMAGE = ? WHERE IMAGE=?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, food.getProductName());
                stm.setFloat(2, food.getPrice());
                stm.setString(3, food.getDescription());
                stm.setInt(4, food.getQuantity());
                stm.setString(5, food.getImage());
                stm.setString(6, imageUrl);

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return result;
    }

    @Override
    public boolean deleteFoodByImageUrl(String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        Product product = getFoodByImageUrl(imageUrl);

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String deleteProductSql = "DELETE FROM FOOD WHERE ID=?";
                stm = con.prepareStatement(deleteProductSql);
                stm.setInt(1, product.getProductID());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    String foodSql = "DELETE FROM PRODUCT WHERE ID=?";
                    stm = con.prepareStatement(foodSql);
                    stm.setInt(1, product.getProductID());
                    rowsAffected = stm.executeUpdate();

                    if (rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return result;
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        Integer ID = rs.getInt("ID");
        String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
        Float PRICE = rs.getFloat("PRICE");
        String DESCRIPTION = rs.getString("DESCRIPTION");
        Integer QUANTITY = rs.getInt("QUANTITY");
        return new Product(ID, PRODUCT_NAME, PRICE, DESCRIPTION, QUANTITY);
    }

    private void closeResources(Connection con, PreparedStatement stm) {
        try {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}