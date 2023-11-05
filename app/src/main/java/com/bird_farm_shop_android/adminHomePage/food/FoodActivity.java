package com.bird_farm_shop_android.adminHomePage.food;

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
import com.bird_farm_shop_android.adminHomePage.bird.BirdActivity;
import com.bird_farm_shop_android.adminHomePage.nest.NestActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    FloatingActionButton BirdFab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView BirdRecyclerView;
    List<ProductDTO> dataList;
    SearchView BirdSearch;
    MyAdapter adapter;

    // Layout Navigation Bar
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, idFoodPage_admin, idBirdPage_admin, idNestPage_admin, logout;

    private void NavigationBar(){
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
                redirectActivity(FoodActivity.this, MainActivity.class);
            }
        });
        idFoodPage_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(FoodActivity.this, FoodActivity.class);
            }
        });

        idBirdPage_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(FoodActivity.this, BirdActivity.class);
            }
        });
        idNestPage_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(FoodActivity.this, NestActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FoodActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_admin_homepage);

        NavigationBar();

        BirdRecyclerView = findViewById(R.id.BirdRecyclerView);
        BirdFab = findViewById(R.id.BirdFab);
        BirdSearch = findViewById(R.id.BirdSearch);
        BirdSearch.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(FoodActivity.this, 1);
        BirdRecyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout_admin_homepage);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(FoodActivity.this, dataList);
        BirdRecyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Food Product");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    ProductDTO dataClass = itemSnapshot.getValue(ProductDTO.class);
                    Log.w("Food Activity Key", itemSnapshot.getKey());
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        BirdFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodActivity.this, FoodUploadActivity.class);
                startActivity(intent);
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
        ArrayList<ProductDTO> searchList = new ArrayList<>();
        for (ProductDTO dataClass: dataList){
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