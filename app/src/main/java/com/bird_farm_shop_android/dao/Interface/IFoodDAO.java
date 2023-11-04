package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.Product;

import java.util.List;

public interface IFoodDAO {

    public Product getFoodByImageUrl(String imageUrl);
    public boolean createFood(Product food);
    public boolean updateFoodByImageUrl(Product food, String imageUrl);
    public boolean deleteFoodByImageUrl(String imageUrl);
}
