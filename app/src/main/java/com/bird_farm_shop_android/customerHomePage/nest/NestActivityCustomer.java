package com.bird_farm_shop_android.customerHomePage.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bird_farm_shop_android.MainActivity;
import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.customerHomePage.bird.BirdActivityCustomer;
import com.bird_farm_shop_android.customerHomePage.food.FoodActivityCustomer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NestActivityCustomer extends AppCompatActivity {

    FloatingActionButton BirdFab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView BirdRecyclerView;
    List<ProductDTOCustomer> dataList;
    SearchView BirdSearch;
    MyAdapterCustomer adapter;

    // Layout Navigation Bar
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
                redirectActivity(NestActivityCustomer.this, MainActivity.class);
            }
        });
        idBirdPage_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NestActivityCustomer.this, BirdActivityCustomer.class);
            }
        });

        idFoodPage_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NestActivityCustomer.this, FoodActivityCustomer.class);
            }
        });
        idNestPage_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NestActivityCustomer.this, NestActivityCustomer.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NestActivityCustomer.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_customer_homepage);

        NavigationBar();

        BirdRecyclerView = findViewById(R.id.BirdRecyclerViewCustomer);
        BirdSearch = findViewById(R.id.BirdSearchCustomer);
        BirdSearch.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(NestActivityCustomer.this, 1);
        BirdRecyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(NestActivityCustomer.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout_customer_homepage);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapterCustomer(NestActivityCustomer.this, dataList);
        BirdRecyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Nest Product");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    ProductDTOCustomer dataClass = itemSnapshot.getValue(ProductDTOCustomer.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                    Log.i("The key of the class", dataClass.getKey());
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        BirdSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }
    public void searchList(String text){
        ArrayList<ProductDTOCustomer> searchList = new ArrayList<>();
        for (ProductDTOCustomer dataClass: dataList){
            if (dataClass.getProductName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
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