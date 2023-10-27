package com.bird_farm_shop_android.dao;

import android.util.Log;

import com.bird_farm_shop_android.DBUltils;
import com.bird_farm_shop_android.models.Image;
import com.bird_farm_shop_android.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BirdDAO {

    private static final BirdDAO instance = new BirdDAO();

    public static BirdDAO getInstance() {
        return instance;
    }

    public List<Product> getAllBird() {
        List<Product> birdList = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String productSql = "SELECT p.ID, p.PRODUCT_NAME, p.PRICE, p.DESCRIPTION, p.QUANTITY " +
                        "FROM PRODUCT p INNER JOIN BIRD b ON p.ID = b.ID";
                stm = con.prepareStatement(productSql);
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    Product p = extractProduct(rs);
                    p.setListImages(getImageListForProduct(con, p.getProductID()));
                    birdList.add(p);
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return birdList;
    }

    public Product getBirdByID(Integer birdID) {
        Connection con = null;
        PreparedStatement stm = null;
        Product bird = null;

        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String productSql = "SELECT p.ID, p.PRODUCT_NAME, p.PRICE, p.DESCRIPTION, p.QUANTITY " +
                        "FROM PRODUCT p INNER JOIN BIRD b ON p.ID = b.ID " +
                        "WHERE p.ID = ?";
                stm = con.prepareStatement(productSql);
                stm.setInt(1, birdID);
                ResultSet rs = stm.executeQuery();

                if (rs.next()) {
                    bird = extractProduct(rs);
                    bird.setListImages(getImageListForProduct(con, bird.getProductID()));
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return bird;
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

    public boolean createBird(Product bird) {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUltils.getConnection();
            if (con != null) {
                // Insert the product information into the PRODUCT table
                String productSql = "INSERT INTO PRODUCT (PRODUCT_NAME, PRICE, DESCRIPTION, QUANTITY) VALUES (?, ?, ?, ?)";
                stm = con.prepareStatement(productSql, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, bird.getProductName());
                stm.setFloat(2, bird.getPrice());
                stm.setString(3, bird.getDescription());
                stm.setInt(4, bird.getQuantity());

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

    public boolean updateBird(Product bird) {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String productSql = "UPDATE PRODUCT SET PRODUCT_NAME=?, PRICE=?, DESCRIPTION=?, QUANTITY=? WHERE ID=?";
                stm = con.prepareStatement(productSql);
                stm.setString(1, bird.getProductName());
                stm.setFloat(2, bird.getPrice());
                stm.setString(3, bird.getDescription());
                stm.setInt(4, bird.getQuantity());
                stm.setInt(5, bird.getProductID());

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

    public boolean deleteBird(Product bird) {
        return deleteBirdByID(bird.getProductID());
    }

    public boolean deleteBirdByID(Integer birdID) {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String deleteProductSql = "DELETE FROM BIRD WHERE ID=?";
                stm = con.prepareStatement(deleteProductSql);
                stm.setInt(1, birdID);

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    String birdSql = "DELETE FROM PRODUCT WHERE ID=?";
                    stm = con.prepareStatement(birdSql);
                    stm.setInt(1, birdID);
                    rowsAffected = stm.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            closeResources(con, stm);
        }

        return false;
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
