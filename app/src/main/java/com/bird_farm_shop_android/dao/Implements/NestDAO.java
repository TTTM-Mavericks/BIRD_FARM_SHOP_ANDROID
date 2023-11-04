package com.bird_farm_shop_android.dao.Implements;

import android.util.Log;

import com.bird_farm_shop_android.database.DBUtils;
import com.bird_farm_shop_android.dao.Interface.INestDAO;
import com.bird_farm_shop_android.entities.Image;
import com.bird_farm_shop_android.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NestDAO implements INestDAO {
    @Override
    public Product getNestByImageUrl(String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        Product nest = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "SELECT p.ID, p.PRODUCT_NAME, p.PRICE, p.DESCRIPTION, p.QUANTITY, p.IMAGE " +
                        "FROM PRODUCT p INNER JOIN NEST b ON p.ID = b.ID " +
                        "WHERE p.IMAGE = ?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, imageUrl);
                ResultSet rs = stm.executeQuery();

                if (rs.next()) {
                    nest = extractProduct(rs);
                //    nest.setListImages(getImageListForProduct(con, nest.getProductID()));
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return nest;
    }

    @Override
    public boolean createNest(Product nest) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "INSERT INTO PRODUCT (PRODUCT_NAME, PRICE, DESCRIPTION, QUANTITY, IMAGE) VALUES (?, ?, ?, ?, ?)";
                stm = con.prepareStatement(productSql, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, nest.getProductName());
                stm.setFloat(2, nest.getPrice());
                stm.setString(3, nest.getDescription());
                stm.setInt(4, nest.getQuantity());
                stm.setString(5, nest.getImage());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = stm.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedProductId = generatedKeys.getInt(1);

                        String nestSql = "INSERT INTO NEST (ID) VALUES (?)";
                        stm = con.prepareStatement(nestSql);
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
    public boolean updateNestByImageUrl(Product nest, String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String productSql = "UPDATE PRODUCT SET PRODUCT_NAME=?, PRICE=?, DESCRIPTION=?, QUANTITY=?, IMAGE = ? WHERE IMAGE=?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, nest.getProductName());
                stm.setFloat(2, nest.getPrice());
                stm.setString(3, nest.getDescription());
                stm.setInt(4, nest.getQuantity());
                stm.setString(5, nest.getImage());
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
    public boolean deleteNestByImageUrl(String imageUrl) {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        Product product = getNestByImageUrl(imageUrl);
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String deleteProductSql = "DELETE FROM NEST WHERE ID=?";
                stm = con.prepareStatement(deleteProductSql);
                stm.setInt(1, product.getProductID());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    String nestSql = "DELETE FROM PRODUCT WHERE ID=?";
                    stm = con.prepareStatement(nestSql);
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
