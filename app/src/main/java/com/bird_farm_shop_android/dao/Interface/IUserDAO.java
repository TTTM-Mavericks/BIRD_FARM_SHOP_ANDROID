package com.bird_farm_shop_android.dao.Interface;


import com.bird_farm_shop_android.entities.User;

import java.util.List;

public interface IUserDAO {
    public boolean createUser(User user);

    public List<User> getAllUser();

    public User getUserByID(int ID);

    public User getUserByEmail(String email);

    public boolean updateUser(User user);

    public boolean deleteUser(User user);

    public boolean deteleUserByID(Integer ID);

    public boolean checkLogin(String email, String password);
}
