package com.bird_farm_shop_android.nest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.bird.BirdUpdateActivity;
import com.bird_farm_shop_android.dao.Implements.NestDAO;
import com.bird_farm_shop_android.dao.Interface.INestDAO;
import com.bird_farm_shop_android.entities.Product;
import com.bird_farm_shop_android.validation.Validation;
import com.bumptech.glide.Glide;
import com.bird_farm_shop_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class NestUpdateActivity extends AppCompatActivity {
    ImageView updateImage;
    Button updateButton;
    EditText updateProductName, updateProductDescription, updateProductPrice;
    String productName, productDescription, productPrice;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private INestDAO nestDAO = new NestDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        updateButton = findViewById(R.id.updateButton);
        updateProductName = findViewById(R.id.updateProductName);
        updateImage = findViewById(R.id.updateImage);
        updateProductDescription = findViewById(R.id.updateProductDescription);
        updateProductPrice = findViewById(R.id.updateProductPrice);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(NestUpdateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(NestUpdateActivity.this).load(bundle.getString("Image")).into(updateImage);
            updateProductName.setText(bundle.getString("ProductName"));
            updateProductDescription.setText(bundle.getString("ProductDescription"));
            updateProductPrice.setText(bundle.getString("ProductPrice"));
            key = bundle.getString("NestKey");
            oldImageURL = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Nest Product").child(key);
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

            }
        });
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Nest Images").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(NestUpdateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout_admin);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                if(imageUrl.isEmpty()) {
                    Toast.makeText(NestUpdateActivity.this, "Product Image is required", Toast.LENGTH_SHORT).show();
                }
               else {
                    updateData();
                }
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        productName = updateProductName.getText().toString().trim();
        productDescription = updateProductDescription.getText().toString().trim();
        productPrice = updateProductPrice.getText().toString();
        ProductDTO dataClass = new ProductDTO(productName, productDescription, productPrice, imageUrl);

        if(imageUrl.isEmpty()) {
            Toast.makeText(NestUpdateActivity.this, "Product Image is required", Toast.LENGTH_SHORT).show();
        }
        else if(productName.isEmpty()) {
            Toast.makeText(NestUpdateActivity.this, "Product Name is required", Toast.LENGTH_SHORT).show();
            updateProductName.setError("Product Name is required");
            updateProductName.requestFocus();
        }
        else if(productDescription.isEmpty()) {
            Toast.makeText(NestUpdateActivity.this, "Product Description is required", Toast.LENGTH_SHORT).show();
            updateProductDescription.setError("Product Description is required");
            updateProductDescription.requestFocus();
        }
        else if(productPrice.isEmpty()) {
            Toast.makeText(NestUpdateActivity.this, "Product Price is required", Toast.LENGTH_SHORT).show();
            updateProductPrice.setError("Product Price is required");
            updateProductPrice.requestFocus();
        }
        else if(!Validation.checkValidNumber(productPrice))
        {
            Toast.makeText(NestUpdateActivity.this, "Product Price is type Double", Toast.LENGTH_SHORT).show();
            updateProductPrice.setError("Product Price is type Double");
            updateProductPrice.requestFocus();
        }
        else {
            // Create Product to insert into SQL
            Float price = Float.parseFloat(productPrice);
            Random random = new Random();
            int quantity = random.nextInt(20) + 1;
            Product FoodProduct = new Product(productName, price, productDescription, quantity, imageUrl);
            databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                        nestDAO.updateNestByImageUrl(FoodProduct, oldImageURL);
                        reference.delete();
                        Intent intent = new Intent(NestUpdateActivity.this, NestActivity.class);
                        startActivity(intent);
                        Toast.makeText(NestUpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NestUpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}