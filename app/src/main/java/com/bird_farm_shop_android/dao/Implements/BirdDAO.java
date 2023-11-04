package com.bird_farm_shop_android.dao.Implements;

import android.util.Log;

import com.bird_farm_shop_android.database.DBUtils;
import com.bird_farm_shop_android.dao.Interface.IBirdDAO;
import com.bird_farm_shop_android.entities.Image;
import com.bird_farm_shop_android.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BirdDAO implements IBirdDAO {

    private static final BirdDAO instance = new BirdDAO();

    public static BirdDAO getInstance() {
        return instance;
    }

    @Override
    public Product getBirdByImageUrl(String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        Product bird = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "SELECT p.ID, p.PRODUCT_NAME, p.PRICE, p.DESCRIPTION, p.QUANTITY, p.IMAGE " +
                        "FROM PRODUCT p INNER JOIN BIRD b ON p.ID = b.ID " +
                        "WHERE p.IMAGE = ?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, imageUrl);
                ResultSet rs = stm.executeQuery();

                if (rs.next()) {
                    bird = extractProduct(rs);
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return bird;
    }


    @Override
    public boolean createBird(Product bird) {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                // Insert the product information into the PRODUCT table
                String productSql = "INSERT INTO PRODUCT (PRODUCT_NAME, PRICE, DESCRIPTION, QUANTITY, IMAGE) VALUES (?, ?, ?, ?, ?)";
                stm = con.prepareStatement(productSql, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, bird.getProductName());
                stm.setFloat(2, bird.getPrice());
                stm.setString(3, bird.getDescription());
                stm.setInt(4, bird.getQuantity());
                stm.setString(5, bird.getImage());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = stm.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedProductId = generatedKeys.getInt(1);

                        // Insert the bird-specific information into the BIRD table
                        String birdSql = "INSERT INTO BIRD (ID) VALUES (?)";
                        stm = con.prepareStatement(birdSql);
                        stm.setInt(1, generatedProductId);

                        rowsAffected = stm.executeUpdate();
                        return rowsAffected > 0;
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return false;
    }

    @Override
    public boolean updateBirdByImageUrl(Product bird, String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "UPDATE PRODUCT SET PRODUCT_NAME=?, PRICE=?, DESCRIPTION=?, QUANTITY=?, IMAGE = ? WHERE IMAGE=?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, bird.getProductName());
                stm.setFloat(2, bird.getPrice());
                stm.setString(3, bird.getDescription());
                stm.setInt(4, bird.getQuantity());
                stm.setString(5, bird.getImage());
                stm.setString(6, imageUrl);

                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return false;
    }
    @Override
    public boolean deleteBirdByImageUrl(String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        Product product = getBirdByImageUrl(imageUrl);
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String deleteProductSql = "DELETE FROM BIRD WHERE ID=?";
                stm = con.prepareStatement(deleteProductSql);
                stm.setInt(1, product.getProductID());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    String birdSql = "DELETE FROM PRODUCT WHERE ID=?";
                    stm = con.prepareStatement(birdSql);
                    stm.setInt(1, product.getProductID());
                    rowsAffected = stm.executeUpdate();
                    return rowsAffected > 0;
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
