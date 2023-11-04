package com.bird_farm_shop_android.bird;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.authenticate.RegisterActivity;
import com.bird_farm_shop_android.dao.Implements.BirdDAO;
import com.bird_farm_shop_android.dao.Interface.IBirdDAO;
import com.bird_farm_shop_android.entities.Product;
import com.bird_farm_shop_android.validation.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class BirdUploadActivity extends AppCompatActivity {
    ImageView uploadProductImage;
    Button saveButton;
    EditText uploadProductName, uploadProductDescription, uploadProductPrice;
    String imageURL;
    Uri uri;

    private IBirdDAO birdDAO = new BirdDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product_admin);
        uploadProductImage = findViewById(R.id.uploadProductImageAdmin);
        uploadProductDescription = findViewById(R.id.uploadProductDescriptionAdmin);
        uploadProductName = findViewById(R.id.uploadProductNameAdmin);
        uploadProductPrice = findViewById(R.id.uploadProductPriceAdmin);
        saveButton = findViewById(R.id.saveButton);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadProductImage.setImageURI(uri);
                        } else {
                            Toast.makeText(BirdUploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }
    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Bird Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(BirdUploadActivity.this);
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
                imageURL = urlImage.toString();
                if(imageURL.isEmpty()) {
                    Toast.makeText(BirdUploadActivity.this, "Product Image is required", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadData();
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
    public void uploadData(){
        // Create Product to store in Firebase
        String productName = uploadProductName.getText().toString();
        String productDescription = uploadProductDescription.getText().toString();
        String productPrice = uploadProductPrice.getText().toString();

        if(imageURL.isEmpty()) {
            Toast.makeText(BirdUploadActivity.this, "Product Image is required", Toast.LENGTH_SHORT).show();
        }
        else if(productName.isEmpty()) {
            Toast.makeText(BirdUploadActivity.this, "Product Name is required", Toast.LENGTH_SHORT).show();
            uploadProductName.setError("Product Name is required");
            uploadProductName.requestFocus();
        }
        else if(productDescription.isEmpty()) {
            Toast.makeText(BirdUploadActivity.this, "Product Description is required", Toast.LENGTH_SHORT).show();
            uploadProductDescription.setError("Product Description is required");
            uploadProductDescription.requestFocus();
        }
        else if(productPrice.isEmpty()) {
            Toast.makeText(BirdUploadActivity.this, "Product Price is required", Toast.LENGTH_SHORT).show();
            uploadProductPrice.setError("Product Price is required");
            uploadProductPrice.requestFocus();
        }
        else if(!Validation.checkValidNumber(productPrice))
        {
            Toast.makeText(BirdUploadActivity.this, "Product Price is type Double", Toast.LENGTH_SHORT).show();
            uploadProductPrice.setError("Product Price is type Double");
            uploadProductPrice.requestFocus();
        }
        else {
            ProductDTO dataClass = new ProductDTO(productName, productDescription, productPrice, imageURL);

            // Create Product to insert into SQL
            Float price = Float.parseFloat(productPrice);
            Random random = new Random();
            int quantity = random.nextInt(20) + 1;
            Product BirdProduct = new Product(productName, price, productDescription, quantity, imageURL);

            //We are changing the child from title to currentDate,
            // because we will be updating title as well and it may affect child value.
            String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            FirebaseDatabase.getInstance().getReference("Bird Product").child(currentDate)
                    .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                birdDAO.createBird(BirdProduct);
                                Toast.makeText(BirdUploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BirdUploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}