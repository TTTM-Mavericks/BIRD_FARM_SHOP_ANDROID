package com.bird_farm_shop_android.customerHomePage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bird_farm_shop_android.MainActivity;
import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.customerHomePage.bird.BirdActivityCustomer;
import com.bird_farm_shop_android.customerHomePage.food.FoodActivityCustomer;
import com.bird_farm_shop_android.customerHomePage.nest.NestActivityCustomer;

public class MainActivityNavigationBarCustomer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menuCustomer;
    LinearLayout homeCustomer, idFoodPage_Customer, idBirdPage_Customer, idNestPage_Customer, logout;
    private void NavigationBar(){
        drawerLayout = findViewById(R.id.drawerLayoutCustomer);
        menuCustomer = findViewById(R.id.menuCustomer);
        homeCustomer = findViewById(R.id.homeCustomer);
        idFoodPage_Customer = findViewById(R.id.idFoodPage_Customer);
        idBirdPage_Customer = findViewById(R.id.idBirdPage_Customer);
        idNestPage_Customer = findViewById(R.id.idNestPage_Customer);
        logout = findViewById(R.id.logout);

        menuCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        homeCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivityNavigationBarCustomer.this, MainActivity.class);
            }
        });
        idBirdPage_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivityNavigationBarCustomer.this, BirdActivityCustomer.class);
            }
        });

        idFoodPage_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivityNavigationBarCustomer.this, FoodActivityCustomer.class);
            }
        });
        idNestPage_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivityNavigationBarCustomer.this, NestActivityCustomer.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivityNavigationBarCustomer.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation_customer_homepage);

        NavigationBar();

    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}