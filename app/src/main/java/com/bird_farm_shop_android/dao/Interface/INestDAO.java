package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.Product;

import java.util.List;

public interface INestDAO {
    public Product getNestByImageUrl(String imageUrl);
    public boolean createNest(Product nest);
    public boolean updateNestByImageUrl(Product nest, String imageUrl);
    public boolean deleteNestByImageUrl(String imageUrl);
}
