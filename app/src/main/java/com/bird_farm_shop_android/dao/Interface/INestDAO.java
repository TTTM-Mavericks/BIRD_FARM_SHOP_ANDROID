package com.bird_farm_shop_android.dao.Interface;

import com.bird_farm_shop_android.entities.Product;

import java.util.List;

public interface INestDAO {
    public List<Product> getAllNest();
    public Product getNestByID(Integer nestID);
    public boolean createNest(Product nest);
    public boolean updateNest(Product nest);
    public boolean deleteNest(Product nest);
    public boolean deleteNestByID(Integer nestID);
}
