package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.Product;

import java.util.List;

public interface IFoodDAO {
    public List<Product> getAllFood();

    public Product getFoodByID(Integer foodID);
    public boolean createFood(Product food);
    public boolean updateFood(Product food);
    public boolean deleteFood(Product food);
    public boolean deleteFoodByID(Integer foodID);
}
