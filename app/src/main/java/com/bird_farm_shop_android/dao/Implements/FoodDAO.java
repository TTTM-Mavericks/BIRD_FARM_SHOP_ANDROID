package com.bird_farm_shop_android.dao.Implements;

import android.util.Log;

import com.bird_farm_shop_android.DBUtils;
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
    public List<Product> getAllFood() {
        List<Product> foodList = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT p.ID, p.PRODUCT_NAME, p.PRICE, p.DESCRIPTION, p.QUANTITY " +
                        "FROM PRODUCT p INNER JOIN FOOD f ON p.ID = f.ID";
                stm = con.prepareStatement(sql);
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    Product p = extractProduct(rs);
                    p.setListImages(getImageListForProduct(con, p.getProductID()));
                    foodList.add(p);
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return foodList;
    }

    @Override
    public Product getFoodByID(Integer foodID) {
        Connection con = null;
        PreparedStatement stm = null;
        Product food = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT p.ID, p.PRODUCT_NAME, p.PRICE, p.DESCRIPTION, p.QUANTITY " +
                        "FROM PRODUCT p INNER JOIN FOOD f ON p.ID = f.ID " +
                        "WHERE p.ID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, foodID);
                ResultSet rs = stm.executeQuery();

                if (rs.next()) {
                    food = extractProduct(rs);
                    food.setListImages(getImageListForProduct(con, food.getProductID()));
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
                String productSql = "INSERT INTO PRODUCT (PRODUCT_NAME, PRICE, DESCRIPTION, QUANTITY) VALUES (?, ?, ?, ?)";
                stm = con.prepareStatement(productSql, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, food.getProductName());
                stm.setFloat(2, food.getPrice());
                stm.setString(3, food.getDescription());
                stm.setInt(4, food.getQuantity());

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
    public boolean updateFood(Product food) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "UPDATE PRODUCT SET PRODUCT_NAME=?, PRICE=?, DESCRIPTION=?, QUANTITY=? WHERE ID=?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, food.getProductName());
                stm.setFloat(2, food.getPrice());
                stm.setString(3, food.getDescription());
                stm.setInt(4, food.getQuantity());
                stm.setInt(5, food.getProductID());

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
    public boolean deleteFood(Product food) {
        return deleteFoodByID(food.getProductID());
    }

    @Override
    public boolean deleteFoodByID(Integer foodID) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String deleteProductSql = "DELETE FROM FOOD WHERE ID=?";
                stm = con.prepareStatement(deleteProductSql);
                stm.setInt(1, foodID);

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    String foodSql = "DELETE FROM PRODUCT WHERE ID=?";
                    stm = con.prepareStatement(foodSql);
                    stm.setInt(1, foodID);
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

    private List<Image> getImageListForProduct(Connection con, Integer productID) {
        List<Image> imageList = new ArrayList<>();
        PreparedStatement IMGstm = null;

        try {
            String IMGsql = "SELECT ID, IMAGE_URL, PRODUCT_ID FROM IMAGE WHERE PRODUCT_ID = ?";
            IMGstm = con.prepareStatement(IMGsql);
            IMGstm.setInt(1, productID);
            ResultSet IMGrs = IMGstm.executeQuery();

            while (IMGrs.next()) {
                Image img = extractImage(IMGrs);
                imageList.add(img);
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(null, IMGstm);
        }

        return imageList;
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        Integer ID = rs.getInt("ID");
        String PRODUCT_NAME = rs.getString("PRODUCT_NAME");
        Float PRICE = rs.getFloat("PRICE");
        String DESCRIPTION = rs.getString("DESCRIPTION");
        Integer QUANTITY = rs.getInt("QUANTITY");
        return new Product(ID, PRODUCT_NAME, PRICE, DESCRIPTION, QUANTITY);
    }

    private Image extractImage(ResultSet rs) throws SQLException {
        Integer ID = rs.getInt("ID");
        String imageUrl = rs.getString("IMAGE_URL");
        Integer productID = rs.getInt("PRODUCT_ID");
        return new Image(ID, imageUrl, productID);
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