package com.bird_farm_shop_android.adminHomePage.bird;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.dao.Implements.BirdDAO;
import com.bird_farm_shop_android.dao.Interface.IBirdDAO;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class BirdDetailActivity extends AppCompatActivity {
    TextView detailProductPriceAdmin, detailProductNameAdmin, detailProductDecriptionAdmin;
    ImageView detailProductImageAdmin;
    FloatingActionButton Detail_deleteButtonAdmin, Detail_editButtonAdmin;
    String key = "";
    String imageUrl = "";
    private IBirdDAO birdDAO = new BirdDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_admin_homepage);
        detailProductNameAdmin = findViewById(R.id.detailProductNameAdmin);
        detailProductImageAdmin = findViewById(R.id.detailProductImageAdmin);
        detailProductDecriptionAdmin = findViewById(R.id.detailProductDecriptionAdmin);
        Detail_deleteButtonAdmin = findViewById(R.id.Detail_deleteButtonAdmin);
        Detail_editButtonAdmin = findViewById(R.id.Detail_editButtonAdmin);
        detailProductPriceAdmin = findViewById(R.id.detailProductPriceAdmin);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailProductDecriptionAdmin.setText(bundle.getString("ProductDescription"));
            detailProductNameAdmin.setText(bundle.getString("ProductName"));
            detailProductPriceAdmin.setText(bundle.getString("ProductPrice"));
            key = bundle.getString("BirdKey");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailProductImageAdmin);
        }
        Detail_deleteButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bird Product");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        birdDAO.deleteBirdByImageUrl(imageUrl);
                        Toast.makeText(BirdDetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), BirdActivity.class));
                        finish();
                    }
                });
            }
        });
        Detail_editButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BirdDetailActivity.this, BirdUpdateActivity.class)
                        .putExtra("ProductName", detailProductNameAdmin.getText().toString())
                        .putExtra("ProductDescription", detailProductDecriptionAdmin.getText().toString())
                        .putExtra("ProductPrice", detailProductPriceAdmin.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("BirdKey", key);
                startActivity(intent);
            }
        });
    }
}