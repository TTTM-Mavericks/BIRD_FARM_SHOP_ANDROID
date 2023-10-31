package com.bird_farm_shop_android;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.dao.Interface.IUserDAO;
import com.bird_farm_shop_android.dao.Implements.UserDAO;
import com.bird_farm_shop_android.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterDoB, editTextRegisterMobile,
            editTextRegisterPwd, editTextRegisterAddress;

    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private final Integer timeExpired = 60;
    private final IUserDAO userDAO = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

      //  getSupportActionBar().setTitle("Register");

        Toast.makeText(RegisterActivity.this, "You can register now.", Toast.LENGTH_LONG).show();

        editTextRegisterFullName = (EditText) findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDoB = findViewById(R.id.editText_register_dob);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
        editTextRegisterAddress = findViewById(R.id.editText_register_Address);

        progressBar = findViewById(R.id.progressBar);
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        Button buttonRegister = findViewById(R.id.button_register_1);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textAddress = editTextRegisterAddress.getText().toString();
                String textGender = radioGroupRegisterGender.getCheckedRadioButtonId() + "";

                Log.d("RegisterActivity", textFullName);
                if(textFullName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Full name is required", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                }
                else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(RegisterActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(RegisterActivity.this, "Email is wrong", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Email is wrong");
                    editTextRegisterEmail.requestFocus();
                }
                else if(userDAO.getUserByEmail(textEmail) != null)
                {
                    Toast.makeText(RegisterActivity.this, "Email is already existed", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Email is already existed");
                    editTextRegisterEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(textDoB)){
                    Toast.makeText(RegisterActivity.this, "Date of birth is required", Toast.LENGTH_SHORT).show();
                    editTextRegisterDoB.setError("Date of birth is required");
                    editTextRegisterDoB.requestFocus();
                }
                else if(TextUtils.isEmpty(textMobile)){
                    Toast.makeText(RegisterActivity.this, "Phone is required", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Phone is required");
                    editTextRegisterMobile.requestFocus();
                }
                else if(TextUtils.isEmpty(textPwd)){
                    Toast.makeText(RegisterActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password is required");
                    editTextRegisterPwd.requestFocus();
                }
                else if(textPwd.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password is more than 6 characters", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password is more than 6 characters");
                    editTextRegisterPwd.requestFocus();
                }
                else if(radioGroupRegisterGender.getCheckedRadioButtonId() == -1){
                    Toast.makeText(RegisterActivity.this, "Gender is required", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                }
                else if(textMobile.length() != 10){
                    Toast.makeText(RegisterActivity.this, "Phone is 10 numbers", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Phone is 10 numbers");
                    editTextRegisterMobile.requestFocus();
                }
                else if(textAddress.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Address is required", Toast.LENGTH_SHORT).show();
                    editTextRegisterAddress.setError("Address is required");
                    editTextRegisterAddress.requestFocus();
                }
                else if(textGender.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Gender is required", Toast.LENGTH_SHORT).show();
                }
                else {
//                    progressBar.setVisibility((View.VISIBLE));
                    registerUser (textFullName, textEmail, textDoB, textGender, textMobile, textPwd, textAddress);

                }
            }
        });
    }

    private User convertToUser(String textFullName, String textEmail, String textDoB, String textGender,
                               String textPhoneNumber, String textPassword, String textAddress)
    {
        String fullName = textFullName;
        String email = textEmail;
        String phoneNumber = textPhoneNumber;
        String password = textPassword;
        Boolean gender = textGender.toUpperCase().equals("male".toUpperCase()) ? true : false;
        String address = textAddress;

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = null;
        try {
            Date date = inputFormat.parse(textDoB);
            formattedDate = outputFormat.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new User(fullName, email, phoneNumber, password, gender, address, formattedDate, 2);
    }

    private void registerUser(String textFullName, String textEmail, String textDoB, String textGender,
                              String textPhoneNumber, String textPassword, String textAddress) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.VISIBLE);

                            FirebaseUser firebaseUser = auth.getCurrentUser();


                            firebaseUser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(),
                                                        "Verification email sent. Please check verify.",
                                                        Toast.LENGTH_SHORT).show();

                                                int countTime = 0;
                                                while (!firebaseUser.isEmailVerified() && countTime < timeExpired)
                                                {
                                                    try {
                                                        Thread.sleep(1000);
                                                    } catch (InterruptedException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    countTime++;
                                                    firebaseUser.reload();
                                                }
                                                if(firebaseUser.isEmailVerified())
                                                {
                                                    //Enter User Data into the FireBase Realtime
//                                                    ReadWriteUserDetail writeUserDetail = new ReadWriteUserDetail(textDoB, textFullName, textGender, textMobile);
                                                    User user = convertToUser(
                                                            textFullName, textEmail, textDoB, textGender, textPhoneNumber, textPassword, textAddress
                                                    );
                                                    Log.w("User Register Info", "User Information: " + user.toString());

                                                    userDAO.createUser(user);
                                                    User registerUser = userDAO.getUserByEmail(textEmail);
                                                    //Extracking user from DB
                                                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Register Users");
                                                    referenceProfile.child(firebaseUser.getUid()).setValue(registerUser);

                                                    Toast.makeText(RegisterActivity.this, "User Created successfully. Please check verify email", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    Log.w("Error Register Activity", "User Created faild.");
                                                    Toast.makeText(RegisterActivity.this, "User Created faild.", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    firebaseUser.delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Log.d("User Deletion", "User account deleted.");
                                                                    } else {
                                                                        Log.w("User Deletion", "User account deletion failed.");
                                                                    }
                                                                }
                                                    });
                                                }
                                            }
                                            else {
                                                Log.e(TAG, "sendEmailVerification", task.getException());
                                                Toast.makeText(getApplicationContext(),
                                                        "Failed to send verification email.",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        }

                            });
                        }
                        else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                editTextRegisterPwd.setError("Your password is too weak, Kindly use a mix of alphabets, number and special characters");
                                editTextRegisterPwd.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                editTextRegisterEmail.setError("Your email is invalid or already exits");
                                editTextRegisterEmail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                editTextRegisterEmail.setError("User is already exits");
                                editTextRegisterEmail.requestFocus();
                            } catch (Exception e){
                                Log.e("RegisterActivity", e.getMessage());
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    }
                });
    }
}
