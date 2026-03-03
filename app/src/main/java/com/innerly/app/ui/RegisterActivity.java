package com.innerly.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper;
import com.innerly.app.utils.SessionManager;
import com.innerly.app.utils.PasswordUtils; // Import PasswordUtils

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String rawPassword = etPassword.getText().toString().trim();

            // 1. Basic Input Validation
            if (email.isEmpty() || rawPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rawPassword.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Hash the Password (According to Guideline 4.1)
            String hashedPassword = PasswordUtils.hashPassword(rawPassword);

            if (hashedPassword == null) {
                Toast.makeText(this, "Error processing password", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3. Register user with Hashed Password
            boolean isInserted = dbHelper.registerUser(email, hashedPassword);

            if (isInserted) {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                // Automatically login after registration
                int userId = dbHelper.checkUser(email, hashedPassword);

                if (userId != -1) {
                    sessionManager.loginUser(userId);
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
                }
            } else {
                Toast.makeText(this, "Registration Failed! Email might already exist.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
