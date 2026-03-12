package com.innerly.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper;
import com.innerly.app.utils.SessionManager;
import com.innerly.app.utils.PasswordUtils; // Import PasswordUtils

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final TextView tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // 1. Basic Input Validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Hash the input password (According to Guideline 4.1)
            // The password provided during login must be hashed to compare with the hash in the DB
            String hashedInput = PasswordUtils.hashPassword(password);

            if (hashedInput == null) {
                Toast.makeText(this, "Error processing login", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3. Check User in SQLite Database (Using Hashed Password)
            int userId = dbHelper.checkUser(email, hashedInput);

            if (userId != -1) {
                // If Login is successful, update session and go to MoodCheck
                sessionManager.loginUser(userId);
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MoodCheckActivity.class));
                finish();
            } else {
                // If credentials are invalid
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

        tvCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
