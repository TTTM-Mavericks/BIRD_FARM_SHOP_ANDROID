package com.bird_farm_shop_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.adminHomePage.MainActivityNavigationBarAdmin;
import com.bird_farm_shop_android.authenticate.LoginActivity;
import com.bird_farm_shop_android.authenticate.RegisterActivity;
import com.bird_farm_shop_android.customerHomePage.MainActivityNavigationBarCustomer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLogin = findViewById(R.id.button_login_main_activity);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonRegister = findViewById(R.id.button_register_main_activity);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button button_adminHomePage = findViewById(R.id.button_adminHomePage);
        button_adminHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityNavigationBarAdmin.class);
                startActivity(intent);
                finish();
            }
        });

        Button button_customerHomePage = findViewById(R.id.button_customerHomePage);
        button_customerHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityNavigationBarCustomer.class);
                startActivity(intent);
                finish();
            }
        });
    }
}