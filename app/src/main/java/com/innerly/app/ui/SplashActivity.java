package com.innerly.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.innerly.app.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // "btnStart" නමැති ImageButton එක සොයාගෙන එයට Click Listener එකක් ලබා දීම
        ImageButton btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            // Arrow button එක ක්ලික් කළ විට LoginActivity වෙත යොමු කිරීම
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            // Splash Screen එක වසා දැමීම
            finish();
        });
    }
}