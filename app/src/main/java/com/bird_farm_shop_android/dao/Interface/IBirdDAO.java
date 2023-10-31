package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.Product;

import java.util.List;

public interface IBirdDAO {
    public List<Product> getAllBird();
    public Product getBirdByID(Integer birdID);
    public boolean createBird(Product bird);
    public boolean updateBird(Product bird);
    public boolean deleteBird(Product bird);
    public boolean deleteBirdByID(Integer birdID);
}
