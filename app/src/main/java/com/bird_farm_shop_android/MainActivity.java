package com.bird_farm_shop_android;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.dao.BirdDAO;
import com.bird_farm_shop_android.models.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BirdDAO birdDAO = BirdDAO.getInstance();
        List<Product> birdList = birdDAO.getAllBird();
        if (birdList != null) {
            for (Product bird : birdList) {
                Log.e("TAG", "onCreate: " + bird.toString());
            }
        }
    }
}