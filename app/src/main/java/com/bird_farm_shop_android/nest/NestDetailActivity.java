package com.bird_farm_shop_android.nest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.dao.Implements.NestDAO;
import com.bird_farm_shop_android.dao.Interface.INestDAO;
import com.bumptech.glide.Glide;
import com.bird_farm_shop_android.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class NestDetailActivity extends AppCompatActivity {
    TextView detailPrice, detailProductName, detailDecription;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";
    private INestDAO nestDAO = new NestDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_admin);
        detailProductName = findViewById(R.id.detailProductNameAdmin);
        detailImage = findViewById(R.id.detailProductImageAdmin);
        detailDecription = findViewById(R.id.detailProductDecriptionAdmin);
        deleteButton = findViewById(R.id.Detail_deleteButtonAdmin);
        editButton = findViewById(R.id.Detail_editButtonAdmin);
        detailPrice = findViewById(R.id.detailProductPriceAdmin);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDecription.setText(bundle.getString("ProductDescription"));
            detailProductName.setText(bundle.getString("ProductName"));
            detailPrice.setText(bundle.getString("ProductPrice"));
            key = bundle.getString("NestKey");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Nest Product");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        nestDAO.deleteNestByImageUrl(imageUrl);
                        Toast.makeText(NestDetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NestActivity.class));
                        finish();
                    }
                });
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NestDetailActivity.this, NestUpdateActivity.class)
                        .putExtra("ProductName", detailProductName.getText().toString())
                        .putExtra("ProductDescription", detailDecription.getText().toString())
                        .putExtra("ProductPrice", detailPrice.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("NestKey", key);
                startActivity(intent);
            }
        });
    }
}