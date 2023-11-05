package com.bird_farm_shop_android.customerHomePage.bird;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.dao.Implements.BirdDAO;
import com.bird_farm_shop_android.dao.Interface.IBirdDAO;
import com.bumptech.glide.Glide;

public class BirdDetailActivityCustomer extends AppCompatActivity {
    TextView detailProductDecriptionCustomer, detailProductNameCustomer, detailProductPriceCustomer;
    ImageView detailProductImageCustomer;
    String key = "";
    String imageUrl = "";
    private IBirdDAO birdDAO = new BirdDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer_homepage);
        detailProductNameCustomer = findViewById(R.id.detailProductNameCustomer);
        detailProductImageCustomer = findViewById(R.id.detailProductImageCustomer);
        detailProductPriceCustomer = findViewById(R.id.detailProductPriceCustomer);
        detailProductDecriptionCustomer = findViewById(R.id.detailProductDecriptionCustomer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailProductPriceCustomer.setText(bundle.getString("ProductDescription"));
            detailProductNameCustomer.setText(bundle.getString("ProductName"));
            detailProductDecriptionCustomer.setText(bundle.getString("ProductPrice"));
            key = bundle.getString("BirdKey");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailProductImageCustomer);
        }
    }
}