package com.bird_farm_shop_android.authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.R;
import com.bird_farm_shop_android.dao.Implements.UserDAO;
import com.bird_farm_shop_android.dao.Interface.IUserDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFirstName, editTextLastName;
    Button btnLogin, back;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textViewRegister;

    private final IUserDAO userDAO = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        editTextEmail = findViewById(R.id.editText_login_email);
        editTextPassword = findViewById(R.id.editText_login_pwd);
        btnLogin = findViewById(R.id.button_login);
        textViewRegister = findViewById(R.id.textView_register_link);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Email is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Password is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if(userDAO.checkLogin(email, password))
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful())
                                {
//                                     Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Login successfully!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}