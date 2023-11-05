package com.bird_farm_shop_android.customerHomePage.food;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.dao.Implements.FoodDAO;
import com.bird_farm_shop_android.dao.Interface.IFoodDAO;
import com.bumptech.glide.Glide;

public class FoodDetailActivityCustomer extends AppCompatActivity {
    TextView detailProductDecriptionCustomer, detailProductPriceCustomer, detailProductNameCustomer;
    ImageView detailProductImageCustomer;
    String key = "";
    String imageUrl = "";
    private IFoodDAO foodDAO =  new FoodDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer_homepage);
        detailProductPriceCustomer = findViewById(R.id.detailProductPriceCustomer);
        detailProductImageCustomer = findViewById(R.id.detailProductImageCustomer);
        detailProductNameCustomer = findViewById(R.id.detailProductNameCustomer);
        detailProductDecriptionCustomer = findViewById(R.id.detailProductDecriptionCustomer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailProductNameCustomer.setText(bundle.getString("ProductDescription"));
            detailProductPriceCustomer.setText(bundle.getString("ProductName"));
            detailProductDecriptionCustomer.setText(bundle.getString("ProductPrice"));
            key = bundle.getString("FoodKey");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailProductImageCustomer);
        }
    }
}