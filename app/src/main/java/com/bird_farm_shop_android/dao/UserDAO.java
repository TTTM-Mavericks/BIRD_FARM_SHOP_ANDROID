package com.bird_farm_shop_android.dao;

import android.util.Log;

import com.bird_farm_shop_android.DBUltils;
import com.bird_farm_shop_android.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public boolean createUser(User user) {
        Boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String sql = "INSERT INTO USER_INFORMATION (FULL_NAME, EMAIL, PHONE_NUMBER, PASSWORD, GENDER, ADDRESS, DATE_OF_BIRTH, ROLE_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, user.getFullName());
                stm.setString(2, user.getEmail());
                stm.setString(3, user.getPhoneNumber());
                stm.setString(4, user.getPassword());
                stm.setBoolean(5, user.getGender());
                stm.setString(6, user.getAddress());
                stm.setString(7, user.getDateOfBirth());
                stm.setInt(8, user.getRoleID());

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

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM USER_INFORMATION";
                PreparedStatement stm = con.prepareStatement(sql);

                ResultSet rs = stm.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        Integer ID = rs.getInt("ID");
                        String fullName = rs.getString("FULL_NAME");
                        String email = rs.getString("EMAIL");
                        String phoneNumber = rs.getString("PHONE_NUMBER");
                        String password = rs.getString("PASSWORD");
                        boolean gender = rs.getBoolean("GENDER");
                        String address = rs.getString("ADDRESS");
                        String dateOfBirth = rs.getString("DATE_OF_BIRTH");
                        int roleId = rs.getInt("ROLE_ID");

                        User user = new User(ID, fullName, email, phoneNumber, password, gender, address, dateOfBirth, roleId);
                        userList.add(user);
                    }
                    rs.close();
                }
                stm.close();
                con.close();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return userList;
        }
    }

    public User getUserByID(int ID) {
        User user = null;
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM USER_INFORMATION WHERE ID = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, ID);

                ResultSet rs = stm.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String fullName = rs.getString("FULL_NAME");
                        String email = rs.getString("EMAIL");
                        String phoneNumber = rs.getString("PHONE_NUMBER");
                        String password = rs.getString("PASSWORD");
                        boolean gender = rs.getBoolean("GENDER");
                        String address = rs.getString("ADDRESS");
                        String dateOfBirth = rs.getString("DATE_OF_BIRTH");
                        int roleId = rs.getInt("ROLE_ID");

                        user = new User(ID, fullName, email, phoneNumber, password, gender, address, dateOfBirth, roleId);
                    }
                    rs.close();
                }
                stm.close();
                con.close();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return user;
        }
    }

    public boolean updateUser(User user) {
        Boolean result = false;
        try {
            Connection con = DBUltils.getConnection();
            if (con != null) {
                String sql = "UPDATE USER_INFORMATION SET FULL_NAME=?, EMAIL=?, PHONE_NUMBER=?, PASSWORD=?, GENDER=?, ADDRESS=?, DATE_OF_BIRTH=?, ROLE_ID=? WHERE ID=?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, user.getFullName());
                stm.setString(2, user.getEmail());
                stm.setString(3, user.getPhoneNumber());
                stm.setString(4, user.getPassword());
                stm.setBoolean(5, user.getGender());
                stm.setString(6, user.getAddress());
                stm.setString(7, user.getDateOfBirth());
                stm.setInt(8, user.getRoleID());
                stm.setInt(9, user.getId());

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

    boolean deleteUser(User user) {
        Boolean result = false;
        try {
            result = deteleUserByID(user.getId());
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            return result;
        }
    }

    public boolean deteleUserByID(Integer ID) {
        Boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String sql = "DELETE FROM USER_INFORMATION WHERE ID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, ID);

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

    public boolean checkLogin(String email, String password) {
        Boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUltils.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM USER_INFORMATION WHERE EMAIL = ? AND PASSWORD = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);

                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
}
