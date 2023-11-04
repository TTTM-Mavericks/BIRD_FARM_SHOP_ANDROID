package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.Product;

import java.util.List;

public interface IBirdDAO {
    public Product getBirdByImageUrl(String imageUrl);
    public boolean createBird(Product bird);
    public boolean updateBirdByImageUrl(Product bird, String imageUrl);
    public boolean deleteBirdByImageUrl(String imageUrl);
}
