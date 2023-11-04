package com.bird_farm_shop_android.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.dao.Implements.FoodDAO;
import com.bird_farm_shop_android.dao.Interface.IFoodDAO;
import com.bumptech.glide.Glide;
import com.bird_farm_shop_android.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class FoodDetailActivity extends AppCompatActivity {
    TextView detailProductPrice, detailProductName, detailProductDecription;
    ImageView detailProductImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";
    private IFoodDAO foodDAO =  new FoodDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_admin);
        detailProductName = findViewById(R.id.detailProductNameAdmin);
        detailProductImage = findViewById(R.id.detailProductImageAdmin);
        detailProductDecription = findViewById(R.id.detailProductDecriptionAdmin);
        deleteButton = findViewById(R.id.Detail_deleteButtonAdmin);
        editButton = findViewById(R.id.Detail_editButtonAdmin);
        detailProductPrice = findViewById(R.id.detailProductPriceAdmin);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailProductDecription.setText(bundle.getString("ProductDescription"));
            detailProductName.setText(bundle.getString("ProductName"));
            detailProductPrice.setText(bundle.getString("ProductPrice"));
            key = bundle.getString("FoodKey");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailProductImage);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Food Product");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        foodDAO.deleteFoodByImageUrl(imageUrl);
                        Toast.makeText(FoodDetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), FoodActivity.class));
                        finish();
                    }
                });
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetailActivity.this, FoodUpdateActivity.class)
                        .putExtra("ProductName", detailProductName.getText().toString())
                        .putExtra("ProductDescription", detailProductDecription.getText().toString())
                        .putExtra("ProductPrice", detailProductPrice.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("FoodKey", key);
                startActivity(intent);
            }
        });
    }
}