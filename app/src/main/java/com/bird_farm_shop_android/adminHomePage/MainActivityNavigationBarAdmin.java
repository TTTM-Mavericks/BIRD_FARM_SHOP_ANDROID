package com.bird_farm_shop_android.adminHomePage;

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

import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.adminHomePage.bird.BirdActivity;
import com.bird_farm_shop_android.adminHomePage.food.FoodActivity;
import com.bird_farm_shop_android.adminHomePage.nest.NestActivity;

public class MainActivityNavigationBarAdmin extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, idFoodPage_admin, idBirdPage_admin, idNestPage_admin, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation_admin_homepage);

        drawerLayout = findViewById(R.id.drawerLayoutAdmin);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        idFoodPage_admin = findViewById(R.id.idFoodPage_admin);
        idBirdPage_admin = findViewById(R.id.idBirdPage_admin);
        idNestPage_admin = findViewById(R.id.idNestPage_admin);
        logout = findViewById(R.id.logout);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        idFoodPage_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivityNavigationBarAdmin.this, FoodActivity.class);
            }
        });
        idBirdPage_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivityNavigationBarAdmin.this, BirdActivity.class);
            }
        });
        idNestPage_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivityNavigationBarAdmin.this, NestActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivityNavigationBarAdmin.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });

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